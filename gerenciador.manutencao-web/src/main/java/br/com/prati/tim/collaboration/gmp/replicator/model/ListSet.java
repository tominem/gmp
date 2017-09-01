package br.com.prati.tim.collaboration.gmp.replicator.model;

import java.util.ArrayList;

public class ListSet<E> extends ArrayList<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public boolean add(E e) {
		if (contains(e)) {
			return false;
		}
		
		return super.add(e);
	}
	
}
