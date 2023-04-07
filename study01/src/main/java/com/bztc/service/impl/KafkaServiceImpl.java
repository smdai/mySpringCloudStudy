package com.bztc.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.bztc.domain.study.UserInfoStudy;
import com.bztc.dto.ResultDto;
import com.bztc.dto.study.UserInfoStudyDto;
import com.bztc.service.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author daism
 * @create 2023-04-06 14:54
 * @description es和kafka操作实现类
 */
@Service
@Slf4j
public class KafkaServiceImpl implements KafkaService {
    @Autowired
    RestHighLevelClient client;

    /**
     * 直接删除es
     *
     * @param userInfo
     */
    @Override
    public void deleteEs(String indexName, UserInfoStudy userInfo) {
        try {
            //插入数据
            DeleteRequest request = new DeleteRequest(indexName, userInfo.getId());
            client.delete(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("删除es异常。", e);
        }
    }
    /**
     * 直接更新es
     *
     * @param userInfo
     */
    @Override
    public void updateEs(String indexName, UserInfoStudy userInfo) {
        try {
            //插入数据
            IndexRequest request = new IndexRequest(indexName);
            request.source(JSONUtil.toJsonStr(userInfo), XContentType.JSON).id(userInfo.getId());
            client.index(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("更新es异常。", e);
        }
    }
    /**
     * 直接插入es
     *
     * @param userInfo
     */
    @Override
    public void insertEs(String indexName, UserInfoStudy userInfo) {
        try {
            //插入时不用检查索引是否存在，不存在会自动创建
//            if(!checkExistIndex(indexName)){
//                //新建索引
//                CreateIndexRequest request = new CreateIndexRequest(indexName);
//                client.indices().create(request, RequestOptions.DEFAULT);
//            }
            //插入数据
            IndexRequest request = new IndexRequest(indexName);
            request.source(JSONUtil.toJsonStr(userInfo), XContentType.JSON);
            client.index(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("插入es异常。", e);
        }
    }

    /**
     * 分页查询
     *
     * @return
     */
    @Override
    public ResultDto<List<UserInfoStudy>> queryByPage(String indexName, UserInfoStudyDto userInfoStudyDto) {
        ResultDto<List<UserInfoStudy>> resultDto = new ResultDto<>();
        List<UserInfoStudy> list = new ArrayList<>();
        try {
            int pageIndex = userInfoStudyDto.getPageIndex();
            int pageSize = userInfoStudyDto.getPageSize();

            SearchRequest request = new SearchRequest(indexName);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            if(!StrUtil.isBlankIfStr(userInfoStudyDto.getName())){
                boolQueryBuilder.must(QueryBuilders.matchQuery("name",userInfoStudyDto.getName()));
            }
            if(!StrUtil.isBlankIfStr(userInfoStudyDto.getAddress())){
                boolQueryBuilder.should(QueryBuilders.matchQuery("address",userInfoStudyDto.getAddress()));
            }
            if(!StrUtil.isBlankIfStr(userInfoStudyDto.getAge())){
                boolQueryBuilder.must(QueryBuilders.matchQuery("age",userInfoStudyDto.getAge()));
            }
            if(!StrUtil.isBlankIfStr(userInfoStudyDto.getSex())){
                boolQueryBuilder.must(QueryBuilders.matchQuery("sex",userInfoStudyDto.getSex()));
            }
            if(CollectionUtil.isNotEmpty(userInfoStudyDto.getQueryInputTime())){
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("inputTime").gte(userInfoStudyDto.getQueryInputTime().get(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).lte(userInfoStudyDto.getQueryInputTime().get(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
            }
            searchSourceBuilder.query(boolQueryBuilder);
            request.source(searchSourceBuilder);
            request.source().from((pageIndex - 1) * pageSize).size(pageSize);
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHits hits = response.getHits();
            resultDto.setTotal(hits.getTotalHits().value);
            for (SearchHit hit : hits.getHits()) {
                UserInfoStudy userInfoStudy = JSONUtil.toBean(hit.getSourceAsString(), UserInfoStudy.class);
                userInfoStudy.setId(hit.getId());
                list.add(userInfoStudy);
            }
            resultDto.setData(list);
        } catch (Exception e) {
            log.error("es查询数据异常。", e);
        }
        return resultDto;
    }

    /**
     * 检查索引是否存在
     *
     * @param indexName
     * @return
     * @throws IOException
     */
    private boolean checkExistIndex(String indexName) throws IOException {
        GetIndexRequest request = new GetIndexRequest(indexName);
        return client.indices().exists(request, RequestOptions.DEFAULT);
    }
}
