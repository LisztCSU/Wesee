package com.liszt.wesee_server.sms.v1;

import com.liszt.wesee_server.sms.v1.yun.SmsSingleSender;
import com.liszt.wesee_server.sms.v1.yun.SmsSingleSenderResult;
import com.liszt.wesee_server.sms.v1.yun.SmsVoiceVerifyCodeSender;
import com.liszt.wesee_server.sms.v1.yun.SmsVoiceVerifyCodeSenderResult;

/**
 * 发送短信
 */
public class SmsSDK {
	private static  final  String accesskey = "5ca5baca87b65f6374f21bc8";
	private  static  final String secretkey = "05c6d6ca154d4282a25752a4cb1b61b1";
    public static void SendSms(String moblie,String vcode) {
    	try {
    		//请根据实际 accesskey 和 secretkey 进行开发，以下只作为演示 sdk 使用
    		//请根据实际 accesskey 和 secretkey 进行开发，以下只作为演示 sdk 使用

    		int type=0;
    		 //初始化单发
	    	SmsSingleSender singleSender = new SmsSingleSender(accesskey, secretkey);

	    	 //普通单发,注意前面必须为【】符号包含，置于头或者尾部。
	    	 singleSender.send(type, "86", moblie, "【Kewail科技】您的验证码" + vcode + "工作人员不会索取，请勿泄漏。", "", "");

	    	
	    	//语音验证码发送
    		//SmsVoiceVerifyCodeSender smsVoiceVerifyCodeSender = new SmsVoiceVerifyCodeSender(accesskey,secretkey);
    		//SmsVoiceVerifyCodeSenderResult smsVoiceVerifyCodeSenderResult = smsVoiceVerifyCodeSender.send("86",phoneNumber, "369865",2,"");
    		//System.out.println(smsVoiceVerifyCodeSenderResult);

    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
