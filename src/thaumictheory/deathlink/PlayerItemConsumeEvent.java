package thaumictheory.deathlink;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;

public class PlayerItemConsumeEvent implements Listener
{
	public PlayerItemConsumeEvent()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerItemConsume(org.bukkit.event.player.PlayerItemConsumeEvent event) 
	{
		if(!event.isCancelled() && Common.enabled) 
		{
			if(event.getItem().getType().equals(Material.MILK_BUCKET)) 
			{
				for(Player player : Common.getPlayingPlayers()) 
				{
					if(event.getPlayer().getUniqueId().equals(player.getUniqueId())) continue; //dont do anything to player who drinks the milk
					if(player.getActivePotionEffects().size() == 0) continue;
					for(PotionEffect effect : player.getActivePotionEffects()) 
					{
						player.removePotionEffect(effect.getType());
					}
					player.playSound(player.getLocation(), Sound.ENTITY_WANDERING_TRADER_DRINK_POTION, 1, 1);
					player.sendMessage(Messaging.chatFormatter("&#cccccc" + event.getPlayer().getName() + " &#FFFFFFdrank milk clearing all potion effects..."));
				}
			}
		}
	}
}
