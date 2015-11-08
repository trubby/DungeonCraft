package Trubby.co.th.Player;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import Trubby.co.th.DG;
import Trubby.co.th.Upgrade.InvManager;

public class DGPlayer {
	
	public Player p;
	public UUID uuid;
	public int swordLevel = 0;
	public int bowLevel = 0;
	public int arrowLevel = 0;
	public int helmetLevel = 0;
	public int chestLevel = 0;
	public int legLevel = 0;
	public int bootsLevel = 0;
	//[][][][][][][][][][]
	//[][][][][][][][][][]
	public int healthLevel = 1;
	
	public int maxMana = 10; //TODO UPDATE MANA
	public int mana = 10;
	
	public long stamina = 5;
	public boolean isSprinting = false;
	
	public int gold = 5000;
	
	
	//INIT
	public DGPlayer(Player p) {
		this.p = p;
		this.uuid = p.getUniqueId();
		
		//CREATE or LOAD SQL
		
		p.getInventory().setHelmet(new ItemStack(Material.AIR));
		p.getInventory().setChestplate(new ItemStack(Material.AIR));
		p.getInventory().setLeggings(new ItemStack(Material.AIR));
		p.getInventory().setBoots(new ItemStack(Material.AIR));
		p.getInventory().clear();
		InvManager.updateAllInv(this);
		
		updateHealth(true);
	}
	
	//UPDATE HEALTH
	public void updateHealth(boolean heal){
		p.setHealthScale(healthLevel + 7);
		p.setMaxHealth(healthLevel + 7);
		if(heal){
			p.setHealth(healthLevel + 7);
		}
	}
	
	//UPDATE EQUIPMENTS
	public void updateEquipments(){
		InvManager.updateAllInv(this);
		
		stamina = bootsLevel + 2;
	}
	
	//STAMITA SPRINTING
	public void runningTask(){
		this.isSprinting = true;
		
		new RunningTask(this).runTaskTimerAsynchronously(DG.plugin, stamina, stamina);
	}
	
	public void updateManaBar(){
		p.setExp( (float)mana / (float)maxMana );
		p.setLevel(mana);
	}
	
}
