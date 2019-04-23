package com.liszt.wesee_server.bean;

public class Nearby {
    private String uid2;
    private String longitude;
    private String latitude;
    private String username;
    private String nickname;
    public void setUid2(String uid2) {
        this.uid2 = uid2;
    }

    public String getUid2() {
        return uid2;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getNickname() {
        return nickname;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
