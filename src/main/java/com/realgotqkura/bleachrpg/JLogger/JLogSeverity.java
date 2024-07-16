package com.realgotqkura.bleachrpg.JLogger;

public enum JLogSeverity {

    INFO("Info"), DEBUG("Debug"), WARN("Warning"), ERROR("Error");

    JLogSeverity(String s){};

    public String getColor(){
        switch(this){
            case DEBUG:
                return JLogColors.JLOG_CYAN;
            case WARN:
                return JLogColors.JLOG_YELLOW;
            case ERROR:
                return JLogColors.JLOG_RED;
            default:
                return JLogColors.JLOG_WHITE;

        }
    }
}
