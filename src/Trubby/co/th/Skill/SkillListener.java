package Trubby.co.th.Skill;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import Trubby.co.th.DG;

public class SkillListener implements Listener {

	@EventHandler
	public void PlayerUseSkill(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(p.getInventory().getHeldItemSlot() == 2){
			DG.plugin.sm.hook.use(p);
		}else if(p.getInventory().getHeldItemSlot() == 3){
			DG.plugin.sm.smokeBomb.use(p);
		}
	}
	
	
}
