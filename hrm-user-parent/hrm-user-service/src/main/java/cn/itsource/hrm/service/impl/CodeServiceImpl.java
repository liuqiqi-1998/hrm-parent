package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.client.RedisClient;
import cn.itsource.hrm.service.ICodeService;
import cn.itsource.hrm.util.Base64Utils;
import cn.itsource.hrm.util.ImageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class CodeServiceImpl implements ICodeService {
    @Autowired
    private RedisClient redisClient;

    @Override
    public String genCode(String key) {
        //生成随机的字符串
        //int width, int height, int codeCount, int lineCount
        int width = 200; //图片宽度
        int height = 50; //图片高度
        int codeCount = 4; //随机数的个数
        int lineCount = 100; //干扰线
        ImageUtil imageUtil = new ImageUtil(width,height,codeCount,lineCount);
        //获取到随机字符串
        String code = imageUtil.getCode();
        //调用redis接口保存验证码  过期时间是3分钟
        redisClient.set(key,code,3*60);
        //生成图片
        ByteArrayOutputStream outputStream = null;
        try {
            //把图片写到ByteArrayOutputStream里
            outputStream = new ByteArrayOutputStream();
            imageUtil.write(outputStream);
            //进行64编码，传到前端的时候，前端会解码成图片
            byte[] bytes = outputStream.toByteArray();
            String encodeToString = Base64Utils.encodeToString(bytes);
            return encodeToString;

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public boolean validateCode(String key, String code) {
        String value = redisClient.get(key);
        if(StringUtils.isNotEmpty(code)){
            return code.equalsIgnoreCase(value);
        }
        return false;
    }
}
