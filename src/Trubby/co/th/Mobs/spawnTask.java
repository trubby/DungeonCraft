package Trubby.co.th.Mobs;

import java.util.Random;

import net.elseland.xikage.MythicMobs.Mobs.MythicMob;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import Trubby.co.th.DG;
import Trubby.co.th.Map.Room;

public class spawnTask extends BukkitRunnable {
	
	int loops;
	int counter = 0;
	
	Random ran = new Random();
	Room room;
	
	
	public spawnTask(Room room) {
		this.loops = room.spawners.size()/3;
		this.room = room;
		
		Bukkit.broadcastMessage("runspawntask!");
	}

	@Override
	public void run() {
		counter++;
		if(counter <= loops){
			for (int i = 0; i < 2; i++) {
				
				Location ranloc = room.spawners.get(ran.nextInt(room.spawners.size())).clone().add(0.5, 0, 0.5);
				MythicMob mm = room.mobs.get(ran.nextInt(room.mobs.size())); 
				
				new spawnEffect(room, ranloc, mm).runTaskTimer(DG.plugin, 0L, 12L);
			}
			
		}else{
			Bukkit.broadcastMessage("cancel");
			this.cancel();
			room.dg.step++;
			room.spawning = false;
		}
		
		// TODO Auto-generated method stub
		
	}
	
	

}
