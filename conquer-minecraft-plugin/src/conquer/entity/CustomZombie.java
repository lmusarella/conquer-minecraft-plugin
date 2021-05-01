package conquer.entity;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.entity.Zombie;

import net.minecraft.server.v1_13_R2.ChatComponentText;
import net.minecraft.server.v1_13_R2.EntityPlayer;
import net.minecraft.server.v1_13_R2.EntityTypes;
import net.minecraft.server.v1_13_R2.EntityZombie;
import net.minecraft.server.v1_13_R2.PathfinderGoalNearestAttackableTarget;

public class CustomZombie extends EntityZombie {
	
	
	public CustomZombie(org.bukkit.World world) {
		super(((CraftWorld)world).getHandle());
		// TODO Auto-generated constructor stub
		Zombie customZombie = (Zombie) this.getBukkitEntity();
		
		this.setBaby(true);
		
		customZombie.setHealth(50);
		
		this.setHealth(50);
		this.setCustomName(new ChatComponentText(ChatColor.RED + "Comunista"));
		this.setCustomNameVisible(true);
		
		this.targetSelector.a(0, new PathfinderGoalNearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
		
		this.getWorld().addEntity(this);
	}
	

}
