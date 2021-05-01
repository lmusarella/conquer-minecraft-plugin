package conquer;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.Function;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;

import conquer.command.Commands;
import conquer.entity.CustomZombie;
import conquer.entity.RidableCaveSpider;
import conquer.event.GeneralEvents;
import conquer.file.UserStatus;
import conquer.file.Users;
import conquer.scoreboard.ScoreBoardCustom;
import conquer.utility.RegistryHax;
import net.minecraft.server.v1_13_R2.DataConverterRegistry;
import net.minecraft.server.v1_13_R2.DataConverterTypes;
import net.minecraft.server.v1_13_R2.EntityTypes;
import net.minecraft.server.v1_13_R2.SharedConstants;

public class Main extends JavaPlugin implements Listener {

	public static Main plugin;
	public static Users usersFile;
	public static UserStatus userStatusFile;
	
	public static EntityTypes CUSTOM_ZOMBIE;
	
	public void onEnable() {
		plugin = this;
		//come registrare un evento
		Bukkit.getPluginManager().registerEvents(new GeneralEvents(), this);
		Bukkit.getPluginManager().registerEvents(this, this);
		//registrare un comand
		getCommand("kit").setExecutor(new Commands());
		//salvare un file config
		saveDefaultConfig();
		
		usersFile = new Users();
		userStatusFile = new UserStatus();
		
		runScoreboardScheduler();
	}
	
	public void onLoad() {
		  RegistryHax.injectNewEntityTypes("custom_cave_spider", "cave_spider", RidableCaveSpider.class, RidableCaveSpider::new);
		  RegistryHax.rebuildWorldGenMobs();

	        // Fix biome's mobs
	      RegistryHax.rebuildBiomes();
	}
	
	public void onDisable() {
		
	}
	
	public static Main getIntance() {
		return plugin;
	}
	
	public static Users getUserFile() {
		return usersFile;
	}
	
	public static UserStatus getUserStatusFile() {
		return userStatusFile;
	}
	
	public static void onReaload() {
		plugin.reloadConfig();
		plugin.getServer().getPluginManager().disablePlugin(plugin);
		plugin.getServer().getPluginManager().enablePlugin(plugin);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {	
		
		Player player = e.getPlayer();
		
		if(Main.getUserFile().isUserRegister(player)) {
			loginHandle(player);
		}else {
			firstLoginHandle(player);
		}
		
		setScoreboard(player);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		removeScoreboard(e.getPlayer());
	}
	
	private void loginHandle(Player player) {	
		player.getPlayer().sendMessage("Bentornato! " + player.getName());
		System.out.println(Main.getUserStatusFile().getUserConf(player).toString());
	}
	
	private void firstLoginHandle(Player player) {
		player.sendMessage("Benvenuto! " + player.getName());
		Main.getUserFile().saveUser(player);
		Main.getUserStatusFile().initializeUserConf(player);
	}
	
	private void removeScoreboard(Player p) {
		if(ScoreBoardCustom.enable.containsKey(p.getName())) {
			ScoreBoardCustom.enable.get(p.getName()).removeScoreBoard();
		}
	}
	
	private void setScoreboard(Player player) {
		if(!ScoreBoardCustom.enable.containsKey(player.getName())){
			ScoreBoardCustom sb = new ScoreBoardCustom(getConfig().getString("scoreboard.title"), getConfig().getString("scoreboard.subtitle"), getConfig().getStringList("scoreboard.content"),  player);
			sb.setScoreBoard();
		}
	}
	
	private void runScoreboardScheduler() {
		Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
			
			@Override
			public void run() {
				for(Player p: Bukkit.getServer().getOnlinePlayers()) {
					if(ScoreBoardCustom.enable.containsKey(p.getName())) {
						ScoreBoardCustom.enable.get(p.getName()).updateScoreBoard();
						System.out.println("Scoreboard: updated!");
					}
				}
				
			}
		}, 200L, Integer.parseInt(getConfig().getString("scoreboard.update-time")) * 20);
	}
	
}
