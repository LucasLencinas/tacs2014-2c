package com.tacs.truequeLibre.endpoints;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tacs.truequeLibre.Main;
import com.tacs.truequeLibre.domain.Item;
import com.tacs.truequeLibre.domain.ObjetoML;

public class FeaturesTest extends AbstractTest{
	
	@Test
	public void testDosItemsSonIgualesConMismosCampos() {
		Item item1 = new Item("Anteojos", "De Sol", new ObjetoML(
  			"http://articulo.mercadolibre.com.ar/MLA-430387888-anteojos-ray-ban-wayfare-_JM", "MLA430387888"));
		item1.setId(1);
		
		assertTrue(item1.equals(Main.items.get(0)));
	}
	
	@Test
	public void testDosItemsSonIgualesConDiferentesCamposSalvoElID() {
		Item item1 = new Item("Notebook", "Lenovo", new ObjetoML(
  			"http://articulo.mercadolibre.com.ar/MLA-523499379-notebook-lenovo-x220-_JM", "MLA523499379"));
		item1.setId(1);
		
		assertTrue(item1.equals(Main.items.get(0)));
	}
	
	@Test
	public void testDosItemsSonDistintosConDistintosCamposYDistintoID() {
		Item item1 = new Item("Mesa", "Cuadrada", new ObjetoML(
  			"http://articulo.mercadolibre.com.ar/MLA-521311328-mesa-de-comedor-cuadrada-140-x-140-linea-neta-_JM", "MLA521311328"));
		item1.setId(2);
		
		assertFalse(item1.equals(Main.items.get(0)));
	}
	
	@Test
	public void testDosItemsSonDistintosConMismosCamposYDistintoID() {
		Item item1 = new Item("Anteojos", "De Sol", new ObjetoML(
  			"http://articulo.mercadolibre.com.ar/MLA-430387888-anteojos-ray-ban-wayfare-_JM", "MLA430387888"));
		item1.setId(2);
		
		assertFalse(item1.equals(Main.items.get(0)));
	}
	
	@Test
	public void testUnaListaContieneUnItemConDeterminadoID() {
		Item item1 = new Item("Anteojos", "De Sol", new ObjetoML(
  			"http://articulo.mercadolibre.com.ar/MLA-430387888-anteojos-ray-ban-wayfare-_JM", "MLA430387888"));
		item1.setId(1);
		
		assertTrue(Main.items.contains(item1));
	}
	@Test
	public void testUnaListaTieneUnElementoConId3(){
		Item unItem = Main.items.findById(3);
		assertNotNull(unItem);
	}
	
	@Test
	public void testUnaListaNoTieneUnElementoConId350(){
		Item unItem = Main.items.findById(350);
		
		assertNull(unItem);
	}
	
	/**
	 * Pruebo algunas cosas de las listas de commons utils
	 **/
	
}
