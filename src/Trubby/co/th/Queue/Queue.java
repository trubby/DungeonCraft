package Trubby.co.th.Queue;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

import Trubby.co.th.DG;

public class Queue {
	
	public ArrayList<UUID> players = new ArrayList<UUID>(); //max 4
	
	public String worldType = "";
	
	public Queue(Player p, String name) {
		players.add(p.getUniqueId());
		worldType = name;
		
		new QueueTask(this).runTaskTimer(DG.plugin, 2, 10);
	}
	
	//TODO task

}
