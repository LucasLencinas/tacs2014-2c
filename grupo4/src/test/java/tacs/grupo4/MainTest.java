
package tacs.grupo4;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.core.header.MediaTypes;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import junit.framework.TestCase;

public class MainTest extends TestCase {

    private SelectorThread threadSelector;
    private WebResource resource;

    public MainTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        threadSelector = Main.startServer();
        Client c = Client.create();
        resource = c.resource(Main.BASE_URI);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        threadSelector.stopEndpoint();
    }

    /**
     * Solo chequea que el home, / , me devuelva algo.
     */
    public void testHome() {
        String responseMsg = resource.path("/").get(String.class);
        assertTrue(responseMsg.length()>0);
    }

    /**
     * No se para que esta!
     * Test if a WADL document is available at the relative path
     * "application.wadl".
     */
    public void testApplicationWadl() {
        String serviceWadl = resource.path("application.wadl").
                accept(MediaTypes.WADL).get(String.class);
        assertTrue(serviceWadl.length() > 0);
    }
}


