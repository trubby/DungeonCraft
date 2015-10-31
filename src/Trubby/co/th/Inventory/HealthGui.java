package Trubby.co.th.Inventory;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import Trubby.co.th.Player.DGPlayer;
import Trubby.co.th.Util.ItemUtil;

public class HealthGui implements Listener{

	String healthInvName = "      Current        Upgrade";
	ItemStack potion = new ItemStack(Material.POTION);
	
	ArrayList<Integer> healthPrice = new ArrayList<>();
	
	public HealthGui() {
		Collections.addAll(healthPrice, 0, 100, 200, 400, 400, 400, 400, 400, 400, 400, 400, 400, 400, 800, 1600, 3200, 6400);
	}
	
	//0 1 2 3 4 5 6 7 8
	public void openHealthGui(DGPlayer dgp){
		Inventory inv = Bukkit.createInventory(null, 9, healthInvName);
		
		inv.setItem(2, ItemUtil.addHealth(potion, 0.0, dgp.healthLevel + 7, false));
		inv.setItem(6, ItemUtil.addHealth(potion, healthPrice.get(dgp.healthLevel), dgp.healthLevel + 8, true));
		
		dgp.p.openInventory(inv);
	}
	
	
}
