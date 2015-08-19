package Trubby.co.th.Player;

import org.bukkit.entity.Player;

import Trubby.co.th.DG;
import Trubby.co.th.Inventory.InvManager;

public class Economy {
	
	//BUY STH.
	public static boolean takeMoney(Player p, int cost){
		
		DGPlayer dgp = DG.plugin.dpm.getDGPlayer(p);
		if(dgp!=null){
			if(dgp.gold >= cost){
				dgp.gold = dgp.gold - cost;
				InvManager.updateGoldSlot(dgp, true);
				return true;
			}else{
				return false;
			}
		}
		
		return false;
	}
	
	public static void addMoney(Player p, int amount){
		
		DGPlayer dgp = DG.plugin.dpm.getDGPlayer(p);
		if(dgp!=null){
			dgp.gold = dgp.gold + amount;
			InvManager.updateGoldSlot(dgp, false);
		}
		
		
		
		//TODO Update
	}
}
