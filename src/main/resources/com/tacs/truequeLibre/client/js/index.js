
	function getMyTrueques(){
		$( "#dynamicRow" ).load( "trueques.html" );
		$.get("truequeLibre/trueques", function( data ) {
			$( "#mainTitle" ).html( "Mis Trueques" );
    	data.forEach( function(el){
    		var row = "<tr><td>"+	el.usuarioSolicitante.nombre+"</td><td>"+
    													el.usuarioSolicitado.nombre+"</td><td>"+
    													el.itemSolicitado.title+"</td><td>"+
    													el.itemOfrecido.title+"</td><td>"+
    													getStatusName(el.estado)+"</td></tr>";
    		$("#truequesTable > tbody").append(row);
    	});
		});
	}

	function getStatusName(statusID){
		switch(statusID){
			case 0: return "Pendiente";
			case 1: return "Aceptado";
			case 2: return "Rechazado";
		}
	}

	function actualizarModal(idItem,idUsuario){
		
		modificarItemDeModal(idItem);
		$.ajax({
	        type: "GET",
	        dataType: "json",
	        url: "truequeLibre/usuarios/"+idUsuario+"/items",
	        success: function (data) {
	        	//Agregar los options al select por cada item
	        	data.forEach( function(el){
	        		var unOption = sprintf("<option value=\"%s\">%s</option>",el.id,el.title);
	        		$("#selectDeModal").append( unOption );
	        	});
	        }
	    });
	}
	
	function modificarItemDeModal(idItem){
		
		$("#myModalLabel").html($("#" + idItem + " > h4 ").text());
		$("#imagenModal").attr("src",$("#" + idItem + " > img ").attr('src'));
		$("#descriptionModal").html($("#" + idItem + " > img ").attr('alt'));

	}
	
	
	//Me da los items de todo el sistema menos los mios
	//El 1 que hardcodee es el id del usuario con el que se logueo una persona
	function getOtherItems(){
		$.ajax({
	        type: "GET",
	        dataType: "json",
	        url: "truequeLibre/items",
	        success: function (data) {
	        	var items = "";
	        	data.forEach( function(el){
	        		if(el.id != "1")
	        			items += generarVistaItem(el);
	        		
	        	});
	            $('#dynamicRow').html(items);
	            $('.img-thumbnail').tooltip();
	        }
	    });
	}
	//El id del usuario, lo hardcodee, le puse un Uno, arreglarlo despues
	function generarVistaItem(item){	
		var vista = sprintf("<div class=\"col-md-4\" id=\"%s\">",item.id);
		vista += sprintf("<h4>%s</h4>",item.title);
		vista += sprintf("<img src=\"%s\" alt=\"%s\" class=\"img-thumbnail\" width=\"100\" height=\"100\" " + 
				" data-toggle=\"tooltip\" data-placement=\"right\" title=\"%s\" data-html=\"true\" >"
				,item.ml.thumbnail,item.description,"Nombre de Usuario:<br>" + item.description);
		vista += sprintf("<p><button class=\"btn btn-primary btn-sm\" data-toggle=\"modal\" " + 
		"data-target=\"#myModal\" onclick=\"actualizarModal(%s,%s)\">Postular Trueque</button></p>",item.id, "1");
		vista += "</div>";
		return vista;
		
		
	}
	
	function sprintf( format )
	{
	  for( var i=1; i < arguments.length; i++ ) {
	    format = format.replace( /%s/, arguments[i] );
	  }
	  return format;
	}

