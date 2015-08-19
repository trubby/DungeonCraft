package Trubby.co.th.Map;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LocLogTask extends BukkitRunnable{
	
	Dungeon d;

	public LocLogTask(Dungeon d) {
		this.d = d;
	}

	@Override
	public void run() {
		if(d.players.size() > 0){
			
			for(UUID uuid : d.players){
				Player p = Bukkit.getPlayer(uuid);
				Location loc = p.getLocation().add(0,-1,0);
				if(loc.getBlock().isLiquid() || loc.getBlock().getType() == Material.AIR){
					
				}else{
					d.locLog.put(uuid, p.getLocation());
				}
			}
			
			
		}else{
			this.cancel();
		}
		
		
	}
	
}
