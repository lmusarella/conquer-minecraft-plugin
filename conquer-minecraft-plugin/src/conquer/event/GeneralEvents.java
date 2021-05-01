package conquer.event;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import conquer.Main;
import conquer.bean.UserConf;
import conquer.entity.RidableCaveSpider;
import conquer.entity.RidableEntity;
import conquer.entity.RidableType;
import conquer.file.UserStatus;
import conquer.utility.Utility;
import net.minecraft.server.v1_13_R2.EnumHand;
import net.minecraft.server.v1_13_R2.World;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;

public class GeneralEvents implements Listener  {

	/**
	 * Implementare eventi generali per tutti gli utenti
	 * @param e
	 */
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		  Player p = e.getPlayer();
		  UserConf us = Main.getUserStatusFile().getUserConf(p);
          if(e.getAction() != Action.LEFT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_BLOCK) { 	 
        	  spawnFireBall(p);     	 
        	  e.setCancelled(true);
        	  if(us != null && us.getCategory() != null) {
        		   Utility.soundPlayer(p, Utility.PROVOCAZIONE, us.getCategory());
        	  }
          }else if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
        	  if(us != null && us.getCategory() != null) {
       		   Utility.soundPlayer(p, Utility.PROPAGANDA, us.getCategory());
        	  }
          } 
	}
	
	private void spawnFireBall(Player p) {	
		 Location eye = p.getEyeLocation();
		 Location loc = eye.add(eye.getDirection().multiply(1.2));
		 Fireball fireball = (Fireball) loc.getWorld().spawnEntity(loc, EntityType.FIREBALL);
		 fireball.setVelocity(loc.getDirection().normalize().multiply(2));
		 fireball.setShooter(p); 
	}
	
	
	@EventHandler   
    public void hitfireball(ProjectileHitEvent evento) {
        if(EntityType.FIREBALL != null) {
            Fireball f = (Fireball) evento.getEntity();
            Location location = f.getLocation();
            f.getWorld().createExplosion(location, 10f);
                   
        }
    }
	
	@EventHandler
    public void onEntityDeath(EntityDeathEvent event){
		if(event.getEntity() != null && event.getEntity().getKiller() instanceof Player){
			Player p = event.getEntity().getKiller();
			UserStatus status = Main.getUserStatusFile();
			UserConf us = status.getUserConf(p);
	       	if(us != null && us.getCategory() != null) {	        
		        Utility.soundPlayer(p, Utility.FINAL, us.getCategory());
		        saveUserConf(p, us, status);	        	      
	       	}
		}
    }
	
	private void saveUserConf(Player p, UserConf us, UserStatus status) {
		us.addKills(1);
    	us.addEnergy(10);
    	if(us.getEnergy() % 100 == 0) {
    		us.addLevel(1);
    	}
    	status.saveUserConf(p, us);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		 Player p = e.getEntity().getPlayer();
		 UserStatus status = Main.getUserStatusFile();
		 UserConf us = status.getUserConf(p);
		 Utility.soundGenericDeathPlayer(p);
		 us.addDeath(1);
		 status.saveUserConf(p, us);	 
	}
	
	 @EventHandler
	    public void onPlayerClick(PlayerInteractEvent event) {
	        if (event.getHand() != EquipmentSlot.HAND) {
	            return; // dont fire twice
	        }

	        Entity vehicle = event.getPlayer().getVehicle();
	        if (vehicle == null) {
	            return; // not riding
	        }

	        RidableEntity ridable = RidableType.getRidable(vehicle);
	        if (ridable == null) {
	            return; // not ridable
	        }

	        boolean cancel = false;
	        switch (event.getAction()) {
	            case LEFT_CLICK_BLOCK:
	                cancel = ridable.onClick(event.getClickedBlock(), event.getBlockFace(), EnumHand.MAIN_HAND);
	                break;
	            case RIGHT_CLICK_BLOCK:
	                cancel = ridable.onClick(event.getClickedBlock(), event.getBlockFace(), EnumHand.OFF_HAND);
	                break;
	            case LEFT_CLICK_AIR:
	                cancel = ridable.onClick(EnumHand.MAIN_HAND);
	                break;
	            case RIGHT_CLICK_AIR:
	                cancel = ridable.onClick(EnumHand.OFF_HAND);
	                break;
	        }

	        if (cancel) {
	            event.setCancelled(true);
	        }
	    }

	    @EventHandler
	    public void onPlayerRightClickEntity(PlayerInteractEntityEvent event) {
	        if (event.getHand() != EquipmentSlot.HAND) {
	            return; // dont fire twice
	        }

	        Entity vehicle = event.getPlayer().getVehicle();
	        if (vehicle == null) {
	            return; // not riding
	        }

	        RidableEntity ridable = RidableType.getRidable(vehicle);
	        if (ridable == null) {
	            return; // not ridable
	        }

	        Entity clicked = event.getRightClicked();
	        if (clicked == vehicle) {
	            return; // clicked own vehicle
	        }

	        ridable.onClick(clicked, EnumHand.OFF_HAND);
	    }

	    @EventHandler
	    public void onPlayerLeftClickEntity(EntityDamageByEntityEvent event) {
	        if (!(event.getDamager() instanceof Player)) {
	            return; // not a player
	        }

	        Entity vehicle = event.getDamager().getVehicle();
	        if (vehicle == null) {
	            return; // not riding
	        }

	        RidableEntity ridable = RidableType.getRidable(vehicle);
	        if (ridable == null) {
	            return; // not ridable
	        }

	        Entity clicked = event.getEntity();
	        if (clicked == vehicle) {
	            return; // clicked own vehicle
	        }

	        ridable.onClick(clicked, EnumHand.MAIN_HAND);
	    }
	
	
	
}
