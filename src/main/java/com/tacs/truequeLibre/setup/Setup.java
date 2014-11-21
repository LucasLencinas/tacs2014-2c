package com.tacs.truequeLibre.setup;

import java.net.URI;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.googlecode.objectify.ObjectifyService;
import com.tacs.truequeLibre.Utils.HandlerDS;
import com.tacs.truequeLibre.Utils.ILlamadasFB;
import com.tacs.truequeLibre.Utils.LlamadasFB;
import com.tacs.truequeLibre.domain.Item;
import com.tacs.truequeLibre.domain.ListaDeItems;
import com.tacs.truequeLibre.domain.ListaDeTrueques;
import com.tacs.truequeLibre.domain.ListaDeUsuarios;
import com.tacs.truequeLibre.domain.ObjetoML;
import com.tacs.truequeLibre.domain.Trueque;
import com.tacs.truequeLibre.domain.Usuario;

public class Setup {
	
		
    public static final String RESOURCES_PATH = "/src/main/resources/com/tacs/truequeLibre/";
    public static final String BASE_URI = "http://localhost:8080/truequeLibre/";
    
    public static Usuario miUsuario;
    public static Usuario usuarioAmigo1;
    
    public static Trueque trueque1;
    public static Trueque trueque2;    
    public static Trueque trueque3;
    public static Trueque trueque4;
    
    public static Item item1;
    public static Item item2;
    public static Item item3;
    public static Item item4;

    
    public static ILlamadasFB facebook;
		public static boolean isSet = false;
    
    public static void load(){
    	loadItems();
    	loadUsuarios();
     	loadTrueques();
     	facebook = new LlamadasFB();
     	
    }

	private static void loadTrueques() {
    	trueque1 = new Trueque(item1,item2,miUsuario, usuarioAmigo1,"Anteojos por Notebook");
    	HandlerDS.guardarTrueque(trueque1);
		}

		private static void loadUsuarios() {
    	miUsuario = new Usuario("Tom A", "1492722194320819");
    	miUsuario.agregarItem(item1);
    	miUsuario.agregarItem(item3);

    	usuarioAmigo1 = new Usuario("Tom B","272570376286889");
    	usuarioAmigo1.agregarItem(item2);
    	usuarioAmigo1.agregarItem(item4);

    	HandlerDS.guardarUsuario(miUsuario);
    	HandlerDS.guardarUsuario(usuarioAmigo1);
    	
    	miUsuario.agregarAmigo(usuarioAmigo1);
		}

		private static void loadItems() {
    	item1 = new Item("Anteojos De Sol", "Los Cambio por una Notebook Lenovo", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-525033435-ray-ban-wayfarer-2140-anteojos-de-sol-varios-modelos-_JM", "MLA525033435",
    			"http://mla-s1-p.mlstatic.com/17009-MLA20130611399_072014-I.jpg"));
    	item2 = new Item("Notebook Lenovo", "La cambio por un sillon de madera", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-526842420-notebook-lenovo-thinkpad-x220-intel-i5-4gb-hd-320gb-125-_JM", "MLA526842420",
    			"http://mla-s1-p.mlstatic.com/18466-MLA20155614258_092014-I.jpg"));
    	item3 = new Item("Mesa Cuadrada", "La cambio por juego de sillas", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-523688946-mesa-de-comedor-cuadrada-140-x-140-linea-neta-_JM", "MLA523688946",
    			"http://mla-s2-p.mlstatic.com/12855-MLA20066714972_032014-I.jpg"));
    	item4 = new Item("Botines Adidas", "Los cambio por zapatillas Nike", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-520889152-botines-adidas-11questra-brasil-2014-fifa-futbol-tapones-pro-_JM", "MLA520889152",
    			"http://mla-s1-p.mlstatic.com/18156-MLA20150804347_082014-I.jpg"));
    	
    	HandlerDS.guardarItem(item1);
    	HandlerDS.guardarItem(item2);
    	HandlerDS.guardarItem(item3);
    	HandlerDS.guardarItem(item4);
    	
		}
    
    public static void unload(){
    	HandlerDS.deleteAll();
    }

    public static void setup() {
    	ObjectifyService.register(Item.class);
    	ObjectifyService.register(Usuario.class);
    	ObjectifyService.register(Trueque.class);
      load();
    	Setup.isSet = true;
    }

    /*--------------For testing--------------*/
		public static HttpServer startServer() {
	   		//Los recursos los va a buscar a este paquete
	       final ResourceConfig rc = new ResourceConfig().packages("com.tacs.truequeLibre.endpoints");
	       HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
	       HttpHandler handlerStatico = new StaticHttpHandler(System.getProperty("user.dir") + RESOURCES_PATH + "/client/");
	       System.out.println(System.getProperty("user.dir"));
	       httpServer.getServerConfiguration().addHttpHandler(handlerStatico, "/");
	 
	       return httpServer;
	     }
}
