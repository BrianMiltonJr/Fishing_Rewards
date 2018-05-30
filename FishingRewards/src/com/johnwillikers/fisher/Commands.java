package com.johnwillikers.fisher;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.johnwillikers.fisher.objects.Tier;

import net.md_5.bungee.api.ChatColor;

public class Commands implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		@SuppressWarnings("unused")
		Player player = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("rr")) {
			if(args[0].equalsIgnoreCase("reload")) {
				FishingRewards.tiers.destroy();
				FishingRewards.rewards.destroy();
				JSONGuy.loadRewards();
				ObjectLogic.logRewards();
				JSONGuy.loadTiers();
				ObjectLogic.logTiers();
				return true;
			}
			if(sender instanceof Player) {
				if(args[0].equalsIgnoreCase("help")&&player.hasPermission("rr.help")) {
					player.sendMessage(ChatColor.GOLD + "Ranked Reload" + ChatColor.GREEN + " Help");
					player.sendMessage(ChatColor.WHITE + " -" + ChatColor.LIGHT_PURPLE + " /rr reload" + ChatColor.WHITE + " - " + ChatColor.RED + "reloads Json without reloading server");
					player.sendMessage(ChatColor.WHITE + " -" + ChatColor.LIGHT_PURPLE + " /rr help" + ChatColor.WHITE + " - " + ChatColor.RED + "displays help");
					return true;
				}
				if(args[0].equalsIgnoreCase("create")&&args.length==2&&player.hasPermission("rr.admin")) {
					if(FishingRewards.tiers.exists(args[1])) {
						Tier tier = FishingRewards.tiers.getTier(args[1]);
						ItemStack rod = new ItemStack(Material.FISHING_ROD);
						ItemMeta meta = rod.getItemMeta();
						ArrayList<String> lore = new ArrayList<String>();
						lore.add(ChatColor.GREEN + "XP: " + ChatColor.BLUE + "0");
						lore.add(ChatColor.GREEN + "Tier: " + ChatColor.BLUE + tier.getTitle());
						meta.setLore(lore);
						meta.setDisplayName(ChatColor.RED + "Ranked " + ChatColor.GRAY + "Rod");
						rod.setItemMeta(meta);
						player.getInventory().addItem(rod);
						FishingRewards.messagePlayer("A Tier " + tier.getTitle() + " fishing rod has been spawned for you", player);
					}else {
						FishingRewards.messagePlayer("Tier " + args[1] + " doesn't exist", player);
					}
					return true;
				}
				if(args[0].equalsIgnoreCase("reload")&&player.hasPermission("rr.reload")) {
					FishingRewards.tiers.destroy();
					FishingRewards.rewards.destroy();
					JSONGuy.loadRewards();
					ObjectLogic.logRewards();
					JSONGuy.loadTiers();
					ObjectLogic.logTiers();
					return true;
				}
			}
		}
		return false;
	}

}
