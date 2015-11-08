package Trubby.co.th.Upgrade;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import Trubby.co.th.DG;
import Trubby.co.th.Player.DGPlayer;
import Trubby.co.th.Util.ItemUtil;

public class InvManager {
	
	/**
	 * 		9  10 11 12 13 14 15 16 17 
	 * 		18 19 20 21 22 23 24 25 26
	 * 	 	27 28 29 30 31 32 33 34 35
	 * 		0  1  2  3  4  5  6  7  8
	 */
	
	public static void updateAllInv(DGPlayer dgp){
		updateGoldSlot(dgp, false);
		updateSword(dgp);
		updateBow(dgp);
		updateArrows(dgp);
		updateHelmet(dgp);
		updateChestplate(dgp);
		updateLegging(dgp);
		updateBoots(dgp);
		updateSkills(dgp);
		
		dgp.p.updateInventory();
		//Update Equipment sword bow arrow helmet chest leg boots
		//Update Skills
	}
	
	//GOLD
	public static void updateGoldSlot(DGPlayer dgp, boolean doRefresh){
		dgp.p.getInventory().setItem(17, ItemUtil.getGold(dgp.gold));
		
		if(doRefresh){
			dgp.p.updateInventory();
		}
	}
	
	//HELMET
	public static void updateHelmet(DGPlayer dgp){
		ItemStack is = ItemManager.helmets.get(dgp.helmetLevel);
		if(is.getType() != Material.STAINED_GLASS_PANE){
			dgp.p.getInventory().setHelmet(is);
		}
	}
	
	//CHEST
	public static void updateChestplate(DGPlayer dgp){
		ItemStack is = ItemManager.chests.get(dgp.chestLevel);
		if(is.getType() != Material.STAINED_GLASS_PANE){
			dgp.p.getInventory().setChestplate(is);
		}
	}
	
	//LEG
	public static void updateLegging(DGPlayer dgp){
		ItemStack is = ItemManager.legs.get(dgp.legLevel);
		if(is.getType() != Material.STAINED_GLASS_PANE){
			dgp.p.getInventory().setLeggings(is);
		}
	}
	
	//BOOTS
	public static void updateBoots(DGPlayer dgp){
		ItemStack is = ItemManager.boots.get(dgp.bootsLevel);
		if(is.getType() != Material.STAINED_GLASS_PANE){
			dgp.p.getInventory().setBoots(is);
		}
	}
	
	//SWORD
	public static void updateSword(DGPlayer dgp){
		ItemStack is = ItemManager.swords.get(dgp.swordLevel);
		if(is.getType() != Material.STAINED_GLASS_PANE){
			dgp.p.getInventory().setItem(0, is);
		}
	}
	
	//BOW
	public static void updateBow(DGPlayer dgp){
		ItemStack is = ItemManager.bows.get(dgp.bowLevel);
		if(is.getType() != Material.STAINED_GLASS_PANE){
			dgp.p.getInventory().setItem(1, is);
		}
	}
	
	//ARROWS
	public static void updateArrows(DGPlayer dgp){
		ItemStack is = ItemUtil.setAmount(ItemManager.etc.get(1), dgp.arrowLevel + 4);
		if(is.getType() != Material.STAINED_GLASS_PANE){
			dgp.p.getInventory().setItem(8, is);
		}
	}
	
	public static void updateSkills(DGPlayer dgp) {
		//TODO
		dgp.p.getInventory().setItem(2, DG.plugin.sm.hook.item());
		dgp.p.getInventory().setItem(3, DG.plugin.sm.smokeBomb.item());
	}

}
