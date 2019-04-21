package com.liszt.wesee_server.python;

//import jdk.internal.util.xml.impl.Input;




import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;

import java.lang.InterruptedException;

@Component
@ConfigurationProperties(prefix = "path")
public class RunPython {
private static String filePath2;
private static String pythonpath;

    public static String getFilePath2() {
        return filePath2;
    }

    public static String getPythonpath() {
        return pythonpath;
    }
    @Value("${path.pythonpath}")
    public  void setPythonpath(String pythonpath) {
        RunPython.pythonpath = pythonpath;
    }

    @Value("${path.filepath2}")
    public  void setFilePath2(String filepath2) {
        RunPython.filePath2 = filepath2;
    }


    public static void run() {
        try {
            String cmdArr =  pythonpath + " " + filePath2 ;
            Process process = Runtime.getRuntime().exec(cmdArr);
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
