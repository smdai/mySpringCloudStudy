package com.bztc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.domain.PersonalMessage;
import com.bztc.service.PersonalMessageService;
import com.bztc.mapper.PersonalMessageMapper;
import org.springframework.stereotype.Service;

/**
* @author daishuming
* @description 针对表【personal_message(个人消息表)】的数据库操作Service实现
* @createDate 2023-12-11 19:08:03
*/
@Service
public class PersonalMessageServiceImpl extends ServiceImpl<PersonalMessageMapper, PersonalMessage>
    implements PersonalMessageService{

}




