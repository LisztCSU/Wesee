package com.liszt.wesee_server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liszt.wesee_server.bean.Appointment;
import com.liszt.wesee_server.bean.AppointmentResult;
import com.liszt.wesee_server.bean.Result;
import com.liszt.wesee_server.bean.ResultList;
import com.liszt.wesee_server.push.pushByaccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private pushByaccount push;

    @RequestMapping("/invite")
    public String invite(String uid, String uid2, String mid, HttpServletRequest request)throws JsonProcessingException {
        Result<Appointment> result = new Result<>();
        HttpSession session = request.getSession(false);
        if (session != null & session.getAttribute("uid") != null && session.getAttribute("uid").equals(uid)) {
            List<Appointment> appointments2 = jdbcTemplate.query("SELECT * FROM appointment WHERE uid=? AND uid2=? AND mid=?",new Object[]{uid2,uid,mid}, new BeanPropertyRowMapper<>(Appointment.class));
            if(appointments2!=null&&appointments2.size()>0){
                result.setCode(2);
            }
            else {
                List<Appointment> appointments = jdbcTemplate.query("SELECT * FROM appointment WHERE uid=? AND uid2=? AND mid=?", new Object[]{uid, uid2, mid}, new BeanPropertyRowMapper<>(Appointment.class));
                if (appointments != null && appointments.size() > 0) {
                    result.setCode(0);
                } else {
                    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                    int succeed = jdbcTemplate.update("INSERT appointment VALUES (?,?,?,?,?,?,now(),?)", new Object[]{uuid, uid, uid2, mid, "1", "0", "0"});
                    if (succeed == 1) {
                        List<String> nickname = jdbcTemplate.queryForList("SELECT nickname FROM user WHERE id=?", String.class, uid);
                        try {
                            push.push(uid2, "新的邀请", "收到来自" + nickname.get(0) + "的邀请！");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        result.setCode(1);
                    }
                }
            }
        } else {
            result.setCode(-1);
        }
        return new ObjectMapper().writeValueAsString(result);

    }
    @RequestMapping("/cancel")
    public String invite(String id, HttpServletRequest request)throws JsonProcessingException {
        Result<String> result = new Result<>();
        result.setMsg("success");
        int succeed = jdbcTemplate.update("DELETE FROM appointment WHERE id=?",new Object[]{id});
        if(succeed == 1){
            result.setCode(1);
        }
        else result.setCode(0);

        return new ObjectMapper().writeValueAsString(result);

    }
    @RequestMapping("/getAppointmentList")
    public ResultList getAppointmentList(String uid){
        ResultList<AppointmentResult> resultList = new ResultList<>();
        resultList.setMsg("success");
            resultList.setCode(0);
        List<AppointmentResult> list = new ArrayList<>();
        List<Appointment> appointments = jdbcTemplate.query("SELECT * FROM appointment WHERE uid=? AND agreed=?",new Object[]{uid,1},new BeanPropertyRowMapper<>(Appointment.class));
        List<Appointment> appointments2 = jdbcTemplate.query("SELECT * FROM appointment WHERE uid2=? AND agreed=?",new Object[]{uid,1},new BeanPropertyRowMapper<>(Appointment.class));
        if(appointments!=null&&appointments.size()>0) {
            resultList.setCode(1);
            for (Appointment appointment : appointments) {
                List<String> nicknames = jdbcTemplate.queryForList("SELECT nickname FROM user WHERE id=?", String.class, appointment.getUid());
                List<String> usernames = jdbcTemplate.queryForList("SELECT  username FROM user WHERE id=?", String.class, appointment.getUid());
                List<String> nicknames2 = jdbcTemplate.queryForList("SELECT nickname FROM user WHERE id=?", String.class, appointment.getUid2());
                List<String> usernames2 = jdbcTemplate.queryForList("SELECT  username FROM user WHERE id=?", String.class, appointment.getUid2());
                List<String> movienames = jdbcTemplate.queryForList("SELECT title FROM movie WHERE id=?", String.class, appointment.getMid());
                list.add(new AppointmentResult(appointment.getId(), nicknames.get(0) + "@" + usernames.get(0)+"&&"+nicknames2.get(0) + "@" + usernames2.get(0), movienames.get(0), appointment.getAgreeDate(), "1"));
            }
        }
            if(appointments2!=null&&appointments2.size()>0){
                resultList.setCode(1);
                for (Appointment appointment:appointments2){
                    List<String> nicknames = jdbcTemplate.queryForList("SELECT nickname FROM user WHERE id=?",String.class,appointment.getUid());
                    List<String> usernames =jdbcTemplate.queryForList("SELECT  username FROM user WHERE id=?",String.class,appointment.getUid());
                    List<String> nicknames2 = jdbcTemplate.queryForList("SELECT nickname FROM user WHERE id=?", String.class, appointment.getUid2());
                    List<String> usernames2 = jdbcTemplate.queryForList("SELECT  username FROM user WHERE id=?", String.class, appointment.getUid2());

                    List<String> movienames = jdbcTemplate.queryForList("SELECT title FROM movie WHERE id=?",String.class,appointment.getMid());
                    list.add(new AppointmentResult(appointment.getId(),nicknames.get(0)+"@"+usernames.get(0)+"&&"+nicknames2.get(0) + "@" + usernames2.get(0),movienames.get(0),appointment.getAgreeDate(),"0"));
                }
                resultList.setDataList(list);
        }

        return resultList;
    }
    @RequestMapping("/getInviteList")
    public ResultList getInviteList(String uid){
        ResultList<AppointmentResult> resultList = new ResultList<>();
        List<AppointmentResult> list = new ArrayList<>();
        resultList.setMsg("success");
        List<Appointment> appointments = jdbcTemplate.query("SELECT * FROM appointment WHERE uid=? AND agreed<>?",new Object[]{uid,"1"},new BeanPropertyRowMapper<>(Appointment.class));
        if(appointments!=null&appointments.size()>0){
            resultList.setCode(1);
            for (Appointment appointment:appointments){
                List<String> nicknames2 = jdbcTemplate.queryForList("SELECT nickname FROM user WHERE id=?",String.class,appointment.getUid2());
                List<String> usernames2 =jdbcTemplate.queryForList("SELECT username name FROM user WHERE id=?",String.class,appointment.getUid2());
                List<String> movienames = jdbcTemplate.queryForList("SELECT title FROM movie WHERE id=?",String.class,appointment.getMid());
                list.add(new AppointmentResult(appointment.getId(),nicknames2.get(0)+"@"+usernames2.get(0).replaceAll("(\\d{3})", "$1∗∗∗"),movienames.get(0),getTime(appointment.getInviteDate()),"1"));
            }
            resultList.setDataList(list);
        }
        else {
            resultList.setCode(0);
        }
        return resultList;
    }
    @RequestMapping("/getReceiveList")
    public ResultList getReceiveList(String uid){
        ResultList<AppointmentResult> resultList = new ResultList<>();
        List<AppointmentResult> list = new ArrayList<>();
        resultList.setMsg("success");
        List<Appointment> appointments = jdbcTemplate.query("SELECT * FROM appointment WHERE uid2=? AND agreed=?",new Object[]{uid,"0"},new BeanPropertyRowMapper<>(Appointment.class));
        if(appointments!=null&appointments.size()>0){
            resultList.setCode(1);
            for (Appointment appointment:appointments){
                List<String> nicknames = jdbcTemplate.queryForList("SELECT nickname FROM user WHERE id=?",String.class,appointment.getUid());
                List<String> usernames =jdbcTemplate.queryForList("SELECT username name FROM user WHERE id=?",String.class,appointment.getUid());
                List<String> movienames = jdbcTemplate.queryForList("SELECT title FROM movie WHERE id=?",String.class,appointment.getMid());
                list.add(new AppointmentResult(appointment.getId(),nicknames.get(0)+"@"+usernames.get(0),movienames.get(0),getTime(appointment.getInviteDate()),"0"));
            }
            resultList.setDataList(list);
        }
        else {
            resultList.setCode(0);
        }
        return resultList;
    }



    @RequestMapping("/accept")
    public String accept( String id, HttpServletRequest request)throws JsonProcessingException {
        Result<Appointment> result = new Result<>();
//        HttpSession session = request.getSession(false);
//        if (session != null & session.getAttribute("uid") != null && session.getAttribute("uid").equals(uid)) {
            int succeed = jdbcTemplate.update("UPDATE appointment SET agreed=?,agreedate=now() WHERE id=?",new Object[]{"1",id});
            if (succeed == 1) {
                   result.setCode(1);
                    List<String> uid1 = jdbcTemplate.queryForList("SELECT uid FROM appointment WHERE id=?", String.class, id);
                    List<String> uid2 = jdbcTemplate.queryForList("SELECT uid2 FROM appointment WHERE id=?", String.class, id);
                    String send = uid1.get(0);

                    List<String> nickname = jdbcTemplate.queryForList("SELECT nickname FROM user WHERE id=?", String.class,uid2.get(0));
                    try {
                        push.push(send, "邀请成功","你邀请的用户" + nickname.get(0) + "已接受邀请");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
//            } else {
//                result.setCode(-1);
//            }
        return new ObjectMapper().writeValueAsString(result);
    }
    @RequestMapping("/reject")
    public String reject( String id, HttpServletRequest request)throws JsonProcessingException {
        Result<Appointment> result = new Result<>();
//        HttpSession session = request.getSession(false);
//        if (session != null & session.getAttribute("uid") != null && session.getAttribute("uid").equals(uid)) {
            int succeed = jdbcTemplate.update("UPDATE appointment SET agreed=? WHERE id=?",new Object[]{"-1",id});
            if (succeed == 1) {
                result.setCode(1);

            } else {
                result.setCode(0);
            }
//        }
//        else {
//            result.setCode(-1);
//        }
        return new ObjectMapper().writeValueAsString(result);
    }
    private String getTime(String date){
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);

        int month = c.get(Calendar.MONTH)+1;

        int day = c.get(Calendar.DATE);

        int hour = c.get(Calendar.HOUR_OF_DAY);

        int minute = c.get(Calendar.MINUTE);

        int second = c.get(Calendar.SECOND);

        String arr[] = date.split(" ");
        String arr2[] = arr[0].split("-");
        String arr3[] = arr[1].split(":");
        int year2 = Integer.parseInt(arr2[0]);
        int month2 = Integer.parseInt(arr2[1]);
        int day2 = Integer.parseInt(arr2[2]);
        int hour2 = Integer.parseInt(arr3[0]);
        int minute2 = Integer.parseInt(arr3[1]);
        int second2 = Integer.parseInt(arr3[2]);


        if(year>year2){
            return (year-year2)+"年前";
        }
        else if(month>month2)
        {
            return (month-month2)+"月前";
        }
        else if(day>day2){
            return (day-day2)+"天前";
        }
        else if(hour>hour2){
            return (hour-hour2)+"小时前";
        }
        else if(minute>minute2){
            return (minute-minute2)+"分钟前";
        }
        else {
            return (second-second2)+"秒前";
        }


    }
}
