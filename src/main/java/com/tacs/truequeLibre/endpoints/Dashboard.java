package com.tacs.truequeLibre.endpoints;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.tacs.truequeLibre.setup.Setup;
import com.tacs.truequeLibre.Utils.HandlerDS;
import com.tacs.truequeLibre.Utils.LlamadasFB;
import com.tacs.truequeLibre.domain.ListaDeUsuarios;
import com.tacs.truequeLibre.domain.ListaDeTrueques;
import com.tacs.truequeLibre.domain.ListaDeItems;
import com.tacs.truequeLibre.domain.Usuario;
import com.tacs.truequeLibre.domain.Estadistica;
import com.tacs.truequeLibre.domain.UsuarioXItems;

@Path("/dashboard")
public class Dashboard {

	/**
	 * Obtener estadisticas.
	 * @return: Un JSON que representa las estadisticas del sistema con cantidad de usuarios y lista de usuarios y trueques
	 */
	/*En el @Context HttpHeader hh estan los parametros que se envian, como headers, cookies y esas cosas.
	 *Desde javascript tengo que guardar previamente la cookie en la sesion, por ahora lo hago con:
	 *document.cookie = 'key=' + value;
	 * */
    @GET 
    @Produces("application/json")
    public Response index(@Context HttpHeaders header) {
     try
     {

      // NEGRADA FIXME
      if(Setup.isSet == false)
      	Setup.setup();
      
      ArrayList<UsuarioXItems> listaDeItemsXUsuario=new ArrayList<UsuarioXItems>();
      ListaDeUsuarios listaUsuarios=HandlerDS.findAllUsers();
      ListaDeItems listaItems=HandlerDS.items();
      int cantidadUsuarios= listaUsuarios.size(); //cantidad usuarios

      ListaDeTrueques listaDeTrueques=HandlerDS.findAllTrueques(); //todos los trueques
      
      for (Usuario usuario : listaUsuarios) {
        UsuarioXItems usuarioXItems= new UsuarioXItems(usuario); //items de cada usuario
        listaDeItemsXUsuario.add(usuarioXItems);
      }

      Estadistica estadistica= new Estadistica(cantidadUsuarios, listaDeItemsXUsuario, listaDeTrueques);
      String estadisticaJson = new Gson().toJson(estadistica);
      return Response.ok(estadisticaJson,MediaType.APPLICATION_JSON).build();

     }
     catch(Exception e)
     {
      System.out.println("Error obteniendo estadisticas"+ e.toString());
      return Response.status(Response.Status.CONFLICT).build();
     }
    }
}