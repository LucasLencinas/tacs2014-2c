
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

	function actualizarModalHacerTrueque(idItem,idUsuario){
		
		actualizarItemDeModalHacerTrueque(idItem);
		$.ajax({
	        type: "GET",
	        dataType: "json",
	        url: "truequeLibre/usuarios/"+idUsuario+"/items",
	        success: function (data) {
	        	//Agregar los options al select por cada item
	        	data.forEach( function(el){
	        		var unOption = sprintf("<option value=\"%s\">%s</option>",el.id,el.title);
	        		$("#selectDeModalHacerTrueque").append( unOption );
	        	});
	        }
	    });
	}
	
	function actualizarItemDeModalHacerTrueque(idItem){
		
		$("#modalHacerTruequeLabel").html($("#" + idItem + " > h4 ").text());
		$("#imagenModalHacerTrueque").attr("src",$("#" + idItem + " > img ").attr('src'));
		$("#descriptionModalHacerTrueque").html($("#" + idItem + " > img ").attr('alt'));

	}
	
	function actualizarModalDeleteItem(idItem,idUsuario){	
		
		$("#modalDeleteItemLabel").html($("#" + idItem + " > h4 ").text());
		$("#imagenModalDeleteItem").attr("src",$("#" + idItem + " > img ").attr('src'));
		$("#descriptionModalDeleteItem").html($("#" + idItem + " > img ").attr('alt'));
		$("#deleteItemButton").attr("onclick","deleteItem("+idItem+","+idUsuario+")")
	}
	
	
	
	
	//Me da los items de todo el sistema menos los mios
	//El 1 que hardcodee es el id del usuario con el que se logueo una persona
	function getOtherItems(){
		$( "#mainTitle" ).html( "Bienvenido a Trueque Libre!" );
		$.ajax({
	        type: "GET",
	        dataType: "json",
	        url: "truequeLibre/items",
	        success: function (data) {
	        	var items = "";
	        	data.forEach( function(el){
	        		if(el.id != "1")
	        			items += generarVistaOtherItem(el);
	        		
	        	});
	            $('#dynamicRow').html(items);
	            $('.img-thumbnail').tooltip();
	        }
	    });
	}
	//El id del usuario, lo hardcodee, le puse un Uno, arreglarlo despues
	function generarVistaOtherItem(item){	
		var vista = sprintf("<div class=\"col-md-4\" id=\"%s\">",item.id);
		vista += sprintf("<h4>%s</h4>",item.title);
		vista += sprintf("<img src=\"%s\" alt=\"%s\" class=\"img-thumbnail\" width=\"100\" height=\"100\" " + 
				" data-toggle=\"tooltip\" data-placement=\"right\" title=\"%s\" data-html=\"true\" >"
				,item.ml.thumbnail,item.description,"Nombre de Usuario:<br>" + item.description);
		vista += sprintf("<p><button class=\"btn btn-primary btn-sm\" data-toggle=\"modal\" " + 
		"data-target=\"#modalHacerTrueque\" onclick=\"actualizarModalHacerTrueque(%s,%s)\">Postular Trueque</button></p>",item.id, "1");
		vista += "</div>";
		return vista;
	}
	
	function getMyItems(){
		$( "#mainTitle" ).html( "Mis Items!" );
		$.ajax({
	        type: "GET",
	        dataType: "json",
	        url: "truequeLibre/usuarios/1/items",
	        success: function (data) {
	        	var items = "";
	        	data.forEach( function(el){
	        		items += generarVistaMyItem(el);
	        	});
	            $('#dynamicRow').html(items);
	            $('.img-thumbnail').tooltip();
	        }
	    });
	}
	
	function generarVistaMyItem(item){	
		var vista = sprintf("<div class=\"col-md-4\" id=\"%s\">",item.id);
		vista += sprintf("<h4>%s</h4>",item.title);
		vista += sprintf("<img src=\"%s\" alt=\"%s\" class=\"img-thumbnail\" width=\"100\" height=\"100\" " + 
				" data-toggle=\"tooltip\" data-placement=\"right\" title=\"%s\" data-html=\"true\" >"
				,item.ml.thumbnail,item.description,"Nombre de Usuario:<br>" + item.description);
		vista += sprintf("<p><button class=\"btn btn-primary btn-sm\" data-toggle=\"modal\" " + 
		"data-target=\"#modalDeleteItem\" onclick=\"actualizarModalDeleteItem(%s,%s)\">Borrar Item</button></p>",item.id, "1");
		vista += "</div>";
		return vista;
	}
	
	function deleteItem(idDeItem,idDeUsuario){
		$("#modalDeleteItem").modal('hide');
		var deleteRequest = {"idItem": idDeItem, "idUsuario": idDeUsuario};
		//Me crea bien el deleteRequest pero llega mal al servidor
		//Stringify funciona bien
		$.ajax({
	        type: "DELETE",
	        data: JSON.stringify(deleteRequest),
			contentType: 'application/json', 
	        url: "truequeLibre/usuarios/1/items",
	        dataType:"json",
	        success: function (data) {
	        	$("#descripcionResultadoOperacion").html('Operacion Exitosa!');
	        },
	        error: function(data){
	        	$("#descripcionResultadoOperacion").html('Hubo un Error en la Operacion!');
	        }
	    });
	}
	
	
	
	function sprintf( format )
	{
	  for( var i=1; i < arguments.length; i++ ) {
	    format = format.replace( /%s/, arguments[i] );
	  }
	  return format;
	}

