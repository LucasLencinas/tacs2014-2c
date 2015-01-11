
	//Me da los items de todo el sistema menos los mios
	//El 1 que hardcodee es el id del usuario con el que se logueo una persona
	function getOtherItems(userID){
		$( "#mainTitle" ).html( "Bienvenido a Trueque Libre!" );
		console.log("A punto de pedir los itesm de amigos del user" + userID);
		$.ajax({
	        type: "GET",
	        dataType: "json",
	        url: "truequeLibre/amigos/",
	        success: function (data) {
	        	var items = "";
	        	if(data[0] == null){
	            	$('#dynamicRow').html("<div class='row'><div class='col-md-12' style='text-align:center'><h3>No tienes amigos que usen esta aplicación.</h3></div></div>");
	        	} else {
		        	data.forEach( function(amigo){
	    	        	amigo.items.forEach( function(it){
	    	        		if(it != null)
	    	        			items += generarVistaOtherItem(it, amigo);
	    	        	});    	       
		        	});
		            $('#dynamicRow').html(items);
		            $('.img-thumbnail').tooltip();
	        	}
	        }
	    });
	}

	function getEstadisticas(){
		$.ajax({
	        type: "GET",
	        dataType: "json",
	        url: "truequeLibre/dashboard/",
	        success: function (data) {
	        	var estadisticasHtml = generarVistaEstadisticas(data);
	        	
	        	//setear grilla usuarios
	        	//setear grilla trueques
	        	
	            $('#dynamicRow').html(estadisticasHtml);
	            $('.img-thumbnail').tooltip();
	        }
	    });
	}
/*
data.forEach( function(amigo){
    	        	amigo.items.forEach( function(it){
    	        		if(it != null)
    	        			items += generarVistaOtherItem(it, amigo);
    	        	});    	       
	        	});
*/
	function generarVistaEstadisticas(estadisticas){

		if(estadisticas.cantidadUsuarios == -1)
			return "<div class='row'><div class='col-md-12' style='text-align:center'><h3>Debes ser administrador para acceder a esta sección.</h3></div></div>";

		var vista = "<div class=\"container\">";
		vista+="			<div class=\"row\">";
		vista+="				<div class=\"col-md-12\">";
		vista += 			sprintf("<h4> Cantidad de usuarios: %s</h4>",estadisticas.cantidadUsuarios);
		vista += 					"<br/>";
		vista += 					"<br/>";
		vista += 					"<h4> Items</h4>";
		vista += 					"<table class=\"table table-striped table-bordered\" id=\"itemsTable\">";
		vista += 						"<thead>";
		vista += 							"<th>Nombre Usuario</th>";
		vista += 							"<th>Item</th>";
		vista += 						"</thead>";
		vista += 						"<tbody>";
		estadisticas.listaDeUsuarios.forEach(function (usuario){
			var nombreUsuario=usuario.nombreUsuario;
			usuario.listaDeItems.forEach(function(item){
				vista += 					"<tr>";
				vista += 				sprintf("<td>%s</td>", nombreUsuario);
				vista += 				sprintf("<td>%s</td>", item.title);
				vista += 					"</tr>";

			})
		});
		vista += 						"</tbody>";
		vista += 					"</table>";
		vista += 					"<br/>";
		vista += 					"<br/>";
		vista += 					"<h4> Trueques</h4>";
		vista += 					"<table class=\"table table-striped table-bordered\" id=\"truequesTable\">";
		vista += 						"<thead>";
		vista += 							"<th>Solicitante</th>";
		vista += 							"<th>Solicitado</th>";
		vista += 							"<th>Item Solicitado</th>";
		vista += 							"<th>Item Ofrecido</th>";
		vista += 							"<th>Estado</th>";
		vista += 						"</thead>";
		vista += 						"<tbody>";
		estadisticas.listaDeTrueques.forEach(function (trueque){
				vista += 					"<tr>";
				vista += 				sprintf("<td>%s</td>", trueque.usuarioSolicitante.nombre);
				vista += 				sprintf("<td>%s</td>",  trueque.usuarioSolicitado.nombre);
				vista += 				sprintf("<td>%s</td>", trueque.itemSolicitado.title);
				vista += 				sprintf("<td>%s</td>", trueque.itemOfrecido.title);
				vista += 				sprintf("<td>%s</td>", getStatusName(trueque.estado));
				vista += 					"</tr>";			
		});
		vista += 						"</tbody>";
		vista += 					"</table>";
		vista+="				</div>";
		vista+="			</div>";
		vista+="		</div>";
		return vista;
	}


	function generarVistaOtherItem(item, amigo){	
		var vista = sprintf("<div class=\"col-md-4\" id=\"%s\">",item.id);
		vista += sprintf("<h4>%s</h4>",item.title);
		vista += sprintf("<img src=\"%s\" alt=\"%s\" class=\"img-thumbnail\" width=\"100\" height=\"100\" " + 
				" data-toggle=\"tooltip\" data-placement=\"right\" title=\"%s\" data-html=\"true\" >"
				,item.ml.thumbnail,item.description, amigo.nombre +":<br>" + item.description);
		vista += sprintf("<p><button class=\"btn btn-primary btn-sm\" data-toggle=\"modal\" " + 
		"data-target=\"#modalHacerTrueque\" onclick=\"actualizarModalHacerTrueque('%s','%s');\">Postular Trueque</button></p>",item.id, amigo.id);
		vista += "</div>";
		return vista;
	}
	
	function cargarDatos(){
		$.ajax({
	        type: "POST",
			contentType: 'text/plain', 
	        url: "truequeLibre/admin/load",
	        data:$("#CodigocargarDatos").val(),
	        dataType:"text/plain",
	        statusCode: {
	            409: function (response) {//Conflicto
	            	alert('Codigo Incorrecto!');
	            },
	            200: function(response) {
	            	alert('Carga Completada!');
	            }
	        }
	    });
	}
	
	function borrarDatos(){
		$.ajax({
	        type: "POST",
			contentType: 'text/plain', 
	        url: "truequeLibre/admin/delete",
	        data:$("#CodigocargarDatos").val() + "-borrado",
	        dataType:"text/plain",
	        statusCode: {
	            409: function (response) {//Conflicto
	            	alert('Codigo Incorrecto!');
	            },
	            200: function(response) {
	            	alert('Borrado Completado!');
	            }
	        }
	    });
	}
	
	