package com.liszt.wesee_server.push;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import com.aliyuncs.push.model.v20160801.PushRequest;
import com.aliyuncs.push.model.v20160801.PushResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "push")
public class sendMsg {
    private static String accessKeyId;
    private  static String accessKeySecret;
    private static long appKey;
    private static String regionId;
    private  static DefaultAcsClient client;

    public static String getRegion() {
        return regionId;
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
        this.regionId =regionId;
    }


    public void send(String account,String title, String body) throws Exception {
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        client = new DefaultAcsClient(profile);
//
        PushRequest pushRequest = new PushRequest();
        // 推送目标
        pushRequest.setAppKey(appKey);
        pushRequest.setTarget("ACCOUNT"); //推送目标: device:推送给设备; account:推送给指定帐号,tag:推送给自定义标签; all: 推送给全部
        pushRequest.setTargetValue(account);
        pushRequest.setPushType("MESSAGE"); // 消息类型 MESSAGE NOTICE
        pushRequest.setDeviceType("ANDROID"); // 设备类型 ANDROID iOS ALL.
        // 推送配置
        pushRequest.setTitle(title); // 消息的标题
        pushRequest.setBody(body); // 消息的内容
        // 推送配置: Android

        PushResponse pushResponse = client.getAcsResponse(pushRequest);
        System.out.printf("RequestId: %s, MessageID: %s\n",
                pushResponse.getRequestId(), pushResponse.getMessageId());

    }


}

