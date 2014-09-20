
package tacs.grupo4;

import com.google.gson.Gson;
import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;


public class Main {

    public static final URI BASE_URI = UriBuilder.fromUri("http://localhost/").port(9998).build();
    
    public static ListaDeItems items;
    
    protected static SelectorThread startServer() throws IOException {
        final Map<String, String> initParams = new HashMap<String, String>();
        initParams.put("com.sun.jersey.config.property.packages", "tacs.grupo4");

        System.out.println("Starting grizzly...");
        SelectorThread threadSelector = GrizzlyWebContainerFactory.create(BASE_URI, initParams);     
        return threadSelector;
    }

    public static void load(){
    	
    	items = new ListaDeItems();
    	Item item1 = new Item("Anteojos", "De Sol", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-430387888-anteojos-ray-ban-wayfare-_JM", "MLA430387888"));
    	Item item2 = new Item("Notebook", "Lenovo", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-523499379-notebook-lenovo-x220-_JM", "MLA523499379"));
    	Item item3 = new Item("Mesa", "Cuadrada", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-521311328-mesa-de-comedor-cuadrada-140-x-140-linea-neta-_JM", "MLA521311328"));
    	Item item4 = new Item("Botines", "Adidas", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-520889152-botines-adidas-11questra-brasil-2014-fifa-futbol-tapones-pro-_JM", "MLA520889152"));
    	items.add(item1);
    	items.add(item2);
    	items.add(item3);
    	items.add(item4);
    }
    
    public static void unload(){
    	items.clear();
    }
    
    
    public static void main(String[] args) throws IOException {
        SelectorThread threadSelector = startServer();
        Main.load();
        System.out.println(String.format("Jersey app started with WADL --> %sapplication.wadl", BASE_URI));
        System.out.println("Hit enter to stop it...");
        System.in.read();
        threadSelector.stopEndpoint();
    }    
}
