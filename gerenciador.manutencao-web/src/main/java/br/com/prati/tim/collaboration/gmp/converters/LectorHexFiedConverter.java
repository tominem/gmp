package br.com.prati.tim.collaboration.gmp.converters;

public class LectorHexFiedConverter implements ConverterComp{

	@Override
	public String display(Object source) {
		return Integer.parseInt(((String)source), 16)+"";
	}

	@Override
	public String convert(Object displayed) {
		int 	parseInt 	= Integer.parseInt((String) displayed);
		String 	upperCase 	= Integer.toHexString(parseInt).toUpperCase();
		return upperCase;
				
	}

	@Override
	public boolean displayValidate(Object displayed, String compName) {
		return true;
	}

	@Override
	public boolean convertValidate(Object source) {
		return true;
	}

}