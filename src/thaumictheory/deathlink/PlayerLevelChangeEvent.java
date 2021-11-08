package thaumictheory.deathlink;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerLevelChangeEvent implements Listener
{
	public PlayerLevelChangeEvent()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerLevelChange(org.bukkit.event.player.PlayerLevelChangeEvent event) 
	{
		if(Common.enabled && Common.isPlayerPlaying(event.getPlayer()))
		{
			
			for (Player selectedPlayer :  Common.getPlayingPlayers())
			{
				if(selectedPlayer.getUniqueId().equals(event.getPlayer().getUniqueId()))
					continue; // dont do anything to the player that called this event they will get levels normally not by this event
				
				selectedPlayer.setLevel(event.getNewLevel());
			}
			Common.xpLevel = event.getNewLevel();
		}
	}
}
