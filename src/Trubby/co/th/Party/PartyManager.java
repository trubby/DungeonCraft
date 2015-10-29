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
	HashMap<Player, Party> inviting = new HashMap<>();

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

	public void invite(Player p, Party party) {

		p.sendMessage(new ComponentBuilder("[Yes] ").color(ChatColor.GREEN).bold(true)
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Join party! : ").color(ChatColor.GRAY).append(party.name).color(ChatColor.YELLOW).create() ))
				.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "partyaccept"))
				
				.append("[No]").color(ChatColor.GREEN).bold(true)
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Decline party invite!").color(ChatColor.GRAY).create() ))
				.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "partydecline"))
				.create() + "");

	}
	
	public void create(Player p, String name){
		Party party = new Party(p, name);
		partys.add(party);
		p.openInventory(party.menu);
	}
	
	public void addPlayer(Player p, Party party){
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
			dgp.p.sendMessage(ChatColor.GREEN + p.getName() + " has left the party.");
		}
		
		party.updatePlayers();
	}

}
