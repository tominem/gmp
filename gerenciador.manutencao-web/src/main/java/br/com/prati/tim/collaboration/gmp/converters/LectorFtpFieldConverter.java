package br.com.prati.tim.collaboration.gmp.converters;

public class LectorFtpFieldConverter implements ConverterComp{

	@Override
	public String display(Object source) {
		String[] result = ((String)source).split(" ");
		StringBuffer str = null;
		for (int i = 0; i < result.length; i++) {
			if (str == null) {
				str = new StringBuffer();
				str.append(Integer.parseInt(result[i].trim(), 16));
			}
			else{
				str.append(".").append(Integer.parseInt(result[i].trim(), 16));
			}
		}
		
		return str != null ? str.toString() : null;
	}

	@Override
	public String convert(Object displayed) {
		
		String[] result = ((String)displayed).split("\\.");
		StringBuffer str = null;
		for (int i = 0; i < result.length; i++) {
			if (str == null) {
				str = new StringBuffer();
				str.append(Integer.toHexString(Integer.parseInt(result[i].trim())).toUpperCase());
			}
			else{
				str.append(" ").append(Integer.toHexString(Integer.parseInt(result[i].trim())).toUpperCase());
			}
		}
		
		return str != null ? str.toString() : null;
	}

	@Override
	public boolean displayValidate(Object displayed, String compName) {
		boolean validate = new IPAddressValidator().validate((String)displayed);
		
		if (validate == false) {
			throw new RuntimeException("IP do lector com formato inválido");
		}
		
		return validate;
	}

	@Override
	public boolean convertValidate(Object source) {
		return false;
	}

}