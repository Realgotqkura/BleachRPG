package com.realgotqkura.bleachrpg.guis;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.items.GuiItems;
import com.realgotqkura.bleachrpg.items.guispecific.TutorialGUIItems;
import com.realgotqkura.bleachrpg.utils.GUISorting;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class TutorialGUI implements Listener {

    private BleachRPG plugin;

    public TutorialGUI(BleachRPG plugin){
        this.plugin = plugin;
    }
    public Inventory tutorialInv;
    private String invTitle = RandomUtils.color("&bBleachRPG Tutorial");

    public void createInv(Player player){
        Inventory inv = Bukkit.createInventory(null, 54, invTitle);

        GUISorting sorting = new GUISorting();
        sorting.GetOuter(inv, 54, false);
        sorting.GetInner(inv, 54);

        GuiItems items = new GuiItems();
        TutorialGUIItems tutItems = new TutorialGUIItems();

        inv.setItem(49, items.GUIClose());

        inv.setItem(20, tutItems.learnDash(plugin));
        inv.setItem(21, tutItems.learnXpGain(plugin));
        inv.setItem(22, tutItems.learnBasicCombat(plugin));

        player.openInventory(inv);
    }

    @EventHandler
    public void clicky(InventoryClickEvent event){
        if(!event.getView().getTitle().equals(invTitle))
            return;

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();

        if(event.getSlot() == 49) {
            player.closeInventory();
        }
    }
}
