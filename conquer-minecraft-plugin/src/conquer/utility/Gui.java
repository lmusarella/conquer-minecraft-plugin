package conquer.utility;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Gui {
	
	private Player p;
	
	public Gui(Player p) {
		this.p = p;
	}
	
	public ItemStack createButton(Material id, int amount, String display, List<String> strings) {
		ItemStack item = new ItemStack(id, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(display);
		meta.setLore(strings);
		item.setItemMeta(meta);
		return item;			
	}
	
	public void open() {
		Inventory gui = Bukkit.createInventory(null, 27, "Indole");
		gui.setItem(0, createButton(Material.DIAMOND_SWORD, 1, "Salvini", Arrays.asList("Salviniano")));
		gui.setItem(8, createButton(Material.SHIELD, 1, "Renzi", Arrays.asList("Renziano")));
		this.p.openInventory(gui);
	}
	

}
