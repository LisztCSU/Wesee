package com.liszt.wesee_server.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liszt.wesee_server.bean.Mate;
import com.liszt.wesee_server.bean.Result;
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
@RequestMapping("/mate")
public class MateController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @RequestMapping("/wantTosee")
    public String add(String uid, String mid, HttpServletRequest request) throws JsonProcessingException {
        Result<Mate> result = new Result<>();
        HttpSession session = request.getSession(false);
        if(session!=null&session.getAttribute("uid")!=null&&session.getAttribute("uid").equals(uid)){
            List<Mate> mates = jdbcTemplate.query("select * from mate where uid=? and mid=?",new Object[]{uid,mid},new BeanPropertyRowMapper<>(Mate.class));
            if(mates!=null&&mates.size()>0){
               int succeed = jdbcTemplate.update("UPDATE mate SET wanted =? WHERE id=?",new Object[]{"1",mates.get(0).getId()});
               if(succeed == 0){
                   result.setCode(0);
               }
                result.setCode(1);

            }
            else {
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
              int succeed = jdbcTemplate.update("INSERT mate VALUES (?,?,?,?)",new Object[]{uuid,uid,mid,"1"});
              if(succeed == 0){
                  result.setCode(0);
              }
                result.setCode(1);
            }


        }
        else {
            result.setCode(-1);
        }
        return new ObjectMapper().writeValueAsString(result);
    }
    @RequestMapping("/cancel")
    public String cancel(String uid, String mid, HttpServletRequest request) throws JsonProcessingException {
        Result<Mate> result = new Result<>();
        HttpSession session = request.getSession(false);
        if(session!=null&session.getAttribute("uid")!=null&&session.getAttribute("uid").equals(uid)){
            List<Mate> mates = jdbcTemplate.query("select * from mate where uid=? and mid=?",new Object[]{uid,mid},new BeanPropertyRowMapper<>(Mate.class));
            if(mates!=null&&mates.size()>0){
                int succeed = jdbcTemplate.update("UPDATE mate SET wanted =? WHERE id=?",new Object[]{"1", mates.get(0).getId()});
                if(succeed == 0){
                    result.setCode(0);
                }
                result.setCode(1);

            }
            else {
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                int succeed = jdbcTemplate.update("INSERT mate VALUES (?,?,?,?)",new Object[]{uuid,uid,mid,"0"});
                if(succeed == 0){
                    result.setCode(0);
                }
                result.setCode(1);
            }


        }
        else {
            result.setCode(-1);
        }
        return new ObjectMapper().writeValueAsString(result);
    }
}
