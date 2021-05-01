package conquer.bean;

import org.bukkit.entity.Player;

import conquer.enums.CategoryEnum;


public class UserConf {

	private Player p;
	private Integer level;
	private CategoryEnum category;
	private Integer energy;
	private Integer kills;
	private Integer death;
	
	public UserConf() {
	}
	
	public void addLevel(Integer value) {
		this.level = this.level + value;
	}
	public void addEnergy(Integer value) {
		this.energy = this.energy + value;
	}
	public void addKills(Integer value) {
		this.kills = this.kills + value;
	}
	public void addDeath(Integer value) {
		this.death = this.death + value;
	}
	
	public Player getP() {
		return p;
	}

	public void setP(Player p) {
		this.p = p;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public CategoryEnum getCategory() {
		return category;
	}

	public void setCategory(CategoryEnum category) {
		this.category = category;
	}

	public Integer getEnergy() {
		return energy;
	}

	public void setEnergy(Integer energy) {
		this.energy = energy;
	}

	public Integer getKills() {
		return kills;
	}

	public void setKills(Integer kills) {
		this.kills = kills;
	}

	public Integer getDeath() {
		return death;
	}

	public void setDeath(Integer death) {
		this.death = death;
	}

	@Override
	public String toString() {
		return "UserConf [p=" + p + ", level=" + level + ", category=" + category + ", energy=" + energy + ", kills="
				+ kills + ", death=" + death + "]";
	}
	
}
