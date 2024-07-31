package com.realgotqkura.bleachrpg.utils.voicelines;

import com.realgotqkura.bleachrpg.utils.RandomUtils;

import java.util.Arrays;
import java.util.List;

public class ShikaiVoiceLines {

    public String[] zangetsuVoicelinesBeforeFight = new String[]{
            RandomUtils.color("&fSo you finally dared to come, %player_name%..."),
            RandomUtils.color("&fYou think just by doing the bare minimum"),
            RandomUtils.color("&fyou would be good enough to defeat me?"),
            RandomUtils.color("&fIf so, I will enjoy breaking you."),
            RandomUtils.color("&fJust to show you your place!")
    };

    public String[] zangetsuWinVoiceLines = new String[]{
            RandomUtils.color("&fIs that all you’ve got?"),
            RandomUtils.color("&fI expected more from someone who dares to wield me."),
            RandomUtils.color("&fYou’re just a kid playing with a sword, %player_name%."),
            RandomUtils.color("&fDon't dare come back weak again"),
            RandomUtils.color("&for it will be your final battle.")
    };

    public String[] zangetsuLossVoiceLines = new String[]{
            RandomUtils.color("&fI underestimated you. "),
            RandomUtils.color("&fPerhaps there’s more to you than I thought."),
            RandomUtils.color("&fYou’ve grown stronger, %player_name%. "),
            RandomUtils.color("&fMaybe you are worthy of wielding my power."),
    };

    public List<String[]> getVoiceLines(String zanpakuto){
        switch (zanpakuto.toLowerCase()){
            case "zangetsu":
                return Arrays.asList(zangetsuVoicelinesBeforeFight, zangetsuWinVoiceLines, zangetsuLossVoiceLines);
            default:
                return null;

        }
    }
}
