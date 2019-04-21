package com.liszt.wesee_server.python;


import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling // 2.开启定时任务
public class CrawlMovie{
    //3.添加定时任务
    @Scheduled(fixedDelay = 1000*10)
    private void configureTasks() {
        System.err.println("执行定时任务1: ");
    }
}