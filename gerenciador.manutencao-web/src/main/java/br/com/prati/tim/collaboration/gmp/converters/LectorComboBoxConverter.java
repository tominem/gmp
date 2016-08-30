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
		
		if (displayed.toString().equals("Desligado")){
			return "0";
		} else if (displayed.toString().equals("Vermelho")){
			return "1";
		} else if (displayed.toString().equals("Azul")){
			return "2";
		} else if (displayed.toString().equals("Ambos")){
			return "3";
		}
		
		return "5";
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