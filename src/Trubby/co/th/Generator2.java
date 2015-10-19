package Trubby.co.th;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

import Trubby.co.th.Map.Dungeon;
import Trubby.co.th.Map.Room;
import Trubby.co.th.Map.VoidGenerator;
import Trubby.co.th.Util.LocationUtil;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;

@SuppressWarnings("deprecation")
public class Generator2 {
	
	ArrayList<String> ccRoomListS = new ArrayList<>();
	ArrayList<String> ccRoomListL = new ArrayList<>();
	ArrayList<String> ccRoomListR = new ArrayList<>();
	ArrayList<String> ccBridgeList = new ArrayList<>();
	CuboidClipboard ccFirst;
	Random ran = new Random();
	
	public Dungeon generateRoom(Location startPoint, int id){
		
		Dungeon d = new Dungeon("Stronghold" + id, "Stronghold", 1);
		d.startLoc = startPoint;
		d.world = startPoint.getWorld();
		DG.plugin.dm.dungeons.add(d);
		
		int safeCount = 0;
		
		boolean isDone = false;
		
		ArrayList<GenCache> firstBuildList = new ArrayList<GenCache>();
		ArrayList<GenCache> bridgeBuildList = new ArrayList<GenCache>();
		ArrayList<GenCache> roomBuildList = new ArrayList<GenCache>();
		
		boolean first = true;
		
		while(!isDone){
			/**
			 * 				FIRST
			 */
			if(first){
				first = false;
				
				CuboidClipboard cc = ccFirst;

				// WANT TO GET
				Vector max;
				Vector min;
				Location myBuildLoc = startPoint;
				Location nextBuildLoc = null;
				BlockFace bf = BlockFace.NORTH;
				
				// OFFSET
				int offsetX = cc.getOffset().getBlockX();
				int offsetZ = cc.getOffset().getBlockZ();
				int offsetY = cc.getOffset().getBlockY();
	
				for (int y = 0; y < cc.getSize().getBlockY(); y++) {
					for (int x = 0; x < cc.getSize().getBlockX(); x++) {
						for (int z = 0; z < cc.getSize().getBlockZ(); z++) {
							com.sk89q.worldedit.Vector currentPoint = new com.sk89q.worldedit.Vector(x, y, z);
							int currentBlock = cc.getPoint(currentPoint).getType();
							if (currentBlock != 0) {
								if (currentBlock == Material.ENDER_CHEST.getId()) {
									
									nextBuildLoc = myBuildLoc.clone().add(x + offsetX, y + offsetY, z + offsetZ);
	
								}
							}
						}
					}
				}
				
				// MIN MAX FINDING
				double yMax = myBuildLoc.getY() + offsetY + cc.getSize().getBlockY() - 1;
				double xMax = myBuildLoc.getX() + offsetX + cc.getSize().getBlockX() - 1;
				double zMax = myBuildLoc.getZ() + offsetZ + cc.getSize().getBlockZ() - 1;

				double yMin = myBuildLoc.getY() + offsetY;
				double xMin = myBuildLoc.getX() + offsetX;
				double zMin = myBuildLoc.getZ() + offsetZ;

				Location l1 = new Location(myBuildLoc.getWorld(), xMax, yMax, zMax);
				Location l2 = new Location(myBuildLoc.getWorld(), xMin, yMin, zMin);
				
				min = CuboidMin(l1, l2);
				max = CuboidMax(l1, l2);
				
				firstBuildList.add(new GenCache(max, min, myBuildLoc, nextBuildLoc, bf, cc));
			}
			
			/**
			 * 			  BRIDGE & ROOM
			 */
			
			if(roomBuildList.size() < 10){
				
				//BRIDGE---------------------------------
				if(true){
					CuboidClipboard cc = loadSchematic(ccBridgeList.get(ran.nextInt(ccBridgeList.size())));
	
					// WANT TO GET
					Vector max;
					Vector min;
					Location myBuildLoc = null; //use for saving
					Location nextBuildLoc = null; //use for saving
					BlockFace bf = null;
					
					boolean isNextToFirst = true;
					
					// IS IT NEXT TO THE FIRST?
					if(roomBuildList.size() > 0){
						isNextToFirst = false;
						GenCache gc = roomBuildList.get(roomBuildList.size() - 1);
						// START BLOCK
						myBuildLoc = gc.nextBuildLoc;
						// ROTATE
						if (getDirection(gc.bf) > 0){
							cc.rotate2D(getDirection(gc.bf));
						}
					}else{
						myBuildLoc = firstBuildList.get(0).nextBuildLoc;
						bf = BlockFace.NORTH;
					}
					
					// OFFSET
					int offsetX = cc.getOffset().getBlockX();
					int offsetZ = cc.getOffset().getBlockZ();
					int offsetY = cc.getOffset().getBlockY();
		
					for (int y = 0; y < cc.getSize().getBlockY(); y++) {
						for (int x = 0; x < cc.getSize().getBlockX(); x++) {
							for (int z = 0; z < cc.getSize().getBlockZ(); z++) {
								com.sk89q.worldedit.Vector currentPoint = new com.sk89q.worldedit.Vector(x, y, z);
								int currentBlock = cc.getPoint(currentPoint).getType();
								if (currentBlock != 0) {
									if (currentBlock == Material.ENDER_CHEST.getId()) {
										
										nextBuildLoc = myBuildLoc.clone().add(x + offsetX, y + offsetY, z + offsetZ);
		
										if(!isNextToFirst){
											bf = roomBuildList.get(roomBuildList.size() - 1).bf;
										}
									}
								}
							}
						}
					}
				
					// MIN MAX FINDING
					double yMax = myBuildLoc.getY() + offsetY + cc.getSize().getBlockY() - 1;
					double xMax = myBuildLoc.getX() + offsetX + cc.getSize().getBlockX() - 1;
					double zMax = myBuildLoc.getZ() + offsetZ + cc.getSize().getBlockZ() - 1;
	
					double yMin = myBuildLoc.getY() + offsetY;
					double xMin = myBuildLoc.getX() + offsetX;
					double zMin = myBuildLoc.getZ() + offsetZ;
	
					Location l1 = new Location(myBuildLoc.getWorld(), xMax, yMax, zMax);
					Location l2 = new Location(myBuildLoc.getWorld(), xMin, yMin, zMin);
					
					min = CuboidMin(l1, l2);
					max = CuboidMax(l1, l2);
					
					bridgeBuildList.add(new GenCache(max, min, myBuildLoc, nextBuildLoc, bf, cc));
				
				}
				
				//ROOM---------------------------------
				if(true){
					BlockFace bf = null;
					GenCache gc = bridgeBuildList.get(bridgeBuildList.size() - 1);
					
					//RANDOM ROOM DIRECTION TYPE
					CuboidClipboard cc = null;
					switch (ran.nextInt(3) + 1) {
					case 1: cc = loadSchematic(ccRoomListL.get(ran.nextInt(ccRoomListL.size()))); bf = turnLeft(gc.bf);
						break;
					case 2: cc = loadSchematic(ccRoomListS.get(ran.nextInt(ccRoomListS.size()))); bf = gc.bf;
						break;
					case 3: cc = loadSchematic(ccRoomListR.get(ran.nextInt(ccRoomListR.size()))); bf = turnRight(gc.bf);
						break;
					default:
						break;
					}
					
					// WANT TO GET
					Vector max;
					Vector min;
					Location myBuildLoc = null;
					Location nextBuildLoc = null;
					ArrayList<Location> spawners = new ArrayList<>();
					
					// START BLOCK GET
					myBuildLoc = gc.nextBuildLoc;
					// ROTATE
					if (getDirection(gc.bf) > 0){
						cc.rotate2D(getDirection(gc.bf));
					}
					
					// OFFSET
					int offsetX = cc.getOffset().getBlockX();
					int offsetZ = cc.getOffset().getBlockZ();
					int offsetY = cc.getOffset().getBlockY();
		
					for (int y = 0; y < cc.getSize().getBlockY(); y++) {
						for (int x = 0; x < cc.getSize().getBlockX(); x++) {
							for (int z = 0; z < cc.getSize().getBlockZ(); z++) {
								com.sk89q.worldedit.Vector currentPoint = new com.sk89q.worldedit.Vector(x, y, z);
								int currentBlock = cc.getPoint(currentPoint).getType();
								if (currentBlock != 0) {
									if (currentBlock == Material.ENDER_CHEST.getId()) {
										
										nextBuildLoc = myBuildLoc.clone().add(x + offsetX, y + offsetY, z + offsetZ);
		
									}else if (currentBlock == Material.MOB_SPAWNER.getId()) {
										Location spawnerLoc = myBuildLoc.clone().add(x + offsetX + 0.4, y + offsetY, z + offsetZ + 0.4);
										spawners.add(spawnerLoc);
									}
								}
							}
						}
					}
				
					// MIN MAX FINDING
					double yMax = myBuildLoc.getY() + offsetY + cc.getSize().getBlockY() - 1;
					double xMax = myBuildLoc.getX() + offsetX + cc.getSize().getBlockX() - 1;
					double zMax = myBuildLoc.getZ() + offsetZ + cc.getSize().getBlockZ() - 1;
	
					double yMin = myBuildLoc.getY() + offsetY;
					double xMin = myBuildLoc.getX() + offsetX;
					double zMin = myBuildLoc.getZ() + offsetZ;
	
					Location l1 = new Location(myBuildLoc.getWorld(), xMax, yMax, zMax);
					Location l2 = new Location(myBuildLoc.getWorld(), xMin, yMin, zMin);
					
					min = CuboidMin(l1, l2);
					max = CuboidMax(l1, l2);
					
					roomBuildList.add(new GenCache(max, min, myBuildLoc, nextBuildLoc, bf, cc, spawners));
				
				}
			
				// OVERLAP CHECK
				if(!roomBuildList.isEmpty()){
					GenCache room = roomBuildList.get(roomBuildList.size() - 1);
					if(isThisRoomOverlap(room, firstBuildList, bridgeBuildList, roomBuildList)){
						
						if(roomBuildList.size() > 1){
							bridgeBuildList.remove(bridgeBuildList.size() - 1);
							bridgeBuildList.remove(bridgeBuildList.size() - 1);
							roomBuildList.remove(roomBuildList.size() - 1);
							roomBuildList.remove(roomBuildList.size() - 1);
						}else{
							bridgeBuildList.remove(bridgeBuildList.size() - 1);
							roomBuildList.remove(roomBuildList.size() - 1);
						}
					}
				}
				
			}else{//has enough rooms and bridges
				isDone = true;
			}
			
			//SAFE WHILE
			if(safeCount > 30){
				Bukkit.broadcastMessage("It's over 30!");
				return null;
			}else{
				safeCount++;
			}
			
		}
		
		//PASTE ALL
		GenCache firstGC = firstBuildList.get(0);
		pasteSchematic(firstGC.myBuildLoc, firstGC.cc);
		
		for(GenCache gc : roomBuildList){
			pasteSchematic(gc.myBuildLoc, gc.cc);
			
			Room r = new Room(d, gc.min, gc.max);
			r.spawners = gc.spawners;
			d.rooms.add(r);
			
			for(Location spawnLoc : gc.spawners){
				if(spawnLoc.getBlock().getType() != Material.MOB_SPAWNER){
					Bukkit.broadcastMessage("wrong");
				}
				spawnLoc.getBlock().setType(Material.AIR);
			}
		}
		
		for(GenCache gc : bridgeBuildList){
			pasteSchematic(gc.myBuildLoc, gc.cc);
		}
		
		Bukkit.broadcastMessage(id + " DONE! {total calculate : " + safeCount + "}");
		
		DG.plugin.dm.saveDungeon(d);
		
		return d;
	}
	
	
	/**
	 * XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	 * XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	 * XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	 */
	
	
	public boolean isThisRoomOverlap(GenCache room, ArrayList<GenCache> first, ArrayList<GenCache> bridges, ArrayList<GenCache> rooms) {
		for (GenCache gc : first) {
			if (LocationUtil.intersects(room.min, room.max, gc.min, gc.max)) {
				return true;
			}
		}
		for (GenCache gc : rooms) {
			if(gc != room){
				if (LocationUtil.intersects(room.min, room.max, gc.min, gc.max)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public org.bukkit.util.Vector CuboidMin(Location l1, Location l2) {
		if (!l1.getWorld().equals(l2.getWorld())) {
			throw new IllegalArgumentException("locations must be on the same world");
		}
		double x1 = Math.min(l1.getBlockX(), l2.getBlockX());
		double y1 = Math.min(l1.getBlockY(), l2.getBlockY());
		double z1 = Math.min(l1.getBlockZ(), l2.getBlockZ());

		return new org.bukkit.util.Vector(x1, y1, z1);
	}

	public org.bukkit.util.Vector CuboidMax(Location l1, Location l2) {
		if (!l1.getWorld().equals(l2.getWorld())) {
			throw new IllegalArgumentException("locations must be on the same world");
		}

		double x2 = Math.max(l1.getBlockX(), l2.getBlockX());
		double y2 = Math.max(l1.getBlockY(), l2.getBlockY());
		double z2 = Math.max(l1.getBlockZ(), l2.getBlockZ());

		return new org.bukkit.util.Vector(x2, y2, z2);
	}

	public BlockFace getBlockFace(Block b) {

		switch (b.getData()) {
		case (byte) 2:
			return BlockFace.NORTH;
		case (byte) 3:
			return BlockFace.SOUTH;
		case (byte) 1:
			return BlockFace.WEST;
		case (byte) 0:
			return BlockFace.EAST;
		default:
			return BlockFace.NORTH;
		}
	}

	public int getDirection(BlockFace face) {
		if (face == BlockFace.NORTH) {
			return 0;
		} else if (face == BlockFace.EAST) {
			return 90;
		} else if (face == BlockFace.WEST) {
			return 270;
		} else if (face == BlockFace.SOUTH) {
			return 180;
		} else {
			return 0;
		}
	}

	public World create(String name) {
		World world = Bukkit.getServer().getWorld(name);
		if (world == null) {
			System.out.println("Loading world '" + name + "'.");
			WorldCreator arenaWorldCreator = new WorldCreator(name);
			arenaWorldCreator.generateStructures(false);
			arenaWorldCreator.generator(new VoidGenerator());
			arenaWorldCreator.type(WorldType.FLAT);
			arenaWorldCreator.environment(Environment.NETHER);
			arenaWorldCreator.seed(0);
			world = arenaWorldCreator.createWorld();
			System.out.println("Done loading world '" + name + "'.");
		} else {
			System.out.println("The world '" + name + "' was already loaded.");
		}
		world.setGameRuleValue("doMobSpawning", "false");
		world.setGameRuleValue("doMobLoot", "false");
		world.setGameRuleValue("keepInventory", "true");
		world.setGameRuleValue("mobGriefing", "false");
		world.setAutoSave(false);
		world.getBlockAt(0, 45, 0).setType(Material.STONE);
		world.setSpawnLocation(0, 46, 0);

		return world;
	}

	public static void pasteSchematic(Location origin, CuboidClipboard schematic) {
		EditSession editSession = new EditSession(new BukkitWorld(origin.getWorld()), 2147483647);
		editSession.setFastMode(true);

		try {
			schematic.paste(editSession, new com.sk89q.worldedit.Vector(origin.getBlockX(), origin.getBlockY(), origin.getBlockZ()), true);
		} catch (MaxChangedBlocksException ignored) {
		}

	}
	
	public BlockFace turnLeft(BlockFace face){
		if (face == BlockFace.NORTH) {
			return BlockFace.WEST;
		} else if (face == BlockFace.WEST) {
			return BlockFace.SOUTH;
		} else if (face == BlockFace.SOUTH) {
			return BlockFace.EAST;
		} else if (face == BlockFace.EAST) {
			return BlockFace.NORTH;
		} else {
			return null;
		}
	}
	
	public BlockFace turnRight(BlockFace face){
		if (face == BlockFace.NORTH) {
			return BlockFace.EAST;
		} else if (face == BlockFace.EAST) {
			return BlockFace.SOUTH;
		} else if (face == BlockFace.SOUTH) {
			return BlockFace.WEST;
		} else if (face == BlockFace.WEST) {
			return BlockFace.NORTH;
		} else {
			return null;
		}
	}
	
	public CuboidClipboard loadSchematic(String str){
		File dataDirectory = DG.plugin.getDataFolder();
		File schematicsDirectory = new File(dataDirectory, "schematics");

		File[] schematics = schematicsDirectory.listFiles();

		for (File schematic : schematics) {
			if (schematic.getName().equalsIgnoreCase(str)) {
				SchematicFormat sf = SchematicFormat.getFormat(schematic);
				CuboidClipboard cc = null;
				try {
					cc = sf.load(schematic);
				} catch (DataException e) {
				} catch (IOException e) {
				}
				return cc;
			}
		}
		
		return null;
	}
	
}
