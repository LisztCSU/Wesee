package com.liszt.wesee_server.bean;

public class Appointment {
    private String id;
    private String uid;
    private String uid2;
    private String mid;
    private String invited;
    private String agreed;
    private String inviteDate;
    private String agreeDate;



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
        this.mid = mid;

    }

    public String getMid() {
        return mid;
    }


    public String getInvited() {
        return invited;
    }

    public void setAgreed(String agreed) {
        this.agreed = agreed;
    }

    public void setInvited(String invited) {
        this.invited = invited;
    }

    public void setUid2(String uid2) {
        this.uid2 = uid2;
    }

    public String getAgreed() {
        return agreed;
    }

    public String getAgreeDate() {
        return agreeDate;
    }

    public String getInviteDate() {
        return inviteDate;
    }

    public void setAgreeDate(String agreeDate) {
        this.agreeDate = agreeDate;
    }

    public void setInviteDate(String inviteDate) {
        this.inviteDate = inviteDate;
    }


}
