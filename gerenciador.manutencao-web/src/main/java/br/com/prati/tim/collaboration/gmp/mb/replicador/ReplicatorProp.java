package br.com.prati.tim.collaboration.gmp.mb.replicador;

import java.io.Serializable;
import java.util.function.Consumer;

import org.apache.commons.beanutils.PropertyUtils;

public class ReplicatorProp implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6080558367629824057L;
	
	private String key;
	private String value;

	private Consumer<ReplicatorProp> consumer;
	
	private int index;

	private Object target;

	public ReplicatorProp() {}
	
	public ReplicatorProp(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public ReplicatorProp(String key, String value, Object target) {
		this.key = key;
		this.value = value;
		this.target = target;
		this.setConsumer(getDynamicConsumer(target));
	}

	public ReplicatorProp(String key, String value, Object target, Consumer<ReplicatorProp> consumer) {
		this.key = key;
		this.value = value;
		this.target = target;
		this.setConsumer(consumer);
	}

	public ReplicatorProp(String key, String value, Object target, Consumer<ReplicatorProp> consumer, int index) {
		this.key = key;
		this.value = value;
		this.target = target;
		this.setConsumer(consumer);
		this.index = index;
	}
	
	private Consumer<ReplicatorProp> getDynamicConsumer(Object target) {
		return s -> {
			
			try {
				
				Object property = PropertyUtils.getProperty(target, s.getKey());
				Object value    = null;
				
				if (property instanceof Integer) {
					
					value = Integer.parseInt(s.getValue());
					
				}
				
				else {
					
					value = s.getValue();
					
				}
				
				PropertyUtils.setProperty(s.getTarget(), s.getKey(), value);
				
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
		};
	}

	public ReplicatorProp(String key, String value, Consumer<ReplicatorProp> consumer) {
		this.key = key;
		this.value = value;
		this.setConsumer(consumer);
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
		if (getConsumer() != null) {
			getConsumer().accept(this);
		}
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReplicatorProp other = (ReplicatorProp) obj;
		if (index != other.index)
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return key;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public Consumer<ReplicatorProp> getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer<ReplicatorProp> consumer) {
		this.consumer = consumer;
	}

}
