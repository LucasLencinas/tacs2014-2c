package com.tacs.truequeLibre.endpoints;

import static org.junit.Assert.*;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tacs.truequeLibre.Main;

public class ItemsTest {

  private static HttpServer server;
  private static WebTarget target;

  @BeforeClass
  public static void setUp() throws Exception {
      server = Main.startServer();
      Client cliente = ClientBuilder.newClient();
      target = cliente.target(Main.BASE_URI);
  }
 
  @Before
  public void cargaItems(){
  	Main.load();
  }
	  

  /**
   * Los items que me devuelve son todos los que estan en memoria
   */
  @Test
  public void testDameTodosLosItems() {
	String itemsJson = new Gson().toJson(Main.items);
	String responseMsg = target.path("/items").request(MediaType.APPLICATION_JSON).get(String.class);//get(String.class);
    assertTrue(responseMsg.equalsIgnoreCase(itemsJson));
  }
  
  /**
   * Le paso un Id y me devuelve el item
   */
  @Test
  public void testDameUnItem() {	
	int id = 1;
	String itemJson = new Gson().toJson(Main.items.findById(id));
	String responseMsg = target.path("/items/".
    		concat(String.valueOf(id))).request(MediaType.APPLICATION_JSON).get(String.class);
    assertTrue(responseMsg.equalsIgnoreCase(itemJson));
  } 
  
  /**
   * Le paso un Id y me borra ese item de la memoria
   */
  @Test
  public void testBorraUnItem() {
  	int id = 3;
  	int cantidadDeItems = Main.items.size();
	String itemJson = "Item " + new Gson().toJson(Main.items.findById(id)) + " eliminado";
	String responseMsg = target.path("/items/".
    		concat(String.valueOf(id))).request(MediaType.APPLICATION_JSON).delete(String.class);
		
    assertTrue(responseMsg.toString().equalsIgnoreCase(itemJson));
    assertTrue(Main.items.size() == cantidadDeItems-1);
  }  
  
  /**
   * Le paso los parametros para crear un item y me lo agrega a la memoria
   */
  @Test
  public void testAgregaUnItem() {
  	int cantidadDeItems = Main.items.size();
  	
    Form form = new Form();
    form.param("title", "Nuevo Celular!");
    form.param("description", "Nokia 1100");
    form.param("ml_permalink", "http://articulo.mercadolibre.com.ar/MLA-521311328-mesa-de-comedor-cuadrada-140-x-140-linea-neta-_JM");
    form.param("ml_id", "MLA521311328");
    
    target.path("/items/").request(MediaType.APPLICATION_JSON_TYPE).
    					   post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED_TYPE),
    							Response.class);
    //assertTrue(responseMsg.equalsIgnoreCase(unItem.toString())); //Esta mal esta comparacion.  Hacer algo para comparar cada campo
    assertTrue(Main.items.size() == cantidadDeItems+1);
  }  
  
  

  @AfterClass
  public static void tearDown() throws Exception {
      server.shutdownNow();
  }
  @After
  public void bajaItems(){
  	Main.unload();
  } 
  
	
}
