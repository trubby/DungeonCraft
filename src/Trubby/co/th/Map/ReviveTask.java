package Trubby.co.th.Map;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class ReviveTask extends BukkitRunnable{

	double x;
	double y;
	double z;
	Dungeon d;
	Player reviver;
	Player revived;
	ArmorStand as;
	String oldString;
	
	int counter = 6;
	
	public ReviveTask(Dungeon d, Player reviver, Player revived, ArmorStand as) {
		// TODO Auto-generated constructor stub
		
		this.d = d;
		this.reviver = reviver;
		this.revived = revived;
		this.as = as;
		this.oldString = as.getCustomName();
		
	}

	@Override
	public void run() {
		counter--;
		if(counter >= 1){
			
			if(counter == 4){
				Location loc = reviver.getLocation();
				
				x = loc.getX();
				y = loc.getY();
				z = loc.getZ();
				return;
			}else if(counter == 5){
				as.setCustomName(ChatColor.GREEN + "Reviving " + counter + "...");
				return;
			}
			
			Location loc = reviver.getLocation();
			
			if(loc.getX() != x || loc.getY() != y || loc.getZ() != z){
				as.setCustomName(oldString);
				reviver.sendMessage(ChatColor.RED + "Do not move while reviving!");
				this.cancel();
			}else{
				as.setCustomName(ChatColor.GREEN + "Reviving " + counter + "...");
			}
			
		}else{
			d.successRevive(reviver, revived, as);
			this.cancel();
		}
		
		// TODO Auto-generated method stub
		
	}
	
}
