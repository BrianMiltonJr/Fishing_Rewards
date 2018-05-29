package com.johnwillikers.fisher;

import com.johnwillikers.fisher.objects.Reward;
import com.johnwillikers.fisher.objects.Tier;

public class ObjectLogic {

	public static Object[] unpackItems(Object[] reward){
		Object[] items = new Object[reward.length-1];
		int itemsIndice=0;
		int forTimer=0;
		for(Object item : reward) {
			if(forTimer>0) {
				items[itemsIndice]= item;
				itemsIndice++;
			}
			forTimer++;
		}
		return items;
		
	}
	
	public static void logRewards() {
		FishingRewards.log("Fishing Rewards", "Debug", "Contents of Rewards");
		FishingRewards.log("Fishing Rewards", "Debug", "-------------------------------");
		String[] keys = FishingRewards.rewards.currentKeys();
		for(String key : keys) {
			Reward reward = FishingRewards.rewards.getReward(key);
			Object[] items = reward.getItems();
			float[] percentages = reward.getPercents();
			FishingRewards.log("Fishing Rewards", "Debug", "Reward: " + reward.getTitle());
			FishingRewards.log("Fishing Rewards", "Debug", "Items: ");
			for(Object item : items) {
				FishingRewards.log("Fishing Rewards", "Debug", " - " + String.valueOf(item));

			}
			FishingRewards.log("Fishing Rewards", "Debug", "Percents:");
			for(float percent : percentages) {
				FishingRewards.log("Fishing Rewards", "Debug", " - " + String.valueOf(percent) + "");

			}
			FishingRewards.log("Fishing Rewards", "Debug", "\n");
		}
		FishingRewards.log("Fishing Rewards", "Debug", "End of Rewards Content");
		FishingRewards.log("Fishing Rewards", "Debug", "--------------------------------\n");


	}
	
	public static void logTiers() {
		FishingRewards.log("Fishing Rewards", "Debug", "Contents of Tiers");
		FishingRewards.log("Fishing Rewards", "Debug", "--------------------------------");
		String[] keys = FishingRewards.tiers.currentKeys();
		for(String key : keys) {
			Tier tier = FishingRewards.tiers.getTier(key);
			FishingRewards.log("Fishing Rewards", "Debug", "Tier: " + tier.getTitle());
			FishingRewards.log("Fishing Rewards", "Debug", "Reward: " + tier.getReward().getTitle());
			FishingRewards.log("Fishing Rewards", "Debug", "Percentage: " + tier.getPercentage());
			FishingRewards.log("Fishing Rewards", "Debug", "XP: " + tier.getXp() + "\n");
		}
		FishingRewards.log("Fishing Rewards", "Debug", "End of Tiers Content");
		FishingRewards.log("Fishing Rewards", "Debug", "----------------------------");
	}

}