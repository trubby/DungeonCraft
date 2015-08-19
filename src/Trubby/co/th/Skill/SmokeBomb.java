package Trubby.co.th.Skill;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import Trubby.co.th.DG;
import Trubby.co.th.Particle.ParticleEffect;
import Trubby.co.th.Player.DGPlayer;

public class SmokeBomb {
	
	HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
	long requireCooldown = 1000 * 5; //cooldown time
	int manaCost = 10;
	
	public void use(Player p){
		
		//	COOLDOWN CHECK
		if(cooldown.containsKey(p.getUniqueId())){
			Bukkit.broadcastMessage("contain");
			long castMillis = cooldown.get(p.getUniqueId());
			long currentCooldown = System.currentTimeMillis() - castMillis;
			
			p.sendMessage("cooldown : " + (requireCooldown - currentCooldown));
			return;
		}else{
			//PUT & AUTO REMOVE
			Bukkit.broadcastMessage("put");
			
			@SuppressWarnings("unused")
			int taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(DG.plugin, new Runnable() {
				
				@Override
				public void run() {
					cooldown.remove(p.getUniqueId());
					Bukkit.broadcastMessage("auro remove");
				}
			}, requireCooldown / 50);
			
			cooldown.put(p.getUniqueId(), System.currentTimeMillis());
		}
		
		//	MANA CHECK
		DGPlayer dgp = DG.plugin.dpm.getDGPlayer(p);
		if(dgp.mana >= manaCost){
			dgp.mana = dgp.mana - manaCost;
			dgp.updateManaBar();
		}else{
			return;
		}
		
		Item i = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.FIREWORK_CHARGE));
		i.setPickupDelay(Integer.MAX_VALUE);
		i.setVelocity(p.getLocation().getDirection().multiply(1.5));
		p.getWorld().playSound(p.getLocation(), Sound.SHOOT_ARROW, 1f, 1f);
		
		new SmokeBombTask(p, i, this).runTaskLater(DG.plugin, 25L);
		
	}

}

class SmokeBombTask extends BukkitRunnable{
	
	int counter = 0;
	Player p;
	Item i;
	SmokeBomb sm;
	
	public SmokeBombTask(Player p, Item i, SmokeBomb sm) {
		this.i = i;
		this.p = p;
		this.sm = sm;
	}
	
	@Override
	public void run() {

			for(Entity e : i.getNearbyEntities(3, 2, 3)){
				if(DG.plugin.mm.isMonster(e)){
					LivingEntity le = (LivingEntity) e;
					le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 10, true, false), true);
					ParticleEffect.SLIME.display(0f, 0f, 0f, 0f, 5, le.getLocation(), 20);
				}
			}
			
			p.getWorld().playSound(i.getLocation(), Sound.FUSE, 1f, 1f);
			ParticleEffect.SMOKE_LARGE.display(3f, 1.5f, 3f, 0f, 15, i.getLocation(), 20);// smokebomb explode
			ParticleEffect.SMOKE_NORMAL.display(3f, 1.5f, 3f, 0f, 15, i.getLocation(), 20);
			ParticleEffect.CLOUD.display(3f, 1.5f, 3f, 0f, 15, i.getLocation(), 20);
			
			//ParticleEffect.MOB_APPEARANCE.display(0f, 0f, 0f, 1f, 1, p.getLocation(), 20);
			
			i.remove();
			
	}
	
	
	
}
