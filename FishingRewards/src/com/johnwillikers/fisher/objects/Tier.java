package com.johnwillikers.fisher.objects;

import java.util.Random;

import com.johnwillikers.fisher.FishingRewards;

public class Tier {

	String title;
	Reward reward;
	float percentage;
	int xp;
	
	public Tier(Object[] tier) {
		this.title = String.valueOf(tier[0]);
		this.reward = FishingRewards.rewards.getReward(String.valueOf(tier[1]));
		this.percentage = Float.valueOf(String.valueOf(tier[2]));
		this.xp = Integer.valueOf(String.valueOf(tier[3]));
	}
	
	public String Run() {
		float percentageRoom=100;
		Random rand = new Random();
		float random = Float.valueOf(String.valueOf(rand.nextInt(100)));
		if(random<(percentageRoom*this.percentage)) {
			return this.reward.getCommand();
		}
		return null;
	}
	
	public Tier getTier() {
		return this;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Reward getReward() {
		return reward;
	}

	public void setReward(Reward reward) {
		this.reward = reward;
	}

	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	
	public int getXp() {
		return xp;
	}

	
	public void setXp(int xp) {
		this.xp = xp;
	}
}
