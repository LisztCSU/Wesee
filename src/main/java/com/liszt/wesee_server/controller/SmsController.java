package com.liszt.wesee_server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liszt.wesee_server.bean.Result;
import com.liszt.wesee_server.bean.User;
import com.liszt.wesee_server.datahelper.RandomString;
import com.liszt.wesee_server.sms.v1.SmsSDK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/sms")
public class SmsController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @RequestMapping("/sendVcode_register")
    public String sendVcode(String mobile, HttpServletRequest request) throws JsonProcessingException {
        Result<User> result = new Result<>();
        result.setMsg(("success"));
        if (Pattern.matches("^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$", mobile)) {
            List<User> users = jdbcTemplate.query("select * from user WHERE mobile=?", new Object[]{mobile}, new BeanPropertyRowMapper(User.class));
            // System.out.println("wsnd:"+MD5encrypt.getMd5(AESencrypt.AESDncode(password,AESencrypt.ENCODERULES)));
            if (users != null && users.size() > 0) {
                result.setCode(2);
            } else {
                result.setCode(1);
                String vcode = RandomString.randomString(6);
                System.out.println("nmsl:" + vcode);
                HttpSession session = request.getSession(true);
                session.setMaxInactiveInterval(15 * 60);
                session.setAttribute("vcode", vcode);
                session.setAttribute("mobile",mobile);
                SmsSDK.SendSms(mobile,vcode);
            }
        } else {
            result.setCode(0);
        }
        return new ObjectMapper().writeValueAsString(result);

    }

    @RequestMapping("/sendVcode_confirm")
    public String sendVcode2(String uid, String mobile, HttpServletRequest request) throws JsonProcessingException {
        Result<User> result = new Result<>();
        result.setMsg(("success"));
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("uid") != null && session.getAttribute("uid").equals(uid)) {
            if (Pattern.matches("^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$", mobile)) {
                List<User> users = jdbcTemplate.query("select * from user WHERE id=?", new Object[]{uid}, new BeanPropertyRowMapper(User.class));
                // System.out.println("wsnd:"+MD5encrypt.getMd5(AESencrypt.AESDncode(password,AESencrypt.ENCODERULES)));
                if (users != null && users.size() > 0) {
                    User user = users.get(0);
                    if (user.getMobile().equals(mobile)) {
                        result.setCode(1);
                        String vcode = RandomString.randomString(6);
                        System.out.println("nmsl2:" + vcode);
                        session.setAttribute("vcode", vcode);
                         SmsSDK.SendSms(mobile,vcode);
                    } else {
                        result.setCode(2);
                    }
                }
            } else {
                result.setCode(0);
            }
        } else {
            result.setCode(-1);
        }
        return new ObjectMapper().writeValueAsString(result);

    }

    @RequestMapping("/sendVcode_bind")
    public String sendVcode3(String uid, String mobile, HttpServletRequest request) throws JsonProcessingException {
        Result<User> result = new Result<>();
        result.setMsg(("success"));
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("uid") != null && session.getAttribute("uid").equals(uid)) {

            if (Pattern.matches("^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$", mobile)) {
                List<User> users = jdbcTemplate.query("select * from user WHERE mobile=?", new Object[]{mobile}, new BeanPropertyRowMapper(User.class));
                // System.out.println("wsnd:"+MD5encrypt.getMd5(AESencrypt.AESDncode(password,AESencrypt.ENCODERULES)));
                if (users != null && users.size() > 0) {
                    result.setCode(2);
                } else {
                    result.setCode(1);
                    String vcode = RandomString.randomString(6);
                    System.out.println("nmsl:" + vcode);
                    session.setAttribute("mobile", mobile);
                    session.setAttribute("vcode", vcode);
                    SmsSDK.SendSms(mobile,vcode);
                }
            } else {
                result.setCode(0);
            }
        }
        else{
            result.setCode(-1);
        }
        return new ObjectMapper().writeValueAsString(result);

    }

}
