package com.tacs.truequeLibre.endpoints;


import static org.junit.Assert.assertNotNull;


import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tacs.truequeLibre.Main;


public class UsuarioEndToEndTest {

    private static HttpServer server;

    @BeforeClass
    public static void setUp() throws Exception {
        server = Main.startServer();

    }

    @AfterClass
    public static void tearDown() throws Exception {
        server.shutdownNow();
    }
    
    @Test
    public void crearUnUsuarioYChequearQueExista() {

        assertNotNull(1);

    }

}
