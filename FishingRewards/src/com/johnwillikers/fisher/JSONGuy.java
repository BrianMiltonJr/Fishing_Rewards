package com.johnwillikers.fisher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.johnwillikers.fisher.objects.Reward;
import com.johnwillikers.fisher.objects.Tier;

public class JSONGuy {

	public static String readFile(File file) {
		String contents = null;
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			contents = br.readLine();
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return contents;
	}
	
	public static void loadTiers() {
		String rawJSON = readFile(FishingRewards.tiersFile);
		JSONObject json = new JSONObject(rawJSON);
		JSONArray tiers = json.getJSONArray("tiers");
		for(int i = 0; i<tiers.length(); i++) {
			JSONArray rawTierData = tiers.getJSONArray(i);
			Object[] tierData = {rawTierData.get(0), rawTierData.get(1), rawTierData.get(2), rawTierData.get(3)};
			Tier tier = new Tier(tierData);
			FishingRewards.tiers.add(tier);
		}
	}
	
	public static void loadRewards() {
		//Load the Rewards array
		String rawJSON = readFile(FishingRewards.rewardsFile);
		JSONObject json = new JSONObject(rawJSON);
		JSONArray rewards = json.getJSONArray("rewards");
		
		/**Loop I in charge of going through each reward Array*/
		//Loop through the array and pack rewards one by one into the Object[][] in FishingRewards
		for(int i = 0; i<rewards.length(); i++) {
			
			//Lets us know what indice is empty in our Items Object[indice][]
			int itemTracking = 0;
			
			//Grabs the rawRewardData
			JSONArray rawRewardData = rewards.getJSONArray(i);
			//See how many item objects we have so we know how big to make the Object[][]
			int rawRewardDataLength = (rawRewardData.length()-1)/2;

			
			//Creates a new Object to let us procedurally loop through the existing values past the name
			//and add the pairs as objects into an object
			Object[][] items = new Object[rawRewardDataLength][];
			
			/**Loop J in chance of grabbing values from each reward Array*/
			//Loop through the rawRewardData past the name two indices at a time
			for(int j = 1; j<rawRewardData.length(); j=j+2) {

				//loading the Data into a Object[] and loading that Object[] into a Object[][]
				Object[] item = {rawRewardData.get(j), rawRewardData.get(j+1)};
				items[itemTracking] = item;
				itemTracking++;

			}

			//Reset Item Tracking so we know where we are at in the items Object[][]
			itemTracking=0;
			//Create the final rewardData object and load the name in indice 0
			Object[] rewardData = new Object[(rawRewardDataLength*2) + 1];
			rewardData[0] = rawRewardData.get(0);
			
			/**Loop K in charge of looping through the items object[] array to pack those items into rewardsObject.. */
			//loop through the items Object[][] and add one Object[] at a time to the rewardData
			int dataIndiceTracker=1;
			for(int k = 0; k<items.length;k++) {
				Object[] rawItem = items[itemTracking];
				rewardData[dataIndiceTracker] = rawItem[0];
				rewardData[dataIndiceTracker+1] = rawItem[1];
				dataIndiceTracker = dataIndiceTracker + 2;
				itemTracking++;
			}
			Reward reward = new Reward(rewardData);
			FishingRewards.rewards.add(reward);
		}
	}

	public static int grabRewardsLength() {
		String rawJSON = readFile(FishingRewards.rewardsFile);
		JSONObject json = new JSONObject(rawJSON);
		JSONArray rewards = json.getJSONArray("rewards");
		return rewards.length();
	}
	
	public static int grabTiersLength() {
		String rawJSON = readFile(FishingRewards.tiersFile);
		JSONObject json = new JSONObject(rawJSON);
		JSONArray tiers = json.getJSONArray("tiers");
		return tiers.length();
	}
}
