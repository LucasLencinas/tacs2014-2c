package com.tacs.truequeLibre.endpoints;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import javax.ws.rs.core.Response;

import com.tacs.truequeLibre.setup.Setup;

@Path("/admin")
public class Admin {
	  
	@POST
  @Produces("text/plain")
  @Consumes("text/plain")
	public Response loadDataStore(String code, @Context HttpHeaders header) {
		//El codigo es 1234 - Lo hago para que se cargue una sola vez la base de datos
		System.out.println("El codigo que se ingreso es: " + code);
		if(code.equalsIgnoreCase("1234")){
    	Setup.setup();
    	return Response.status(Response.Status.OK).build();
		}else
			return Response.status(Response.Status.CONFLICT).build();
  	
	  }

	@POST
  @Produces("text/plain")
  @Consumes("text/plain")
	public Response deleteDataStore(String code, @Context HttpHeaders header) {
		//El codigo es 1234 - Lo hago para que se cargue una sola vez la base de datos
		System.out.println("El codigo que se ingreso es: " + code);
		if(code.equalsIgnoreCase("1234-borrado")){
    	Setup.setup();
    	return Response.status(Response.Status.OK).build();
		}else
			return Response.status(Response.Status.CONFLICT).build();
  	
	  }
	
	
	}