package Trubby.co.th.Party;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import Trubby.co.th.DG;
import net.md_5.bungee.api.ChatColor;

public class PartyListener implements Listener{
	
	PartyManager parm = DG.plugin.parm;

	@EventHandler
	public void onClickPartyInv(InventoryClickEvent e){
		if(e.getInventory().getName().equalsIgnoreCase(ChatColor.DARK_GRAY + "Party > ")){
			e.setCancelled(true);
			if(e.getClickedInventory() == e.getWhoClicked().getOpenInventory().getTopInventory()){
				
				if(e.getSlot() == 9){
					parm.typingName.add(e.getWhoClicked().getUniqueId());
					e.getWhoClicked().sendMessage(ChatColor.GREEN + "Please type the name of player you want to invite.");
				}else if(e.getSlot() == 17){
					Player p = (Player) e.getWhoClicked();
					Party currentParty = parm.getParty(p);
					parm.removePlayer(p, currentParty);
				}
			}
		}
	}
	
	@EventHandler
	public void onNameType(AsyncPlayerChatEvent e){
		Player p = e.getPlayer();
		
		//prevent not in any party
		Party party = parm.getParty(p);
		if(party == null){
			parm.typingName.remove(p.getUniqueId());
			return;
		}
		
		if(parm.typingName.contains(p.getUniqueId())){
			e.setCancelled(true);
			
			Player invited = Bukkit.getPlayer(e.getMessage());
			if(invited != null){
				parm.invite(invited, party);
				p.sendMessage(ChatColor.GREEN + "Sent in invitation to " + invited.getName());
				
				parm.typingName.remove(p.getUniqueId());
			}else{
				p.sendMessage(ChatColor.RED + "Could not find player name " + e.getMessage());
				
				parm.typingName.remove(p.getUniqueId());
			}
		}
		
	}
	
}
