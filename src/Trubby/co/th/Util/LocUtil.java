package Trubby.co.th.Util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class LocUtil {
	
	/**
	 * Converts a Location-array into a Stringarray to prepare it for data-storage.
	 * @param list (Location[]) Array of Locations
	 * @return (StringList) The list containing the location-strings
	 */
	public static List<String> toStringList(List<Location> list) {
		List<String> stringList = new ArrayList<String>();
		if (list != null) {
			for (Location loc : list) {
				if (loc != null) {
					stringList.add(locationToString(loc));
				}
			}
		}
		return stringList;
	}
	
	public static List<String> toStringList(Location[] list) {
		List<String> stringList = new ArrayList<String>();
		if (list != null) {
			for (Location loc : list) {
				if (loc != null) {
					stringList.add(locationToString(loc));
				}
			}
		}
		return stringList;
	}
	
	/**
	 * 
	 * @param loc
	 * @return
	 */
	public static String locationToString(Location loc) {
		return loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," +
				loc.getPitch() + "," + loc.getYaw();
	}
	
	/**
	 * 
	 * @param locationString
	 * @return
	 */
	public static Location stringToLocation(String locationString) {
		String[] s = locationString.split(",");
		
		Location loc = new Location(Bukkit.getWorld(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]),
				Double.parseDouble(s[3]));
		if (s.length > 4) {
			loc.setPitch(Float.parseFloat(s[4]));
			loc.setYaw(Float.parseFloat(s[5]));
		}
		
		return loc;
	}
	
	public static String vectorToString(Vector vec) {
		return vec.getX() + "," + vec.getY() + "," + vec.getZ();
	}
	
	public static Vector stringToVec(String vecString) {
		String[] s = vecString.split(",");
		Vector vec = new Vector(Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]));
		return vec;
	}

}
