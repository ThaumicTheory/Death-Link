package thaumictheory.deathlink;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class EntityDamageEvent implements Listener
{
	public EntityDamageEvent()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void EntityDamage(org.bukkit.event.entity.EntityDamageEvent event) 
	{
		if(!event.isCancelled() && Common.enabled && event.getEntity() instanceof Player && Common.isPlayerPlaying(((Player)event.getEntity())) && !event.getCause().equals(DamageCause.CUSTOM))
		{
			Player player = (Player) event.getEntity();
			
			for (Player selectedPlayer :  Common.getPlayingPlayers())
			{
				if(selectedPlayer.getUniqueId().equals(player.getUniqueId()))
					continue; // dont do anything to the player that called this event they will take damage normally not by this event
				
				selectedPlayer.setHealth(player.getHealth()); // corrects incase of a desync
				selectedPlayer.damage(event.getFinalDamage()); //damages all the players
				if(event.getFinalDamage() > 0.05f) selectedPlayer.sendMessage(Messaging.chatFormatter("&#cccccc" + player.getName() + " &#FF0000took &#990000" + Common.moreFriendlyRounding(event.getFinalDamage()/2) + " hearts &#ff0000of damage" + Common.friendlyReason(event.getCause()) + "!"));
			}
			Common.currentHP = player.getHealth() - event.getFinalDamage();
			Common.currentHPPercent = Common.currentHP / Common.maxHealth;
		}
	}
}
