package tacs.grupo4.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class Home {
	@GET
	@Produces("text/html")
    public String show() {
        return "<html><body><h1>Grupo 4</h1><h2>Entrega 1</h2> <h3>Paths disponibles:</h3> "
        		+ "<a href='items'> GET \\items </a><br>"
        		+ "<a href='items\\1'>GET \\items\\{item_id}</a><br>"
        		+ "<a href='items\\new'>GET \\items\\new</a><br>"
        		+ "POST \\items <br>"
        		+ "<a href='items\\1\\edit'>GET \\items\\{item_id}\\edit</a><br>"
        		+ "PUT \\items\\{item_id}<br>"
        		+ "DELETE \\items\\{item_id}<br><br>"
        		+ "<a href='trueques'> GET \\trueques </a><br>"
        		+ "<a href='trueques\\solicitudes'> GET \\trueques\\solicitudes </a><br>"
        		+ "<a href='items\\1'>GET \\trueques\\{trueque_id}</a><br>"
        		+ "<a href='trueques\\new'>GET \\trueques\\new</a><br>"
        		+ "POST \\trueques <br>"
        		+ "PUT \\treques\\{trueque_id}\\accept<br>"
        		+ "PUT \\treques\\{trueque_id}\\reject<br>"
        		+ "<p><i>Nota: Usar cualquier n&uacute;mero como id.</id></p>"
        		+ "</body></html>";
        		
    }
}
