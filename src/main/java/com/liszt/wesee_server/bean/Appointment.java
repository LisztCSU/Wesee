package com.liszt.wesee_server.bean;

public class Appointment {
    private String id;
    private String uid;
    private String uid2;
    private String mid;
    private String invited;
    private String accepted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid2() {
        return uid2;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setMid(String mid) {

    }

    public String getMid() {
        return mid;
    }

    public String getAccepted() {
        return accepted;
    }

    public String getInvited() {
        return invited;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }

    public void setInvited(String invited) {
        this.invited = invited;
    }

    public void setUid2(String uid2) {
        this.uid2 = uid2;
    }

}
