package com.liszt.wesee_server.python;

import com.liszt.wesee_server.bean.Movie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Component
@ConfigurationProperties(prefix = "path")
public class FileToBean {
     private static String filepath;
    public static String getFilepath() {
        return filepath;
    }
    @Value("${path.filepath}")
    public  void setFilepath(String filepath) {
        FileToBean.filepath = filepath;
    }

    public List<Movie> store() {

        FileReader file = null;
        try {
            file = new FileReader(filepath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(file);
        List<Movie> list = new ArrayList<>();//读取文件
        try {
            String str;

            while ((str = br.readLine()) != null) {
                String[] arr = str.split(",");
               list.add(new Movie(arr[0],arr[1],arr[2],arr[3],arr[4],arr[5],arr[6],arr[7],arr[8],arr[9],"0"));
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return list;

    }


}
