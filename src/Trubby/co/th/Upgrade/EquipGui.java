package Trubby.co.th.Upgrade;

import java.util.ArrayList;
import java.util.Collections;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import Trubby.co.th.DG;
import Trubby.co.th.Player.DGPlayer;
import Trubby.co.th.Player.Economy;
import Trubby.co.th.Util.ItemUtil;

public class EquipGui implements Listener{
	
	/** 0  1  2 : 3  4  5 : 6  7  8
	 *  9  10 11: 12 13 14: 15 16 17
	 *  18 19 20: 21 22 23: 24 25 26
	 *  27 28 29: 30 31 32: 33 34 35
	 * @param p
	 */
	
	String equipInvName = "  Current                Upgrade";
	
	// --------------- EQUIP. UPGRADE ---------------
	public void openEquipUpgrade(Player p){
		DGPlayer dgp = DG.plugin.dpm.getDGPlayer(p);

		Inventory inv = Bukkit.createInventory(null, 36, equipInvName);
		int swordLvl = dgp.swordLevel;
		int bowLvl = dgp.bowLevel;
		int arrowLvl = dgp.arrowLevel;
		int helmetLvl = dgp.helmetLevel;
		int chestLvl = dgp.chestLevel;
		int legLvl = dgp.legLevel;
		int bootsLvl = dgp.bootsLevel;
		
		//-- empty slot
		ArrayList<Integer> empty_slots = new ArrayList<>();
		Collections.addAll(empty_slots, 3,4,5,12,13,14,21,22,23,30,31,32);
		
		for(int i : empty_slots){
			inv.setItem(i, ItemManager.etc.get(0));
		}
		//--

		//SWORD
		inv.setItem(11, ItemUtil.addCurrent(ItemManager.swords.get(swordLvl)));
		inv.setItem(17, ItemUtil.addPrice(ItemManager.swords.get(swordLvl + 1), ItemManager.swordPrice.get(swordLvl + 1)));
		
		//BOW
		inv.setItem(9, ItemUtil.addCurrent(ItemManager.bows.get(bowLvl)));
		inv.setItem(15, ItemUtil.addPrice(ItemManager.bows.get(bowLvl + 1), ItemManager.bowPrice.get(bowLvl + 1)));
		
		//ARROW
		inv.setItem(18, ItemUtil.addCurrent(ItemUtil.setAmount(ItemManager.etc.get(1), arrowLvl + 4)));
		inv.setItem(24, ItemUtil.addPrice(ItemUtil.setAmount(ItemManager.etc.get(1), arrowLvl + +5), ItemManager.arrowPrice.get(arrowLvl + 5)));

		//HELMET
		inv.setItem(1, ItemUtil.addCurrent(ItemManager.helmets.get(helmetLvl)));
		inv.setItem(7, ItemUtil.addPrice(ItemManager.helmets.get(helmetLvl + 1), ItemManager.helmetPrice.get(helmetLvl + 1)));
		
		//CHEST
		inv.setItem(10, ItemUtil.addCurrent(ItemManager.chests.get(chestLvl)));
		inv.setItem(16, ItemUtil.addPrice(ItemManager.chests.get(chestLvl + 1), ItemManager.chestPrice.get(chestLvl + 1)));
		
		//LEG
		inv.setItem(19, ItemUtil.addCurrent(ItemManager.legs.get(legLvl)));
		inv.setItem(25, ItemUtil.addPrice(ItemManager.legs.get(legLvl + 1), ItemManager.legPrice.get(legLvl + 1)));
		
		//BOOTS
		inv.setItem(28, ItemUtil.addCurrent(ItemManager.boots.get(bootsLvl)));
		inv.setItem(34, ItemUtil.addPrice(ItemManager.boots.get(bootsLvl + 1), ItemManager.bootsPrice.get(bootsLvl + 1)));
		
		p.openInventory(inv);
	}
	
	public void updateEquipUpgradeGUI(Player p){
		
		Inventory inv = p.getOpenInventory().getTopInventory();
		
		DGPlayer dgp = DG.plugin.dpm.getDGPlayer(p);

		int swordLvl = dgp.swordLevel;
		int bowLvl = dgp.bowLevel;
		int arrowLvl = dgp.arrowLevel;
		int helmetLvl = dgp.helmetLevel;
		int chestLvl = dgp.chestLevel;
		int legLvl = dgp.legLevel;
		int bootsLvl = dgp.bootsLevel;
		
		//SWORD
		inv.setItem(11, ItemUtil.addCurrent(ItemManager.swords.get(swordLvl)));
		inv.setItem(17, ItemUtil.addPrice(ItemManager.swords.get(swordLvl + 1), ItemManager.swordPrice.get(swordLvl + 1)));
		
		//BOW
		inv.setItem(9, ItemUtil.addCurrent(ItemManager.bows.get(bowLvl)));
		inv.setItem(15, ItemUtil.addPrice(ItemManager.bows.get(bowLvl + 1), ItemManager.bowPrice.get(bowLvl + 1)));
		
		//ARROW
		inv.setItem(18, ItemUtil.addCurrent(ItemUtil.setAmount(ItemManager.etc.get(1), arrowLvl + 4)));
		inv.setItem(24, ItemUtil.addPrice(ItemUtil.setAmount(ItemManager.etc.get(1), arrowLvl + +5), ItemManager.arrowPrice.get(arrowLvl + 5)));

		//HELMET
		inv.setItem(1, ItemUtil.addCurrent(ItemManager.helmets.get(helmetLvl)));
		inv.setItem(7, ItemUtil.addPrice(ItemManager.helmets.get(helmetLvl + 1), ItemManager.helmetPrice.get(helmetLvl + 1)));
		
		//CHEST
		inv.setItem(10, ItemUtil.addCurrent(ItemManager.chests.get(chestLvl)));
		inv.setItem(16, ItemUtil.addPrice(ItemManager.chests.get(chestLvl + 1), ItemManager.chestPrice.get(chestLvl + 1)));
		
		//LEG
		inv.setItem(19, ItemUtil.addCurrent(ItemManager.legs.get(legLvl)));
		inv.setItem(25, ItemUtil.addPrice(ItemManager.legs.get(legLvl + 1), ItemManager.legPrice.get(legLvl + 1)));
		
		//BOOTS
		inv.setItem(28, ItemUtil.addCurrent(ItemManager.boots.get(bootsLvl)));
		inv.setItem(34, ItemUtil.addPrice(ItemManager.boots.get(bootsLvl + 1), ItemManager.bootsPrice.get(bootsLvl + 1)));
	}
	
	@EventHandler
	public void onEquipInvClick(InventoryClickEvent e){
		
		if(e.getClickedInventory() == null){
			return;
		}
		
		//EQUIPMENT UPGRADE INVENTORY
		if(e.getClickedInventory().getTitle().equalsIgnoreCase(equipInvName)){
			Player p = (Player) e.getWhoClicked();
			e.setCancelled(true);
			
			//Upgrade SWORD
			if(e.getSlot() == 17){
				upgrade(p, "sword");
			}
			
			//Upgrade BOW
			else if(e.getSlot() == 15){
				upgrade(p, "bow");
			}
			
			//Upgrade ARROWS
			else if(e.getSlot() == 24){
				upgrade(p, "arrow");
			}
			
			//Upgrade HELMET
			else if(e.getSlot() == 7){
				upgrade(p, "helmet");
			}
			
			//Upgrade CHEST
			else if(e.getSlot() == 16){
				upgrade(p, "chest");
			}
			
			//Upgrade LEG
			else if(e.getSlot() == 25){
				upgrade(p, "leg");
			}
			
			//Upgrade BOOTS
			else if(e.getSlot() == 34){
				upgrade(p, "boots");
			}	
		}else if(e.getClickedInventory().getType() == InventoryType.PLAYER){
			if(!e.getWhoClicked().isOp()){
				e.setCancelled(true);
			}
		}
		
		
		
	}
	
	public void upgrade(Player p, String type){
		DGPlayer dgp = DG.plugin.dpm.getDGPlayer(p);
		
		switch (type) {
		case "sword":
			if(dgp.swordLevel + 2 >= ItemManager.swords.size()){
				p.closeInventory();
				p.sendMessage(ChatColor.RED + "You already reached maximum sword level.");
				//TODO message
			}else{
				if(Economy.takeMoney(p,  ItemManager.swordPrice.get(dgp.swordLevel + 1))){
					dgp.swordLevel++;
					updateEquipUpgradeGUI(p);
					p.sendMessage(ChatColor.GREEN + "Successful upgrade sword.");
					p.getWorld().playSound(p.getLocation(), Sound.LEVEL_UP, 1f, 1f);
					dgp.updateEquipments();
				}else{
					p.sendMessage(ChatColor.RED + "You don't have enough gold.");
				}
			}
			break;
		
		case "bow":
			if(dgp.bowLevel + 2 >= ItemManager.bows.size()){
				p.closeInventory();
				p.sendMessage(ChatColor.RED + "You already reached maximum bow level.");
				//TODO message
			}else{
				if(Economy.takeMoney(p,  ItemManager.bowPrice.get(dgp.bowLevel + 1))){
					dgp.bowLevel++;
					updateEquipUpgradeGUI(p);
					p.sendMessage(ChatColor.GREEN + "Successful upgrade bow.");
					p.getWorld().playSound(p.getLocation(), Sound.LEVEL_UP, 1f, 1f);
					dgp.updateEquipments();
				}else{
					p.sendMessage(ChatColor.RED + "You don't have enough gold.");
				}
			}
			break;
			
		case "arrow":
			if(dgp.swordLevel + 2 >= ItemManager.arrowPrice.size()){
				p.closeInventory();
				p.sendMessage(ChatColor.RED + "You already reached maximum arrows level.");
				//TODO message
			}else{
				if(Economy.takeMoney(p,  ItemManager.arrowPrice.get(dgp.arrowLevel + 1))){
					dgp.arrowLevel++;
					updateEquipUpgradeGUI(p);
					p.sendMessage(ChatColor.GREEN + "Successful upgrade arrows.");
					p.getWorld().playSound(p.getLocation(), Sound.LEVEL_UP, 1f, 1f);
					dgp.updateEquipments();
				}else{
					p.sendMessage(ChatColor.RED + "You don't have enough gold.");
				}
			}
			break;
			
		case "helmet":
			if(dgp.helmetLevel + 2 >= ItemManager.helmets.size()){
				p.closeInventory();
				p.sendMessage(ChatColor.RED + "You already reached maximum helmet level.");
				//TODO message
			}else{
				if(Economy.takeMoney(p,  ItemManager.helmetPrice.get(dgp.helmetLevel + 1))){
					dgp.helmetLevel++;
					updateEquipUpgradeGUI(p);
					p.sendMessage(ChatColor.GREEN + "Successful upgrade helmet.");
					p.getWorld().playSound(p.getLocation(), Sound.LEVEL_UP, 1f, 1f);
					dgp.updateEquipments();
				}else{
					p.sendMessage(ChatColor.RED + "You don't have enough gold.");
				}
				
			}
			break;
		
		case "chest":
			if(dgp.chestLevel + 2 >= ItemManager.chests.size()){
				p.closeInventory();
				p.sendMessage(ChatColor.RED + "You already reached maximum chest level.");
				//TODO message
			}else{
				if(Economy.takeMoney(p,  ItemManager.chestPrice.get(dgp.chestLevel + 1))){
					dgp.chestLevel++;
					updateEquipUpgradeGUI(p);
					p.sendMessage(ChatColor.GREEN + "Successful upgrade chest.");
					p.getWorld().playSound(p.getLocation(), Sound.LEVEL_UP, 1f, 1f);
					dgp.updateEquipments();
				}else{
					p.sendMessage(ChatColor.RED + "You don't have enough gold.");
				}
				
			}
			break;
		
		case "leg":
			if(dgp.legLevel + 2 >= ItemManager.legs.size()){
				p.closeInventory();
				p.sendMessage(ChatColor.RED + "You already reached maximum leg level.");
				//TODO message
			}else{
				if(Economy.takeMoney(p,  ItemManager.legPrice.get(dgp.legLevel + 1))){
					dgp.legLevel++;
					updateEquipUpgradeGUI(p);
					p.sendMessage(ChatColor.GREEN + "Successful upgrade leg.");
					p.getWorld().playSound(p.getLocation(), Sound.LEVEL_UP, 1f, 1f);
					dgp.updateEquipments();
				}else{
					p.sendMessage(ChatColor.RED + "You don't have enough gold.");
				}
				
			}
			break;
			
		case "boots":
			if(dgp.bootsLevel + 2 >= ItemManager.boots.size()){
				p.closeInventory();
				p.sendMessage(ChatColor.RED + "You already reached maximum boots level.");
				//TODO message
			}else{
				if(Economy.takeMoney(p,  ItemManager.bootsPrice.get(dgp.bootsLevel + 1))){
					dgp.bootsLevel++;
					updateEquipUpgradeGUI(p);
					p.sendMessage(ChatColor.GREEN + "Successful upgrade boots.");
					p.getWorld().playSound(p.getLocation(), Sound.LEVEL_UP, 1f, 1f);
					dgp.updateEquipments();
				}else{
					p.sendMessage(ChatColor.RED + "You don't have enough gold.");
				}
				
			}
			break;
			
			//TODO add sound
			//TODO other type
		default:
			break;
		}
		
		
	}
	
	
}
