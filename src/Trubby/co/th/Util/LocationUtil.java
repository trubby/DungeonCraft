package Trubby.co.th.Util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.util.Vector;

public class LocationUtil {
	
	//DO CUBOIDS INTERSECT?
	public static boolean intersects(Vector Amin, Vector Amax, Vector Bmin, Vector Bmax) {
		List<Vector> corners = getCorners(Amin, Amax);

		for (Vector vec : corners) {
			if (contains(Bmin, Bmax, vec)) {
				return true;
			}
		}

		corners = getCorners(Bmin, Bmax);

		for (Vector vec : corners) {
			if (contains(Amin, Amax, vec)) {
				return true;
			}
		}
        
        if((Amin.getX() <= Bmax.getX() && Amax.getX() >= Bmin.getX()) &&
        	(Amin.getY() <= Bmax.getY() && Amax.getY() >= Bmin.getY()) && 
       		(Amin.getZ() <= Bmax.getZ() && Amax.getZ() >= Bmin.getZ())){
        	
        	return true;
        }

        return false;
    }
	
	//GET 8 CORNERS
	public static List<Vector> getCorners(Vector min, Vector max) {
		List<Vector> corners = new ArrayList<Vector>();

		double minx = min.getX();
		double miny = min.getY();
		double minz = min.getZ();
		double maxx = max.getX();
		double maxy = max.getY();
		double maxz = max.getZ();

		corners.add(new Vector(minx, miny, minz));
		corners.add(new Vector(minx, miny, maxz));
		corners.add(new Vector(minx, maxy, minz));
		corners.add(new Vector(minx, maxy, maxz));
		corners.add(new Vector(maxx, miny, minz));
		corners.add(new Vector(maxx, miny, maxz));
		corners.add(new Vector(maxx, maxy, minz));
		corners.add(new Vector(maxx, maxy, maxz));

		return corners;
	}
	
	//CHECK OVERLAP VECTOR
	public static boolean contains(Vector vMin, Vector vMax, Vector isIn) {
		double x = isIn.getX();
		double y = isIn.getY();
		double z = isIn.getZ();
		
		if(vMin == null){
			return false;
		}
		
		if(vMax == null){
			return false;
		}
		
		return (x >= vMin.getBlockX()) 
				&& (x <= vMax.getBlockX())
				&& (y >= vMin.getBlockY())
				&& (y <= vMax.getBlockY())
				&& (z >= vMin.getBlockZ())
				&& (z <= vMax.getBlockZ());
	}

}
