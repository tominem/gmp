package br.com.prati.tim.collaboration.gmp.mb;

public class ValidateComponent {

	private String componentID;
	
	private String labelComponent;

	private String entityAttribute;
	
	public ValidateComponent() {
	}
	
	public ValidateComponent(String componentID, String labelComponent, String entityAttribute) {
		this.componentID = componentID;
		this.labelComponent = labelComponent;
		this.entityAttribute = entityAttribute;
	}

	public String getComponentID() {
		return componentID;
	}

	public void setComponentID(String componentID) {
		this.componentID = componentID;
	}

	public String getEntityAttribute() {
		return entityAttribute;
	}

	public void setEntityAttribute(String entityAttribute) {
		this.entityAttribute = entityAttribute;
	}

	public String getLabelComponent() {
		return labelComponent;
	}

	public void setLabelComponent(String labelComponent) {
		this.labelComponent = labelComponent;
	}

}
