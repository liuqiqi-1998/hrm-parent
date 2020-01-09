package cn.itsource.hrm.service.impl;

import cn.itsource.basic.util.StrUtils;
import cn.itsource.hrm.client.RedisClient;
import cn.itsource.hrm.service.ISMSService;
import cn.itsource.hrm.util.SMSUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SMSServiceImpl implements ISMSService {
    @Autowired
    private RedisClient redisClient;
    @Override
    public void sendSMS(String phone) {
        //当前验证是否有效
        String key = "SMSCODE:"+phone+":REG";
        String value = redisClient.get(key);
        String code = null;
        //判断图像验证是否有效i
        if(StringUtils.isNotEmpty(value)){
            String[] split = value.split(",");
            code = split[0];
            Long time = Long.valueOf(split[1]);
            //判断是否过了重发时间（30s）
            if(System.currentTimeMillis()-time >0){
                //过了重发时间 可以重新发送,设置重发时间是当前时间+30s，设置value = 验证码+重发时间
                time = System.currentTimeMillis()+30*1000;
                value = code +","+ time;
            }else {
                //没有过重发时间---是恶意访问
                throw new  RuntimeException("想撒呢？！");
            }
        }else {
            //无效了就再生成一个验证码
            code = StrUtils.getRandomString(6);
            value =code +","+ (System.currentTimeMillis()+30*1000);
        }
        //存储到redis
        redisClient.set(key,value,3*60);
        //调用短信发送的接口
        SMSUtils.send(phone,code);

    }
}
