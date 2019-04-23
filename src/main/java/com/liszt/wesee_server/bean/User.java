package com.liszt.wesee_server.bean;

public class User {

    private String id;
    private String username;
    private String mobile;
    private String password;
    private String nickname;
    private String isAuth;        ;



    public String getId(){return id; }
    public String getIsAuth(){return isAuth;}

    public String getMobile(){return mobile;}
    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public  void setIsAuth(String isAuth){this.isAuth = isAuth; }
    public  void setMobile(String  mobile){this.mobile=mobile;}

}
