package Trubby.co.th.Util;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

@SuppressWarnings("deprecation")
public class ScoreboardUtils {

	public static Scoreboard build(String str, ArrayList<UUID> players) {
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = sb.registerNewObjective("LIFE", "dummy");
		obj.setDisplayName(str);
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);

		for(UUID uuid : players){
			Score score = obj.getScore(Bukkit.getPlayer(uuid));
			score.setScore(3);
		}
		
		return sb;
	}

	public static void reset(Scoreboard sb) {
		sb.getObjective(DisplaySlot.SIDEBAR).unregister();
	}

	public static void clear(Player... players) {
		for (Player p : players)
			p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}

}