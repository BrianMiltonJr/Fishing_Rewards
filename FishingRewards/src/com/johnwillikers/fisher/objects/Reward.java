package com.johnwillikers.fisher.objects;

import java.util.Random;

import com.johnwillikers.fisher.ObjectLogic;

public class Reward {

	String title;
	Object[] items;
	float[] percents;
	
	public Reward(Object[] reward) {
		this.title = String.valueOf(reward[0]);
		Object[] unpackedItems = ObjectLogic.unpackItems(reward);
		this.items = new Object[unpackedItems.length/2];
		this.percents = new float[unpackedItems.length/2];
		int objectCheck=0;
		int itemsIndice=0;
		for(Object item : unpackedItems) {
			if(objectCheck==0) {
				this.items[itemsIndice]=item;
				objectCheck=1;
			}else {
				this.percents[itemsIndice]=Float.valueOf(String.valueOf(item));
				objectCheck=0;
				itemsIndice++;
			}
		}
	}

	public String getCommand() {
		float percentageRoom=100;
		float currentlyStored=0;
		float[] reservedPercentage = new float[this.percents.length];
		int indice=0;
		for(float percent : this.percents) {
			float amountTaken=(percentageRoom*percent);
			reservedPercentage[indice]=(currentlyStored+amountTaken);
			currentlyStored=currentlyStored+(percentageRoom*percent);
			indice++;
		}
		Random ran = new Random();
		float random = Float.valueOf(ran.nextInt(100));
		float previousFloat = 0;
		boolean isFirst = true;
		int forTimer=0;
		for(float percentage : reservedPercentage) {
			if(isFirst) {
				if(random<percentage) {
					return String.valueOf(this.items[forTimer]);
				}else {
					previousFloat=percentage;
					forTimer++;
				}
				isFirst=false;
			}else {
				if(random>=previousFloat&&random<percentage) {
					return String.valueOf(this.items[forTimer]);
				}else {
					previousFloat=percentage;
					forTimer++;
				}
			}
		}
		return null;
	}
	
	public Reward getReward() {
		return this;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Object[] getItems() {
		return items;
	}

	public void setItmes(Object[] rewardables) {
		this.items = rewardables;
	}

	public float[] getPercents() {
		return percents;
	}

	public void setPercents(float[] percents) {
		this.percents = percents;
	}

}
