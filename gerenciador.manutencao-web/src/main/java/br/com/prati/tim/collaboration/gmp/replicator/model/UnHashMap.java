package br.com.prati.tim.collaboration.gmp.replicator.model;

import java.util.HashMap;

public class UnHashMap<K, V> extends HashMap<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public V put(K key, V value) {
		if (containsKey(key)) {
			return super.get(key);
		}
		else{
			return super.put(key, value);
		}
	}

}
