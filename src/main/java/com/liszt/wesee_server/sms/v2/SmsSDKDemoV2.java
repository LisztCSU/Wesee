package com.liszt.wesee_server.sms.v2;

import java.util.ArrayList;
import java.util.List;

import com.liszt.wesee_server.sms.v2.yun.SmsSingleSender;
import com.liszt.wesee_server.sms.v2.yun.SmsSingleSenderResult;

public class SmsSDKDemoV2 {
    public static void main(String[] args) {
    	try {
    		//请根据实际 accesskey 和 secretkey 进行开发，以下只作为演示 sdk 使用
    		//请根据实际 accesskey 和 secretkey 进行开发，以下只作为演示 sdk 使用
    		String accesskey = "xxx";
    		String secretkey = "xxx";
    		
    		//type:0普通短信 1营销短信
    		int type=0;
    		//国家区号
    		String nationcode="86";
    		//手机号码
    		String phoneNumber = "xxx";
    		//短信签名ID (登录后台页面获取)
    		String signId="5aa7ef278475af0e19b05f5b";
    		//模板ID(登录后台页面获取)
    		String templateId="5a9599a56fcafe461546b953";
    		//短信模板的变量值 ，将短信模板中的变量{0},{1}替换为参数中的值，如果短信模板中没有变量，则这个值填null
    		List<String> params=new ArrayList<String>();
    		//模板中存在多个可变参数，可以添加对应的值。
    		params.add("362565");
    		//自定义字段，用户可以根据自己的需要来使用
    		String ext="";
    		
    		
    		 //初始化单发
	    	SmsSingleSender singleSender = new SmsSingleSender(accesskey, secretkey);
	    	SmsSingleSenderResult singleSenderResult;
	    	 //普通单发,注意前面必须为【】符号包含，置于头或者尾部。
	    	singleSenderResult = singleSender.send(type, nationcode, phoneNumber, signId, templateId, params, ext);
	    	System.out.println(singleSenderResult);
	    	
	    	
	    	//语音验证码发送
    		//SmsVoiceVerifyCodeSender smsVoiceVerifyCodeSender = new SmsVoiceVerifyCodeSender(accesskey,secretkey);
    		//SmsVoiceVerifyCodeSenderResult smsVoiceVerifyCodeSenderResult = smsVoiceVerifyCodeSender.send("86",phoneNumber, "444144",2,"");
    		//System.out.println(smsVoiceVerifyCodeSenderResult);

    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
