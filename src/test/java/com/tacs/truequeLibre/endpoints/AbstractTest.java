package com.tacs.truequeLibre.endpoints;

import static org.junit.Assert.*;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tacs.truequeLibre.Main;
import com.tacs.truequeLibre.domain.Item;
import com.tacs.truequeLibre.domain.ListaDeItems;
import com.tacs.truequeLibre.domain.ListaDeTrueques;
import com.tacs.truequeLibre.domain.ObjetoML;
import com.tacs.truequeLibre.domain.Trueque;
import com.tacs.truequeLibre.domain.Usuario;

public abstract class AbstractTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	 
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	protected Item item1;
	protected Item item2;
	protected Item item3;
	protected Item item4;
	protected ListaDeItems items;
	protected Usuario miUsuario;
	protected Usuario usuarioAmigo;
	

	@Before
	public void setUp() {
    	items = new ListaDeItems();
    	item1 = new Item("Anteojos", "De Sol", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-430387888-anteojos-ray-ban-wayfare-_JM", "MLA430387888"));
    	item2 = new Item("Notebook", "Lenovo", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-523499379-notebook-lenovo-x220-_JM", "MLA523499379"));
    	item3 = new Item("Mesa", "Cuadrada", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-521311328-mesa-de-comedor-cuadrada-140-x-140-linea-neta-_JM", "MLA521311328"));
    	item4 = new Item("Botines", "Adidas", new ObjetoML(
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
	
	}

	@After
	public void tearDown() throws Exception {
	}
		 
	  
	
	
}
