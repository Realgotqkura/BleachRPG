package com.realgotqkura.bleachrpg.utils.objectclasses.spirits;

public enum ZanpakutoSpirits {

    NULL, ZANGETSU, SENBONZAKURA;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public static ZanpakutoSpirits fromString(String s){
        switch(s.toLowerCase()){
            case "zangetsu":
                return ZANGETSU;
            case "senbonzakura":
                return SENBONZAKURA;
            default:
                return NULL;
        }
    }
}
