package Trubby.co.th.Player;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DGPlayerManager {
	
	HashMap<UUID, DGPlayer> players = new HashMap<>();
	
	public void addPlayer(Player p){
		
		players.put(p.getUniqueId(), new DGPlayer(p));
		
	}
	
	public DGPlayer getDGPlayer(Player p){
		if(players.containsKey(p.getUniqueId())){
			return players.get(p.getUniqueId());
		}
		
		return null;
	}
	
	public void refreshOnlinePlayer(){
		for(Player p : Bukkit.getOnlinePlayers()){
			addPlayer(p);
		}
	}
	
	//TODO remove player
	

}
