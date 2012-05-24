package br.ufmg.dcc.lp.converters;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

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
@FacesConverter(value="setConverter",forClass=Set.class)
public class SetConverter implements Converter,Serializable{
	
	private SistemaDAO sistemaDAO;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)	throws ConverterException {
		
		Set<Sistema> l = new TreeSet<Sistema>();
		if(arg2.equals("")){
			return l;
		}
		
		sistemaDAO = (SistemaDAO) FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get("sistemaDAO");
		
		for(String s : arg2.trim().substring(1,arg2.length()-1).split(",")){
			l.add(sistemaDAO.get(Long.getLong(s.trim()),null,null).get(0));
		}
		return l;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	public SistemaDAO getSistemaDAO() {
		return sistemaDAO;
	}

	public void setSistemaDAO(SistemaDAO sistemaDAO) {
		this.sistemaDAO = sistemaDAO;
	}

}
