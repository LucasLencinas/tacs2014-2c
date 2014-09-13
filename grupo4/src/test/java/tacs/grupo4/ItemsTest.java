package tacs.grupo4;

import static org.junit.Assert.*;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
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
  	int id = 3;
  	//http://articulo.mercadolibre.com.ar/MLA-523679126-nokia-1100-libre-optimo-estado-_JM
  	
  	int cantidadDeItems = Main.items.size();;
  	Item unItem = new Item(0, "Celular", "Nokia 1100", new ObjetoML("http://articulo.mercadolibre.com.ar/MLA-521311328-mesa-de-comedor-cuadrada-140-x-140-linea-neta-_JM", "MLA521311328"));
  	
  	
    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    params.add("title", unItem.getTitulo());
    params.add("description", unItem.getDescripcion());
    params.add("ml_permalink", unItem.getObjML().getPermalink());
    params.add("ml_id", unItem.getObjML().getId());
    
		String responseMsg = resource.path("/items").queryParams(params).post(String.class);
		
    //assertTrue(responseMsg.equalsIgnoreCase(unItem.toString())); //Esta mal esta comparacion.  Hacer algo para comparar cada campo
    assertTrue(Main.items.size() == cantidadDeItems+1);
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
