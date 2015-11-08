package Trubby.co.th.Map;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import Trubby.co.th.Util.ItemBuilder;
import net.md_5.bungee.api.ChatColor;

public class DungeonGui implements Listener{
	
	String dungeonInvName = "            DungeonCraft";
	ArrayList<ItemStack> allowed = new ArrayList<>();
	ArrayList<ItemStack> denied = new ArrayList<>();
	
	public DungeonGui() {
		new ItemBuilder(Material.STONE)
		.name(ChatColor.GOLD + "Stronghold " + ChatColor.GREEN + "(Level 1)")
		.lore(ChatColor.ITALIC + "Fight your way through")
		.lore(ChatColor.ITALIC + "the catacombs of once")
		.lore(ChatColor.ITALIC + "powerful castle")
		.lore(ChatColor.ITALIC + "")
		.lore(ChatColor.GOLD + "NORMAL")
		.lore(ChatColor.ITALIC + "Fight with 3 lives")
		.lore(ChatColor.ITALIC + "Lives will be reset each floor").build();
		
		
	}

	@EventHandler
	public void onHealthInvClick(InventoryClickEvent e){
		
		if(e.getClickedInventory() == null){
			return;
		}
		
		//EQUIPMENT UPGRADE INVENTORY
		if(e.getClickedInventory().getTitle().equalsIgnoreCase(dungeonInvName)){
			Player p = (Player) e.getWhoClicked();
			e.setCancelled(true);
			//TODO check unlocked
			//Upgrade SWORD
			if(e.getSlot() == 6){
				//upgradeHealth(p);
			}
		}
	}
	
}
