package com.liszt.wesee_server.bean;

public class User {

    private String id;
    private String username;
    private String nickname;
    private String mobile;
    private String password;
    private String token;


    public String getId(){return id; }
    public String getMobile(){return mobile;}
    public String getNickname() {
        return nickname;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public  void setMobile(String  mobile){this.mobile=mobile;}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
