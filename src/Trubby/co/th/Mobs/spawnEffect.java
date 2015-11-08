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
			case 1: new CircleEffect(1.3f, 15).play(spawnLoc);
				break;
			case 2: new CircleEffect(0.7f, 12).play(spawnLoc);
				break;
			case 3: new CircleEffect(0.4f, 9).play(spawnLoc);
				break;
			default:
				break;
			}
		}else{
			mm.spawn(spawnLoc, 1); //TODO add entity to room
			ParticleEffect.FLAME.display(0f, 0f, 0f, 0.3f, 10, spawnLoc.clone().add(0,1,0), 16);
			this.cancel();
		}
	}
}
