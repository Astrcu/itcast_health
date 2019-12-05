package com.itheima.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public class SMSUtils {
    public static final String VALIDATE_CODE = "SMS_178761766";//发送短信验证码
    public static final String ORDER_NOTICE = "SMS_165106805";//体检预约成功通知
    /**
     * 发送短信
     * @param phoneNumbers
     * @param param
     * @throws ClientException
     */
    public static void sendShortMessage(String phoneNumbers,String param) throws ClientException {
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        // 初始化ascClient需要的几个参数
        final String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
        final String accessKeyId = "LTAI4FnZt7NLAbVj5goczzCb";// 你的accessKeyId,
        final String accessKeySecret = "ofGARZB6FiW4HJICb3uf1RnhngJ2Ur";// 你的accessKeySecret，
        // 初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        // 组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        // 使用post提交
        request.setMethod(MethodType.POST);
        // 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumbers(phoneNumbers);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName("传智不健康");
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(VALIDATE_CODE);
        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        // 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        if(param !=null || param.length() > 0){
            request.setTemplateParam("{\"code\":\""+param+"\"}");
        }
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        System.out.println("SendSms Result:"+sendSmsResponse.getMessage());
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            // 请求成功
            System.out.println("请求成功");
        }else{
            System.out.println("发送短信失败");
            System.out.println(sendSmsResponse.getCode());
            System.out.println(sendSmsResponse.getMessage());
        }
    }
    public static void main(String args[]) throws Exception{
        String validateCode = ValidateCodeUtils.generateValidateCode4String(4);
        System.out.println("validateCode:"+validateCode);
        SMSUtils.sendShortMessage("17701202562",validateCode);
    }
}