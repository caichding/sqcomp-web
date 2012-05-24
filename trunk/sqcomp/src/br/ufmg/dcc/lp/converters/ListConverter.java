package br.ufmg.dcc.lp.converters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@SuppressWarnings("serial")
@ManagedBean
@ApplicationScoped
@FacesConverter(value="listConverter",forClass=List.class)
public class ListConverter implements Converter,Serializable{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)	throws ConverterException {
		List<Object> l = new ArrayList<Object>();
		if(arg2.equals("")){
			return l;
		}
		for(String s : arg2.trim().substring(1,arg2.length()-1).split(",")){
			l.add((Object)s.trim());
		}
		return l;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

}
