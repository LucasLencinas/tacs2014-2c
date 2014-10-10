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

/**
 * Main class.
 * 
 */
public class Main {
	
    public static final String RESOURCES_PATH = "/src/main/resources/com/tacs/truequeLibre/";
    public static final String BASE_URI = "http://localhost:8080/truequeLibre/";
    
    public static ListaDeItems items;
    public static ListaDeUsuarios usuarios;
    public static ListaDeTrueques trueques;
    
    public static Usuario miUsuario;
    public static Usuario usuarioAmigo1;
    public static Usuario usuarioAmigo2;
    public static Usuario usuarioAmigo3;
    
    public static Trueque trueque1;
    public static Trueque trueque2;
    public static Trueque trueque3;
    
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
    	
    	loadItems();
    	loadUsuarios();
     	loadTrueques();

    }

		private static void loadTrueques() {
			
			trueques = new ListaDeTrueques();
    	//Parece media fea la creacion de un trueque, solo deberian estar los items.
    	//Pero si lo hago asi, entonces cada item tendria que saber su dueño. Pregunarlo. FIXME
    	trueque1 = new Trueque(item1,item2,miUsuario, usuarioAmigo1,"Anteojos por Notebook");
    	trueque2 = new Trueque(item3,item5,miUsuario, usuarioAmigo2,"Mesa por Moto");
    	trueque3 = new Trueque(item7,item8,usuarioAmigo2, usuarioAmigo3,"Musculosa por Auto");
    	
    	//Esto esta re mal creo, se repiten relaciones por todos lados, preguntarle a los chicos. FIXME
    	miUsuario.getTrueques().add(trueque1);
    	miUsuario.getTrueques().add(trueque3);
    	usuarioAmigo1.getTrueques().add(trueque1);
    	usuarioAmigo2.getTrueques().add(trueque2);
    	usuarioAmigo2.getTrueques().add(trueque3);
    	usuarioAmigo3.getTrueques().add(trueque3);
    	
    	trueques.add(trueque1);
    	trueques.add(trueque2);
    	trueques.add(trueque3);
		}

		private static void loadUsuarios() {
			usuarios = new ListaDeUsuarios();
    	//Falta el agregarAmigos a todos los usuarios. TODO
    	miUsuario = new Usuario("Mi Usuario");
    	miUsuario.agregarItem(item1);
    	miUsuario.agregarItem(item3);
    	miUsuario.agregarItem(item10);

    	usuarioAmigo1 = new Usuario("Usuario Amigo1");
    	usuarioAmigo1.agregarItem(item2);
    	usuarioAmigo1.agregarItem(item4);

    	usuarioAmigo2 = new Usuario("Usuario Amigo2");
    	usuarioAmigo2.agregarItem(item5);
    	usuarioAmigo2.agregarItem(item6);
    	usuarioAmigo2.agregarItem(item7);
   	
    	usuarioAmigo3 = new Usuario("Usuario Amigo3");
    	usuarioAmigo3.agregarItem(item8);
    	usuarioAmigo3.agregarItem(item9);

    	
    	usuarios.add(miUsuario);
    	usuarios.add(usuarioAmigo1);
    	usuarios.add(usuarioAmigo2);
    	usuarios.add(usuarioAmigo3);
    	
		}

		private static void loadItems() {
			items = new ListaDeItems();
    	item1 = new Item("Anteojos", "De Sol", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-430387888-anteojos-ray-ban-wayfare-_JM", "MLA430387888"));
    	item2 = new Item("Notebook", "Lenovo", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-523499379-notebook-lenovo-x220-_JM", "MLA523499379"));
    	item3 = new Item("Mesa", "Cuadrada", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-521311328-mesa-de-comedor-cuadrada-140-x-140-linea-neta-_JM", "MLA521311328"));
    	item4 = new Item("Botines", "Adidas", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-520889152-botines-adidas-11questra-brasil-2014-fifa-futbol-tapones-pro-_JM", "MLA520889152"));
    	item5 = new Item("Moto", "Yamaha", new ObjetoML(
    			"http://moto.mercadolibre.com.ar/MLA-526751708-yamaha-xtz-250-_JM", "MLA526751708"));
    	item6 = new Item("Camiseta Futbol", "Estudiantes", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-524762371-remera-olan-estudiantes-de-la-plata-retro-original-pincha-_JM", "MLA524762371"));
    	item7 = new Item("Musculosa", "Seleccion argentina", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-523588201-musculosa-de-la-sel-argentina-adidas-2013-_JM", "MLA523588201"));
    	item8 = new Item("Auto", "Fiat 147", new ObjetoML(
    			"http://auto.mercadolibre.com.ar/MLA-527665484-fiat-147-tr-92-_JM", "MLA527665484"));
    	item9 = new Item("Auto", "Gol", new ObjetoML(
    			"http://auto.mercadolibre.com.ar/MLA-527265001-gol-power-aire-y-direccion-vendo-o-permuto-urgente-_JM", "MLA527265001"));
    	item10 = new Item("Camiseta Futbol", "Racing 2004", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-525681267-remera-original-racing-2004-topper-nunca-se-uso-negociable-_JM", "MLA525681267"));
    	
    	
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
		}
    
    public static void unload(){
    	items.clear();
    	usuarios.clear();
    	trueques.clear();
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
