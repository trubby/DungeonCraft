package Trubby.co.th.Queue;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import Trubby.co.th.DG;
import Trubby.co.th.Map.Dungeon;
import net.md_5.bungee.api.ChatColor;

public class QueueTask extends BukkitRunnable{
	
	ArrayList<String> randomLoadingText = new ArrayList<>();//TODO move to somewhere else
	
	Random ran = new Random();
	int minPlayer = 2;
	
	Queue q;
	public QueueTask(Queue q) {
		this.q = q;
		
		randomLoadingText.add("Are you ready?");
	}
	
	boolean generating = false;
	byte countdown = 30;
	
	Dungeon d;
	
	@Override
	public void run() {
		if(generating){
			if(countdown > 1){
				//GEN
				if(countdown == 15){
					int randomID = ran.nextInt(50);
					d = DG.plugin.dm.loadDungeon(q.worldType,q.worldType + "1", 1);//TODO Random
					DG.plugin.dm.ST_takenId.add(randomID);
				}
				
				if(countdown == 10){
					for(UUID uuid : q.players){
						d.players.add(uuid);
					}
				}
				
				sendTitleGenerating();
				countdown--;
			}else{
				//TODO START
				d.start();
				this.cancel();
				//TODO cancel
			}
			return;
		}
		
		if(q.players.size() >= (minPlayer)){
			generating = true;
		}
		sendTitleWaiting();
	}
	
	//TODO START
	//TODO count
	
	byte slot = 1;
	
	public void sendTitleWaiting(){
		String title;
		String subTitle;
		
		if(slot == 1){
			title = "Waiting for  |";
			subTitle = 4 - q.players.size() + " more player...";
			slot++;
		}else if(slot == 2){
			title = "Waiting for /";
			subTitle = 4 - q.players.size() + " more player...";
			slot++;
		}else if(slot == 3){
			title = "Waiting for | ";
			subTitle = 4 - q.players.size() + " more player...";
			slot++;
		}else{
			title = "Waiting for \\";
			subTitle = 4 - q.players.size() + " more player...";
			slot = 1;
		}
		
		for(UUID uuid : q.players){
			DG.plugin.nms.sendTitleToPlayer(Bukkit.getPlayer(uuid), ChatColor.AQUA + title, subTitle, 0, 20, 0);
		}
	}
	
	public void sendTitleGenerating(){
		String title;
		String subTitle;
		
		if(slot == 1){
			title = "Generating  |";
			subTitle ="are you ready?";
			slot++;
		}else if(slot == 2){
			title = "Generating /";
			subTitle ="are you ready?";
			slot++;
		}else if(slot == 3){
			title = "Generating | ";
			subTitle ="are you ready?";
			slot++;
		}else{
			title = "Generating \\";
			subTitle ="are you ready?";
			slot = 1;
		}
		
		for(UUID uuid : q.players){
			DG.plugin.nms.sendTitleToPlayer(Bukkit.getPlayer(uuid), ChatColor.GREEN + title, subTitle, 0, 20, 0);
		}
	}
	
}
