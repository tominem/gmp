package br.com.prati.tim.collaboration.gmp.converters;

public class DoubleValueToHexConverter  implements ConverterComp{

	@Override
	public Double display(Object source) {
		
		String sourceStr = (String) source;
		
		int result = Integer.parseInt(sourceStr.trim(), 16);
		
		return Double.valueOf(result);
	}

	@Override
	public String convert(Object displayed) {
		
		Double newValue = (double) displayed;

		Integer value = 0;

		if (newValue.intValue() % 2 != 0){

			value = (int) (newValue.intValue() + 1);

		}else{

			value = (int) newValue.intValue();

		}
		
		return Integer.toHexString(value).toUpperCase();
	}

	@Override
	public boolean displayValidate(Object displayed, String compName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean convertValidate(Object source) {
		// TODO Auto-generated method stub
		return false;
	}


}