package thaumictheory.deathlink;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class Common {
	public static Spigot spigot;
	public static boolean enabled = true;
	public static int maxPlayerHealthIncrease = 5;
	public static double maxHealth;
	public static double currentHP;
	public static double currentHPPercent = 1;
	public static int foodLevel = 20;
	public static float exhaustionLevel = 0;
	public static float saturationLevel = 0;
	
	public static List<Player> getPlayingPlayers() 
	{
		List<Player> playerList = new ArrayList<>();
		for(Player player : Bukkit.getOnlinePlayers()) 
		{
			if(Common.playableGameMode(player.getGameMode()) && player.getHealth() > 0) 
			{
				playerList.add(player);
			}
		}
		return playerList;
	}
	public static void resetCheck() 
	{
		if(currentHP > 0) return;
		
		foodLevel = 20;
		exhaustionLevel = 0;
		saturationLevel = 0;
		currentHPPercent = 1;
		currentHP = 0;
		maxHealth = 0;
		for(Player player : getPlayingPlayers()) 
		{
			updateHealthPlayerJoin(player);
		}
	}
	
	public static boolean isPlayerPlaying(Player player) 
	{
		List<Player> playing = getPlayingPlayers();
		return playing.contains(player);
	}
	public static int numberOfPlayers() 
	{
		return getPlayingPlayers().size();
	}
	
	public static void updateHealthPlayerJoin(Player player) 
	{
		if(numberOfPlayers() <= maxPlayerHealthIncrease) 
		{
			maxHealth +=20;
			for (Player selectedPlayer :  Common.getPlayingPlayers())
			{
				if(selectedPlayer.getUniqueId().equals(player.getUniqueId())) continue;
				selectedPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
				selectedPlayer.setHealth(maxHealth * currentHPPercent);
			}
			player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
			player.setHealth(maxHealth * currentHPPercent);
		}
		else
		{
			player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
			player.setHealth(currentHP);
		}
	}
	public static void updateHealthPlayerLeave(Player player) 
	{
		player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
		if(numberOfPlayers() < maxPlayerHealthIncrease) 
		{
			maxHealth -=20;
			for (Player selectedPlayer :  Common.getPlayingPlayers())
			{
				if(selectedPlayer.getUniqueId().equals(player.getUniqueId()))
					continue; // dont do anything to the player that called this event they will take damage normally not by this event
				selectedPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
				selectedPlayer.setHealth(maxHealth * currentHPPercent);
			}
		}
	}
	
	public static boolean playableGameMode(GameMode gameMode) 
	{
		return gameMode.equals(GameMode.SURVIVAL) || gameMode.equals(GameMode.ADVENTURE);
	}
}
