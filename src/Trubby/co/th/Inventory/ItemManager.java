package Trubby.co.th.Inventory;

import java.util.ArrayList;
import java.util.Collections;

import net.elseland.xikage.MythicMobs.Items.MythicItem;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemManager {
	
	//INIT
	public static void AddAllItems() {
		addSwords();
		addHelmets();
		addChestplates();
		addLeggings();
		addBoots();
		addBows();
		
		addETC();
	}
	
	public static ArrayList<Integer> swordPrice = new ArrayList<Integer>();
	public static ArrayList<ItemStack> swords = new ArrayList<ItemStack>();
	
	public static ArrayList<Integer> helmetPrice = new ArrayList<Integer>();
	public static ArrayList<ItemStack> helmets = new ArrayList<ItemStack>();
	
	public static ArrayList<Integer> chestPrice = new ArrayList<Integer>();
	public static ArrayList<ItemStack> chests = new ArrayList<ItemStack>();
	
	public static ArrayList<Integer> legPrice = new ArrayList<Integer>();
	public static ArrayList<ItemStack> legs = new ArrayList<ItemStack>();
	
	public static ArrayList<Integer> bootsPrice = new ArrayList<Integer>();
	public static ArrayList<ItemStack> boots = new ArrayList<ItemStack>();
	
	public static ArrayList<Integer> bowPrice = new ArrayList<Integer>();
	public static ArrayList<ItemStack> bows = new ArrayList<ItemStack>();
	
	public static ArrayList<Integer> arrowPrice = new ArrayList<Integer>();//TODO
	
	public static ArrayList<ItemStack> etc = new ArrayList<ItemStack>();
	
	public static void addETC(){
		
		MythicItem mietc1 = MythicItem.getMythicItem("brown_blank");	ItemStack etc1 = mietc1.generateItemStack(1);
		ItemStack arrow = new ItemStack(Material.ARROW); 	//1
		Collections.addAll(arrowPrice, 0,0,0,0,0,100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400,1500,1600,1700,1800,1900);
		Collections.addAll(etc, etc1, arrow);				//2
		
	}
	
	public static void addSwords(){
		MythicItem misw1 = MythicItem.getMythicItem("sw1");	ItemStack sw1 = misw1.generateItemStack(1);
		MythicItem misw2 = MythicItem.getMythicItem("sw2");	ItemStack sw2 = misw2.generateItemStack(1);
		MythicItem misw3 = MythicItem.getMythicItem("sw3");	ItemStack sw3 = misw3.generateItemStack(1);
		MythicItem misw4 = MythicItem.getMythicItem("sw4");	ItemStack sw4 = misw4.generateItemStack(1);
		MythicItem misw5 = MythicItem.getMythicItem("sw5");	ItemStack sw5 = misw5.generateItemStack(1);
		MythicItem miswMax = MythicItem.getMythicItem("sw_max"); ItemStack swMax = miswMax.generateItemStack(1);
		
		Collections.addAll(swords, sw1,sw2,sw3,sw4,sw5,
								swMax);
		Collections.addAll(swordPrice, 0, 100, 200, 400, 500,
									0);
	}
	
	public static void addHelmets(){
		MythicItem mishel1 = MythicItem.getMythicItem("hel_blank");	ItemStack hel1 = mishel1.generateItemStack(1);
		MythicItem mishel2 = MythicItem.getMythicItem("hel1");	ItemStack hel2 = mishel2.generateItemStack(1);
		MythicItem mishel3 = MythicItem.getMythicItem("hel2");	ItemStack hel3 = mishel3.generateItemStack(1);
		MythicItem mishel4 = MythicItem.getMythicItem("hel3");	ItemStack hel4 = mishel4.generateItemStack(1);
		MythicItem mishel5 = MythicItem.getMythicItem("hel4");	ItemStack hel5 = mishel5.generateItemStack(1);
		MythicItem mishel6 = MythicItem.getMythicItem("hel5");	ItemStack hel6 = mishel6.generateItemStack(1);
		MythicItem mishelMax = MythicItem.getMythicItem("hel_max"); ItemStack helMax = mishelMax.generateItemStack(1);
		
		Collections.addAll(helmets, hel1,hel2,hel3,hel4,hel5,
								hel6, helMax);
		Collections.addAll(helmetPrice, 0, 25, 100, 200, 400,
									800, 0);
	}
	
	public static void addBoots(){
		MythicItem misboots1 = MythicItem.getMythicItem("boots_blank");	ItemStack boots1 = misboots1.generateItemStack(1);
		MythicItem misboots2 = MythicItem.getMythicItem("boots1");	ItemStack boots2 = misboots2.generateItemStack(1);
		MythicItem misboots3 = MythicItem.getMythicItem("boots2");	ItemStack boots3 = misboots3.generateItemStack(1);
		MythicItem misboots4 = MythicItem.getMythicItem("boots3");	ItemStack boots4 = misboots4.generateItemStack(1);
		MythicItem misboots5 = MythicItem.getMythicItem("boots4");	ItemStack boots5 = misboots5.generateItemStack(1);
		MythicItem misboots6 = MythicItem.getMythicItem("boots5");	ItemStack boots6 = misboots6.generateItemStack(1);
		MythicItem misbootsMax = MythicItem.getMythicItem("boots_max"); ItemStack bootsBlank = misbootsMax.generateItemStack(1);
		
		Collections.addAll(boots, boots1,boots2,boots3,boots4,boots5,
								boots6,	bootsBlank);
		Collections.addAll(bootsPrice, 0, 25, 100, 200, 400,
									800, 0);
	}
	
	public static void addChestplates(){
		MythicItem mischest1 = MythicItem.getMythicItem("chest_blank");	ItemStack chest1 = mischest1.generateItemStack(1);
		MythicItem mischest2 = MythicItem.getMythicItem("chest1");	ItemStack chest2 = mischest2.generateItemStack(1);
		MythicItem mischest3 = MythicItem.getMythicItem("chest2");	ItemStack chest3 = mischest3.generateItemStack(1);
		MythicItem mischest4 = MythicItem.getMythicItem("chest3");	ItemStack chest4 = mischest4.generateItemStack(1);
		MythicItem mischest5 = MythicItem.getMythicItem("chest4");	ItemStack chest5 = mischest5.generateItemStack(1);
		MythicItem mischest6 = MythicItem.getMythicItem("chest5");	ItemStack chest6 = mischest6.generateItemStack(1);
		MythicItem mischestMax = MythicItem.getMythicItem("chest_max"); ItemStack chestBlank = mischestMax.generateItemStack(1);
		
		Collections.addAll(chests, chest1,chest2,chest3,chest4,chest5,
								chest6,	chestBlank);
		Collections.addAll(chestPrice, 0, 25, 100, 200, 400,
									800, 0);
	}
	
	public static void addLeggings(){
		MythicItem misleg1 = MythicItem.getMythicItem("leg_blank");	ItemStack leg1 = misleg1.generateItemStack(1);
		MythicItem misleg2 = MythicItem.getMythicItem("leg1");	ItemStack leg2 = misleg2.generateItemStack(1);
		MythicItem misleg3 = MythicItem.getMythicItem("leg2");	ItemStack leg3 = misleg3.generateItemStack(1);
		MythicItem misleg4 = MythicItem.getMythicItem("leg3");	ItemStack leg4 = misleg4.generateItemStack(1);
		MythicItem misleg5 = MythicItem.getMythicItem("leg4");	ItemStack leg5 = misleg5.generateItemStack(1);
		MythicItem misleg6 = MythicItem.getMythicItem("leg5");	ItemStack leg6 = misleg6.generateItemStack(1);
		MythicItem mislegMax = MythicItem.getMythicItem("leg_max"); ItemStack legBlank = mislegMax.generateItemStack(1);
		
		Collections.addAll(legs, leg1,leg2,leg3,leg4,leg5,
								leg6,	legBlank);
		Collections.addAll(legPrice, 0, 25, 100, 200, 400,
									800, 0);
	}
	
	public static void addBows(){
		for (int i = 1; i <= 5; i++) {
			MythicItem mibow = MythicItem.getMythicItem("bow"+i);	ItemStack bow = mibow.generateItemStack(1);
			bows.add(bow);
			
		}
		
		MythicItem mibowMax = MythicItem.getMythicItem("bow_max"); ItemStack bowMax = mibowMax.generateItemStack(1);
		bows.add(bowMax);
		
		Collections.addAll(bowPrice, 0, 100, 200, 400, 500,
									0);
	}
	
}
