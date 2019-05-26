package com.liszt.wesee_server.controller;

import com.liszt.wesee_server.bean.Admin;
import com.liszt.wesee_server.bean.Movie;
import com.liszt.wesee_server.python.FileToBean;
import com.liszt.wesee_server.python.RunPython;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private FileToBean fileToBean;
    @Autowired
    private RunPython runPython;
    @PostMapping("/admin/crawlMovie")
    public String crawlMovie(String id, HttpServletRequest request, Map<String,Object> map) {

        HttpSession session = request.getSession(false);
        if(session!=null&&session.getAttribute("id")!=null&&session.getAttribute("id").equals(id)) {
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
            for (Movie movie : fileToBean.store()) {
                addMovie(movie);
            }
            map.put("msg","更新成功");
            return "home";
        }
        else {
            map.put("msg","未登录");
            return "login";
        }

    }

    public void addMovie(Movie movie) {
        String sql = "insert into movie ( id, title, score,star,  duration, votecount, region, director,actors, imgUrl) value (?,?,?,?,?,?,?,?,?,?)";
        String sql2 = "insert ignore  into movie_all ( id, title, score,star,  duration, votecount, region, director,actors, imgUrl) value (?,?,?,?,?,?,?,?,?,?)";
        Object args[] = {movie.getId(), movie.getTitle(), movie.getScore(), star(movie.getStar()), movie.getDuration(), movie.getVotecount(), movie.getRegion(), movie.getDirector(), movie.getActors(), movie.getImgUrl()};
        jdbcTemplate.update(sql, args);
        jdbcTemplate.update(sql2, args);
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

    @PostMapping("/admin/login")
        public String login(String username,String password, HttpServletRequest request,Map<String,Object> map){

            List<Admin> admins = jdbcTemplate.query("select * from admin where admin=? and password=?",new Object[]{username,password},new BeanPropertyRowMapper<>(Admin.class));
            if(admins!=null&&admins.size()>0){
                HttpSession session = request.getSession(true);
                    session.setAttribute("id",admins.get(0).getId());
                    return "home";//redirect重定向
                } else {
                    //向页面输出的内容
                    map.put("msg","用户名或密码错误");
                   return "login";


        }

    }
    @GetMapping("/admin/login")
    public String login(){

            return "login";
        }

    @GetMapping("/admin/home")
    public String home(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("id") != null) {
            return "home";

        } else {
            return "login";
        }
    }


}


