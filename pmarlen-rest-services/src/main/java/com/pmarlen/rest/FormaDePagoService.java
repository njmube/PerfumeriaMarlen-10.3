package com.pmarlen.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("formaDePagoService")
@RequestMapping("/formaDePago")
public class FormaDePagoService {
	private Logger logger;

	public FormaDePagoService() {
		logger = LoggerFactory.getLogger(FormaDePagoService.class);
		logger.debug("->FormaDePagoService, created");
	}
	
	@Autowired
	private AlmacenJPAController formaDePagoJPAController;	

	public void setAlmacenJPAController(AlmacenJPAController formaDePagoJPAController){
		this.formaDePagoJPAController=formaDePagoJPAController;
	}
	
	@RequestMapping(value = "getAll", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<com.pmarlen.wscommons.services.dto.Almacen> getAll() {	
		logger.debug("->getAll invoked.");
		List<com.pmarlen.wscommons.services.dto.Almacen> result=new ArrayList<com.pmarlen.wscommons.services.dto.Almacen>();		

		final List<Almacen> jpaList = formaDePagoJPAController.findAllEntities();
		logger.debug("->getAll jpaList is null ?"+(jpaList==null));

		com.pmarlen.wscommons.services.dto.Almacen dto = null;
		for(Almacen l: jpaList){
			dto = new com.pmarlen.wscommons.services.dto.Almacen(l);			
			result.add(dto);
			logger.debug("->getAll \tadd dto:"+dto);
		}
		
		return result;
	}
}