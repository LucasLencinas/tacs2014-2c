package tacs.grupo4;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
/**
 * 
 * 
 *
 */
public class ItemsTest {

	private static SelectorThread server;
  private static WebResource resource;

  @BeforeClass
  public static void setUp() throws Exception {
      server = Main.startServer();
      Client cliente = Client.create();
      resource = cliente.resource(Main.BASE_URI);
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
    String responseMsg = resource.path("/items").get(String.class);
    assertTrue(responseMsg.equalsIgnoreCase(itemsJson));
  }

  /**
   * Le paso un Id y me devuelve el item
   */
  @Test
  public void testDameUnItem() {	
		int id = 1;
		String itemJson = new Gson().toJson(Main.items.get(id -1));
    String responseMsg = resource.path("/items/".
    		concat(String.valueOf(id))).get(String.class);
    assertTrue(responseMsg.equalsIgnoreCase(itemJson));
  } 
  
  /**
   * Le paso un Id y me borra ese item de la memoria
   */
  @Test
  public void testBorraUnItem() {
  	int id = 3;
  	int cantidadDeItems = Main.items.size();;
		String itemJson = "Item " + new Gson().toJson(Main.items.get(id-1)) + " eliminado";
		String responseMsg = resource.path("/items/".
    		concat(String.valueOf(id))).delete(String.class);
		
    assertTrue(responseMsg.equalsIgnoreCase(itemJson));
    assertTrue(Main.items.size() == cantidadDeItems-1);
  }  
  
  /**
   * Le paso los parametros para crear un item y me lo agrega a la memoria
   */
  @Test
  public void testAgregaUnItem() {

  }  
  
  

  @AfterClass
  public static void tearDown() throws Exception {
      server.stopEndpoint();
  }
  @After
  public void bajaItems(){
  	Main.unload();
  } 
  
	
}
