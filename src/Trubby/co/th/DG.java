package Trubby.co.th;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.EulerAngle;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;

import Trubby.co.th.IO.FileManager;
import Trubby.co.th.Map.Dungeon;
import Trubby.co.th.Map.DungeonManager;
import Trubby.co.th.Mobs.MobManager;
import Trubby.co.th.Mobs.MobsListener;
import Trubby.co.th.NPC.NPCListener;
import Trubby.co.th.Party.Party;
import Trubby.co.th.Party.PartyListener;
import Trubby.co.th.Party.PartyManager;
import Trubby.co.th.Player.DGPlayer;
import Trubby.co.th.Player.DGPlayerManager;
import Trubby.co.th.Player.PlayerListener;
import Trubby.co.th.Queue.QueueManager;
import Trubby.co.th.Queue.QueueManager.DungeonType;
import Trubby.co.th.Skill.SkillListener;
import Trubby.co.th.Skill.SkillManager;
import Trubby.co.th.Upgrade.EquipGui;
import Trubby.co.th.Upgrade.HealthGui;
import Trubby.co.th.Upgrade.ItemManager;
import Trubby.co.th.Util.VolatileCodeEnabled_v1_8_R3;
import net.md_5.bungee.api.ChatColor;

@SuppressWarnings("deprecation")
public class DG extends JavaPlugin {

	//GITHUB test :D
	
	/**
	 * 		TO-DO
	 * 		- party ***--
	 * 		- add the end of stage
	 * 		- ===== skill =====
	 * 		- skills sound
	 * 		- more skills
	 * 		- skill shop
	 * 		- =================
	 * 		- dungeon gui
	 * 		- upgrades gui ***__
	 * 		- SQL
	 * 		- logout listener remove : DGplayer, Party
	 * 		- link party with queue
	 * 		- death cam
	 * 	note
	 *  PacketType.Play.Client.WINDOW_CLICK
	 */
	
	public DungeonManager dm;
	public DGPlayerManager dpm;
	public EquipGui egui;
	public HealthGui hgui;
	public Generator gen;
	public MobManager mm;
	public SkillManager sm;
	public QueueManager qm;
	public FileManager fm;
	public PartyManager parm;
	public VolatileCodeEnabled_v1_8_R3 nms;
	
	public Location lobby;
	
	ArrayList<CuboidClipboard> ccList = new ArrayList<>();
	ArrayList<CuboidClipboard> ccBridgeList = new ArrayList<>();
	CuboidClipboard ccFirst;

	public static DG plugin;

	@Override
	public void onEnable() {
		plugin = this;

		dm = new DungeonManager();
		dpm = new DGPlayerManager();
		egui = new EquipGui();
		gen = new Generator();
		mm = new MobManager();
		sm = new SkillManager();
		qm = new QueueManager();
		fm = new FileManager();
		parm = new PartyManager();
		nms = new VolatileCodeEnabled_v1_8_R3();
		hgui = new HealthGui();
		
		lobby = Bukkit.getWorld("DG_lobby").getSpawnLocation();
		
		Bukkit.getPluginManager().registerEvents(new MobsListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		Bukkit.getPluginManager().registerEvents(egui, this);
		Bukkit.getPluginManager().registerEvents(hgui, this);
		Bukkit.getPluginManager().registerEvents(new NPCListener(), this);
		Bukkit.getPluginManager().registerEvents(new SkillListener(), this);
		Bukkit.getPluginManager().registerEvents(new PartyListener(), this);
		
		//items
		ItemManager.AddAllItems();

		//Schematic
		loadSchematic();
		
		//DGPlayer refresh
		dpm.refreshOnlinePlayer();
		
		runStaminaRegenTask();
		runManaRegenTask();
	}
	
	public void onDisable(){
		for(Dungeon d : dm.dungeons){
			d.reset();
			Bukkit.unloadWorld(d.startLoc.getWorld(), false);
		}
	}

	Random ran = new Random();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (label.equalsIgnoreCase("dgori")) {
			
		} else if (label.equalsIgnoreCase("rs")) {

			Player p = (Player) sender;

			/*
			 * p.sendMessage(""+MobCommon.getAllMythicMobs()); MythicMob mm =
			 * MobCommon.getMythicMob("testSkel"); if (mm == null) {
			 * p.sendMessage("null"); return false; } Entity e = (Entity)
			 * mm.spawn(p.getLocation(), 1);
			 * 
			 * LivingEntity le = (LivingEntity) e; le.setHealth(0);
			 * p.sendMessage("yay");
			 */

			/*if (MythicItem.getMythicItem("sw1") != null) {
				MythicItem mi = MythicItem.getMythicItem("sw1");
				p.getInventory().addItem(new ItemStack[] { mi.generateItemStack(1, p, p) });
				p.sendMessage("yay");
			} else {
				p.sendMessage("null");
			}*/
			
			egui.openEquipUpgrade(p);

		}else if(label.equalsIgnoreCase("dg")) {
			
			nms.sendTitleToPlayer((Player) sender, ChatColor.GREEN + "test", ":D", 0, 10, 8);

		}else if(label.equalsIgnoreCase("dglist")) {
			for(Dungeon d : dm.dungeons){
				sender.sendMessage("" + d.worldName);
			}
		}else if(label.equalsIgnoreCase("dgnew")){
			for (int i = 1; i <= 1; i++) {
				World world = gen.create("Stronghold" + i);
				Location startLoc = new Location(world, 0.00, 45, 0.00);
				gen.generateRoom(startLoc, i);
			}
		}else if(label.equalsIgnoreCase("dgtest")){
			Player p = (Player) sender;
			ArmorStand as = (ArmorStand) p.getWorld().spawnEntity(p.getLocation().add(0, -1.2, 0), EntityType.ARMOR_STAND);
			as.setGravity(false);
			as.setCustomName(ChatColor.RED + "tub_" + ChatColor.WHITE + " (Right Click to revive)");
			as.setCustomNameVisible(true);
			as.setHeadPose(new EulerAngle(-1.4, 0, 0));
			as.setBodyPose(new EulerAngle(-1.5, 0, 0));
			as.setRightArmPose(new EulerAngle(-1.4, 0, 0));
			as.setLeftArmPose(new EulerAngle(-1.4, 0, 0));
			
			SkullMeta sm = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
			sm.setOwner("tub_");
			ItemStack is = new ItemStack(Material.SKULL_ITEM);
			is.setDurability((short) 3);
			is.setItemMeta(sm);
			
			as.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
			as.setHelmet(is);
			as.setItemInHand(new ItemStack(Material.IRON_AXE));
			
			p.sendMessage("test");
		}else if(label.equalsIgnoreCase("load")){
			dm.loadDungeon("Stronghold","Stronghold1", 1);//suppost to be random
			sender.sendMessage("loaded");
		}else if(label.equalsIgnoreCase("del")){
			Bukkit.unloadWorld("Stronghold1", false);
			sender.sendMessage("deleted");
		}else if(label.equalsIgnoreCase("q")){
			Player p = (Player) sender;
			qm.addMe(p, DungeonType.ST_E);
			//qm.stronghold_easy.add(p.getUniqueId());
			p.sendMessage("QUEUE!");
		}else if(label.equalsIgnoreCase("test")){
			
		}else if(label.equalsIgnoreCase("party")){
			Player p = (Player) sender;
			if(args.length > 0){
				if(args[0].equalsIgnoreCase("create")){
					if(args.length > 1){
						parm.create(p, args[1]);
						p.sendMessage(ChatColor.GREEN + "Party created name : " + ChatColor.WHITE + "" + ChatColor.ITALIC + args[1]);
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1f, 1f);
						return true;
					}else{
						p.sendMessage("/party create [name]");
						return true;
					}
				}else if(args[0].equalsIgnoreCase("accept")){
					if(parm.inviting.containsKey(p.getUniqueId())){
						Party party = parm.inviting.get(p.getUniqueId());
						parm.addPlayer(p, party);
						parm.inviting.remove(p.getUniqueId());
					}
				}else if(args[0].equalsIgnoreCase("decline")){
					
				}
			}
			
			Party par = parm.getParty(p);
			if(par != null){
				p.openInventory(par.menu);
				p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1f, 1f);
			}else{
				p.sendMessage("You're not in the party. /party create [name] to create.");
			}
		}
		return false;
	}

	public void loadSchematic(){
		File dataDirectory = this.getDataFolder();
		File schematicsDirectory = new File(dataDirectory, "schematics");

		File[] schematics = schematicsDirectory.listFiles();

		for (File schematic : schematics) {
			if (schematic.getName().endsWith(".schematic")) {

				SchematicFormat sf = SchematicFormat.getFormat(schematic);
				CuboidClipboard cc = null;

				// IS FIRST
				if (schematic.getName().endsWith("start.schematic")) {
					// ccFirst = FIRST
					try {
						cc = sf.load(schematic);
						gen.ccFirst = cc;

					} catch (IOException | DataException e) {
					}

				} else if (schematic.getName().startsWith("b")) {
					// ADD TO ccBridgeList
						gen.ccBridgeList.add(schematic.getName());
				} else if (schematic.getName().startsWith("rs")) {
					// ADD TO ccList
						gen.ccRoomListS.add(schematic.getName());
				} else if (schematic.getName().startsWith("rl")) {
					// ADD TO ccList
						gen.ccRoomListL.add(schematic.getName());
				} else if (schematic.getName().startsWith("rr")) {
					// ADD TO ccList
						gen.ccRoomListR.add(schematic.getName());
				}
			}
		}
	}
	
	public void runStaminaRegenTask(){
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			@Override
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()){
					//Stamina
					if(!p.isSprinting()){
						if(p.getFoodLevel() <= 18){
							p.setFoodLevel(p.getFoodLevel() + 2);
						}else if(p.getFoodLevel() == 19){
							p.setFoodLevel(p.getFoodLevel() + 1);
						}
					}
				}
			}
		}, 14L, 14L);
		
	}
	
	public void runManaRegenTask(){
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			@Override
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()){
					//Mana
					DGPlayer dgp = dpm.getDGPlayer(p);
					if(dgp.mana < dgp.maxMana){
						dgp.mana++;
						dgp.updateManaBar();
					}
				}
			}
		}, 20L, 20L);
		
	}
	
}
