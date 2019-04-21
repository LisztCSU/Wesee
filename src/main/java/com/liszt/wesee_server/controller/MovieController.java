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

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
   @Autowired
   private  FileToBean fileToBean;
   @Autowired
   private RunPython runPython;
    @RequestMapping("/crawlMovie")
    public String crawlMovie(String admin, String password) {

        if(admin == null||password == null || jdbcTemplate.queryForRowSet("select id from admin where admin=? and password=?",new Object[]{admin,password})==null){
            return "failed";
        }
        String createSql = "CREATE TABLE  `movie` (" +
                "`id` varchar(50) NOT NULL," +
                "`title` varchar(50)," +
                "`score` varchar(50)," +
                "`star` varchar(50)," +
                "`duration` varchar(50)," +
                "`votecount` varchar(50)," +
                "`region` varchar(50)," +
                "`director` varchar(50)," +
                "`actors` varchar(50)," +
                "`imgUrl` varchar(255)," +
                "PRIMARY KEY (`id`)" + ")ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        String deleteSql = "DROP TABLE  IF EXISTS `movie` ";
        runPython.run();
        System.out.print("重新爬取");
        jdbcTemplate.execute(deleteSql);
        jdbcTemplate.execute(createSql);
        for (Movie movie :fileToBean.store()) {
            addMovie(movie);
        }
        return "success";
    }

    public void addMovie(Movie movie) {
        String sql = "insert into movie ( id, title, score,star,  duration, votecount, region, director,actors, imgUrl) value (?,?,?,?,?,?,?,?,?,?)";
        Object args[] = {movie.getId(), movie.getTitle(), movie.getScore(), star(movie.getStar()), movie.getDuration(), movie.getVotecount(), movie.getRegion(), movie.getDirector(), movie.getActors(), movie.getImgUrl()};
        jdbcTemplate.update(sql, args);
    }
    public String star(String s){
        if (s.equals("00"))
            return "暂无评分";
        if (s.equals("05"))
            return "☆";
        if (s.equals("10"))
            return "★";
        if (s.equals("15"))
            return "★☆";
        if (s.equals("20"))
            return "★★";
        if (s.equals("25"))
            return "★★☆";
        if (s.equals("30"))
            return "★★★";
        if (s.equals("35"))
            return "★★★☆";
        if (s.equals("40"))
            return "★★★★";
        if (s.equals("45"))
            return "★★★★☆";
        if (s.equals("50"))
            return "★★★★★";
        return null;
    }


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