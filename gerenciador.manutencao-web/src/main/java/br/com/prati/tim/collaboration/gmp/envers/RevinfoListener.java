package br.com.prati.tim.collaboration.gmp.envers;


import org.hibernate.envers.RevisionListener;

import br.com.prati.tim.collaboration.gmp.mb.login.SessionUtil;

public class RevinfoListener  implements RevisionListener {
    
	public void newRevision(Object revisionEntity) {
    	
        Revinfo revEntity = (Revinfo) revisionEntity;
        
		if (SessionUtil.getCracha() != null) {
			revEntity.setUsuario(String.valueOf(SessionUtil.getCracha()));
		} else {
			revEntity.setUsuario("AUTOMATICO");
		}
	
        
        revEntity.setSistema("GMP");
        
    }
}