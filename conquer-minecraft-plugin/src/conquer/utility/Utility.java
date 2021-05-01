package conquer.utility;

import java.util.Random;

import org.bukkit.entity.Player;

import conquer.Main;
import conquer.bean.UserConf;
import conquer.enums.CategoryEnum;

public class Utility {
	
	public static final Integer ZERO = 0;
	private static final String salvini_image_url = "https://www.minecraftskins.com/uploads/skins/2018/12/27/matteo-salvini-12683142.png?v117";
	private static final String renzi_image_url = "https://www.minecraftskins.com/uploads/skins/2017/05/23/skin_20170523015637185122.png?v117";
	public static final String PROPAGANDA = "prop";
	public static final String PROVOCAZIONE = "prov";
	public static final String PRESENTAZIONE = "pres";
	public static final String FINAL = "final";
	public static final String RANDOM = "randomdeath";
	
	public static UserConf getDefaultUserConf(Player p) {
		UserConf conf = new UserConf();
		conf.setP(p);
		conf.setLevel(ZERO);
		conf.setKills(ZERO);
		conf.setDeath(ZERO);
		conf.setEnergy(ZERO);
		conf.setCategory(null);
		return conf;
	}
	
	public static CategoryEnum getCategoryFromString(String code) {
		return CategoryEnum.fromCode(code);
	}
	
	public static String getSalviniSkin() {
		return salvini_image_url;
	}
	
	public static String getRenziSkin() {
		return renzi_image_url;
	}
	
	public static void soundPlayer(Player p,String soundType, CategoryEnum category) {
		category = category == null ? Main.getUserStatusFile().getUserCategory(p) : category;
		if(category.equals(CategoryEnum.SALVINI)) {
			soundSalvini(p, soundType);
		} else if(category.equals(CategoryEnum.RENZI)) {
			soundRenzi(p, soundType);
		}
	}
	
	public static void soundGenericDeathPlayer(Player p) {
		String sound_url = RANDOM;	
		p.getLocation().getWorld().playSound(p.getLocation(), sound_url, 3.0F, 1.0F);
	}
	
	private static void soundSalvini(Player p, String soundType) {
		String sound_url = soundType + CategoryEnum.SALVINI.name().toLowerCase() + getRandomIndex(getRangeFromTypeS(soundType)).toString();	
		p.getLocation().getWorld().playSound(p.getLocation(), sound_url, 3.0F, 1.0F);
	}
	
	private static void soundRenzi(Player p, String soundType) {
		String sound_url = soundType + CategoryEnum.RENZI.name().toLowerCase() + getRandomIndex(getRangeFromTypeR(soundType)).toString();
		p.getLocation().getWorld().playSound(p.getLocation(), sound_url, 3.0F, 1.0F);
	}
	
	private static Integer getRandomIndex(Integer range) {
		if(range != 0) {
			Random random = new Random();
			int rand = 0;
			while (true){
			    rand = random.nextInt(range);
			    if(rand !=0) break;
			}
			return rand;
		}else {
			return 0;
		}
	}
	
	private static Integer getRangeFromTypeR(String type) {
		Integer res = 0;
		switch(type) {
		case PROPAGANDA:
			res = 7;
			break;
		case PROVOCAZIONE:
			res = 9;
			break;
		default:
			break;
		}
		return res;
	}
	
	private static Integer getRangeFromTypeS(String type) {
		Integer res = 0;
		switch(type) {
		case PROPAGANDA:
			res = 18;
			break;
		case PROVOCAZIONE:
			res = 18;
			break;
		default:
			break;
		}
		return res;
	}

}
