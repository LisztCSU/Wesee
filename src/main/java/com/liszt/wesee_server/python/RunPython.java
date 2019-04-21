package com.liszt.wesee_server.python;

//import jdk.internal.util.xml.impl.Input;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

import java.lang.InterruptedException;
@Component

public class RunPython {
private static String filePath2;
    @Value(value = "${URI.filePath2}")
    private void setResourcePath(String  filePath2){
        this.filePath2= filePath2 ;
    }
    public static void run() {
        try {
            String exe = "python";
            String argv0 = "changsha";
            String cmdArr = exe + " " + filePath2 + " " + argv0;
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
