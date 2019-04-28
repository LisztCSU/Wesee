package com.liszt.wesee_server.push;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.push.model.v20160801.PushNoticeToAndroidRequest;
import com.aliyuncs.push.model.v20160801.PushNoticeToAndroidResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "push")
public class pushByaccount {
    private static String accessKeyId;
    private  static String accessKeySecret;
    private static long appKey;
    private static String region;
    private  static DefaultAcsClient client;

    public static String getRegion() {
        return region;
    }

    public static String getAccessKeyId() {
        return accessKeyId;
    }

    public static long getAppKey() {
        return appKey;
    }

    public static String getAccessKeySecret() {
        return accessKeySecret;
    }
    @Value("${push.accessKeyId}")
    public void setAccessKeyId(String accessKeyId){
        this.accessKeyId=accessKeyId;
    }
    @Value("${push.acessKeySecret}")
    public  void setAccessKeySecret(String accessKeySecret){
        this.accessKeySecret = accessKeySecret;
    }
    @Value("${push.appKey}")
    public void setAppKey(String appKey){
        this.appKey = Long.parseLong(appKey);
    }
    @Value("${push.regionId}")
    public void setRegion(String regionId){
        this.region =regionId;
    }


    public void push(String account, String body) throws Exception {
        IClientProfile profile = DefaultProfile.getProfile(region, accessKeyId, accessKeySecret);
        client = new DefaultAcsClient(profile);
        PushNoticeToAndroidRequest androidRequest = new PushNoticeToAndroidRequest();
        //安全性比较高的内容建议使用HTTPS
        androidRequest.setProtocol(ProtocolType.HTTPS);
        //内容较大的请求，使用POST请求
        androidRequest.setMethod(MethodType.POST);
        androidRequest.setAppKey(appKey);
        androidRequest.setTarget("ACCOUNT");
        androidRequest.setTargetValue(account);
        androidRequest.setTitle("尊敬的用户");
        androidRequest.setBody(body);
        androidRequest.setExtParameters("{\"k1\":\"v1\"}");
        PushNoticeToAndroidResponse pushNoticeToAndroidResponse = client.getAcsResponse(androidRequest);
        System.out.printf("RequestId: %s, MessageId: %s\n",
                pushNoticeToAndroidResponse.getRequestId(), pushNoticeToAndroidResponse.getMessageId());

    }


}
