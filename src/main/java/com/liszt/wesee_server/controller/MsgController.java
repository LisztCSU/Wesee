package com.liszt.wesee_server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liszt.wesee_server.bean.Result;
import com.liszt.wesee_server.push.sendMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/msg")
@RestController
public class MsgController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private sendMsg send;

    @RequestMapping("/sendMsg")
    public String send(String uid, String id, String msg, String time) throws JsonProcessingException {
        Result<String> result = new Result();
        result.setMsg("success");
        result.setCode(0);
        List<String> objectId = jdbcTemplate.queryForList("SELECT uid FROM appointment WHERE id=?", String.class, id);
        List<String> objectId2 = jdbcTemplate.queryForList("SELECT uid2 FROM appointment WHERE id=?", String.class, id);


        if (uid.equals(objectId.get(0))) {
            List<String> nicknames = jdbcTemplate.queryForList("SELECT nickname FROM user WHERE id=?",String.class,uid);
            List<String> usernames =jdbcTemplate.queryForList("SELECT  username FROM user WHERE id=?",String.class,uid);

            try {
                send.send(objectId2.get(0),nicknames.get(0)+"@"+usernames.get(0)+"&&"+id+"&&"+time, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            List<String> nicknames = jdbcTemplate.queryForList("SELECT nickname FROM user WHERE id=?",String.class,objectId2.get(0));
            List<String> usernames =jdbcTemplate.queryForList("SELECT  username FROM user WHERE id=?",String.class,objectId2.get(0));

            try {
                send.send(objectId.get(0),nicknames.get(0)+"@"+usernames.get(0)+"&&"+id+"&&"+time, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        result.setCode(1);

        return new ObjectMapper().writeValueAsString(result);
    }
}



