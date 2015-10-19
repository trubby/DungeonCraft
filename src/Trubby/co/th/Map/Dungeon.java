package Trubby.co.th.Map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.EulerAngle;

import Trubby.co.th.DG;
import Trubby.co.th.Util.ScoreboardUtils;
import Trubby.co.th.Util.StrUtil;
import net.md_5.bungee.api.ChatColor;

public class Dungeon {
	
	/** - state -
	 *  0 = Idle
	 *  1 = start
	 */ 
	
	//TODO 
	
	// ID prevent same world
	// World name
	
	public String worldName;
	public String worldKind;
	public BukkitTask locLogTask;
	public int level;
	
	public ArrayList<UUID> players = new ArrayList<>();
	public ArrayList<Room> rooms = new ArrayList<>();
	public HashMap<ArmorStand, UUID> deathBodies = new HashMap<>();
	public HashMap<UUID, Location> locLog = new HashMap<>();
	public ArrayList<UUID> revivingAS = new ArrayList<>();
	public int step = 0;
	public int state = 0;
	
	public ArrayList<Location> openedChests = new ArrayList<Location>(); //TODO
	public World world;
	public Location startLoc;
	public Scoreboard sb;
	
	// init
	public Dungeon(String worldName, String worldKind, int level) {
		this.worldName = worldName;
		this.worldKind = worldKind;
		this.level = level;
	}
	
	public void spawnMobs(Room room){
		Room r = rooms.get(step);
		r.runSpawn();
	}
	
	public void deathNormal(Player p){
		
		Location loc = p.getLocation();
		
		if(!p.getLocation().add(0, -1, 0).getBlock().getType().isSolid()){
			loc = locLog.get(p.getUniqueId());
		}
		
		broadCast(ChatColor.RED + "" + ChatColor.BOLD + "!!! " + ChatColor.RED + "" + ChatColor.YELLOW + p.getName() + ChatColor.RED + " died." + ChatColor.WHITE + " (Right click " + ChatColor.UNDERLINE + "with bare hand" + ChatColor.RED + ")" + ChatColor.GRAY + " to revive this player.");
		
		ArmorStand as = (ArmorStand) p.getWorld().spawnEntity(loc.add(0, -1.2, 0), EntityType.ARMOR_STAND);
		as.setGravity(false);
		as.setCustomName(ChatColor.RED + p.getName() + ChatColor.WHITE + " (Right Click to revive)");
		as.setCustomNameVisible(true);
		as.setHeadPose(new EulerAngle(-1.4, 0, 0));
		as.setBodyPose(new EulerAngle(-1.5, 0, 0));
		as.setRightArmPose(new EulerAngle(-1.4, 0, 0));
		as.setLeftArmPose(new EulerAngle(-1.4, 0, 0));
		as.setVisible(false);
		
		SkullMeta sm = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
		sm.setOwner(p.getName());
		ItemStack is = new ItemStack(Material.SKULL_ITEM);
		is.setDurability((short) 3);
		is.setItemMeta(sm);
		
		as.setChestplate(p.getInventory().getChestplate());
		as.setHelmet(is);
		as.setItemInHand(new ItemStack(p.getItemInHand()));
		
		p.setGameMode(GameMode.SPECTATOR);
		
		deathBodies.put(as, p.getUniqueId());
		
		@SuppressWarnings("deprecation")
		Score score = sb.getObjective(DisplaySlot.SIDEBAR).getScore(p);
		score.setScore(score.getScore() - 1);
		
		if(checkAllDeath()){
			Bukkit.broadcastMessage("all death");
		}
	}
	
	public void revive(Player reviver, Player revived, ArmorStand as){
		@SuppressWarnings("deprecation")
		Score score = sb.getObjective(DisplaySlot.SIDEBAR).getScore(revived);
		if(score.getScore() < 1){
			reviver.sendMessage(ChatColor.RED + revived.getName() + " has no more life left.");
			return;
		}
		
		new ReviveTask(this, reviver, revived, as).runTaskTimer(DG.plugin, 0, 20);
	}
	
	public void successRevive(Player reviver, Player revived, ArmorStand as){
		reviver.sendMessage(ChatColor.GREEN + "Successful reving " + revived.getName() + "!");
		revived.sendMessage(ChatColor.GREEN + "You have beed revived by " + reviver.getName() + "!");
		
		revived.teleport(as.getLocation().add(0, 1.3, 0));
		revived.setGameMode(GameMode.SURVIVAL);
		
		as.remove();
		deathBodies.remove(as);
	}
	
	public void tryToUsePortal(){
		boolean doStandOnPortal = true;
		for(UUID uuid : players){
			Material mat = Bukkit.getPlayer(uuid).getLocation().add(0, -0.5, 0).getBlock().getType();
			if(mat != Material.WOOL){
				doStandOnPortal = false;
			}
		}
		
		if(doStandOnPortal){
			goNextLevel();
		}else{
			broadCast(ChatColor.RED + "Please stand inside the portal to proceed next level.");
		}
	}
	
	public void goNextLevel(){
		
	}
	
	public boolean checkAllDeath(){
		for(UUID uuid : players){
			if(Bukkit.getPlayer(uuid).getGameMode() == GameMode.ADVENTURE){
				return false;
			}
		}
		return true;
	}

	public void broadCast(String str){
		for(UUID uuid : players){
			Bukkit.getPlayer(uuid).sendMessage(str);
		}
	}
	
	public void start(){
		//SETUP NEW SCOREBOARD
		this.sb = ScoreboardUtils.build(ChatColor.RED + worldKind + ChatColor.WHITE + "" + ChatColor.ITALIC + StrUtil.getLevelText(level) + ChatColor.GRAY + " | " + ChatColor.RED + "life", players);
		//TELEPORT ALL PLAYER TO
		for(UUID uuid : players){
			Player p = Bukkit.getPlayer(uuid);
			p.teleport(startLoc);
			p.setScoreboard(sb);
		}
		
		locLogTask = new LocLogTask(this).runTaskTimer(DG.plugin, 10, 100);
		
		broadCast("START!");
	}
	
	public void reset(){
		for(UUID uuid : players){
			Player p = Bukkit.getPlayer(uuid);
			p.teleport(DG.plugin.lobby);
			p.setGameMode(GameMode.ADVENTURE);
			p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
			
			//TODO reset scoreboard
		}
		
		players.clear();
		
		for(ArmorStand as : deathBodies.keySet()){
			as.remove();
		}
	}
	
	//TODO reset
	//TODO start
	//TODO finish
	//TODO scoreboard

}
