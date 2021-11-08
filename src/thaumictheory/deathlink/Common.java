package thaumictheory.deathlink;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Common {
	public static Spigot spigot;
	public static boolean enabled = true;
	public static final int maxPlayerHealthIncrease = 11;
	public static final double healthPerIncrease = 6;
	public static final double healthStarting = 20;
	public static double maxHealth;
	public static double currentHP;
	public static double currentHPPercent = 1;
	public static int foodLevel = 20;
	public static int xpLevel = 0;
	public static float xpToNextLevelPercent = 0;
	
	public static Player host;
	
	public static boolean sharableEffect(PotionEffectType potionType) 
	{
		List<PotionEffectType> blackListed = List.of(PotionEffectType.HARM, PotionEffectType.HEAL, PotionEffectType.WITHER, PotionEffectType.POISON,PotionEffectType.REGENERATION);
		return !blackListed.contains(potionType);
	}
	
	public static double moreFriendlyRounding(double value) 
	{
		value *=10;
		value = Math.round(value);
		value /=10;
		return value;
	}
	
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
		assignHost(player);
		if(numberOfPlayers() < maxPlayerHealthIncrease) 
		{
			if(maxHealth < healthStarting) maxHealth = healthStarting;
			else maxHealth +=healthPerIncrease;
			for (Player selectedPlayer :  Common.getPlayingPlayers())
			{
				if(selectedPlayer.getUniqueId().equals(player.getUniqueId())) continue;
				selectedPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
				selectedPlayer.setHealth(maxHealth * currentHPPercent);
			}
			player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
			player.setHealth(maxHealth * currentHPPercent);
			currentHP = maxHealth * currentHPPercent;
		}
		else
		{
			player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
			player.setHealth(currentHP);
		}
		if(xpLevel != 0) player.setLevel(xpLevel);
		if(foodLevel != 20) player.setFoodLevel(foodLevel);
		if(xpToNextLevelPercent != 0) player.setExp(xpToNextLevelPercent);
		for(PotionEffect effect : host.getActivePotionEffects()) 
		{
			if(sharableEffect(effect.getType())) player.addPotionEffect(effect);
		}
	}
	public static void assignNewHost(Player leaver) 
	{
		if(!host.getUniqueId().equals(leaver.getUniqueId())) return;
		int i = 0;
		Player player = null;
		List<Player> players = getPlayingPlayers();
		players.remove(leaver);
		
		do 
		{
			if(i >= players.size()) 
			{
				host = null;
				return;
			}
			player = getPlayingPlayers().get(i++);
		}while(leaver.getUniqueId().equals(player.getUniqueId()));
	}
	public static void assignHost(Player joiner) 
	{
		List<Player> players = getPlayingPlayers();
		players.add(joiner);
		if(host != null) return;
		host = players.get(0);
	}
	public static void updateHealthPlayerLeave(Player player) 
	{
		assignNewHost(player);
		player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
		if(numberOfPlayers() <= maxPlayerHealthIncrease) 
		{
			if(maxHealth == healthStarting) maxHealth = 0;
			else maxHealth -=healthPerIncrease;
			for (Player selectedPlayer :  Common.getPlayingPlayers())
			{
				if(selectedPlayer.getUniqueId().equals(player.getUniqueId()))
					continue; // dont do anything to the player that called this event they will take damage normally not by this event
				selectedPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
				selectedPlayer.setHealth(maxHealth * currentHPPercent);
			}
			currentHP = maxHealth * currentHPPercent;
		}
	}
	
	public static boolean playableGameMode(GameMode gameMode) 
	{
		return gameMode.equals(GameMode.SURVIVAL) || gameMode.equals(GameMode.ADVENTURE);
	}
	
	public static String friendlyReason(DamageCause reason) 
	{
		switch(reason.toString()) 
		{
			case "BLOCK_EXPLOSION":
				return " from an exploding block";
			case "CONTACT":
				return " because they like spikey things";
			case "CRAMMING":
				return " because they are too popular";
			case "CUSTOM":
				return " because a plugin hates them";
			case "DRAGON_BREATH":
				return " from dragons breath";
			case "DROWNING":
				return " because they forgot to breathe";
			case "DRYOUT":
				return " because they somehow hacked the game and are now taking damage from a normally impossible way. They have turned into a fish they are drying out";
			case "ENTITY_ATTACK":
				return ", they are getting attacked";
			case "ENTITY_EXPLOSION":
				return " from an exploding entity";
			case "ENTITY_SWEEP_ATTACK":
				return " because they were too close to a attack";
			case "FALL":
				return " because they broke their legs";
			case "FALLING_BLOCK":
				return " because they weren't wearing a hard hat";
			case "FIRE":
				return " because they're standing in fire";
			case "FIRE_TICK":
				return " they are still on fire";
			case "FLY_INTO_WALL":
				return " because they skipped out on their flying lessons";
			case "FREEZE":
				return " because of hypothermia";
			case "HOT_FLOOR":
				return ". They didn't check their feet";
			case "LAVA":
				return ". They like swimming in lava";
			case "LIGHTNING":
				return ". They were struck by lightning";
			case "MAGIC":
				return ". They have been hit by magic";
			case "MELTING":
				return " because they are still hacking and they are melting like a snowman";
			case "POISON":
				return ". They are still poisoned";
			case "PROJECTILE":
				return " because some projectile hit them";
			case "STARVATION":
				return ". They, I mean you should check your hunger";
			case "SUFFOCATION":
				return " because their current position being shared by a block";
			case "suicide":
				return ". Well thats just boring isn't it, you are mean't to die by others not yourself :(";
			case "THORNS":
				return " because they don't realise that they are attacking thorns";
			case "VOID":
				return " they are in the void, well GG I guess";
			case "WITHER":
				return " they are withering away";
			default:
				return ". Well ehh... If you see this i misstyped something in my code or a new version has a new damage type so post an issue on github with what damage type it is; thanks!";
				
		}
	}
}
