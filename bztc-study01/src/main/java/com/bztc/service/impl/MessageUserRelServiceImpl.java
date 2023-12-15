package com.bztc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.domain.MessageUserRel;
import com.bztc.service.MessageUserRelService;
import com.bztc.mapper.MessageUserRelMapper;
import org.springframework.stereotype.Service;

/**
* @author daishuming
* @description 针对表【message_user_rel(个人消息与用户关联表)】的数据库操作Service实现
* @createDate 2023-12-11 19:08:03
*/
@Service
public class MessageUserRelServiceImpl extends ServiceImpl<MessageUserRelMapper, MessageUserRel>
    implements MessageUserRelService{

}




