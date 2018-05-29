package com.johnwillikers.fisher.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.johnwillikers.fisher.FishingRewards;
import com.johnwillikers.fisher.objects.Tier;

public class OnFished implements Listener{
	
	@EventHandler
	public void onBobberHooked(PlayerFishEvent e) {
		if(e.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
			Player player = e.getPlayer();
			ItemStack rod = player.getItemInHand();
			ItemMeta meta = rod.getItemMeta();
			String name = meta.getDisplayName();
			if(name.equalsIgnoreCase("ranked rod")) {
				List<String> lore = meta.getLore();
				String tierString = lore.get(1);
				tierString=tierString.substring(6);
				Tier tier = FishingRewards.tiers.getTier(tierString);
				String command = tier.Run();
				if(!(command==null)) {
					//Sees if the pole can advance to the next tier
					int possibleTierString = (Integer.valueOf(tierString)+1);
					if(FishingRewards.tiers.exists(String.valueOf(possibleTierString))) {
						int xp = Integer.valueOf(lore.get(0).substring(4));
						xp++;
						lore.set(0, "XP: " + xp);
						Tier possibleTier = FishingRewards.tiers.getTier(String.valueOf(possibleTierString));
						int possibleXp=possibleTier.getXp();
						if(xp==possibleXp){
							lore.set(1, String.valueOf("Tier: " + possibleTier.getTitle()));
							lore.set(0, "XP: 0");
						}
					}
					meta.setLore(lore);
					String amount = command.substring(command.length()-1);
					String item = command.substring(9, command.length()-2);
					Bukkit.getPlayer(player.getUniqueId()).sendMessage("[Ranked Rods] you have earned " + amount + " " + item);
					command=command.replace("%p", player.getName());
					command=command.substring(1);
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
					player.getItemInHand().setItemMeta(meta);
				}else {
					Bukkit.getPlayer(player.getUniqueId()).sendMessage("[Ranked Rods] you didn't get anything, maybe you'll have better luck next time");
				}
				e.setCancelled(true);
			}
		}
	}
}
