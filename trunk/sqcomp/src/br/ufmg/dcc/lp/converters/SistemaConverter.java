package br.ufmg.dcc.lp.converters;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.ufmg.dcc.lp.dao.SistemaDAO;
import br.ufmg.dcc.lp.model.Sistema;

@SuppressWarnings("serial")
@ManagedBean
@ApplicationScoped
@FacesConverter(value="sistemaConverter",forClass=Sistema.class)
public class SistemaConverter implements Converter,Serializable {

	private SistemaDAO sistemaDAO;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)	throws ConverterException {
		try{
			sistemaDAO = (SistemaDAO) FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get("sistemaDAO");
			return sistemaDAO.get(Long.parseLong(arg2),null,null).get(0);
			
		}catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}
		
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)	throws ConverterException {
		return arg2.toString();
	}

	public SistemaDAO getSistemaDAO() {
		return sistemaDAO;
	}

	public void setSistemaDAO(SistemaDAO sistemaDAO) {
		this.sistemaDAO = sistemaDAO;
	}

}
