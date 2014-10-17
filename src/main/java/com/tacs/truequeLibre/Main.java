package com.tacs.truequeLibre;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.tacs.truequeLibre.domain.Item;
import com.tacs.truequeLibre.domain.ListaDeItems;
import com.tacs.truequeLibre.domain.ListaDeTrueques;
import com.tacs.truequeLibre.domain.ListaDeUsuarios;
import com.tacs.truequeLibre.domain.ObjetoML;
import com.tacs.truequeLibre.domain.Trueque;
import com.tacs.truequeLibre.domain.Usuario;
import com.tacs.truequeLibre.services.UsersService;

/**
 * Main class.
 * 
 */
public class Main {
	
    public static final String RESOURCES_PATH = "/src/main/resources/com/tacs/truequeLibre/";
    public static final String BASE_URI = "http://localhost:8080/truequeLibre/";
    
   
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
    	DummyLoader.loadData();
    }

    public static Usuario current_user(){
    	return UsersService.find(0);
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

	public static void unload() {
		DummyLoader.clearAll();		
	}


}
