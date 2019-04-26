package com.liszt.wesee_server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liszt.wesee_server.bean.Result;
import com.liszt.wesee_server.bean.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/setting")
public class SettingController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @RequestMapping("/getSetting")
    public String getSetting(String uid, HttpServletRequest request)throws JsonProcessingException {
        Result<Setting> result = new Result<>();
        result.setMsg("success");
        HttpSession session = request.getSession(false);
        if(session!=null&session.getAttribute("uid")!=null&&session.getAttribute("uid").equals(uid)) {
            List<Setting> settingList = jdbcTemplate.query("SELECT * FROM setting WHERE id=?",new Object[]{uid},new BeanPropertyRowMapper<>(Setting.class));
            if(settingList!=null &&settingList.size()>0){
                result.setCode(1);
                result.setData(settingList.get(0));
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
    @RequestMapping("/changeSetting")
    public String changeSetting(String uid, String recommendedAccept,String max,String far, HttpServletRequest request)throws JsonProcessingException {
        Result<Setting> result = new Result<>();
        result.setMsg("success");
        HttpSession session = request.getSession(false);
        if(session!=null&session.getAttribute("uid")!=null&&session.getAttribute("uid").equals(uid)) {
            int maxInt = Integer.parseInt(max);
            int farInt = Integer.parseInt(far);
             int succeed =jdbcTemplate.update("UPDATE setting SET recommendedAccept=?,max=?,far=? WHERE id=?",new Object[]{recommendedAccept,maxInt,farInt,uid});
             if(succeed==1){
                 result.setCode(1);
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


}
