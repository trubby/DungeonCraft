package Trubby.co.th.Util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.md_5.bungee.api.ChatColor;

public class ItemUtil {
	
	public static ItemStack addPrice(ItemStack isold, double price){
		
		if(isold.getType() == Material.STAINED_GLASS_PANE){
			return isold;
		}
		
		ItemStack is = isold.clone();
		
		if(is.hasItemMeta()){
			ItemMeta im = is.getItemMeta();
			List<String> lores = im.getLore();
			lores.add(0, ChatColor.DARK_GRAY + "Price: " + ChatColor.WHITE + price + ChatColor.DARK_GRAY + " gold");
			im.setLore(lores);
			is.setItemMeta(im);
		}else{
			ItemMeta im = is.getItemMeta();
			List<String> lores = new ArrayList<String>();
			lores.add(ChatColor.DARK_GRAY + "Price: " + ChatColor.WHITE + price + ChatColor.DARK_GRAY + " gold");
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
			lores.add(0, ChatColor.GREEN + "" + ChatColor.UNDERLINE + "Current Item");
			im.setLore(lores);
			is.setItemMeta(im);
		}else{
			ItemMeta im = is.getItemMeta();
			List<String> lores = new ArrayList<String>();
			lores.add(ChatColor.GREEN + "" + ChatColor.UNDERLINE + "Current Item");
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
		im.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "Your gold" + ChatColor.YELLOW + " > " + gold);
		is.setItemMeta(im);
		
		return is;
	}
	
	public static ItemStack getHead(Player p, boolean leader){
		ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta meta1 = (SkullMeta)is.getItemMeta();
        meta1.setOwner(p.getName());
        is.setItemMeta(meta1);
        
        ItemMeta im = is.getItemMeta();
        if(leader){
        	im.setDisplayName(ChatColor.ITALIC + "" + p.getName() + ChatColor.YELLOW + "" + ChatColor.BOLD + " Leader");
        }else{
        	im.setDisplayName(ChatColor.ITALIC + "" + p.getName());
        }
        
		List<String> lores = new ArrayList<String>();
		if(leader){
			lores.add(ChatColor.DARK_GRAY + "Leader");
		}else{
			lores.add(ChatColor.DARK_GRAY + "Member");
		}
		
		im.setLore(lores);
		is.setItemMeta(im);
        
        return is;
	}
	
public static ItemStack addHealth(ItemStack isold, double price, double health, boolean isUpgrade){
		
		ItemStack is = isold.clone();
		
		if(isUpgrade){
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(ChatColor.WHITE + "Upgrade");
			List<String> lores = new ArrayList<>();
			lores.add(ChatColor.DARK_GRAY + "Price: " + ChatColor.WHITE + price + ChatColor.DARK_GRAY + " gold");
			lores.add(ChatColor.GRAY + "Health : " + ChatColor.RED + health);
			im.setLore(lores);
			is.setItemMeta(im);
		}else{
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(ChatColor.WHITE + "Your Health");
			List<String> lores = new ArrayList<>();
			lores.add(ChatColor.GRAY + "Health : " + ChatColor.RED + health);
			im.setLore(lores);
			is.setItemMeta(im);
		}

		return is;
	}
	
}
