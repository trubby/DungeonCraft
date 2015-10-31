package Trubby.co.th.Party;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import Trubby.co.th.DG;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.PacketPlayOutCamera;

public class PartyListener implements Listener{
	
	PartyManager parm = DG.plugin.parm;

	@EventHandler
	public void onClickPartyInv(InventoryClickEvent e){
		if(e.getInventory().getName().startsWith(ChatColor.DARK_GRAY + "Party > ")){
			e.setCancelled(true);
			if(e.getClickedInventory() == e.getWhoClicked().getOpenInventory().getTopInventory()){
				Player p = (Player) e.getWhoClicked();
				if(e.getSlot() == 9){
					if(parm.typingName.contains(p.getUniqueId())){
						p.sendMessage(ChatColor.GREEN + "Type the name of player you want to invite."); //TODO text
						return;
					}
					parm.typingName.add(p.getUniqueId());
					p.sendMessage(ChatColor.GREEN + "Please type the name of player you want to invite.");
					p.closeInventory();
					p.playSound(p.getLocation(), Sound.CLICK, 1f, 1f);
				}else if(e.getSlot() == 17){
					Party currentParty = parm.getParty(p);
					parm.removePlayer(p, currentParty);
					p.playSound(p.getLocation(), Sound.CLICK, 1f, 1f);
					p.closeInventory();
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
				//already in party check
				if(parm.getParty(invited) != null){
					p.sendMessage(ChatColor.GREEN + invited.getName() + " is already in the party.");
					//return;
				}
				
				parm.invite(invited, party);
				p.sendMessage(ChatColor.GREEN + "Sent in invitation to " + invited.getName());
				
				parm.typingName.remove(p.getUniqueId());
			}else{
				p.sendMessage(ChatColor.RED + "Could not find player name " + e.getMessage());
				
				parm.typingName.remove(p.getUniqueId());
			}
		}
	}
	
	public void test(PlayerInteractAtEntityEvent e){
		CraftEntity ce = (CraftEntity) e.getRightClicked();
		Entity en = ce.getHandle();
		PacketPlayOutCamera camera = new PacketPlayOutCamera(en);
        
		((CraftPlayer)e.getPlayer()).getHandle().playerConnection.sendPacket(camera);
		
		Bukkit.broadcastMessage("test");
	}
	
}
