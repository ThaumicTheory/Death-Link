package thaumictheory.deathlink;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
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
			int foodDifference = event.getFoodLevel() - player.getFoodLevel();
			boolean positive = event.getFoodLevel() - player.getFoodLevel() > 0;
			
			for (Player selectedPlayer :  Common.getPlayingPlayers())
			{
				if(selectedPlayer.getUniqueId().equals(player.getUniqueId()))
					continue; // dont do anything to the player that called this event they will take damage normally not by this event
				
				selectedPlayer.setFoodLevel(event.getFoodLevel());
				//selectedPlayer.setSaturatedRegenRate(player.getSaturatedRegenRate());
				if(positive) 
				{
					selectedPlayer.playSound(selectedPlayer.getLocation(), Sound.ENTITY_PLAYER_BURP, 1, 1);
					selectedPlayer.sendMessage(Messaging.chatFormatter("&#cccccc" + player.getName() + " &#00FFFFate some food &#009999+" + foodDifference + "&#00FFFF!"));
				}
				else 
				{
					//selectedPlayer.sendMessage(Messaging.chatFormatter("&#cccccc" + player.getName() + " &#FFFF00depleted hunger &#555555" + foodDifference + "&#FFFF00!"));
				}
			}
			Common.foodLevel = event.getFoodLevel();
		}
	}
}
