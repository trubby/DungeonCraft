package Trubby.co.th.Party;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import Trubby.co.th.DG;
import Trubby.co.th.Player.DGPlayer;
import Trubby.co.th.Util.ItemUtil;
import net.md_5.bungee.api.ChatColor;

public class Party {

	public ArrayList<DGPlayer> players = new ArrayList<>();
	public String leader;
	public String name;
	public Inventory menu;
	
	public Party(Player p, String name) {
		leader = p.getName();
		players.add(DG.plugin.dpm.getDGPlayer(p));
		this.name = name;
		createPartyMenu();
	}
	
	public void createPartyMenu(){
		menu = Bukkit.createInventory(null, 18, ChatColor.DARK_GRAY + "Party > " + ChatColor.BLACK + name);
		menu.setItem(9, inviteButton());
		menu.setItem(17, leaveButton());
		
		updatePlayers();
	}
	
	// 0 1  2  3  4  5  6  7  8
	// 9 10 11 12 13 14 15 16 17
	public void updatePlayers(){
		
		int i = 1;
		for(DGPlayer dgp : players){
			if(dgp.p.getName() == leader){
				menu.setItem(4, ItemUtil.getHead(dgp.p, true));
			}else{
				switch (i) {
				case 1: menu.setItem(12, ItemUtil.getHead(dgp.p, true));i++; break;
				case 2: menu.setItem(13, ItemUtil.getHead(dgp.p, true));i++; break;
				case 3: menu.setItem(14, ItemUtil.getHead(dgp.p, true));i++; break;
				default:break;
				}
			}
		}
	}
	
	public void changeLeader(Player p){
		leader = p.getName();
		broadcast("Change leader to " + p.getName());//TODO prefix
	}

	public void broadcast(String str){
		for(DGPlayer dgp : players){
			dgp.p.sendMessage(str);
		}
	}
	
	public ItemStack inviteButton(){
		ItemStack is = new ItemStack(Material.STAINED_CLAY, 1, (short)5);//TODO color
		
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.GREEN + "Invite " + ChatColor.GRAY + "(Click)");
		
		List<String> lores = new ArrayList<String>();
		lores.add(ChatColor.GRAY + "Click and then type");
		lores.add(ChatColor.GRAY + "the " + ChatColor.WHITE + "name " + ChatColor.GRAY + "of person");
		lores.add(ChatColor.GRAY + "you want to invite");
		im.setLore(lores);
		
		is.setItemMeta(im);
		
		return is;
	}
	
	public ItemStack leaveButton(){
		ItemStack is = new ItemStack(Material.STAINED_CLAY, 1, (short)14);
		
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.RED + "Leave party " + ChatColor.GRAY + "(Click)");
		
		List<String> lores = new ArrayList<String>();
		lores.add(ChatColor.GRAY + "Quit from this party");
		im.setLore(lores);
		
		is.setItemMeta(im);
		
		return is;
	}
	
}
