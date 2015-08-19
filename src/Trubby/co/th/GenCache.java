package Trubby.co.th;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

import com.sk89q.worldedit.CuboidClipboard;

@SuppressWarnings("deprecation")
public class GenCache {
	
	public Vector max;
	public Vector min;
	
	public Location myBuildLoc;
	public Location nextBuildLoc;
	public BlockFace bf;
	
	public ArrayList<Location> spawners = new ArrayList<>();
	
	public CuboidClipboard cc;
	
	public GenCache(Vector max, Vector min, Location myBuildLoc, Location nextBuildLoc, BlockFace bf, CuboidClipboard cc) {
		this.max = max;
		this.min = min;
		
		this.myBuildLoc = myBuildLoc;
		this.nextBuildLoc = nextBuildLoc;
		
		this.bf = bf;
		this.cc = cc;
	}
	
	public GenCache(Vector max, Vector min, Location myBuildLoc, Location nextBuildLoc, BlockFace bf, CuboidClipboard cc, ArrayList<Location> spawners) {
		this.max = max;
		this.min = min;
		
		this.myBuildLoc = myBuildLoc;
		this.nextBuildLoc = nextBuildLoc;
		
		this.bf = bf;
		this.cc = cc;
		this.spawners = spawners;
	}

}
