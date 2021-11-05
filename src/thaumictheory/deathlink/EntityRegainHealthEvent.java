package thaumictheory.deathlink;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityRegainHealthEvent implements Listener
{
	public EntityRegainHealthEvent()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void EntityDamage(org.bukkit.event.entity.EntityRegainHealthEvent event) 
	{
		if(!event.isCancelled() && Common.enabled && event.getEntity() instanceof Player && Common.isPlayerPlaying(((Player)event.getEntity())))
		{
			Player player = (Player) event.getEntity();
			
			for (Player selectedPlayer :  Common.getPlayingPlayers())
			{
				if(selectedPlayer.getUniqueId().equals(player.getUniqueId()))
					continue; // dont do anything to the player that called this event they will take damage normally not by this event
				
				selectedPlayer.setHealth(player.getHealth() + event.getAmount()); //damages all the players
				Common.currentHP = player.getHealth() + event.getAmount();
				Common.currentHPPercent = Common.currentHP / Common.maxHealth;
			}
		}
	}
}
