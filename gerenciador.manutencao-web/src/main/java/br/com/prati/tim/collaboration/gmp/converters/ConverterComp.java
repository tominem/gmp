package br.com.prati.tim.collaboration.gmp.converters;

public interface ConverterComp {
	
	public final static String BASE_CLASS_PACKAGE = "br.prati.tim.collaboration.iem.converters.";

	/**
	 * Exibe a manipulação do valor de origem em um componente
	 * @param source - valor original vindo de um recurso externo
	 * @return 
	 */
	Object display(Object source);
	
	/**
	 * Converte o valor exibido em um componente para o valor de origem
	 * @param displayed - valor mostrado no componente
	 */
	String convert(Object displayed);
	
	/**
	 * valida o valor de exibição
	 * @param displayed
	 * @param compName - nome do componente a ser validado
	 * @return
	 */
	boolean displayValidate(Object displayed, String compName);
	
	/**
	 * valida o valor de conversão
	 * @param source
	 * @return
	 */
	boolean convertValidate(Object source);

}
