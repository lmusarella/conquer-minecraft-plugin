package conquer.command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import conquer.Main;
import conquer.enums.CategoryEnum;
import conquer.utility.Utility;

public class Commands implements CommandExecutor {

	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("kit")) { 
	        if (sender instanceof Player) {
	            Player p = (Player) sender;         
	            if(args != null && args.length == 1) {
	            	if(args[0].equalsIgnoreCase(CategoryEnum.SALVINI.name())) {
	            		Bukkit.dispatchCommand(p, "skin " +  "url " + Utility.getSalviniSkin());	     			
	     				p.sendMessage("Hai scelto: " + CategoryEnum.SALVINI.name());    				 
	     				Utility.soundPlayer(p, Utility.PRESENTAZIONE, CategoryEnum.SALVINI);   				
	     				Main.getUserStatusFile().initializeUserCategory(p, CategoryEnum.SALVINI);
	            	} else if(args[0].equalsIgnoreCase(CategoryEnum.RENZI.name())) {
	            		Bukkit.dispatchCommand(p, "skin " + "url " + Utility.getRenziSkin());	
	    				p.sendMessage("Hai scelto: " + CategoryEnum.RENZI.name());    				 
	    				Utility.soundPlayer(p, Utility.PRESENTAZIONE, CategoryEnum.RENZI);    				
	    				Main.getUserStatusFile().initializeUserCategory(p, CategoryEnum.RENZI);	
	            	}	            		            		           				
	            ItemStack diamond = new ItemStack(Material.DIAMOND_SWORD);
		        diamond.addEnchantment(Enchantment.DAMAGE_ALL, 1);	
		        ItemStack bricks = new ItemStack(Material.BEDROCK);
		        bricks.setAmount(64);	       
		        p.getInventory().addItem(bricks, diamond);  
	            }      
	        }
	        return true;
		}else {
			return false;
		}
	}
}
