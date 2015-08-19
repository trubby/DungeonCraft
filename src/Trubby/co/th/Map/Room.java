package Trubby.co.th.Map;

import java.util.ArrayList;
import java.util.Random;

import net.elseland.xikage.MythicMobs.Mobs.MythicMob;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import Trubby.co.th.DG;
import Trubby.co.th.Mobs.spawnTask;

public class Room {
	
	Random ran = new Random();
	public Dungeon dg;
	public boolean spawning = false;
	
	public ArrayList<Location> spawners = new ArrayList<>();
	public ArrayList<MythicMob> mobs = DG.plugin.mm.sh_es; //TODO Entity DG
	
	public Vector min;
	public Vector max;
	
	public Room(Dungeon dg, Vector min, Vector max) {
		this.dg = dg;
		this.min = min;
		this.max = max;
	}
	
	@SuppressWarnings("unused")
	public void runSpawn(){
		
		int amount = spawners.size()/3;
		BukkitTask task = new spawnTask(this).runTaskTimer(DG.plugin, 20L, 70L);
		spawning = true;
		
	}
	
	//TODO
	public void getMonster(int Mode, int Level){
		
		
		
		
		
		
	}

}
