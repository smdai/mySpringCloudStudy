package com.bztc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.domain.OpenAiInfo;
import com.bztc.service.OpenAiInfoService;
import com.bztc.mapper.OpenAiInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author daishuming
* @description 针对表【open_ai_info(OpenAI信息表)】的数据库操作Service实现
* @createDate 2023-12-01 15:35:11
*/
@Service
public class OpenAiInfoServiceImpl extends ServiceImpl<OpenAiInfoMapper, OpenAiInfo>
    implements OpenAiInfoService{

}




