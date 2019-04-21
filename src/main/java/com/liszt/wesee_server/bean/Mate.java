package com.liszt.wesee_server.bean;

public class Mate {
    private String id;
    private String uid;
    private String mid;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getMid() {
        return mid;
    }

    public String getUid() {
        return uid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
