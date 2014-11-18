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
    
    public static ListaDeItems items;
    public static ListaDeUsuarios usuarios;
    public static ListaDeTrueques trueques;
    
    public static Usuario miUsuario;
    public static Usuario usuarioAmigo1;
    public static Usuario usuarioAmigo2;
    public static Usuario usuarioAmigo3;
    public static Usuario usuarioAmigo4;
    
    public static Trueque trueque1;
    public static Trueque trueque2;    
    public static Trueque trueque3;
    public static Trueque trueque4;
    
    public static Item item1;
    public static Item item2;
    public static Item item3;
    public static Item item4;
    public static Item item5;
    public static Item item6;
    public static Item item7;
    public static Item item8;
    public static Item item9;
    public static Item item10;
    
    public static ILlamadasFB facebook;
		public static boolean isSet = false;
    
    public static void load(){
    	loadItems();
    	loadUsuarios();
     	loadTrueques();
     	facebook = new LlamadasFB();
     	
    }

	private static void loadTrueques() {
			
		trueques = new ListaDeTrueques();
    	trueque1 = new Trueque(item1,item2,miUsuario, usuarioAmigo1,"Anteojos por Notebook");
    	trueque2 = new Trueque(item3,item5,miUsuario, usuarioAmigo2,"Mesa por Moto");
    	trueque3 = new Trueque(item7,item8,usuarioAmigo2, usuarioAmigo3,"Musculosa por Auto");
    	trueque4 = new Trueque(item5,item1,usuarioAmigo2, miUsuario,"Moto por Auto");
    	
    	trueques.add(trueque1);
    	trueques.add(trueque2);
    	trueques.add(trueque3);
    	trueques.add(trueque4);
    	
    	for (Trueque trueque : trueques) {
    		HandlerDS.guardarTrueque(trueque);	
			}
		}

		private static void loadUsuarios() {
			usuarios = new ListaDeUsuarios();
    	miUsuario = new Usuario("Tom A", "1492722194320819");
    	miUsuario.agregarItem(item1);
    	miUsuario.agregarItem(item3);
    	miUsuario.agregarItem(item10);

    	usuarioAmigo1 = new Usuario("Tom B","272570376286889");
    	usuarioAmigo1.agregarItem(item2);
    	usuarioAmigo1.agregarItem(item4);

    	usuarioAmigo2 = new Usuario("Tom C","307305166123222");
    	usuarioAmigo2.agregarItem(item5);
    	usuarioAmigo2.agregarItem(item6);
    	usuarioAmigo2.agregarItem(item7);
   	
    	usuarioAmigo3 = new Usuario("Tom D","359154707587167");
    	usuarioAmigo3.agregarItem(item8);
    	usuarioAmigo3.agregarItem(item9);

    	usuarioAmigo4 = new Usuario("Tom E","347614002065391");

    	usuarios.add(miUsuario);
    	usuarios.add(usuarioAmigo1);
    	usuarios.add(usuarioAmigo2);
    	usuarios.add(usuarioAmigo3);
    	usuarios.add(usuarioAmigo4);
    	
    	for (Usuario usuario : usuarios) {
				HandlerDS.guardarUsuario(usuario);
			}
    	
    	miUsuario.agregarAmigo(usuarioAmigo1);
    	miUsuario.agregarAmigo(usuarioAmigo2);
    	miUsuario.agregarAmigo(usuarioAmigo3);
    	
    	usuarioAmigo2.agregarAmigo(usuarioAmigo3);
    	usuarioAmigo2.agregarAmigo(usuarioAmigo4);
    	
		}

		private static void loadItems() {
			items = new ListaDeItems();
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
    	item5 = new Item("Moto Yamaha", "La cambio por un Fiat 147", new ObjetoML(
    			"http://moto.mercadolibre.com.ar/MLA-526751708-yamaha-xtz-250-_JM", "MLA526751708",
    			"http://mla-s1-p.mlstatic.com/19567-MLA20173504975_102014-I.jpg"));
    	item6 = new Item("Camiseta Futbol Estudiantes", "La cambio por una camiseta de River", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-524762371-remera-olan-estudiantes-de-la-plata-retro-original-pincha-_JM", "MLA524762371",
    			"http://mla-s2-p.mlstatic.com/10850-MLA20035018254_012014-I.jpg"));
    	item7 = new Item("Musculosa Seleccion argentina", "La cambio por un PenDrive", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-523588201-musculosa-de-la-sel-argentina-adidas-2013-_JM", "MLA523588201",
    			"http://mla-s1-p.mlstatic.com/9770-MLA20020656962_122013-I.jpg"));
    	item8 = new Item("Auto Fiat 147", "Lo cambio por una Moto", new ObjetoML(
    			"http://auto.mercadolibre.com.ar/MLA-527665484-fiat-147-tr-92-_JM", "MLA527665484",
    			"http://mla-s2-p.mlstatic.com/19762-MLA20176858358_102014-M.jpg"));
    	item9 = new Item("Auto Gol", "Lo cambio por un LCD 50 pulgadas", new ObjetoML(
    			"http://auto.mercadolibre.com.ar/MLA-527265001-gol-power-aire-y-direccion-vendo-o-permuto-urgente-_JM", "MLA527265001",
    			"http://mla-s1-p.mlstatic.com/19619-MLA20175213019_102014-I.jpg"));
    	item10 = new Item("Camiseta Futbol Racing 2004", "La cambio por un pantalon de Independiente", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-525681267-remera-original-racing-2004-topper-nunca-se-uso-negociable-_JM", "MLA525681267",
    			"http://mla-s1-p.mlstatic.com/19377-MLA20169415720_092014-I.jpg"));
    	
    	items.add(item1);
    	items.add(item2);
    	items.add(item3);
    	items.add(item4);
    	items.add(item5);
    	items.add(item6);
    	items.add(item7);
    	items.add(item8);
    	items.add(item9);
    	items.add(item10);
    	
    	for (Item item : items) {
				HandlerDS.guardarItem(item);
			}
		}
    
    public static void unload(){
    	items.clear();
    	usuarios.clear();
    	trueques.clear();
    }

    public static void setup() {
    	ObjectifyService.register(Item.class);
    	ObjectifyService.register(Usuario.class);
    	ObjectifyService.register(Trueque.class);
      load();
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
