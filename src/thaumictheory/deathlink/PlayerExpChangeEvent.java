package thaumictheory.deathlink;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerExpChangeEvent implements Listener
{
	public PlayerExpChangeEvent()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerExpChange(org.bukkit.event.player.PlayerExpChangeEvent event) 
	{
		if(Common.enabled && Common.isPlayerPlaying(event.getPlayer()))
		{
			for (Player selectedPlayer :  Common.getPlayingPlayers())
			{
				if(selectedPlayer.getUniqueId().equals(event.getPlayer().getUniqueId()))
					continue; // dont do anything to the player that called this event they will get exp normally not by this event
				
				Common.spigot.scheduler.scheduleSyncDelayedTask(Common.spigot, new Runnable()
				{

					@Override
					public void run() 
					{
						selectedPlayer.setExp(event.getPlayer().getExp()); //delay the task by as little as possible so this event is complete on the task updating
						Common.xpToNextLevelPercent = event.getPlayer().getExp();
					} 		
		    	}, 0);
				
			}

		}
	}
}
