package com.liszt.wesee_server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liszt.wesee_server.bean.Result;
import com.liszt.wesee_server.bean.User;
import com.liszt.wesee_server.datahelper.AESencrypt;
import com.liszt.wesee_server.datahelper.MD5encrypt;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/login")
    public String login(String account, String password, HttpServletRequest request) throws JsonProcessingException {
        Result<User> result = new Result<>();
        String dbpassword = MD5encrypt.getMd5(AESencrypt.AESDncode(password, AESencrypt.ENCODERULES));
        result.setMsg("success");
        List<User> users = jdbcTemplate.query("select * from user WHERE (username=? OR mobile=?) AND password=? ", new Object[]{account, account, dbpassword}, new BeanPropertyRowMapper(User.class));
        // System.out.println("wsnd:"+MD5encrypt.getMd5(AESencrypt.AESDncode(password,AESencrypt.ENCODERULES)));
        if (users != null && users.size() > 0) {
            result.setCode(1);
            User user = users.get(0);
            user.setIsAuth("1");
            jdbcTemplate.update("UPDATE  user SET isAuth=? WHERE id = ?", new Object[]{"1", user.getId(),});
            result.setData(user);
            HttpSession session = request.getSession(true);
            session.setAttribute("uid", user.getId());
            session.setMaxInactiveInterval(24 * 3600);
        } else {
            result.setCode(0);
        }
        return new ObjectMapper().writeValueAsString(result);
    }

    @RequestMapping("/register")
    public String register(String username, String password, String confirmPassword, String vcode, HttpServletRequest request) throws JsonProcessingException {
        Result<User> result = new Result<>();
        String dbpassword = MD5encrypt.getMd5(AESencrypt.AESDncode(password, AESencrypt.ENCODERULES));
        result.setMsg(("success"));
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("vcode") != null) {
            String mobile_server = session.getAttribute("mobile").toString();
            String vcode_server = session.getAttribute("vcode").toString();
            System.out.println("wsnd:" + mobile_server + " " + vcode_server);
            if (!vcode_server.equals(vcode)) {
                result.setCode(0);
            } else if (!password.equals(confirmPassword)) {
                result.setCode(1);
            } else {
                List<User> users = jdbcTemplate.query("select * from user WHERE username=?", new Object[]{username}, new BeanPropertyRowMapper(User.class));
                if (users != null && users.size() > 0) {
                    result.setCode(2);
                } else {
                    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                    int successed = jdbcTemplate.update("INSERT INTO user VALUES(?, ?, ?, ?, ?, now(), ?)", new Object[]{uuid, username, "新用户", mobile_server, dbpassword, "0"});
                    if (successed == 0) {
                        result.setCode(3);
                    } else {
                        result.setCode(4);
                    }
                }
            }
        } else {
            result.setCode(-1);
        }
        return new ObjectMapper().writeValueAsString(result);
    }

    @RequestMapping("/getUser")
    public String getUser(String uid, HttpServletRequest request) throws JsonProcessingException {
        Result<User> result = new Result<>();
        result.setMsg(("success"));
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("uid") != null && session.getAttribute("uid").equals(uid)) {
            List<User> users = jdbcTemplate.query("select * from user WHERE id=?", new Object[]{uid}, new BeanPropertyRowMapper(User.class));
            //System.out.println("wsnd:"+uid);
            if (users != null && users.size() > 0) {
                result.setCode(1);
                User user = users.get(0);
                user.setMobile(user.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1∗∗∗∗$2"));
                result.setData(user);
            } else {
                result.setCode(0);
            }
        } else {
            result.setCode(-1);
        }


        return new ObjectMapper().writeValueAsString(result);

    }




    @RequestMapping("/loginCancel")
    public String loginCacel(String uid, HttpServletRequest request) throws JsonProcessingException {
        Result<User> result = new Result<>();
        result.setMsg(("success"));
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("uid") != null && session.getAttribute("uid").equals(uid)) {
            int succeed = jdbcTemplate.update("UPDATE user SET isAuth=? WHERE id=?", new Object[]{"0", uid,});
            if (succeed == 0) {
                result.setCode(0);
            } else {
                result.setCode(1);
            }
        } else {
            result.setCode(-1);
        }
        return new ObjectMapper().writeValueAsString(result);
    }
    @RequestMapping("/untiedMobile")
    public String utiedMobile(String uid, String vcode, HttpServletRequest request) throws JsonProcessingException{
        Result<User> result = new Result<>();
        result.setMsg("success");
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("uid") != null && session.getAttribute("uid").equals(uid)){
            String vcode_server = session.getAttribute("vcode").toString();
            if (vcode.equals(vcode_server)){
                int succeed = jdbcTemplate.update("UPDATE user SET mobile=? WHERE id=?", new Object[]{"",uid});
                if(succeed == 0){
                    result.setCode(2);
                }
                else {
                    result.setCode(1);
                }
            }
        }
        else {
            result.setCode(-1);
        }
        return new ObjectMapper().writeValueAsString(result);
    }
    @RequestMapping("/confirmVcode")
    public String confirmVcode(String uid, String vcode, HttpServletRequest request) throws JsonProcessingException{
        Result<User> result = new Result<>();
        result.setMsg("success");
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("uid") != null && session.getAttribute("uid").equals(uid)){
            String vcode_server = session.getAttribute("vcode").toString();
            if (vcode.equals(vcode_server)){
                    result.setCode(1);
                }
            }
        else {
            result.setCode(-1);
        }
        return new ObjectMapper().writeValueAsString(result);
    }
    @RequestMapping("/bindMobile")
    public String bindMobile(String uid, String vcode, HttpServletRequest request) throws JsonProcessingException{
        Result<User> result = new Result<>();
        result.setMsg("success");
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("uid") != null && session.getAttribute("uid").equals(uid)){
            String vcode_server = session.getAttribute("vcode").toString();
            String mobile_server = session.getAttribute("mobile").toString();
            if (vcode.equals(vcode_server)){
                int succeed = jdbcTemplate.update("UPDATE user SET mobile=? WHERE id=?", new Object[]{mobile_server,uid});
                if(succeed == 0){
                    result.setCode(2);
                }
                else {
                    result.setCode(1);
                }
            }
            else {
                result.setCode(0);
            }
        }
        else {
            result.setCode(-1);
        }
        return new ObjectMapper().writeValueAsString(result);
    }
    @RequestMapping("/changeNickname")
    public String changeNickname(String uid, String nickname, HttpServletRequest request) throws JsonProcessingException{
        Result<User> result = new Result<>();
        result.setMsg("success");
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("uid") != null && session.getAttribute("uid").equals(uid)){
                int succeed = jdbcTemplate.update("UPDATE user SET nickname=? WHERE id=?", new Object[]{nickname,uid});
                if(succeed == 0){
                    result.setCode(0);
                }
                else {
                    result.setCode(1);
                }

        }
        else {
            result.setCode(-1);
        }
        return new ObjectMapper().writeValueAsString(result);
    }
    @RequestMapping("/changePassword")
    public String changePassword(String uid, String oldPassword, String newPassword, String confirmPassword, HttpServletRequest request) throws JsonProcessingException{
        Result<User> result = new Result<>();
        result.setMsg("success");
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("uid") != null && session.getAttribute("uid").equals(uid)) {
            if (confirmPassword.equals(newPassword)) {
                String dbPassword = MD5encrypt.getMd5(AESencrypt.AESDncode(oldPassword, AESencrypt.ENCODERULES));
                String newDbPassword = MD5encrypt.getMd5(AESencrypt.AESDncode(newPassword, AESencrypt.ENCODERULES));
                List<User> users = jdbcTemplate.query("select * from user WHERE password=? AND id=?", new Object[]{dbPassword, uid}, new BeanPropertyRowMapper(User.class));
                if (users != null && users.size() > 0) {
                    int succeed = jdbcTemplate.update("UPDATE user SET password=? WHERE id=?", new Object[]{newDbPassword, uid});
                    if (succeed == 0) {
                        result.setCode(0);
                    } else {
                        result.setCode(1);
                    }
                }
                else {
                    result.setCode(2);
                }
            } else {
                result.setCode(3);
            }
        }

        else {
            result.setCode(-1);
        }
        return new ObjectMapper().writeValueAsString(result);
    }

}

