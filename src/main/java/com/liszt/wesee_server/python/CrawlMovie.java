package com.liszt.wesee_server.python;


import com.liszt.wesee_server.bean.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling // 2.开启定时任务
public class CrawlMovie{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private  FileToBean fileToBean;
    @Autowired
    private RunPython runPython;
    private String crawlMovie() {
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
    private String star(String s){
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
    @Scheduled(fixedDelay = 1000*3600*24)
    private void configureTasks() {
        if(crawlMovie().equals("success"))
        System.err.println("更新电影列表成功");
    }
}