package thaumictheory.deathlink;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
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
	public void EntityRegainHealth(org.bukkit.event.entity.EntityRegainHealthEvent event) 
	{
		if(!event.isCancelled() && Common.enabled && event.getEntity() instanceof Player && Common.isPlayerPlaying(((Player)event.getEntity())))
		{
			Player player = (Player) event.getEntity();
			
			for (Player selectedPlayer :  Common.getPlayingPlayers())
			{
				if(selectedPlayer.getUniqueId().equals(player.getUniqueId()))
					continue; // dont do anything to the player that called this event they will take damage normally not by this event
				
				if(selectedPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() < selectedPlayer.getHealth() + event.getAmount()) selectedPlayer.setHealth(selectedPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
				else selectedPlayer.setHealth(player.getHealth() + event.getAmount()); //heals all the players
				if(event.getAmount() >= 0.5f) selectedPlayer.sendMessage(Messaging.chatFormatter("&#cccccc" + player.getName() + " &#FF0000 healed &#FFcccc" + Common.moreFriendlyRounding(event.getAmount()/2) + " hearts!"));
			}
			Common.currentHP = player.getHealth() + event.getAmount();
			Common.currentHPPercent = Common.currentHP / Common.maxHealth;
		}
	}
}
