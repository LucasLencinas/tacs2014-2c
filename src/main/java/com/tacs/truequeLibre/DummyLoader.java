package com.tacs.truequeLibre;

import com.tacs.truequeLibre.domain.Item;
import com.tacs.truequeLibre.domain.ObjetoML;
import com.tacs.truequeLibre.domain.Trueque;
import com.tacs.truequeLibre.domain.Usuario;
import com.tacs.truequeLibre.services.ItemsService;
import com.tacs.truequeLibre.services.TruequesService;
import com.tacs.truequeLibre.services.UsersService;

public class DummyLoader {
	public static void loadData(){
		createUsers();
		createItems();
		createTrueques();
	}
	
	public static void createUsers(){
		 UsersService.add(new Usuario("Mi Usuario"));
		 UsersService.add(new Usuario("Usuario Amigo1"));
		 UsersService.add(new Usuario("Usuario Amigo2"));
		 UsersService.add(new Usuario("Usuario Amigo3"));
	}
	
	public static void createItems(){
	  	ItemsService.add(0, new Item("Anteojos De Sol", "Los Cambio por una Notebook Lenovo", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-525033435-ray-ban-wayfarer-2140-anteojos-de-sol-varios-modelos-_JM", "MLA525033435",
    			"http://mla-s1-p.mlstatic.com/17009-MLA20130611399_072014-I.jpg")));
	  	ItemsService.add(1, new Item("Notebook Lenovo", "La cambio por un sillon de madera", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-526842420-notebook-lenovo-thinkpad-x220-intel-i5-4gb-hd-320gb-125-_JM", "MLA526842420",
    			"http://mla-s1-p.mlstatic.com/18466-MLA20155614258_092014-I.jpg")));
	  	ItemsService.add(0, new Item("Mesa Cuadrada", "La cambio por juego de sillas", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-523688946-mesa-de-comedor-cuadrada-140-x-140-linea-neta-_JM", "MLA523688946",
    			"http://mla-s2-p.mlstatic.com/12855-MLA20066714972_032014-I.jpg")));
	  	ItemsService.add(1, new Item("Botines Adidas", "Los cambio por zapatillas Nike", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-520889152-botines-adidas-11questra-brasil-2014-fifa-futbol-tapones-pro-_JM", "MLA520889152",
    			"http://mla-s1-p.mlstatic.com/18156-MLA20150804347_082014-I.jpg")));
	  	ItemsService.add(1, new Item("Moto Yamaha", "La cambio por un Fiat 147", new ObjetoML(
    			"http://moto.mercadolibre.com.ar/MLA-526751708-yamaha-xtz-250-_JM", "MLA526751708",
    			"http://mla-s1-p.mlstatic.com/19567-MLA20173504975_102014-I.jpg")));
	  	ItemsService.add(2, new Item("Camiseta Futbol Estudiantes", "La cambio por una camiseta de River", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-524762371-remera-olan-estudiantes-de-la-plata-retro-original-pincha-_JM", "MLA524762371",
    			"http://mla-s2-p.mlstatic.com/10850-MLA20035018254_012014-I.jpg")));
	  	ItemsService.add(2, new Item("Musculosa Seleccion argentina", "La cambio por un PenDrive", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-523588201-musculosa-de-la-sel-argentina-adidas-2013-_JM", "MLA523588201",
    			"http://mla-s1-p.mlstatic.com/9770-MLA20020656962_122013-I.jpg")));
	  	ItemsService.add(3, new Item("Auto Fiat 147", "Lo cambio por una Moto", new ObjetoML(
    			"http://auto.mercadolibre.com.ar/MLA-527665484-fiat-147-tr-92-_JM", "MLA527665484",
    			"http://mla-s2-p.mlstatic.com/19762-MLA20176858358_102014-M.jpg")));
	  	ItemsService.add(3, new Item("Auto Gol", "Lo cambio por un LCD 50 pulgadas", new ObjetoML(
    			"http://auto.mercadolibre.com.ar/MLA-527265001-gol-power-aire-y-direccion-vendo-o-permuto-urgente-_JM", "MLA527265001",
    			"http://mla-s1-p.mlstatic.com/19619-MLA20175213019_102014-I.jpg")));
	  	ItemsService.add(3, new Item("Camiseta Futbol Racing 2004", "La cambio por un pantalon de Independiente", new ObjetoML(
    			"http://articulo.mercadolibre.com.ar/MLA-525681267-remera-original-racing-2004-topper-nunca-se-uso-negociable-_JM", "MLA525681267",
    			"http://mla-s1-p.mlstatic.com/19377-MLA20169415720_092014-I.jpg")));
	}
	
	private static void createTrueques() {
	
		TruequesService.addTrueque(new Trueque(ItemsService.find(0),ItemsService.find(1),"Anteojos por Notebook"));
		TruequesService.addTrueque(new Trueque(ItemsService.find(2),ItemsService.find(4),"Mesa por Moto"));
		TruequesService.addTrueque(new Trueque(ItemsService.find(4), ItemsService.find(6),"Musculosa por Auto"));
		TruequesService.addTrueque(new Trueque(ItemsService.find(4),ItemsService.find(7), "Moto por Auto"));
	}
	
	public static void clearAll(){
		TruequesService.clearAll();
		ItemsService.clearAll();
		UsersService.clearAll();
	}

}
