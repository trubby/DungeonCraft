package Trubby.co.th.Listener;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import Trubby.co.th.DG;
import Trubby.co.th.Particle.ParticleEffect;

public class MobsListener implements Listener {

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		event.blockList().clear(); // clears list of blocks to be blown up
	}
	
	@EventHandler
	public void onItemDestroy(EntityDamageEvent e){
		if(e.getEntityType() == EntityType.DROPPED_ITEM){
			e.setCancelled(true);
		}
	}
	
	HashMap<UUID, Integer> creeperTask = new HashMap<UUID, Integer>();
	
	@EventHandler
	public void onEntityTarget(EntityTargetLivingEntityEvent e){
		if(e.getEntityType() == EntityType.CREEPER){
			final Creeper c = (Creeper) e.getEntity();
			if(!creeperTask.containsKey(c.getUniqueId())){
				int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(DG.plugin, new Runnable() {
					@Override
					public void run() {
						if(!c.isDead()){
							if(c.getTarget() == null){
								Bukkit.getScheduler().cancelTask(creeperTask.get(c.getUniqueId()));
								creeperTask.remove(c.getUniqueId());
								return;
							}
							
							ParticleEffect.LAVA.display(0f, 0f, 0f, 0f, 1, c.getLocation().add(0, 1, 0), 20);
							c.getLocation().getWorld().playSound(c.getLocation(), Sound.FIZZ, 1f, 1f);
						}else{
							Bukkit.getScheduler().cancelTask(creeperTask.get(c.getUniqueId()));
							creeperTask.remove(c.getUniqueId());
						}
					}
				}, 1L, 10L);
				creeperTask.put(c.getUniqueId(), taskId);
			}
		}
	}
	
	

}
