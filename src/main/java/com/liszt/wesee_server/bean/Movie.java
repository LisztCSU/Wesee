package com.liszt.wesee_server.bean;

public class Movie {
    private String id;
    private String title;
    private String score;
    private String star;
    private String duration;
    private String votecount;
    private String region;
    private String director;
    private String  actors;
    private String imgUrl;
    private String wantcount;
    public Movie(){
        super();
    }
    public Movie(String id,String title,String score,String star, String duration,String votecount,String region,String director,String actors,String imgUrl,String wantcount){
        this.id = id;
        this.title = title;
        this.score = score;
        this.star = star;
        this.duration = duration;
        this.votecount = votecount;
        this.region = region;
        this.director = director;
        this.actors = actors;
        this.imgUrl = imgUrl;
        this.wantcount = wantcount;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRegion() {
        return region;
    }

    public String getScore() {
        return score;
    }

    public String getStar() {
        return star;
    }

    public String getTitle() {
        return title;
    }


    public void setRegion(String region) {
        this.region = region;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getVotecount() {
        return votecount;
    }

    public void setVotecount(String votecount) {
        this.votecount = votecount;
    }

    public String getWantcount() {
        return wantcount;
    }

    public void setWantcount(String wantcount) {
        this.wantcount = wantcount;
    }
}

