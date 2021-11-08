package thaumictheory.deathlink;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

public class EntityPotionEffectEvent implements Listener
{
	public EntityPotionEffectEvent()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void EntityPotionEffect(org.bukkit.event.entity.EntityPotionEffectEvent event) 
	{
		if(!event.isCancelled() && Common.enabled && event.getEntity() instanceof Player && Common.isPlayerPlaying(((Player)event.getEntity())) && !event.getCause().equals(Cause.PLUGIN))
		{
			if(event.getNewEffect() != null && Common.sharableEffect(event.getNewEffect().getType())) 
			{
				Player player = (Player) event.getEntity();
				
				for (Player selectedPlayer :  Common.getPlayingPlayers())
				{
					if(selectedPlayer.getUniqueId().equals(player.getUniqueId()))
						continue; // dont do anything to the player that called this event they will get the potion normally not by this event
					
					selectedPlayer.addPotionEffect(event.getNewEffect());
					selectedPlayer.sendMessage(Messaging.chatFormatter("&#cccccc" + player.getName() + " &#003300 gave everyone " + event.getNewEffect().getType().toString() + " for " + (((float)event.getNewEffect().getDuration())/20) + " seconds."));
				}
			}
		}
	}
}
