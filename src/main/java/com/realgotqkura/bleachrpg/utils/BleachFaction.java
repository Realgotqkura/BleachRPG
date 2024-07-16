package com.realgotqkura.bleachrpg.utils;

public enum BleachFaction {

    SHINIGAMI, QUINCY, HOLLOW, FULLBRING, NOT_CHOSEN;


    public static BleachFaction fromValue(int value){
        switch (value){
            case 1:
                return SHINIGAMI;
            case 2:
                return QUINCY;
            case 3:
                return HOLLOW;
            case 4:
                return FULLBRING;
            default:
                return NOT_CHOSEN;
        }
    }

    public int toInt(){
        switch (this){
            case SHINIGAMI:
                return 1;
            case QUINCY:
                return 2;
            case HOLLOW:
                return 3;
            case FULLBRING:
                return 4;
            case NOT_CHOSEN:
            default:
                return 0;
        }
    }
}
