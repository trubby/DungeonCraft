package Trubby.co.th.Queue;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class QueueManager {

	public enum DungeonType{ST_E,ST_N,ST_H};
	
	public ArrayList<Queue> stronghold_easy = new ArrayList<Queue>();
	public ArrayList<Queue> stronghold_normal = new ArrayList<Queue>();
	public ArrayList<Queue> stronghold_hard = new ArrayList<Queue>();
	int min = 1;

	//GET DUNGEON QUEUE FROM TYPE INPUT
	public ArrayList<Queue> getDungeonQueue(DungeonType type){
		
		switch (type) {
		case ST_E:return stronghold_easy;
		case ST_N:return stronghold_normal;
		case ST_H:return stronghold_hard;
		default: break;
		}
		
		return null;
	}
	
	//GET DUNGEON TYPE IN STRING
	public String getTypeName(DungeonType type){
		
		switch (type) {
		case ST_E:return "Stronghold";
		case ST_N:return "Stronghold";
		case ST_H:return "Stronghold";
		default: break;
		}
		
		return null;
		
	}
	
	public void addMe(Player p, DungeonType type){
		
		ArrayList<Queue> queue = getDungeonQueue(type);
		boolean added = false;
		for(Queue q : queue){
			if(q.players.size() < 4){
				q.players.add(p.getUniqueId());
				added = true;
				break;
			}
		}
		
		if(!added){
			queue.add(new Queue(p, getTypeName(type)));
			Bukkit.broadcastMessage("ADDED NEW QUEUE");
		}
		
		
		
		
	}

	/*public void checkAvailable() {
		

		while (stronghold_easy.size() >= min) {

			Dungeon d = DG.plugin.dm.getFreeDungeon();
			if (d == null) {
				return;
			}

			for (UUID uuid : stronghold_easy) {
				Bukkit.getPlayer(uuid);
				d.players.add(uuid);
			}

			stronghold_easy.remove(0);

			d.start();
		}
	}*/

}
