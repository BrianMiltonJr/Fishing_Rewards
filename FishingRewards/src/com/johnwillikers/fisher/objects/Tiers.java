package com.johnwillikers.fisher.objects;

import java.util.HashMap;

public class Tiers {

	HashMap<String, Tier> tiers = new HashMap<String, Tier>();
	
	public Tiers() {
		
	}
	
	public Tier getTier(String title) {
		if(tiers.containsKey(title)) {
			return tiers.get(title);
		}
		return null;
	}
	
	public boolean exists(String title) {
		if(tiers.containsKey(title))
			return true;
		return false;
	}
	
	public void destroy() {
		this.tiers.clear();
	}
	
	public void add(Tier tier) {
		tiers.put(tier.getTitle(), tier.getTier());
	}
	
	public void remove(Tier tier) {
		tiers.remove(tier.getTitle());
	}
	
	public String[] currentKeys() {
		Object[] rawKeys = tiers.keySet().toArray();
		String[] keys = new String[rawKeys.length];
		for(int i=0;i<rawKeys.length;i++) {
			keys[i] = String.valueOf(rawKeys[i]);
		}
		return keys;
	}
}
