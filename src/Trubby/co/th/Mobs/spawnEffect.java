package Trubby.co.th.Mobs;


import net.elseland.xikage.MythicMobs.Mobs.MythicMob;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import Trubby.co.th.Map.Room;
import Trubby.co.th.Particle.CircleEffect;
import Trubby.co.th.Particle.ParticleEffect;

public class spawnEffect extends BukkitRunnable {
	
	int counter = 0;
	
	Room room;
	Location spawnLoc;
	MythicMob mm;
	
	public spawnEffect(Room room,Location spawnLoc, MythicMob mm) {
		this.room = room;
		this.spawnLoc = spawnLoc;
		this.mm = mm;
	}

	@Override
	public void run() {
		counter++;
		if(counter <= 3){
			switch (counter) {
			case 1: new CircleEffect(0.3f, 8).play(spawnLoc);
				break;
			case 2: new CircleEffect(0.2f, 8).play(spawnLoc);
				break;
			case 3: new CircleEffect(0.1f, 8).play(spawnLoc);
				break;
			default:
				break;
			}
		}else{
			mm.spawn(spawnLoc, 1); //TODO add entity to room
			ParticleEffect.FLAME.display(0.5f, 0.5f, 0.5f, 0.2f, 7, spawnLoc.clone().add(0,1,0), 12);
			this.cancel();
		}
	}
}
