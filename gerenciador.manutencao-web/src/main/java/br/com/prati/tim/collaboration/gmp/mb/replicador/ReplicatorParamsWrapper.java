package br.com.prati.tim.collaboration.gmp.mb.replicador;

import java.util.Optional;
import java.util.function.Consumer;

import br.com.prati.tim.collaboration.gmp.replicator.model.ReplicatorParams;

public class ReplicatorParamsWrapper {

	private ReplicatorParams params;

	public ReplicatorParamsWrapper(ReplicatorParams params) {
		this.params = params;
	}
	
	public ReplicatorProp getUniqueIdentityID() {
		return getReplicatorProp("uniqueIdentityID", getStringValue(params.getUniqueIdentityID()), this::setUniqueIdentityID);
	}
	
	private ReplicatorProp getReplicatorProp(String key, String value, Consumer<String> consumer) {
		return new ReplicatorProp(key, value, consumer);
	}
	
	public ReplicatorProp getLocalDatabaseName() {
		return getReplicatorProp("localDatabaseName", getStringValue(params.getLocalDatabaseName()), this::setLocalDatabaseName);
	}
	
	private void setLocalDatabaseName(String localDatabaseName) {
		params.setLocalDatabaseName(localDatabaseName);
	}

	private String getStringValue(String value) {
		return Optional.ofNullable(value)
					   .orElse("");
	}

	private void setUniqueIdentityID(String uniqueIdentityID) {
		int value = Integer.parseInt(uniqueIdentityID);
		params.setUniqueIdentityID(value);
	}
	
	private String getStringValue(Integer value) {
		return Optional.ofNullable(value)
						.orElse(0)
						.toString();
	}

}
