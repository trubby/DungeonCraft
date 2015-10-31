package Trubby.co.th.Inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import Trubby.co.th.DG;
import Trubby.co.th.Player.DGPlayer;
import net.md_5.bungee.api.ChatColor;

public class NPCListener implements Listener{

	@EventHandler (priority = EventPriority.HIGHEST)
	public void onClickNPC(PlayerInteractEntityEvent e){
		if(e.getPlayer().getWorld().getName().equalsIgnoreCase("DG_lobby")){
			
			if(ChatColor.stripColor(e.getRightClicked().getCustomName()).equalsIgnoreCase("Health Upgrade!")){
				e.setCancelled(true);
				DGPlayer dgp = DG.plugin.dpm.getDGPlayer(e.getPlayer());
				DG.plugin.hgui.openHealthGui(dgp);
			}else if(ChatColor.stripColor(e.getRightClicked().getCustomName()).equalsIgnoreCase("Equip Upgrade!")){
				e.setCancelled(true);
				DG.plugin.invm.openEquipUpgrade(e.getPlayer());
			}
		}
	}
	
}
