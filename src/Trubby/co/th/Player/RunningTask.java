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
		if(dgp.p.isSprinting()){
			dgp.p.setFoodLevel(dgp.p.getFoodLevel() - 1);
			
		}else{
			this.cancel();
			dgp.isSprinting = false;
		}
	}
	
	
	

}
