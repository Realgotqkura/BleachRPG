package com.realgotqkura.bleachrpg.utils.objectclasses;

import net.md_5.bungee.api.chat.BaseComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BleachActionBar {

    /**
     * This class is a more refined ActionBar system with priority
     * As multiple action bars will be fighting for priority when time comes
     * I decided to make a system that would prioritize more important action bars.
     *
     * This system can only support non-updating action bars so the default one can't be included.
     *
     */

    public static HashMap<BleachActionBar, Boolean> actionBars = new HashMap<>(); // <-- The boolean is whether it's active.

    private BaseComponent[] text;
    private int priority;
    private String name;
    public BleachActionBar(BaseComponent[] text, int priority, String name){
        this.priority = priority;
        this.text = text;
        this.name = name;

        actionBars.put(this, false);
    }

    public BaseComponent[] getText(){
        return text;
    }

    public int getPriority(){
        return priority;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled(){
        return actionBars.get(this);
    }


    public void setEnabled(boolean value){
        actionBars.replace(this, value);
    }
    /**
     *
     * @param name
     * @return returns the bar if found otherwise null
     */
    public static BleachActionBar getActionBar(String name){
        for (BleachActionBar bar : actionBars.keySet()) {
            if (bar.name.equals(name)) {
                return bar;
            }
        }

        return null;
    }

    public static List<BleachActionBar> getEnabledActionBars(){
        List<BleachActionBar> bars = new ArrayList<>();
        for(BleachActionBar bar : actionBars.keySet()){
            if(bar.isEnabled()){
                bars.add(bar);
            }
        }

        return bars;
    }

    /**
     * Gets the highest priority action bar out of the ones enabled
     * @return
     */
    public static BleachActionBar getHighestPriorityEnabled(){
        BleachActionBar currentBar = null;
        for(BleachActionBar bar : getEnabledActionBars()){
            if(currentBar == null){
                currentBar = bar;
                continue;
            }

            if(currentBar.getPriority() < bar.getPriority()){
                currentBar = bar;
            }
        }
        return currentBar;
    }
}
