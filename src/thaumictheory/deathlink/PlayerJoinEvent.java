package thaumictheory.deathlink;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinEvent implements Listener
{
	public PlayerJoinEvent()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) 
	{
		if(Common.playableGameMode(event.getPlayer().getGameMode())) 
		{
			Common.updateHealthPlayerJoin(event.getPlayer());
		}
	}
}
