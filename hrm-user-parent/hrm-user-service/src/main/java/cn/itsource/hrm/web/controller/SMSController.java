package cn.itsource.hrm.web.controller;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.hrm.service.ICodeService;
import cn.itsource.hrm.service.ISMSService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
public class SMSController {
    @Autowired
    private ISMSService smsService;
    @Autowired
    private ICodeService codeService;

    @GetMapping("/send")
    public AjaxResult send(String key,String code,String phone){
        //验证图形验证码
        boolean imageCode = codeService.validateCode(key,code);
        if(!imageCode){
            return AjaxResult.me().setSuccess(false).setMessage("验证码错误！");
        }

        try {
            //发送短信验证码
            if(StringUtils.isEmpty(phone)){
                return AjaxResult.me().setSuccess(false).setMessage("请输入正确的手机号！");
            }
            //验证手机号的正确
            //发短信
            smsService.sendSMS(phone);
            return AjaxResult.me().setMessage("发送成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("发送失败！！");
        }

    }
}
