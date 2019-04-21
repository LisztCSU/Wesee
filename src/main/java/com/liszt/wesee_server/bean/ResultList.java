package com.liszt.wesee_server.bean;

import java.util.List;

public class ResultList<T> {

    private int code;
    private String msg;
    private List<T> dataList;

    public void setCode(int code) {
        this.code = code;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<T>getDataList(){return dataList;}
}
