package Trubby.co.th.SkillList;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import Trubby.co.th.DG;
import Trubby.co.th.Particle.ParticleEffect;
import Trubby.co.th.Player.DGPlayer;
import Trubby.co.th.Util.VectorUtil;
import net.md_5.bungee.api.ChatColor;

public class Hook {
	
	int manaCost = 4;
	
	public void use(Player p){
		
		//	MANA CHECK
		DGPlayer dgp = DG.plugin.dpm.getDGPlayer(p);
		if(dgp.mana >= manaCost){
			dgp.mana = dgp.mana - manaCost;
			dgp.updateManaBar();
		}else{
			return;
		}
		
		Item i = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.NETHER_STAR));
		i.setPickupDelay(Integer.MAX_VALUE);
		i.setVelocity(p.getLocation().getDirection().multiply(1.5));
		p.getWorld().playSound(p.getLocation(), Sound.SHOOT_ARROW, 1f, 1f);
		new HookTask(p, i, this).runTaskTimer(DG.plugin, 4L, 1L);
	}
	
	public ItemStack item(){
		
		ItemStack is = new ItemStack(Material.CLAY_BALL);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName("Hook " + ChatColor.GRAY + "(left click)");
		
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Utility Skill");
		lore.add(ChatColor.WHITE + "\"Hook target enemy to yourslef or.");
		lore.add(ChatColor.WHITE + "hook yourself to other player.\"");
		lore.add(ChatColor.GRAY + "Mana : " + ChatColor.GREEN + "4");
		lore.add(ChatColor.GRAY + "Cooldown : " + ChatColor.GREEN + "0 sec.");
		im.setLore(lore);
		
		is.setItemMeta(im);
		
		return is;
	}

}

class HookTask extends BukkitRunnable{
	
	int counter = 0;
	Player p;
	Item i;
	Hook sm;
	
	public HookTask(Player p, Item i, Hook sm) {
		this.i = i;
		this.p = p;
		this.sm = sm;
	}
	
	@Override
	public void run() {

		if(counter <= 20){
			counter++;
			
			for(Entity e : i.getNearbyEntities(0.4, 0.8, 0.4)){
				if(DG.plugin.mm.isMonster(e)){
					VectorUtil.pullEntityToLocation(e, p.getLocation());
					p.getWorld().playSound(p.getLocation(), Sound.SLIME_WALK, 1f, 1f);
					ParticleEffect.SMOKE_LARGE.display(0.2f, 0.2f, 0.2f, 0.3f, 5, i.getLocation(), 20);
					i.remove();
					this.cancel();
				}else if(e instanceof Player){
					Player targetP = (Player) e;
					if(targetP != p){
						VectorUtil.pullEntityToLocation(p, e.getLocation());
						p.getWorld().playSound(p.getLocation(), Sound.SLIME_WALK, 1f, 1f);
						ParticleEffect.SMOKE_LARGE.display(0.2f, 0.2f, 0.2f, 0.3f, 5, i.getLocation(), 20);
						i.remove();
						this.cancel();
					}
				}
			}
			
		}else{
			this.cancel();
			i.remove();
		}
		
	}
	
	
	
}
