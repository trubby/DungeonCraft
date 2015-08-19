package Trubby.co.th.Util;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {
	
	public static ItemStack addPrice(ItemStack isold, double price){
		
		if(isold.getType() == Material.STAINED_GLASS_PANE){
			return isold;
		}
		
		ItemStack is = isold.clone();
		
		if(is.hasItemMeta()){
			ItemMeta im = is.getItemMeta();
			List<String> lores = im.getLore();
			lores.add(0, ChatColor.YELLOW + "Price: " + ChatColor.WHITE + price + ChatColor.YELLOW + " gold");
			im.setLore(lores);
			is.setItemMeta(im);
		}else{
			ItemMeta im = is.getItemMeta();
			List<String> lores = new ArrayList<String>();
			lores.add(ChatColor.YELLOW + "Price: " + ChatColor.WHITE + price + ChatColor.YELLOW + " gold");
			im.setLore(lores);
			is.setItemMeta(im);
		}
		return is;
	}
	
	public static ItemStack addCurrent(ItemStack isold){
		
		if(isold.getType() == Material.STAINED_GLASS_PANE){
			return isold;
		}
		
		ItemStack is = isold.clone();
		
		if(is.hasItemMeta()){
			ItemMeta im = is.getItemMeta();
			List<String> lores = im.getLore();
			lores.add(0, ChatColor.GREEN + "Current Item");
			im.setLore(lores);
			is.setItemMeta(im);
		}else{
			ItemMeta im = is.getItemMeta();
			List<String> lores = new ArrayList<String>();
			lores.add(ChatColor.GREEN + "Current Item");
			im.setLore(lores);
			is.setItemMeta(im);
		}
		return is;
	}
	
	public static ItemStack setAmount(ItemStack isold, int amount){
		
		if(isold.getType() == Material.STAINED_GLASS_PANE){
			return isold;
		}
		
		ItemStack is = isold.clone();
		
		is.setAmount(amount);
		return is;
	}
	
	public static ItemStack getGold(int gold){
		ItemStack is = new ItemStack(Material.GOLD_NUGGET);
		
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.YELLOW + "Your gold : " + gold);
		is.setItemMeta(im);
		
		return is;
	}

}
