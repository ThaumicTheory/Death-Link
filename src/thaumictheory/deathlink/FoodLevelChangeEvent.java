package thaumictheory.deathlink;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FoodLevelChangeEvent implements Listener
{
	public FoodLevelChangeEvent()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void FoodLevelChange(org.bukkit.event.entity.FoodLevelChangeEvent event) 
	{
		if(!event.isCancelled() && Common.enabled && event.getEntity() instanceof Player && Common.isPlayerPlaying(((Player)event.getEntity())))
		{
			Player player = (Player) event.getEntity();
			
			for (Player selectedPlayer :  Common.getPlayingPlayers())
			{
				if(selectedPlayer.getUniqueId().equals(player.getUniqueId()))
					continue; // dont do anything to the player that called this event they will take damage normally not by this event
				
				selectedPlayer.setFoodLevel(event.getFoodLevel());
				selectedPlayer.setSaturation(player.getSaturation());
				selectedPlayer.setSaturatedRegenRate(player.getSaturatedRegenRate());
				Common.foodLevel = event.getFoodLevel();
			}
		}
	}
}
