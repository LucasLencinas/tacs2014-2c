package com.tacs.truequeLibre.endpoints;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;
import com.tacs.truequeLibre.Utils.HandlerDS;
import com.tacs.truequeLibre.domain.Item;
import com.tacs.truequeLibre.domain.ListaDeItems;
import com.tacs.truequeLibre.domain.ListaDeTrueques;
import com.tacs.truequeLibre.domain.ListaDeUsuarios;
import com.tacs.truequeLibre.domain.ObjetoML;
import com.tacs.truequeLibre.domain.Trueque;
import com.tacs.truequeLibre.domain.Usuario;

public abstract class AbstractTest {

  private final LocalServiceTestHelper helper =
      new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	

	protected  Item item1;
	protected Item item2;
	protected Item item3;
	protected Item item4;
	protected ListaDeItems items;
	protected Usuario miUsuario;
	protected Usuario usuarioAmigo;
	protected static boolean estaSeteado;

	@Before
	public void setUp() {
			helper.setUp();
    	ObjectifyService.register(Item.class);
    	ObjectifyService.register(Usuario.class);
    	ObjectifyService.register(Trueque.class);
		
	  	items = new ListaDeItems();
	  	item1 = new Item("Anteojos", "De Sol", new ObjetoML(
	  			"http://articulo.mercadolibre.com.ar/MLA-525033435-ray-ban-wayfarer-2140-anteojos-de-sol-varios-modelos-_JM", "MLA525033435",
	  			"http://mla-s1-p.mlstatic.com/17009-MLA20130611399_072014-I.jpg", "mercadolibre"));
	  	HandlerDS.guardarItem(item1);
	  	item2 = new Item("Notebook", "Lenovo", new ObjetoML(
	  			"http://articulo.mercadolibre.com.ar/MLA-526842420-notebook-lenovo-thinkpad-x220-intel-i5-4gb-hd-320gb-125-_JM", "MLA526842420",
	  			"http://mla-s1-p.mlstatic.com/18466-MLA20155614258_092014-I.jpg", "mercadolibre"));
	  	HandlerDS.guardarItem(item2);
	  	item3 = new Item("Mesa", "Cuadrada", new ObjetoML(
	  			"http://articulo.mercadolibre.com.ar/MLA-523688946-mesa-de-comedor-cuadrada-140-x-140-linea-neta-_JM", "MLA523688946",
	  			"http://mla-s2-p.mlstatic.com/12855-MLA20066714972_032014-I.jpg", "mercadolibre"));
	  	HandlerDS.guardarItem(item3);
	  	item4 = new Item("Botines", "Adidas", new ObjetoML(
	  			"http://articulo.mercadolibre.com.ar/MLA-520889152-botines-adidas-11questra-brasil-2014-fifa-futbol-tapones-pro-_JM", "MLA520889152",
	  			"http://mla-s1-p.mlstatic.com/18156-MLA20150804347_082014-I.jpg", "mercadolibre"));
	  	HandlerDS.guardarItem(item4);

	  	items.add(item1);
	  	items.add(item2);
	  	items.add(item3);
	  	items.add(item4);
	  	
	  	
	  	miUsuario = new Usuario("Mi Usuario","123");
	  	usuarioAmigo = new Usuario("Usuario Amigo","1234");
	  	
	  	HandlerDS.guardarUsuario(miUsuario);
	  	HandlerDS.guardarUsuario(usuarioAmigo);
	  	
	  	miUsuario.agregarItem(item1);
	  	miUsuario.agregarItem(item3);
	  	
	  	usuarioAmigo.agregarItem(item2);
	}

	@After
	public void tearDown() throws Exception {
		ListaDeItems.contador = 0;
		ListaDeTrueques.contador = 0;
		ListaDeUsuarios.contador = 0;
		helper.tearDown();
		}
		 
	  
	
	
}
