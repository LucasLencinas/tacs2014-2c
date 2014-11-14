package com.tacs.truequeLibre.endpoints;

import static org.junit.Assert.*;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.Gson;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tacs.truequeLibre.setup.Setup;
import com.tacs.truequeLibre.Utils.LlamadasMockFB;
import com.tacs.truequeLibre.domain.Usuario;

public class ItemsTest {

  private static WebTarget target;
  private static HttpServer server;

  @BeforeClass
  public static void setUp() throws Exception {
    	server = Setup.startServer();
      Client cliente = ClientBuilder.newClient();
      target = cliente.target(Setup.BASE_URI);
  }
 
  @Before
  public void cargaItems(){
  	Setup.load();
  	Setup.facebook = new LlamadasMockFB();
  }

  /**
   * Los items que me devuelve son todos los del usuario actual
	**/
  @Test
  public void testDameTodosLosItems() {
		String itemsJson = new Gson().toJson(Setup.items);
		String responseMsg = target.path("/items").request(MediaType.APPLICATION_JSON).get(String.class);
	  assertTrue(responseMsg.equalsIgnoreCase(itemsJson));
  }
  
  /**
   * Le paso un Id y me devuelve el item
   */
  @Test
  public void testDameUnItem() {	
	int id = 1;
	String itemJson = new Gson().toJson(Setup.items.findById(id));
	String responseMsg = target.path("/items/".
    		concat(String.valueOf(id))).request(MediaType.APPLICATION_JSON).get(String.class);
    assertTrue(responseMsg.equalsIgnoreCase(itemJson));
  } 
  
  /**
   * Le paso un Id y me borra ese item del usuario actual
   */
  @Test
  public void testBorraUnItem() {
  	Usuario usuario = Setup.facebook.getLoggedUser(null);
  	//int cantidadDeItems =usuario.getItems().size(); 
  	int id = 3;
  	int cantidadDeItems = Setup.items.size();
	String itemJson = "Item " + new Gson().toJson(Setup.items.findById(id)) + " eliminado";
	String responseMsg = target.path("/items/".
    		concat(String.valueOf(id))).request(MediaType.APPLICATION_JSON).delete(String.class);
		
    assertTrue(Setup.items.size() == cantidadDeItems-1);
  }  
  
  /**
   * Le paso los parametros para crear un item y me lo agrega a la memoria
   */
  @Test
  public void testAgregaUnItem() {
  	Usuario usuario = Setup.facebook.getLoggedUser(null);
  	int cantidadDeItems =usuario.getItems().size(); 

  	String json = "{'title':'Nuevo Celular!', 'description': 'Nokia 1100', 'ml': {'permalink': 'http://articulo.mercadolibre.com.ar/MLA-521311328-mesa-de-comedor-cuadrada-140-x-140-linea-neta-_JM', 'id': 'MLA521311328'}}";
    
    target.path("/items/").request(MediaType.APPLICATION_JSON_TYPE).
    					   post(Entity.entity(json,MediaType.APPLICATION_JSON_TYPE),
    							Response.class);
    assertTrue(Setup.facebook.getLoggedUser(null).getItems().size() == cantidadDeItems+1);
  }  
  
  

  @AfterClass
  public static void tearDown() throws Exception {
  }
  @After
  public void bajaItems(){
  	Setup.unload();
  } 
  
	
}
