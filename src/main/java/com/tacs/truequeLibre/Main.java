package com.tacs.truequeLibre;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.tacs.truequeLibre.domain.Item;
import com.tacs.truequeLibre.domain.ListaDeItems;
import com.tacs.truequeLibre.domain.ListaDeTrueques;
import com.tacs.truequeLibre.domain.ObjetoML;
import com.tacs.truequeLibre.domain.Trueque;
import com.tacs.truequeLibre.domain.Usuario;

/**
 * Main class.
 * 
 */
public class Main {
	
    public static final String RESOURCES_PATH = "/src/main/resources/com/tacs/truequeLibre/";
    public static final String BASE_URI = "http://localhost:8080/truequeLibre/";
    public static ListaDeItems items;
    public static Usuario miUsuario;
    public static Usuario usuarioAmigo;
    public static ListaDeTrueques trueques;
    
    public static HttpServer startServer() {
  		//Los recursos los va a buscar a este paquete
      final ResourceConfig rc = new ResourceConfig().packages("com.tacs.truequeLibre.endpoints");
      
      HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
      HttpHandler handlerStatico = new StaticHttpHandler(System.getProperty("user.dir") + RESOURCES_PATH + "/client/");
      System.out.println(System.getProperty("user.dir"));
      httpServer.getServerConfiguration().addHttpHandler(handlerStatico, "/");

      return httpServer;
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
    	
    	miUsuario = new Usuario("Mi Usuario");
    	miUsuario.agregarItem(item1);
    	miUsuario.agregarItem(item3);
    	usuarioAmigo = new Usuario("Usuario Amigo");
    	usuarioAmigo.agregarItem(item2);
    	
    	Trueque trueque1 = new Trueque(item1,item2,miUsuario, usuarioAmigo,"descripcion");
    	trueques.add(trueque1);
    }
    
    public static void unload(){
    	items.clear();
    }
    
    public static void main(String[] args) throws IOException {
        HttpServer server = null;
        try {
            server = startServer();
            load();
            System.in.read();
        } catch (Exception e) {
            System.out.println("Hubo un problema con el servidor: " + e);
            if (server != null)
                server.shutdownNow();
        }
    }


}
