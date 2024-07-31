package com.realgotqkura.bleachrpg.guis;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.items.GuiItems;
import com.realgotqkura.bleachrpg.items.guispecific.SpiritualLevelItems;
import com.realgotqkura.bleachrpg.utils.GUISorting;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import com.realgotqkura.bleachrpg.utils.objectclasses.BleachPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class SpiritualLevelInv implements Listener {

    private BleachRPG plugin;

    public SpiritualLevelInv(BleachRPG plugin){
        this.plugin = plugin;
    }
    public Inventory spiritualInv;
    private String invTitle = RandomUtils.color("&b&lSpiritual Levels");

    public void createInv1(Player player){
        Inventory inv = Bukkit.createInventory(null, 54, invTitle + RandomUtils.color(" &b&l1"));
        BleachPlayer bleachPlayer = new BleachPlayer(player);
        int level = bleachPlayer.getSpiritualLevel();

        SpiritualLevelItems spiritualLevelItems = new SpiritualLevelItems();
        GUISorting sorting = new GUISorting();
        sorting.fillLowestRow(inv);
        sorting.GetInner(inv, 54);

        GuiItems items = new GuiItems();

        inv.setItem(49, items.GUIClose());



        //First right row
        for(int i = 0; i < 9; i++){
            inv.setItem(i, spiritualLevelItems.skillItemStack(i, (level >= i+1) ? true : false));
        }

        //Reverse row
        inv.setItem(17, spiritualLevelItems.skillItemStack(9, (level >= 10) ? true : false));
        inv.setItem(26, spiritualLevelItems.skillItemStack(10, (level >= 11) ? true : false));
        inv.setItem(25, spiritualLevelItems.skillItemStack(11, (level >= 12) ? true : false));
        inv.setItem(24, spiritualLevelItems.skillItemStack(12, (level >= 13) ? true : false));
        inv.setItem(23, spiritualLevelItems.skillItemStack(13, (level >= 14) ? true : false));
        inv.setItem(22, spiritualLevelItems.skillItemStack(14, (level >= 15) ? true : false));
        inv.setItem(21, spiritualLevelItems.skillItemStack(15, (level >= 16) ? true : false));
        inv.setItem(20, spiritualLevelItems.skillItemStack(16, (level >= 17) ? true : false));
        inv.setItem(19, spiritualLevelItems.skillItemStack(17, (level >= 18) ? true : false));
        inv.setItem(18, spiritualLevelItems.skillItemStack(18, (level >= 19) ? true : false));

// Second right row
        inv.setItem(27, spiritualLevelItems.skillItemStack(19, (level >= 20) ? true : false));
        inv.setItem(36, spiritualLevelItems.skillItemStack(20, (level >= 21) ? true : false));
        inv.setItem(37, spiritualLevelItems.skillItemStack(21, (level >= 22) ? true : false));
        inv.setItem(38, spiritualLevelItems.skillItemStack(22, (level >= 23) ? true : false));
        inv.setItem(39, spiritualLevelItems.skillItemStack(23, (level >= 24) ? true : false));
        inv.setItem(40, spiritualLevelItems.skillItemStack(24, (level >= 25) ? true : false));
        inv.setItem(41, spiritualLevelItems.skillItemStack(25, (level >= 26) ? true : false));
        inv.setItem(42, spiritualLevelItems.skillItemStack(26, (level >= 27) ? true : false));
        inv.setItem(43, spiritualLevelItems.skillItemStack(27, (level >= 28) ? true : false));
        inv.setItem(44, spiritualLevelItems.skillItemStack(28, (level >= 29) ? true : false));

        inv.setItem(53, items.page(true));

        inv.setItem(35, items.GUIglass());
        inv.setItem(9, items.GUIglass());
        player.openInventory(inv);
    }

    public void createInv2(Player player){
        Inventory inv = Bukkit.createInventory(null, 54, invTitle + RandomUtils.color(" &b&l2"));
        BleachPlayer bleachPlayer = new BleachPlayer(player);
        int level = bleachPlayer.getSpiritualLevel();

        SpiritualLevelItems spiritualLevelItems = new SpiritualLevelItems();
        GUISorting sorting = new GUISorting();
        sorting.GetInner(inv, 54);
        sorting.GetOuter(inv, 54, false);

        GuiItems items = new GuiItems();

        inv.setItem(49, items.GUIClose());



        inv.setItem(36, spiritualLevelItems.skillItemStack(29, (level >= 30) ? true : false));
        inv.setItem(37, spiritualLevelItems.skillItemStack(30, (level >= 31) ? true : false));
        inv.setItem(28, spiritualLevelItems.skillItemStack(31, (level >= 32) ? true : false));
        inv.setItem(19, spiritualLevelItems.skillItemStack(32, (level >= 33) ? true : false));
        inv.setItem(10, spiritualLevelItems.skillItemStack(33, (level >= 34) ? true : false));

        inv.setItem(11, spiritualLevelItems.skillItemStack(34, (level >= 35) ? true : false));

        inv.setItem(12, spiritualLevelItems.skillItemStack(35, (level >= 36) ? true : false));
        inv.setItem(21, spiritualLevelItems.skillItemStack(36, (level >= 37) ? true : false));
        inv.setItem(30, spiritualLevelItems.skillItemStack(37, (level >= 38) ? true : false));
        inv.setItem(39, spiritualLevelItems.skillItemStack(38, (level >= 39) ? true : false));

        inv.setItem(40, spiritualLevelItems.skillItemStack(39, (level >= 40) ? true : false));

        inv.setItem(41, spiritualLevelItems.skillItemStack(40, (level >= 41) ? true : false));
        inv.setItem(32, spiritualLevelItems.skillItemStack(41, (level >= 42) ? true : false));
        inv.setItem(23, spiritualLevelItems.skillItemStack(42, (level >= 43) ? true : false));
        inv.setItem(14, spiritualLevelItems.skillItemStack(43, (level >= 44) ? true : false));

        inv.setItem(15, spiritualLevelItems.skillItemStack(44, (level >= 45) ? true : false));

        inv.setItem(16, spiritualLevelItems.skillItemStack(45, (level >= 46) ? true : false));
        inv.setItem(25, spiritualLevelItems.skillItemStack(46, (level >= 47) ? true : false));
        inv.setItem(34, spiritualLevelItems.skillItemStack(47, (level >= 48) ? true : false));
        inv.setItem(43, spiritualLevelItems.skillItemStack(48, (level >= 49) ? true : false));
        inv.setItem(44, spiritualLevelItems.skillItemStack(49, (level >= 50) ? true : false));

        inv.setItem(45, items.page(false));
        player.openInventory(inv);
    }

    @EventHandler
    public void clicky(InventoryClickEvent event){
        if(!event.getView().getTitle().contains(invTitle))
            return;

        int titlePage = 0;

        if(event.getView().getTitle().contains("1")){
            titlePage = 1;
        }else{
            titlePage = 2;
        }

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();

        if(event.getSlot() == 49) {
            player.closeInventory();
        }

        if(titlePage == 1 && event.getSlot() == 53){
            createInv2(player);
        }

        if(titlePage == 2 && event.getSlot() == 45){
            createInv1(player);
        }
    }
}
