package thaumictheory.deathlink;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityExhaustionEvent implements Listener
{
	public EntityExhaustionEvent()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void EntityExhaustion(org.bukkit.event.entity.EntityExhaustionEvent event) 
	{
		if(!event.isCancelled() && Common.enabled && event.getEntity() instanceof Player && Common.isPlayerPlaying(((Player)event.getEntity())))
		{
			Player player = (Player) event.getEntity();
			
			for (Player selectedPlayer :  Common.getPlayingPlayers())
			{
				if(selectedPlayer.getUniqueId().equals(player.getUniqueId()))
					continue; // dont do anything to the player that called this event they will take damage normally not by this event
				
				selectedPlayer.setExhaustion(event.getExhaustion());
				selectedPlayer.setSaturation(player.getSaturation());
				Common.exhaustionLevel = event.getExhaustion();
				Common.saturationLevel = player.getSaturation();
			}
		}
	}
}
