package Trubby.co.th.Mobs;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import net.elseland.xikage.MythicMobs.Mobs.MobCommon;
import net.elseland.xikage.MythicMobs.Mobs.MythicMob;

public class MobManager {
	
	public ArrayList<MythicMob> sh_es = new ArrayList<MythicMob>();
	
	// INIT
	public MobManager() {
		addStronghold();
		addMonstersCheckList();
	}
	
	/**
	 * 			EACH LV MONSTERS
	 */
	public void addStronghold(){
		sh_es.add(MobCommon.getMythicMob("lv1_skeleton"));
		sh_es.add(MobCommon.getMythicMob("lv1_zombie"));
		sh_es.add(MobCommon.getMythicMob("lv1_creeper"));
		
		//TODO more mobs
	}

	/**
	 * 			IS MONSTER CHECK
	 */
	ArrayList<EntityType> monsters = new ArrayList<EntityType>();
	
	public void addMonstersCheckList(){
		Collections.addAll(monsters, EntityType.ZOMBIE, EntityType.SKELETON, EntityType.CREEPER);
	}
	
	public boolean isMonster(Entity e){
		if(monsters.contains(e.getType())){
			return true;
		}
		return false;
	}
}
