package br.com.prati.tim.collaboration.gmp.envers;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;


@Entity
@Table(name="revinfo", schema="audit")
@RevisionEntity(RevinfoListener.class)
public class Revinfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @RevisionNumber
    private int               rev;

    @RevisionTimestamp
    private long              revtstmp;
    private String            usuario;
    private String			  sistema;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Transient
    public Date getRevisionDate() {
        return new Date(getRevtstmp());
    }

    

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultRevisionEntity)) {
            return false;
        }

        final DefaultRevisionEntity that = (DefaultRevisionEntity) o;
        return getRev() == that.getId() && revtstmp == that.getTimestamp();
    }

    @Override
    public int hashCode() {
        int result;
        result = getRev();
        result = 31 * result + (int) (revtstmp ^ (revtstmp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "DefaultRevisionEntity(id = " + getRev() + ", revisionDate = "
                + DateFormat.getDateTimeInstance().format(getRevisionDate()) + ")";
    }

	public String getSistema() {
		return sistema;
	}

	public void setSistema(String sistema) {
		this.sistema = sistema;
	}

	public long getRevtstmp() {
		return revtstmp;
	}

	public void setRevtstmp(long revtstmp) {
		this.revtstmp = revtstmp;
	}

	public int getRev() {
		return rev;
	}

	public void setRev(int rev) {
		this.rev = rev;
	}
}