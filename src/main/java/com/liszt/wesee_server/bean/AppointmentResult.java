package com.liszt.wesee_server.bean;

public class AppointmentResult {
    private String id;
    private String objectname;
    private String moviename;
    private String time;
    private String initiative;
    public AppointmentResult(String id,String objectname,String moviename,String time,String initiative){
        this.id = id;
        this.objectname = objectname;
        this.moviename = moviename;
        this.time = time;
        this.initiative = initiative;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getObjectname() {
        return objectname;
    }

    public String getTime() {
        return time;
    }

    public void setObjectname(String objectname) {
        this.objectname = objectname;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInitiative() {
        return initiative;
    }

    public void setInitiative(String initiative) {
        this.initiative = initiative;
    }
}
