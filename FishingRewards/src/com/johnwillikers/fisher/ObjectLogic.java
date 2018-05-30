package com.johnwillikers.fisher;

import org.bukkit.ChatColor;

import com.johnwillikers.fisher.objects.Reward;
import com.johnwillikers.fisher.objects.Tier;

public class ObjectLogic {

	/**
	 * Unpacks all the items from an Object[] describing a reward into an Object[] Just describing the items
	 * @param reward
	 * @return
	 */
	public static Object[] unpackItems(Object[] reward){
		//Create an Object[] Big enough for just the items
		Object[] items = new Object[reward.length-1];
		//Where in the items are we
		int itemsIndice=0;
		//Where in out for loop are where
		int forTimer=0;
		for(Object item : reward) {
			//Only fill our items Object[] with items and not the reward name
			if(forTimer>0) {
				items[itemsIndice]= item;
				itemsIndice++;
			}
			forTimer++;
		}
		return items;
		
	}
	
	/**
	 * Prints all the current Rewards to Console
	 */
	public static void logRewards() {
		FishingRewards.log(ChatColor.RED + "Debug", "Contents of Rewards");
		FishingRewards.log(ChatColor.RED + "Debug", "-------------------------------");
		String[] keys = FishingRewards.rewards.currentKeys();
		for(String key : keys) {
			Reward reward = FishingRewards.rewards.getReward(key);
			Object[] items = reward.getItems();
			float[] percentages = reward.getPercents();
			FishingRewards.log(ChatColor.RED + "Debug", "Reward: " + reward.getTitle());
			FishingRewards.log(ChatColor.RED + "Debug", "Items: ");
			for(Object item : items) {
				FishingRewards.log(ChatColor.RED + "Debug", " - " + String.valueOf(item));

			}
			FishingRewards.log(ChatColor.RED + "Debug", "Percents:");
			for(float percent : percentages) {
				FishingRewards.log(ChatColor.RED + "Debug", " - " + String.valueOf(percent) + "");

			}
			FishingRewards.log(ChatColor.RED + "Debug", "\n");
		}
		FishingRewards.log(ChatColor.RED + "Debug", "End of Rewards Content");
		FishingRewards.log(ChatColor.RED + "Debug", "--------------------------------\n");


	}
	
	/**
	 * Prints all the current Tiers to console
	 */
	public static void logTiers() {
		FishingRewards.log(ChatColor.RED + "Debug", "Contents of Tiers");
		FishingRewards.log(ChatColor.RED + "Debug", "--------------------------------");
		String[] keys = FishingRewards.tiers.currentKeys();
		for(String key : keys) {
			Tier tier = FishingRewards.tiers.getTier(key);
			FishingRewards.log(ChatColor.RED + "Debug", "Tier: " + tier.getTitle());
			FishingRewards.log(ChatColor.RED + "Debug", "Reward: " + tier.getReward().getTitle());
			FishingRewards.log(ChatColor.RED + "Debug", "Percentage: " + tier.getPercentage());
			FishingRewards.log(ChatColor.RED + "Debug", "XP: " + tier.getXp() + "\n");
		}
		FishingRewards.log(ChatColor.RED + "Debug", "End of Tiers Content");
		FishingRewards.log(ChatColor.RED + "Debug", "----------------------------");
	}

}