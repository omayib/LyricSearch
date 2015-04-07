package com.lirik.lagu.app.utilities;

/**
 * Created by omayib on 3/23/15.
 */
public class Logger {
    public static void OUT(Class c,String toLog){
        System.out.println(c.getName() +" : "+toLog);
    }
}
