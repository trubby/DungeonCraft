package Trubby.co.th.Listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import Trubby.co.th.DG;
import Trubby.co.th.Map.Dungeon;
import Trubby.co.th.Map.Room;
import Trubby.co.th.Particle.ParticleEffect;
import Trubby.co.th.Player.DGPlayer;
import Trubby.co.th.Player.Economy;
import Trubby.co.th.Util.ActionBarAPI;
import net.md_5.bungee.api.ChatColor;

public class PlayerListener implements Listener{

	Vector vMin = null;
	Vector vMax = null;
	
	Vector v2Min = null;
	Vector v2Max = null;
	
	/*
	 * 		PLAYER JOIN [WELCOME]
	 */
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		DG.plugin.dpm.addPlayer(p);
		DG.plugin.nms.sendTitleToPlayer(p, ChatColor.RED + "Dungeon" + ChatColor.GOLD + "Craft" + ChatColor.YELLOW + "!", "It's time!", 10, 50, 30);
		ParticleEffect.MOB_APPEARANCE.display(0, 0, 0, 0.1f, 1, p.getLocation(), p);
	}
	
	@EventHandler
	public void PlayerInter(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(p.getItemInHand().getType() == Material.APPLE){
			vMin = p.getLocation().toVector();
			p.sendMessage("vmin");
		}else if(p.getItemInHand().getType() == Material.GOLDEN_APPLE){
			vMax = p.getLocation().toVector();
			p.sendMessage("vmax");
		}else if(p.getItemInHand().getType() == Material.PORK){
			v2Min = p.getLocation().toVector();
			p.sendMessage("v2min");
		}else if(p.getItemInHand().getType() == Material.ARROW){
			v2Max = p.getLocation().toVector();
			p.sendMessage("v2max");
		}else if(p.getItemInHand().getType() == Material.ARROW){
			v2Max = p.getLocation().toVector();
			p.sendMessage("v2max");
		}
	}
	
	//MOB LOOT MONEY
	@EventHandler
	public void onPickupItem(PlayerPickupItemEvent e){
		ItemStack is = e.getItem().getItemStack();
		if(is.getType() == Material.GOLD_NUGGET){
			e.setCancelled(true);
			
			Player p = e.getPlayer();
			
			Economy.addMoney(p, is.getAmount());
			p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1f, 1f);
			
			e.getItem().remove();
		}
	}
	
	/**
	 *		DUNGEON REGION CHECK
	 */
	@EventHandler
	public void onPlayerMoveInDungeon(PlayerMoveEvent e) {
		if(e.getPlayer().getGameMode() == GameMode.SPECTATOR)return;
		if ((e.getFrom().getBlockX() != e.getTo().getBlockX()) || (e.getFrom().getBlockZ() != e.getTo().getBlockZ())) {
			
			final Player p = e.getPlayer();
			Dungeon d = DG.plugin.dm.getDungeon(p);
			
			if (d != null) {
				final Vector pVec = e.getTo().toVector();

				if (d.step >= d.rooms.size()) {
					return;
				}

				Room r = d.rooms.get(d.step);
				if(!r.spawning){
					if (contains(r.min, r.max, pVec)) {
						p.sendMessage("You're enter " + d.step);
						d.spawnMobs(r);
	
						return;
					}
				}else if(d.step < d.rooms.size() - 1){
					Room r2 = d.rooms.get(d.step + 1);
					if (contains(r2.min, r2.max, pVec)) {
						p.teleport(e.getFrom());
						p.sendMessage(ChatColor.RED + "You cannot pass yet!");
	
						return;
					}
				}
			}
		}
	}
	
	//CHECK PLAYER IN REGION
	public boolean contains(Vector vMin, Vector vMax, Vector pt) {
		double x = pt.getX();
		double y = pt.getY();
		double z = pt.getZ();
		
		if(vMin == null){
			Bukkit.broadcastMessage("min null");
			return false;
		}
		
		if(vMax == null){
			Bukkit.broadcastMessage("max null");
			return false;
		}
		
		return (x >= vMin.getBlockX()) && (x < vMax.getBlockX() + 1)
				&& (y >= vMin.getBlockY())
				&& (y < vMax.getBlockY() + 1)
				&& (z >= vMin.getBlockZ())
				&& (z < vMax.getBlockZ() + 1);
	}
	
	/**
	 * 		SPRINTING STAMINA
	 */
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		if ((e.getFrom().getBlockX() != e.getTo().getBlockX()) || (e.getFrom().getBlockZ() != e.getTo().getBlockZ())) {
			Player p = e.getPlayer();
			
			if(!p.isSprinting())return;
			
			DGPlayer dgp = DG.plugin.dpm.getDGPlayer(p);
			if(dgp != null){
				if(!dgp.isSprinting){
					dgp.runningTask();
				}
			}
		}
	}
	
	/*
	 * 		FORCE DAMAGE CALCULATOR
	 */
	@EventHandler
	public void onAttack(EntityDamageByEntityEvent e){
		if(e.getDamager() instanceof Player){
			Player p = (Player) e.getDamager();
			
			if(p.getWorld().getName().equalsIgnoreCase("DG_lobby")){
				e.setDamage(0);
				return;
			}
			
			Bukkit.broadcastMessage("" + e.getDamage());
			
			DGPlayer dgp = DG.plugin.dpm.getDGPlayer(p);
			e.setDamage(dgp.swordLevel + 1);
			
			LivingEntity le = (LivingEntity) e.getEntity();
			
			int currentHealth;
			if(e.getDamage() >= le.getHealth()){
				currentHealth = 0;
			}else{
				currentHealth = (int) (le.getHealth() - e.getDamage());
			}
			
			ActionBarAPI.send(p, ChatColor.RED + "" + le.getType() + " : " + ChatColor.RESET + ChatColor.ITALIC + "" + currentHealth + "/" + le.getMaxHealth());
			
		}else if(e.getDamager() instanceof Arrow){
			if(((Arrow)e.getDamager()).getShooter() instanceof Player){
				Player p = (Player) ((Arrow)e.getDamager()).getShooter();
				
				if(p.getWorld().getName().equalsIgnoreCase("DG_lobby")){
					e.setDamage(0);
					return;
				}
				
				LivingEntity le = (LivingEntity) e.getEntity();
				DGPlayer dgp = DG.plugin.dpm.getDGPlayer(p);
				if(e.getDamage() <5){
					e.setDamage(1 + dgp.bowLevel + 1);
				}else if(e.getDamage() <10){
					e.setDamage(2 + dgp.bowLevel + 1);
				}else if(e.getDamage() <10){
					e.setDamage(3 + dgp.bowLevel + 1);
				}
				
				int currentHealth;
				if(e.getDamage() >= le.getHealth()){
					currentHealth = 0;
				}else{
					currentHealth = (int) (le.getHealth() - e.getDamage());
				}
				
				ActionBarAPI.send(p, ChatColor.RED + "" + le.getType() + " : " + ChatColor.RESET + ChatColor.ITALIC + "" + currentHealth + "/" + le.getMaxHealth());
			}
		}
	}
	
	//PREVENT LOBBY DAMAGE
	@EventHandler
	public void onDamage(EntityDamageEvent e){
		LivingEntity le = isLivingEntity(e.getEntity());
		
		if(le != null){
			if(le.getWorld().getName().equalsIgnoreCase("DG_lobby")){
				if(e.getDamage() > 0){
					e.setDamage(0);
				}
				return;
			}
		}
		
		if(e.getEntityType() == EntityType.ENDER_CRYSTAL){
			e.setCancelled(true);
		}
	}
	
	public LivingEntity isLivingEntity(Entity e){
		
		if(e.getType() == EntityType.VILLAGER || e.getType() == EntityType.ZOMBIE || 
				e.getType() == EntityType.SKELETON || e.getType() == EntityType.WITCH || 
				e.getType() == EntityType.PIG || e.getType() == EntityType.CHICKEN || 
				e.getType() == EntityType.HORSE || e.getType() == EntityType.IRON_GOLEM){
			
			
			return (LivingEntity) e;
		}
		return null;
	}
	
	/*
	 * 		PLAYER RESPAWN EVENT
	 */
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e){
		Dungeon d = DG.plugin.dm.getDungeon(e.getPlayer());
		if(d != null){
			e.setRespawnLocation(d.startLoc);
		}
	}
	
	/*
	 * 		PLAYER DEATH IN DUNGEON
	 */
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		Player p = e.getEntity();
		Dungeon d = DG.plugin.dm.getDungeon(p);
		
		if(d != null){
			d.deathNormal(p);
		}
	}
	
	@EventHandler
	public void onManipulateAS(PlayerArmorStandManipulateEvent e){
		Player p = e.getPlayer();
		ArmorStand as = e.getRightClicked();
		Dungeon d = DG.plugin.dm.getDungeon(p);
		
		if(d != null){
			e.setCancelled(true);
			d.revive(p, Bukkit.getPlayer(d.deathBodies.get(as)), as);
		}
	}
	
	@EventHandler
	void death(PlayerDeathEvent e) {
		final Player p = e.getEntity();
		if(!p.isDead())return;
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(DG.plugin, new Runnable() {
			public void run() {
				p.spigot().respawn();
				p.setCanPickupItems(false);
			}
		}, 15L);
	}

	@EventHandler
	public void respawn(PlayerRespawnEvent e) {
		e.getPlayer().setCanPickupItems(true);
	}
		
	
}
