package Trubby.co.th.Player;

import org.bukkit.scheduler.BukkitRunnable;

public class RunningTask extends BukkitRunnable{
	
	DGPlayer dgp;
	
	public RunningTask(DGPlayer dgp) {
		this.dgp = dgp;
		
	}
	
	int cooldown = 0;
	
	@Override
	public void run() {
		//avoid bug
		if(dgp.p.getFoodLevel() < 2){
			dgp.isSprinting = false;
			this.cancel();
			return;
		}
		
		if(dgp.p.isSprinting()){
			
			dgp.p.setFoodLevel(dgp.p.getFoodLevel() - 1);
			
		}else{
			this.cancel();
			dgp.isSprinting = false;
		}
	}
	
	
	

}
