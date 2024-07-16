package com.realgotqkura.bleachrpg.utils;

public enum ShinigamiStage {

    HUMAN, REGULAR, SHIKAI, BANKAI, TRUE_BANKAI, MUGETSU;

    public int toInt(){
        switch (this){
            default:
                return 0;
            case REGULAR:
                return 1;
            case SHIKAI:
                return 2;
            case BANKAI:
                return 3;
            case TRUE_BANKAI:
                return 4;
            case MUGETSU:
                return 5;
        }
    }

    public static ShinigamiStage fromInt(int num){
        switch (num) {
            default:
                return HUMAN;
            case 1:
                return REGULAR;
            case 2:
                return SHIKAI;
            case 3:
                return BANKAI;
            case 4:
                return TRUE_BANKAI;
            case 5:
                return MUGETSU;
        }
    }
}
