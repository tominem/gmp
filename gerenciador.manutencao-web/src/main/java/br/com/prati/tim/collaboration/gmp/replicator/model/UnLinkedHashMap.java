package br.com.prati.tim.collaboration.gmp.replicator.model;

import java.util.LinkedHashMap;

public class UnLinkedHashMap<K, V> extends LinkedHashMap<K, V> {

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
