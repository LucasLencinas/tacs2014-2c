package com.tacs.truequeLibre.endpoints;


import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.tacs.truequeLibre.domain.Item;
import com.tacs.truequeLibre.domain.Usuario;
import com.tacs.truequeLibre.setup.Setup;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataStoreTest {

    private final LocalServiceTestHelper helper =
        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
        Setup.setup();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    // run this test twice to prove we're not leaking any state across tests
    private void doTest() {
        
        ofy().save().entity(Setup.trueque1.getItemOfrecido()).now(); 
        Item item = ofy().load().type(Item.class).id(Setup.trueque1.getItemOfrecido().getId()).now();
        assertEquals(Setup.trueque1.getItemOfrecido(),  item);
        
        ofy().save().entity(Setup.trueque1.getItemSolicitado()).now(); 
        item = ofy().load().type(Item.class).id(Setup.trueque1.getItemSolicitado().getId()).now();
        assertEquals(item.getDescripcion(), Setup.trueque1.getItemSolicitado().getDescripcion());
        assertEquals(item.getObjML().getPermalink(), Setup.trueque1.getItemSolicitado().getObjML().getPermalink());
        
        ofy().save().entity(Setup.trueque1.getUsuarioSolicitado()).now(); 
        Usuario usuario = ofy().load().type(Usuario.class).id(Setup.trueque1.getUsuarioSolicitado().getId()).now();
        assertEquals(usuario, Setup.trueque1.getUsuarioSolicitado());
        assertEquals(usuario.getNombre(), Setup.trueque1.getUsuarioSolicitado().getNombre());
    }

    @Test
    public void testInsert1() {
        doTest();
    }

    @Test
    public void testInsert2() {
        doTest();
    }
}

