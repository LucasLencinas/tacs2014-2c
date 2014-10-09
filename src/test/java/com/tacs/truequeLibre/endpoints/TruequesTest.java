package com.tacs.truequeLibre.endpoints;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tacs.truequeLibre.Main;
import com.tacs.truequeLibre.Utils.TruequeStatusConstants;
import com.tacs.truequeLibre.domain.Item;
import com.tacs.truequeLibre.domain.ListaDeTrueques;
import com.tacs.truequeLibre.domain.ObjetoML;
import com.tacs.truequeLibre.domain.Trueque;

public class TruequesTest extends AbstractTest{
	protected Trueque trueque1;
	protected Trueque trueque2;
	protected ListaDeTrueques trueques;
	@Before
	public void setUp(){	
		super.setUp();
    	trueques = new ListaDeTrueques();
    	trueque1 = new Trueque(item1,item2,miUsuario, usuarioAmigo,"descripcion");
    	trueques.add(trueque1);
    	trueque2 = new Trueque(item1,item2,usuarioAmigo, miUsuario,"descripcion");
    	trueques.add(trueque1);
		
	}
	@Test
	public void testAceptoUnItem() throws Exception {
		trueque1.aceptarTrueque();
		assertTrue(miUsuario.getItems().contains(item2));
		assertTrue(usuarioAmigo.getItems().contains(item1));
		assertTrue(trueque1.getEstado() == TruequeStatusConstants.ACCEPTED.getID());
	}

}
