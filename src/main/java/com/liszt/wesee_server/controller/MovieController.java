package com.liszt.wesee_server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liszt.wesee_server.bean.Movie;
import com.liszt.wesee_server.bean.ResultList;
import com.liszt.wesee_server.python.FileToBean;
import com.liszt.wesee_server.python.RunPython;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @RequestMapping("/getMovieList")
    public ResultList  getMovieList()throws JsonProcessingException {
        ResultList<Movie> result = new ResultList<>();
        result.setMsg("success");
        List<Movie> movies = jdbcTemplate.query("SELECT * FROM movie", new BeanPropertyRowMapper(Movie.class));
        if (movies != null && movies.size() > 0) {
            result.setCode(1);
         for(Movie movie: movies) {
             List<String> ids = jdbcTemplate.queryForList("SELECT id FROM mate WHERE mid=? and wanted =?", String.class, movie.getId(), "1");
             if (ids != null && ids.size() > 0) {
                 movie.setWantcount(ids.size() + "");
             } else {
                 movie.setWantcount("0");
             }
         }
            result.setDataList(movies);
        } else {
            result.setCode(0);
        }
        return result;
    }
}