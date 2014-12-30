package com.tacs.truequeLibre.endpoints;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.tacs.truequeLibre.Utils.HandlerDS;
import com.tacs.truequeLibre.domain.Item;
import com.tacs.truequeLibre.domain.ListaDeTrueques;
import com.tacs.truequeLibre.domain.ObjetoML;
import com.tacs.truequeLibre.domain.Trueque;
import com.tacs.truequeLibre.domain.Usuario;
import com.tacs.truequeLibre.setup.Setup;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataStoreTest {
	
		public Item anteojos;
		public Item camisetaRacing;
		public Usuario usuario1;
		public Usuario usuario2;
		public Trueque truequeTest;

    private final LocalServiceTestHelper helper =
        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
        Setup.setupTest();
        
        anteojos = new Item("Anteojos De Sol Para Test", "Los Cambio por una Camiseta de futbol", new ObjetoML(
      			"http://articulo.mercadolibre.com.ar/MLA-525033435-ray-ban-wayfarer-2140-anteojos-de-sol-varios-modelos-_JM", "MLA525033435",
      			"http://mla-s1-p.mlstatic.com/17009-MLA20130611399_072014-I.jpg", "mercadolibre"));
      	
      	camisetaRacing= new Item("Camiseta Futbol Racing 2004", "La cambio por unos Anteojos", new ObjetoML(
      			"http://articulo.mercadolibre.com.ar/MLA-525681267-remera-original-racing-2004-topper-nunca-se-uso-negociable-_JM", "MLA525681267",
      			"http://mla-s1-p.mlstatic.com/19377-MLA20169415720_092014-I.jpg", "mercadolibre"));
      	
      	usuario1 = new Usuario("Usuario1", "1234567890");
      	usuario1.agregarItem(anteojos);
      	usuario2 = new Usuario("Usuario2", "0987654321");
      	usuario2.agregarItem(camisetaRacing);
      	
        HandlerDS.guardarItem(anteojos);
        HandlerDS.guardarItem(camisetaRacing);
        
        HandlerDS.guardarUsuario(usuario1);
        HandlerDS.guardarUsuario(usuario2);
        
        truequeTest = new Trueque(anteojos, camisetaRacing, usuario1, usuario2, "Cambio anteojos por camiseta");
        HandlerDS.guardarTrueque(truequeTest);
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    
    @Test
    public void filtroTruequesPorUsuarioTest(){

      ListaDeTrueques truequesFiltrados = HandlerDS.findTruequeByUser(usuario1);
      assertEquals(truequesFiltrados.size(), 1);      
      assertEquals(truequesFiltrados.get(0).getUsuarioSolicitante().getId(), usuario1.getId()); 	
    }

    @Test
    public void filtroTruequesPorItemTest(){

      ListaDeTrueques truequesFiltrados = HandlerDS.findTruequeByItem(anteojos);
      assertEquals(truequesFiltrados.size(), 1);      
      assertEquals(truequesFiltrados.get(0).getUsuarioSolicitante().getId(), usuario1.getId());
      assertEquals(truequesFiltrados.get(0).getItemOfrecido().getId(), anteojos.getId()); 
    }
    
    
    @Test
    public void saveAndGetItemAndUserTest() {
    	HandlerDS.guardarItem(Setup.trueque1.getItemOfrecido()); 
      Item item = HandlerDS.findItemById(Setup.trueque1.getItemOfrecido().getId());
      assertEquals(Setup.trueque1.getItemOfrecido(),  item);
      
      HandlerDS.guardarUsuario(Setup.trueque1.getUsuarioSolicitado()); 
      Usuario usuario = HandlerDS.findUsuarioById(Setup.trueque1.getUsuarioSolicitado().getId());
      assertEquals(usuario, Setup.trueque1.getUsuarioSolicitado());
      assertEquals(usuario.getNombre(), Setup.trueque1.getUsuarioSolicitado().getNombre());
    }
    
    @Test
    public void saveAnUserAndGetAnItemOfAFriendTest(){
    	HandlerDS.guardarUsuario(Setup.miUsuario);
    	Usuario usuarioGuardado = HandlerDS.findUsuarioById(Setup.miUsuario.getId());
    	assertEquals(usuarioGuardado, Setup.miUsuario);
    	
    	Usuario amigoOfDataStore = usuarioGuardado.getAmigos().findById(Setup.usuarioAmigo1.getId());
    	assertEquals(amigoOfDataStore.getId(), Setup.usuarioAmigo1.getId());
    	assertEquals(amigoOfDataStore.getItems().findById(Setup.item2.getId()), 
    			Setup.usuarioAmigo1.getItems().findById(Setup.item2.getId()));
    }

    @Test
    public void getTruequeByItemTest(){
    	ListaDeTrueques trueques = HandlerDS.findTruequeByItem(truequeTest.getItemOfrecido());
    	Trueque truequeBuscado = trueques.get(0);
    	assertEquals(truequeBuscado.getId(),truequeTest.getId());
    }
}


