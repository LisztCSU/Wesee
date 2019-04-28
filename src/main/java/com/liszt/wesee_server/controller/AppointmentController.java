package com.liszt.wesee_server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liszt.wesee_server.bean.Appointment;
import com.liszt.wesee_server.bean.Result;
import com.liszt.wesee_server.push.pushByaccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
            List<Appointment> appointments = jdbcTemplate.query("SELECT * FROM appointment WHERE uid=? AND uid2=? AND mid=?",new Object[]{uid,uid2,mid}, new BeanPropertyRowMapper<>(Appointment.class));
            if(appointments!=null&&appointments.size()>0){
                result.setCode(0);
            }
            else {
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                int succeed = jdbcTemplate.update("INSERT appointment VALUES (?,?,?,?,?,?)",new Object[]{uuid,uid,uid2,mid,"1","0"});
                if(succeed==1){
                    try {
                      //  push.push(uid2,"收到来自xxx的邀请！");
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    result.setCode(1);
                }
            }
        } else {
            result.setCode(-1);
        }
        return new ObjectMapper().writeValueAsString(result);

    }
}
