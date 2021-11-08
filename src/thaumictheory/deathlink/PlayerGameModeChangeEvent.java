package thaumictheory.deathlink;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerGameModeChangeEvent implements Listener
{
	public PlayerGameModeChangeEvent()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerJoin(org.bukkit.event.player.PlayerGameModeChangeEvent event) 
	{
		if(!event.isCancelled() && Common.enabled) 
		{
			GameMode oldGameMode = event.getPlayer().getGameMode();
			GameMode newGameMode = event.getNewGameMode();
			
			if(Common.playableGameMode(newGameMode) && !Common.playableGameMode(oldGameMode)) //enable player
			{
				Common.updateHealthPlayerJoin(event.getPlayer());
			}
			else if(!Common.playableGameMode(newGameMode) && Common.playableGameMode(oldGameMode)) //disable player
			{
				Common.updateHealthPlayerLeave(event.getPlayer());
			}
		}
	}
}
