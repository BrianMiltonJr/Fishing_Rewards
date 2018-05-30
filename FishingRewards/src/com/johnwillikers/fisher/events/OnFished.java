package com.johnwillikers.fisher.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.johnwillikers.fisher.FishingRewards;
import com.johnwillikers.fisher.objects.Tier;

import net.md_5.bungee.api.ChatColor;

public class OnFished implements Listener{
	
	@EventHandler
	public void onBobberHooked(PlayerFishEvent e) {
		//Run only when the player has caught a fish
		if(e.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
			//Grabbing Details from the event
			Player player = e.getPlayer();
			ItemStack rod = player.getItemInHand();
			ItemMeta meta = rod.getItemMeta();
			//Check to see if the player is using our rod or not
			if(meta.hasLore()) {
				//grab details from the rod
				List<String> lore = meta.getLore();
				//Grab just the tier from the Tier lore
				String tierString = lore.get(1);
				tierString=tierString.substring(10);
				//Create a new tier and populate it with the tier from the rod
				Tier tier = FishingRewards.tiers.getTier(tierString);
				//Get the command from the Tier's Reward
				String command = tier.Run();
				//Checks to see if the tier actually rewarded them
				if(!(command==null)) {
					//Pole Tier advancement logic
					
					//Add 1 to the tier and check to see if it exists
					int possibleTierString = (Integer.valueOf(tierString)+1);
					if(FishingRewards.tiers.exists(String.valueOf(possibleTierString))) {
						//If so then grab just the XP from the Rod's Lore
						int xp = Integer.valueOf(lore.get(0).substring(8));
						//Increment the XP and set the new XP in the Lore
						xp++;
						lore.set(0, ChatColor.GREEN + "XP: " + ChatColor.BLUE + xp);
						//Grab the Tier from Tiers and grab the XP
						Tier possibleTier = FishingRewards.tiers.getTier(String.valueOf(possibleTierString));
						int possibleXp=possibleTier.getXp();
						//Check to see if our XP matches the Tiers Required XP
						if(xp==possibleXp){
							//If so set the new Tier and set the XP to 0
							lore.set(1, String.valueOf(ChatColor.GREEN + "Tier: " + ChatColor.BLUE + possibleTier.getTitle()));
							lore.set(0, ChatColor.GREEN + "XP: " + ChatColor.BLUE + "0");
						}
					}
					//Set the Lore in the Meta
					meta.setLore(lore);
					//Let the player know they reeled something in
					FishingRewards.messagePlayer("you reeled something in", player);
					//replace %P with their name
					command=command.replace("%p", player.getName());
					command=command.substring(1);
					//Run the command as the console
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
					//Set the Meta on the Pole in the player's hand
					player.getItemInHand().setItemMeta(meta);
				}else {
					FishingRewards.messagePlayer("you didn't get anything, maybe you'll have better luck next time", player);
				}
				//Don't let our pole drop xp or a Fish
				e.setExpToDrop(0);
				Entity entity = e.getCaught();
				entity.remove();
			}
		}
	}
}
