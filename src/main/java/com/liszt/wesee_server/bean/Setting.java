package com.liszt.wesee_server.bean;

public class Setting {
    private String id;
    private String recommendedAccept;
    private String max;
    private String far;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFar() {
        return far;
    }

    public String getMax() {
        return max;
    }

    public String getRecommendedAccept() {
        return recommendedAccept;
    }

    public void setFar(String far) {
        this.far = far;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public void setRecommendedAccept(String recommendedAccept) {
        this.recommendedAccept = recommendedAccept;
    }
}
