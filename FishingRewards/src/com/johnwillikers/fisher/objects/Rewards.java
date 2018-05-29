package com.johnwillikers.fisher.objects;

import java.util.HashMap;

public class Rewards {

	HashMap<String, Reward> rewards;
	
	public Rewards() {
		this.rewards = new HashMap<String, Reward>();
	}
	
	public Reward getReward(String title) {
		if(rewards.containsKey(title)) {
			return rewards.get(title);
		}
		return null;
	}
	
	public void destroy() {
		this.rewards.clear();
	}
	
	public void add(Reward reward) {
		rewards.put(reward.getTitle(), reward.getReward());
	}
	
	public void remove(Reward reward) {
		rewards.remove(reward.getTitle());
	}
	
	public int length() {
		return rewards.size();
	}
	
	public String[] currentKeys() {
		Object[] rawKeys = rewards.keySet().toArray();
		String[] keys = new String[rawKeys.length];
		for(int i=0;i<rawKeys.length;i++) {
			keys[i] = String.valueOf(rawKeys[i]);
		}
		return keys;
	}
}
