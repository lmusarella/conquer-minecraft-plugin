package conquer.file;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import conquer.Main;
import conquer.bean.UserConf;
import conquer.enums.CategoryEnum;
import conquer.utility.Utility;

public class UserStatus {
	
	private File file = new File(Main.getIntance().getDataFolder() + "/users-status.yml");
	private FileConfiguration userStatus;
	
	public UserStatus() {
		if(!file.exists()) {
			try {
				file.createNewFile();
				userStatus = YamlConfiguration.loadConfiguration(file);
				userStatus.createSection("user_list");
				userStatus.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}else {
			userStatus = YamlConfiguration.loadConfiguration(file);
		}	
	}

	public FileConfiguration getFile() {
		return userStatus;
	}
	
	public void saveUserConf(Player p, UserConf userConf) {
		
		this.userStatus.set("user_list." + p.getName() + ".level", userConf.getLevel());
		this.userStatus.set("user_list." + p.getName() + ".energy", userConf.getEnergy());
		this.userStatus.set("user_list." + p.getName() + ".kills", userConf.getKills());
		this.userStatus.set("user_list." + p.getName() + ".death", userConf.getDeath());
		this.userStatus.set("user_list." + p.getName() + ".category", userConf.getCategory().getCode());
		
		try {
			this.userStatus.save(file);
			System.out.println(p.getName() + " status: update!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initializeUserConf(Player p) {
		
		UserConf userConf = Utility.getDefaultUserConf(p);
		
		userStatus.createSection("user_list." + p.getName());
		userStatus.createSection("user_list." + p.getName() + ".level");
		userStatus.createSection("user_list." + p.getName() + ".energy");
		userStatus.createSection("user_list." + p.getName() + ".kills");
		userStatus.createSection("user_list." + p.getName() + ".death");
		userStatus.createSection("user_list." + p.getName() + ".category");
			
		userStatus.set("user_list." + p.getName() + ".level", userConf.getLevel());
		userStatus.set("user_list." + p.getName() + ".energy", userConf.getEnergy());
		userStatus.set("user_list." + p.getName() + ".kills", userConf.getKills());
		userStatus.set("user_list." + p.getName() + ".death", userConf.getDeath());
		
		try {
			this.userStatus.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initializeUserCategory(Player p,CategoryEnum category) {
		
		userStatus.set("user_list." + p.getName() + ".category", category.getCode());
		
		try {
			this.userStatus.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Set<String> getUserList(){
		return this.userStatus.getConfigurationSection("user_list").getKeys(false);
	}
	
	public UserConf getUserConf(Player p) {
		UserConf conf = new UserConf();
		conf.setP(p);
		conf.setLevel(Integer.parseInt(this.userStatus.getString("user_list." + p.getName() + ".level")));
		conf.setKills(Integer.parseInt(this.userStatus.getString("user_list." + p.getName() + ".kills")));
		conf.setDeath(Integer.parseInt(this.userStatus.getString("user_list." + p.getName() + ".death")));
		conf.setEnergy(Integer.parseInt(this.userStatus.getString("user_list." + p.getName() + ".energy")));
		conf.setCategory(Utility.getCategoryFromString(this.userStatus.getString("user_list." + p.getName() + ".category")));
		return conf;
	}
	
	public CategoryEnum getUserCategory(Player p) {
		return Utility.getCategoryFromString(this.userStatus.getString("user_list." + p.getName() + ".category"));
	}
}
