
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
	
	