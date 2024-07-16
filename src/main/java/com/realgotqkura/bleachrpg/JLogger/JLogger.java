package com.realgotqkura.bleachrpg.JLogger;

public class JLogger {

    public static void log(JLogSeverity severity, String message){
        System.out.println(severity.getColor() + "[" + severity + "]: " + message + JLogColors.JLOG_RESET);
    }
}
