//package br.com.prati.tim.collaboration.gmp.mb.tipocomponente;
//
//import java.util.Map;
//
//import javax.faces.application.FacesMessage;
//import javax.faces.component.UIComponent;
//import javax.faces.context.FacesContext;
//import javax.faces.validator.FacesValidator;
//import javax.faces.validator.Validator;
//import javax.faces.validator.ValidatorException;
//import javax.inject.Inject;
//
//import org.primefaces.validate.ClientValidator;
//
//import br.com.prati.tim.collaboration.gmp.ejb.tipocomponente.CadTipoComponenteEJB;
//
//@FacesValidator("tpComponenteExistenteValidator")
//public class TipoCompoenenteExistenteValidator implements Validator, ClientValidator {
//
//	@Inject
//	private CadTipoComponenteEJB cadTpEquipamentoEjb;
//
//	@Override
//	public Map<String, Object> getMetadata() {
//		return null;
//	}
//
//	@Override
//	public String getValidatorId() {
//		return "tpComponenteExistenteValidator";
//	}
//
//	@Override
//	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
//
//		String descricaoAttr = (String) component.getAttributes().get("descricaoAnterior");
//
//		boolean existente = cadTpEquipamentoEjb.validaCadastroExistente(value.toString());
//		
//		//valida a descrição
//		if (existente && descricaoAttr != null) {
//                
//			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "O descrição do tipo de componente informado já existe.", null));
//		}
//		
//		//valida o nome
//		else if (existente){
//			
//			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "O nome do tipo de componente informado já existe.", null));
//			
//		}
//
//	}
//
//}
