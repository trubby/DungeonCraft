package Trubby.co.th.Map;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import Trubby.co.th.DG;
import Trubby.co.th.Util.LocUtil;

public class DungeonManager {
	
	public ArrayList<Dungeon> dungeons = new ArrayList<>();
	public ArrayList<Integer> ST_takenId = new ArrayList<>();
	
	//GET PLAYER's DUNGEON
	public Dungeon getDungeon(Player p){
		for(Dungeon d : dungeons){
			if(d.players.contains(p.getUniqueId())){
				return d;
			}
		}
		return null;
	}
	
	//GET DUNGEON FROM WORLD
	public Dungeon getDungeon(World w){
		for(Dungeon d : dungeons){
			if(d.world == w){
				return d;
			}
		}
		return null;
	}
	
	//GET IDLE DUNGEON
	public Dungeon getFreeDungeon(){
		for(Dungeon d : dungeons){
			if(d.state == 0){
				return d;
			}
		}
		return null;
	}
	
	public void saveDungeon(Dungeon d){
		File f = DG.plugin.fm.dungeonCreateFile(d.worldKind, d.worldName);
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		
		fc.set("StartLocation", LocUtil.locationToString(d.startLoc));
		
		int count = 1;
		for(Room r : d.rooms){
			String roomPath = "Room_" + count;
			
			fc.set(roomPath + ".Min", LocUtil.vectorToString(r.min));
			fc.set(roomPath + ".Max", LocUtil.vectorToString(r.max));
			
			fc.set(roomPath + ".Spawners", LocUtil.toStringList(r.spawners));
			count++;
		}
		
		try {
			fc.save(f);
		} catch (IOException e) {
			System.out.println("CANNOT SAVE!!!!!!!!!!!!");
		}
	}
	
	public Dungeon loadDungeon(String kindName, String worldName, int level){//TODO ADD LEVEL OR MODE OR MAP
		DG.plugin.gen.create(worldName, true);
		
		File folder = new File(DG.plugin.getDataFolder(), kindName);
		File f = new File(folder, worldName + ".yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		Location startLoc = LocUtil.stringToLocation(fc.getString("StartLocation"));
		
		//DUNGEON
		Dungeon d = new Dungeon(worldName, "Stronghold", level); //TODO remove
		d.startLoc = startLoc;
		d.world = d.startLoc.getWorld();
		
		for (int i = 1; i <= 10; i++) {
			String roomPath = "Room_" + i;

			Vector min = LocUtil.stringToVec(fc.getString(roomPath + ".Min"));
			Vector max = LocUtil.stringToVec(fc.getString(roomPath + ".Max"));
			
			//ROOM
			Room r = new Room(d, min, max);
			for(String loc_str : fc.getStringList(roomPath + ".Spawners")){
				r.spawners.add(LocUtil.stringToLocation(loc_str));
			}
			
			//TODO getLevelMonster
			d.rooms.add(r);
		}
		
		DG.plugin.dm.dungeons.add(d);
		return d;
		
	}
	
	
}
