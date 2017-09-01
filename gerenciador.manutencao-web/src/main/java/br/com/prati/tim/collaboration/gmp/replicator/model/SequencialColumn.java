package br.com.prati.tim.collaboration.gmp.replicator.model;

import java.math.BigInteger;

public class SequencialColumn {

	private String name;

	private BigInteger firstRecord;

	private BigInteger lastRecord;

	public SequencialColumn(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigInteger getFirstRecord() {
		return firstRecord;
	}

	public void setFirstRecord(BigInteger firstRecord) {
		this.firstRecord = firstRecord;
	}

	public BigInteger getLastRecord() {
		if (lastRecord == null) {
			lastRecord = new BigInteger("0");
		}
		return lastRecord;
	}

	public void setLastRecord(BigInteger lastRegister) {
		this.lastRecord = lastRegister;
	}
}
