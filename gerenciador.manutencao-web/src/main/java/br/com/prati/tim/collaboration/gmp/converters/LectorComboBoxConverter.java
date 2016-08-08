package br.com.prati.tim.collaboration.gmp.converters;

public class LectorComboBoxConverter  implements ConverterComp{

	@Override
	public String display(Object source) {
		
		int sourceInt = Integer.parseInt((String) source);
		
		switch (sourceInt) {
		case 0:
			return "Desligado";
		case 1:
			return "Vermelho";
		case 2:
			return "Azul";
		case 3:
			return "Ambos";
		}
		
		return null;
	}

	@Override
	public String convert(Object displayed) {
		return displayed+"";
	}

	@Override
	public boolean displayValidate(Object displayed, String compName) {
		return false;
	}

	@Override
	public boolean convertValidate(Object source) {
		return false;
	}


}