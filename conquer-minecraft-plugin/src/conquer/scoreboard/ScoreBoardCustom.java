package conquer.scoreboard;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import conquer.Main;
import conquer.bean.UserConf;

public class ScoreBoardCustom {

	private String title;
	private String subTitle;
	private List<String> content;
	private Scoreboard scoreBoard;
	private Objective ob;
	private Player p;
	private String[] score;
	public static HashMap<String, ScoreBoardCustom> enable = new HashMap<>();
	
	public static final String LEVEL = "Livello";
	public static final String ENERGY = "Energia";
	public static final String KILLS = "Kill";
	public static final String DEATH = "Death";
	
	public ScoreBoardCustom(String title, String subTitle, List<String> content, Player p) {
		super();
		this.title = title;
		this.subTitle = subTitle;
		this.content = content;
		this.p = p;
		this.ob = null;
		this.score = new String[content.size()];
		settingScoreBoard();
	}
	
	@SuppressWarnings("deprecation")
	private void settingScoreBoard() {
		this.scoreBoard = Bukkit.getScoreboardManager().getNewScoreboard();
		this.ob = this.scoreBoard.registerNewObjective(this.p.getName(), this.p.getName() + "-0");
		this.ob.setDisplaySlot(DisplaySlot.SIDEBAR);
		setContent();
	}
	
	private void setContent() {
		
		this.ob.setDisplayName(translate(this.title));
		this.ob.getScore(this.subTitle).setScore(this.content.size() + 1);
		int j = this.content.size();
		String count = "";
		for(String line: this.content) {
			line = translate(line);
			if(line.isEmpty()) {
				count = count + "$r";
				line = count;
			}
			this.score[(j - 1)] = line;
			this.ob.getScore(line).setScore(j);
			j--;
		}
	}
	
	public void setScoreBoard() {
		this.p.setScoreboard(this.scoreBoard);
		enable.put(this.p.getName(), this);
	}
	
	public void removeScoreBoard() {
		this.p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		this.ob.unregister();
		enable.remove(this.p.getName());
	}
	
	public void updateScoreBoard() {
		for(String s: this.score) {
			this.scoreBoard.resetScores(s);
		}
		setContent();
		this.p.setScoreboard(this.scoreBoard);
	}
	
	private String translate(String s) {
		UserConf us = Main.getUserStatusFile().getUserConf(this.p);
		s = s.replace("[ONLINE]", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()));
		s = s.replace("[MAXPLAYER]", String.valueOf(Bukkit.getServer().getMaxPlayers()));
		s = s.replace("[CATEGORY]", us.getCategory() != null ? us.getCategory().name() : "CIVILE");
		s = s.replace("[NAME]", this.p.getName());
		s = s.replace("[LEVEL]", LEVEL);
		s = s.replace("[ENERGY]", ENERGY);
		s = s.replace("[KILLS]", KILLS);
		s = s.replace("[DEATH]", DEATH);
		s = s.replace("[LEV-VALUE]", String.valueOf(us.getLevel()));
		s = s.replace("[ENG-VALUE]", String.valueOf(us.getEnergy()));
		s = s.replace("[KILL-VALUE]", String.valueOf(us.getKills()));
		s = s.replace("[DEATH-VALUE]", String.valueOf(us.getDeath()));
		return s;
	}
}
