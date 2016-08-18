package br.com.prati.tim.collaboration.gmp.converters;

public class DoubleValueToHexConverter  implements ConverterComp{

	@Override
	public Double display(Object source) {
		
		String sourceStr = (String) source;
		
		try{
			int result = Integer.parseInt(sourceStr.trim(), 16);
			return Double.valueOf(result);
		}catch(NumberFormatException e){
			Double result = Double.parseDouble(sourceStr);
			return result;
		}
	}

	@Override
	public String convert(Object displayed) {
		
		Double newValue = null;
		
		try{
			newValue = (double) displayed;
		}catch(Exception e){
			newValue = new Double(displayed.toString());
		}
		
		Integer value = (int) newValue.intValue();

		
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