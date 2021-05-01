package conquer.file;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import conquer.Main;

public class Users {
	
	private File file = new File(Main.getIntance().getDataFolder() + "/users.yml");
	private FileConfiguration usersFile;
	
	public Users() {
		if(!file.exists()) {
			try {
				file.createNewFile();
				usersFile = YamlConfiguration.loadConfiguration(file);
				usersFile.createSection("user_list");
				usersFile.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}else {
			usersFile = YamlConfiguration.loadConfiguration(file);
		}	
	}

	public FileConfiguration getFile() {
		return usersFile;
	}
	
	public void saveUser(Player p) {
		this.usersFile.set("user_list." + p.getName(), true);
		try {
			this.usersFile.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isUserRegister(Player p) {
		System.out.println(p.getName());
		System.out.println(this.usersFile);
		System.out.println(this.usersFile.getString("user_list." + p.getName()));
		return this.usersFile.getString("user_list." + p.getName()) != null;
	}
	
	public List<String> getUserList(){
		return this.usersFile.getStringList("user_list");
	}
}
