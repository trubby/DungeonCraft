package Trubby.co.th.Party;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import Trubby.co.th.DG;
import Trubby.co.th.Player.DGPlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

public class PartyManager {

	ArrayList<Party> partys = new ArrayList<>();
	ArrayList<UUID> typingName = new ArrayList<>();
	public HashMap<UUID, Party> inviting = new HashMap<>();

	public Party getParty(Player p) {

		DGPlayer dgp = DG.plugin.dpm.getDGPlayer(p);

		if (dgp == null)
			return null;

		for (Party party : partys) {
			if (party.players.contains(dgp)) {
				return party;
			}
		}
		return null;
	}

	public void invite(Player p, Party party) { // Party >> tub_ invite you to join party [Yes] [No]
		p.spigot().sendMessage(new ComponentBuilder("[YES] ").color(ChatColor.GREEN).bold(true)
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Join party! : ").color(ChatColor.GREEN).append(party.name).color(ChatColor.YELLOW).create() ))
				.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept"))
				
				.append("[NO]").color(ChatColor.RED).bold(true)
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Decline party invite!").color(ChatColor.RED).create() ))
				.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party decline"))
				.create());

		inviting.put(p.getUniqueId(), party);
	}
	
	public void create(Player p, String name){
		Party party = new Party(p, name);
		partys.add(party);
		p.openInventory(party.menu);
	}
	
	public void addPlayer(Player p, Party party){
		if(party.players.size() > 4){
			p.sendMessage(ChatColor.RED + "This party is full!");
			return;
		}
		
		party.players.add(DG.plugin.dpm.getDGPlayer(p));
		
		for(DGPlayer dgp : party.players){
			dgp.p.sendMessage(ChatColor.GREEN + p.getName() + " has join the party.");
		}
		
		party.updatePlayers();
	}
	
	public void removePlayer(Player p, Party party){
		party.players.remove(DG.plugin.dpm.getDGPlayer(p));
		p.sendMessage(ChatColor.RED + "You left the party.");
		
		for(DGPlayer dgp : party.players){
			dgp.p.sendMessage(ChatColor.RED + p.getName() + " has left the party.");
		}
		
		party.updatePlayers();
	}

}
