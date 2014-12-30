package com.tacs.truequeLibre.endpoints;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.tacs.truequeLibre.domain.Item;
import com.tacs.truequeLibre.domain.ObjetoML;

public class FeaturesTest extends AbstractTest{

	@Before
	public void setUp() {
		super.setUp();
	}
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testDosItemsSonIgualesConDiferentesCamposSalvoElID() {
		item1 = new Item("Anteojos", "De Sol", new ObjetoML(
  			"http://articulo.mercadolibre.com.ar/MLA-525033435-ray-ban-wayfarer-2140-anteojos-de-sol-varios-modelos-_JM", "MLA525033435",
  			"http://mla-s1-p.mlstatic.com/17009-MLA20130611399_072014-I.jpg", "mercadolibre"));
		item1.setId(1);
		
		assertTrue(item1.equals(items.findById(1)));
	}
	@Test
	public void testDosItemsSonDistintosConDistintosCamposYDistintoID() {
  	item1 = new Item("Mesa", "Cuadrada", new ObjetoML(
  			"http://articulo.mercadolibre.com.ar/MLA-523688946-mesa-de-comedor-cuadrada-140-x-140-linea-neta-_JM", "MLA523688946",
  			"http://mla-s2-p.mlstatic.com/12855-MLA20066714972_032014-I.jpg", "mercadolibre"));
		item1.setId(2);
		
		assertFalse(item1.equals(items.findById(1)));
	}
	@Test
	public void testDosItemsSonDistintosConMismosCamposYDistintoID() {
  	item1 = new Item("Anteojos", "De Sol", new ObjetoML(
  			"http://articulo.mercadolibre.com.ar/MLA-525033435-ray-ban-wayfarer-2140-anteojos-de-sol-varios-modelos-_JM", "MLA525033435",
  			"http://mla-s1-p.mlstatic.com/17009-MLA20130611399_072014-I.jpg", "mercadolibre"));
		item1.setId(2);
		
		assertFalse(item1.equals(items.findById(1)));
	}
	@Test
	public void testUnaListaContieneUnItemConDeterminadoID() {
		item1 = new Item("Anteojos", "De Sol", new ObjetoML(
  			"http://articulo.mercadolibre.com.ar/MLA-525033435-ray-ban-wayfarer-2140-anteojos-de-sol-varios-modelos-_JM", "MLA525033435",
  			"http://mla-s1-p.mlstatic.com/17009-MLA20130611399_072014-I.jpg", "mercadolibre"));
		item1.setId(1);
		//no entiendo este test, no es lo mismo que hacer assertNotNull(items.findById(1)) ?? Seria muy parecido al de abajo
		assertNotNull(items.findById(item1.getId()));
	}
	
	@Test
	public void testUnaListaTieneUnElementoConId3(){
		Item unItem = items.findById(3);
		assertNotNull(unItem);
	}
	@Test
	public void testUnaListaNoTieneUnElementoConId350(){
		Item unItem = items.findById(350);
		assertNull(unItem);
	}
	
}
