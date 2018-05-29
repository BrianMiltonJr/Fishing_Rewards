package com.johnwillikers.fisher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.johnwillikers.fisher.events.OnFished;
import com.johnwillikers.fisher.objects.Rewards;
import com.johnwillikers.fisher.objects.Tiers;

import net.md_5.bungee.api.ChatColor;

public class FishingRewards extends JavaPlugin {

	public static String dir = "./plugins/fishing_rewards/";
	public static File rewardsFile = new File(dir + "rewards.json");
	public static File tiersFile = new File(dir + "tiers.json");
	public static Rewards rewards = new Rewards();
	public static Tiers tiers = new Tiers();
	
	public static void print(File file, String string) {
		try {
			if(file.exists()) {
				file.delete();
			}
			PrintWriter pr = new PrintWriter(file);
			pr.println(string);
			pr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void messagePlayer(String msg, Player p) {
		String body = ChatColor.LIGHT_PURPLE + "[" + ChatColor.GOLD + "Ranked Rods" + ChatColor.LIGHT_PURPLE + "] " + ChatColor.AQUA;
		body = body + msg;
		Bukkit.getPlayer(p.getUniqueId()).sendMessage(body);
	}
	
	public void createFilePath() {
		File file = new File(dir);
		if(!file.exists()) {
			file.mkdirs();
		}
		/*if(!rewards.exists()) {
			YamlGuy.buildDefaultReward();
		}
		if(!tiers.exists()) {
			YamlGuy.buildDefaultTier();
		}*/
	}
	
	public static void log(String name, String type, String msg){
		//System.out.println("[" + name + "]|" + "[" + level + "] " + msg); deprecated code, old method of sending messages to console, but doesn't support coloring. Left for the lulz
		Server server = Bukkit.getServer();
		ConsoleCommandSender console = server.getConsoleSender();
		console.sendMessage(ChatColor.WHITE + "[" + name + ChatColor.WHITE + "]|" + "[" + type + ChatColor.WHITE + "] " + msg);
	}
	
	@Override
	public void onEnable() {
		//Start Server Init
		createFilePath();
		getServer().getPluginManager().registerEvents(new OnFished(), this);
		this.getCommand("rr").setExecutor(new Commands());
		JSONGuy.loadRewards();
		ObjectLogic.logRewards();
		JSONGuy.loadTiers();
		ObjectLogic.logTiers();
		//Create Custom Ranked Rod Recipe
		ItemStack rod = new ItemStack(Material.FISHING_ROD);
		ItemMeta meta = rod.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("XP: 0");
		lore.add("Tier: 0");
		meta.setLore(lore);
		meta.setDisplayName("Ranked Rod");
		rod.setItemMeta(meta);
		ShapelessRecipe rankedRod = new ShapelessRecipe(rod);
		rankedRod.addIngredient(Material.FISHING_ROD);
		rankedRod.addIngredient(Material.DIAMOND);
		rankedRod.addIngredient(Material.GLOWSTONE_DUST);
		getServer().addRecipe(rankedRod);
	}
	
	@Override
	public void onDisable() {
		tiers.destroy();
		rewards.destroy();
	}
}