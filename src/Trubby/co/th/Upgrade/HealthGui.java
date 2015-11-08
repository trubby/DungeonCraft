package Trubby.co.th.Upgrade;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import Trubby.co.th.DG;
import Trubby.co.th.Player.DGPlayer;
import Trubby.co.th.Player.Economy;
import Trubby.co.th.Util.ItemUtil;
import net.elseland.xikage.MythicMobs.Items.MythicItem;
import net.md_5.bungee.api.ChatColor;

public class HealthGui implements Listener{

	String healthInvName = "      Current        Upgrade";
	ItemStack potionCurrent;
	ItemStack potionUpgrade;
	
	ArrayList<Integer> healthPrice = new ArrayList<>();
	
	public HealthGui() {
		Collections.addAll(healthPrice, 0, 100, 200, 400, 400, 400, 400, 400, 400, 400, 400, 400, 400, 800, 1600, 3200, 6400);
		MythicItem myHealthIconC = MythicItem.getMythicItem("healthIconCurrent");
		MythicItem myHealthIconU = MythicItem.getMythicItem("healthIconUpgrade");
		
		potionCurrent = myHealthIconC.generateItemStack(1);
		potionUpgrade = myHealthIconU.generateItemStack(1);
	}
	
	//0 1 2 3 4 5 6 7 8
	public void openHealthGui(DGPlayer dgp){
		Inventory inv = Bukkit.createInventory(null, 9, healthInvName);
		
		inv.setItem(2, ItemUtil.addHealth(potionCurrent, 0.0, dgp.healthLevel + 7, false));
		inv.setItem(6, ItemUtil.addHealth(potionUpgrade, healthPrice.get(dgp.healthLevel), dgp.healthLevel + 8, true));
		
		dgp.p.openInventory(inv);
	}
	
	@EventHandler
	public void onHealthInvClick(InventoryClickEvent e){
		
		if(e.getClickedInventory() == null){
			return;
		}
		
		//EQUIPMENT UPGRADE INVENTORY
		if(e.getClickedInventory().getTitle().equalsIgnoreCase(healthInvName)){
			Player p = (Player) e.getWhoClicked();
			e.setCancelled(true);
			
			//Upgrade SWORD
			if(e.getSlot() == 6){
				upgradeHealth(p);
			}
		}
	}
	
	
	public void upgradeHealth(Player p){
		DGPlayer dgp = DG.plugin.dpm.getDGPlayer(p);
		
		
		if(dgp.healthLevel + 2 >= healthPrice.size()){
			p.closeInventory();
			p.sendMessage(ChatColor.RED + "You already reached maximum health level.");
		}else{
			if(Economy.takeMoney(p, healthPrice.get(dgp.healthLevel))){
				dgp.healthLevel++;
				p.closeInventory();
				p.sendMessage(ChatColor.GREEN + "Successful upgrade health.");
				p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
				p.getWorld().playSound(p.getLocation(), Sound.LEVEL_UP, 1f, 1f);
				dgp.updateHealth(true);
			}else{
				p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
				p.sendMessage(ChatColor.RED + "You don't have enough gold.");
			}
		}
	}
	
	
}
