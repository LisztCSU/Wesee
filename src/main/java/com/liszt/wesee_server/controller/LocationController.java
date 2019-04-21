package com.liszt.wesee_server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liszt.wesee_server.bean.Location;
import com.liszt.wesee_server.bean.Result;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping("/location")
public class LocationController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @RequestMapping("/add")
    public String add(String uid,String longitude ,String latitude, HttpServletRequest request) throws JsonProcessingException {
        Result<Location> result = new Result<>();
        result.setMsg(("success"));
       HttpSession session = request.getSession(false);
       if(session != null && session.getAttribute("uid")!= null && session.getAttribute("uid").equals(uid)) {
           List<Location> locations = jdbcTemplate.query("select * from user_location WHERE id=?", new Object[]{uid}, new BeanPropertyRowMapper(Location.class));
           double longitude2 = Double.parseDouble(longitude);
           double latitude2 =  Double.parseDouble(latitude);
           if (locations !=null&&locations.size() > 0 ) {
               int succeed = jdbcTemplate.update("UPDATE  user_location SET longitude=? AND latitude=?", new Object[]{longitude2, latitude2});
               if (succeed == 0) {
                   result.setCode(0);
               } else {
                   result.setCode(1);
                  // Test test = new Test();
               }
           }
           else {
               int succeed = jdbcTemplate.update("INSERT user_location VALUES (?,?,?)",new Object[]{uid,longitude2,latitude2});
               if(succeed == 0){
                   result.setCode(0);
               }
               else {
                   result.setCode(1);
                   //Test test = new Test();
               }
           }
       }
       else {
           result.setCode(-1);
       }
        return new ObjectMapper().writeValueAsString(result);

    }
}
