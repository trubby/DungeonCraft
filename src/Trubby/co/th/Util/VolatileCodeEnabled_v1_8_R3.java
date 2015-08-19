package Trubby.co.th.Util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.AttributeModifiable;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.EntityChicken;
import net.minecraft.server.v1_8_R3.EntityCreeper;
import net.minecraft.server.v1_8_R3.EntityFireworks;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityZombie;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagDouble;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagLong;
import net.minecraft.server.v1_8_R3.NBTTagString;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R3.PlayerConnection;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftChicken;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftCreeper;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

public class VolatileCodeEnabled_v1_8_R3 implements VolatileCodeHandler {
	public VolatileCodeEnabled_v1_8_R3() {
		try {
			this.packet63Fields[0] = PacketPlayOutWorldParticles.class.getDeclaredField("a");
			this.packet63Fields[1] = PacketPlayOutWorldParticles.class.getDeclaredField("b");
			this.packet63Fields[2] = PacketPlayOutWorldParticles.class.getDeclaredField("c");
			this.packet63Fields[3] = PacketPlayOutWorldParticles.class.getDeclaredField("d");
			this.packet63Fields[4] = PacketPlayOutWorldParticles.class.getDeclaredField("e");
			this.packet63Fields[5] = PacketPlayOutWorldParticles.class.getDeclaredField("f");
			this.packet63Fields[6] = PacketPlayOutWorldParticles.class.getDeclaredField("g");
			this.packet63Fields[7] = PacketPlayOutWorldParticles.class.getDeclaredField("h");
			this.packet63Fields[8] = PacketPlayOutWorldParticles.class.getDeclaredField("i");
			this.packet63Fields[9] = PacketPlayOutWorldParticles.class.getDeclaredField("j");
			this.packet63Fields[10] = PacketPlayOutWorldParticles.class.getDeclaredField("k");
			for (int i = 0; i <= 10; i++) {
				this.packet63Fields[i].setAccessible(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void PlaySoundAtLocation(Location location, String sound, float volume, float pitch) {
		((CraftWorld) location.getWorld()).getHandle().makeSound(location.getX(), location.getY(), location.getZ(), sound, volume, pitch);
	}

	public void CreateFireworksExplosion(Location location, boolean flicker, boolean trail, int type, int[] colors, int[] fadeColors, int flightDuration) {
		net.minecraft.server.v1_8_R3.ItemStack item = new net.minecraft.server.v1_8_R3.ItemStack(Item.getById(401), 1, 0);

		NBTTagCompound tag = item.getTag();
		if (tag == null) {
			tag = new NBTTagCompound();
		}
		NBTTagCompound explTag = new NBTTagCompound();
		explTag.setByte("Flicker", (byte) (flicker ? 1 : 0));
		explTag.setByte("Trail", (byte) (trail ? 1 : 0));
		explTag.setByte("Type", (byte) type);
		explTag.setIntArray("Colors", colors);
		explTag.setIntArray("FadeColors", fadeColors);

		NBTTagCompound fwTag = new NBTTagCompound();
		fwTag.setByte("Flight", (byte) flightDuration);
		NBTTagList explList = new NBTTagList();
		explList.add(explTag);
		fwTag.set("Explosions", explList);
		tag.set("Fireworks", fwTag);

		item.setTag(tag);

		EntityFireworks fireworks = new EntityFireworks(((CraftWorld) location.getWorld()).getHandle(), location.getX(), location.getY(), location.getZ(), item);
		((CraftWorld) location.getWorld()).getHandle().addEntity(fireworks);
		if (flightDuration == 0) {
			((CraftWorld) location.getWorld()).getHandle().broadcastEntityEffect(fireworks, (byte) 17);
			fireworks.die();
		}
	}

	Field[] packet63Fields = new Field[11];

	public void setMaxHealth(org.bukkit.entity.Entity e, double health) {
		AttributeInstance attributes = ((EntityInsentient) ((CraftLivingEntity) e).getHandle()).getAttributeInstance(GenericAttributes.maxHealth);
		attributes.setValue(health);
		LivingEntity l = (LivingEntity) e;
		l.setHealth(l.getMaxHealth());
	}

	public void setFollowRange(org.bukkit.entity.Entity e, double range) {
		AttributeInstance attributes = ((EntityInsentient) ((CraftLivingEntity) e).getHandle()).getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
		if (attributes != null) {
			attributes.setValue(range);
		}
	}

	public void setKnockBackResistance(org.bukkit.entity.Entity e, double knock) {
		AttributeInstance attributes = ((EntityInsentient) ((CraftLivingEntity) e).getHandle()).getAttributeInstance(GenericAttributes.c);
		if (attributes != null) {
			attributes.setValue(knock);
		}
	}

	public void setMobSpeed(org.bukkit.entity.Entity e, double speed) {
		AttributeInstance attributes = ((EntityInsentient) ((CraftLivingEntity) e).getHandle()).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
		if (attributes != null) {
			attributes.setValue(speed);
		}
	}

	public void setAttackDamage(org.bukkit.entity.Entity e, double damage) {
		AttributeInstance attributes = ((EntityInsentient) ((CraftLivingEntity) e).getHandle()).getAttributeInstance(GenericAttributes.ATTACK_DAMAGE);
		if (attributes != null) {
			attributes.setValue(damage);
		}
	}

	public org.bukkit.inventory.ItemStack setItemAttribute(String attr, double amount, org.bukkit.inventory.ItemStack item) {
		VolatileCodeHandler.Attributes a = VolatileCodeHandler.Attributes.get(attr);
		int op = -1;

		net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(item);
		NBTTagList attrmod = getItemAttributeList(nms);
		NBTTagCompound c = new NBTTagCompound();
		if (op == -1) {
			op = a.op;
		}
		c.set("Name", new NBTTagString(attr));
		c.set("AttributeName", new NBTTagString(a.name));
		c.set("Amount", new NBTTagDouble(amount));
		c.set("Operation", new NBTTagInt(op));
		UUID randUUID = UUID.randomUUID();
		c.set("UUIDMost", new NBTTagLong(randUUID.getMostSignificantBits()));
		c.set("UUIDLeast", new NBTTagLong(randUUID.getLeastSignificantBits()));
		attrmod.add(c);
		nms.getTag().set("AttributeModifiers", attrmod);
		org.bukkit.inventory.ItemStack i = CraftItemStack.asCraftMirror(nms);

		return i;
	}

	public NBTTagList getItemAttributeList(net.minecraft.server.v1_8_R3.ItemStack nms) {
		if (nms.getTag() == null) {
			nms.setTag(new NBTTagCompound());
		}
		NBTTagList attrmod = nms.getTag().getList("AttributeModifiers", 10);
		if (attrmod == null) {
			nms.getTag().set("AttributeModifiers", new NBTTagList());
		}
		return nms.getTag().getList("AttributeModifiers", 10);
	}

	public void listItemAttributes(Player player) {
	}

	public void setCreeperFuseTicks(Creeper c, int t) {
		EntityCreeper entCreeper = ((CraftCreeper) c).getHandle();
		Field fuseF = null;
		try {
			fuseF = EntityCreeper.class.getDeclaredField("maxFuseTicks");
		} catch (Exception ex) {
		}
		fuseF.setAccessible(true);
		try {
			fuseF.setInt(entCreeper, t);
		} catch (Exception ex) {
		}
	}

	public void setCreeperExplosionRadius(Creeper c, int r) {
		EntityCreeper entCreeper = ((CraftCreeper) c).getHandle();
		Field radiusF = null;
		try {
			radiusF = EntityCreeper.class.getDeclaredField("explosionRadius");
		} catch (Exception ex) {
		}
		radiusF.setAccessible(true);
		try {
			radiusF.setInt(entCreeper, r);
		} catch (Exception ex) {
		}
	}

	public void setZombieSpawnReinforcements(Zombie z, double d) {
		EntityZombie zombie = ((CraftZombie) z).getHandle();

		AttributeModifiable ar = (AttributeModifiable) zombie.getAttributeMap().a("Spawn Reinforcements Chance");

		ar.setValue(d);
	}

	public void setEntitySilent(org.bukkit.entity.Entity e) {
		net.minecraft.server.v1_8_R3.Entity ee = ((CraftEntity) e).getHandle();
		ee.b(true);
	}

	public void setChickenHostile(Chicken c) {
		EntityChicken chicken = ((CraftChicken) c).getHandle();

		chicken.i(true);
	}

	public void setTarget(LivingEntity entity, LivingEntity target) {
		if (target == null) {
			return;
		}
		if ((entity instanceof Creature)) {
			((Creature) entity).setTarget(target);
		}
		((EntityInsentient) ((CraftLivingEntity) entity).getHandle()).setGoalTarget(((CraftLivingEntity) target).getHandle());
	}

	public org.bukkit.inventory.ItemStack setItemUnbreakable(org.bukkit.inventory.ItemStack i) {
		if (!(i instanceof CraftItemStack)) {
			i = CraftItemStack.asCraftCopy(i);
		}
		NBTTagCompound tag = getTag(i);
		if (tag == null) {
			tag = new NBTTagCompound();
		}
		tag.setByte("Unbreakable", (byte) 1);
		return setTag(i, tag);
	}

	public org.bukkit.inventory.ItemStack setItemHideFlags(org.bukkit.inventory.ItemStack i) {
		if (!(i instanceof CraftItemStack)) {
			i = CraftItemStack.asCraftCopy(i);
		}
		NBTTagCompound tag = getTag(i);
		if (tag == null) {
			tag = new NBTTagCompound();
		}
		tag.setInt("HideFlags", 63);
		return setTag(i, tag);
	}

	public void sendTitleToPlayer(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
		PlayerConnection conn = ((CraftPlayer) player).getHandle().playerConnection;

		PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn, stay, fadeOut);
		conn.sendPacket(packet);
		if (title != null) {
			packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, new ChatComponentText(title));
			conn.sendPacket(packet);
		}
		if (subtitle != null) {
			packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, new ChatComponentText(subtitle));
			conn.sendPacket(packet);
		}
	}

	public void sendActionBarMessageToPlayer(Player player, String message) {
		PlayerConnection conn = ((CraftPlayer) player).getHandle().playerConnection;
		PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(message), (byte) 2);
		conn.sendPacket(packet);
	}

	private static NBTTagCompound getTag(org.bukkit.inventory.ItemStack item) {
		if ((item instanceof CraftItemStack)) {
			try {
				Field field = CraftItemStack.class.getDeclaredField("handle");
				field.setAccessible(true);
				return ((net.minecraft.server.v1_8_R3.ItemStack) field.get(item)).getTag();
			} catch (Exception e) {
			}
		}
		return null;
	}

	private static org.bukkit.inventory.ItemStack setTag(org.bukkit.inventory.ItemStack item, NBTTagCompound tag) {
		CraftItemStack craftItem = null;
		if ((item instanceof CraftItemStack)) {
			craftItem = (CraftItemStack) item;
		} else {
			craftItem = CraftItemStack.asCraftCopy(item);
		}
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = null;
		try {
			Field field = CraftItemStack.class.getDeclaredField("handle");
			field.setAccessible(true);
			nmsItem = (net.minecraft.server.v1_8_R3.ItemStack) field.get(item);
		} catch (Exception e) {
		}
		if (nmsItem == null) {
			nmsItem = CraftItemStack.asNMSCopy(craftItem);
		}
		nmsItem.setTag(tag);
		try {
			Field field = CraftItemStack.class.getDeclaredField("handle");
			field.setAccessible(true);
			field.set(craftItem, nmsItem);
		} catch (Exception e) {
		}
		return craftItem;
	}

	@Override
	public void doParticleEffect(Location paramLocation, String paramString, float paramFloat1, float paramFloat2, int paramInt1, float paramFloat3,
			float paramFloat4, int paramInt2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aiGoalSelectorHandler(LivingEntity paramLivingEntity, List<String> paramList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aiTargetSelectorHandler(LivingEntity paramLivingEntity, List<String> paramList) {
		// TODO Auto-generated method stub
		
	}
}
