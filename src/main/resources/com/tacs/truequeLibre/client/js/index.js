
	function getMyTrueques(){
		$( "#dynamicRow" ).load( "trueques.html" );
		$.get("truequeLibre/miPerfil/trueques", function( data ) {
			$( "#mainTitle" ).html( "Mis Trueques" );
    	data.forEach( function(trueque){
    		var row = "<tr><td>"+	trueque.usuarioSolicitante.nombre+"</td><td>"+
    													trueque.usuarioSolicitado.nombre+"</td><td>"+
    													trueque.itemSolicitado.title+"</td><td>"+
    													trueque.itemOfrecido.title+"</td><td>"+
    													getStatusName(trueque.estado)+"</td></tr>";
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

	function getMySolicitudes(){
		$( "#dynamicRow" ).load( "truequesPendientes.html" );
		$.get("truequeLibre/trueques/pendientes", function( data ) {
			$( "#mainTitle" ).html( "Mis Solicitudes" );
    	data.forEach( function(trueque){
    		var row = "<tr><td>"+	trueque.usuarioSolicitante.nombre+"</td><td>"+
    													getThumbnail(trueque.itemSolicitado)+"</td><td>"+
    													getThumbnail(trueque.itemOfrecido)+"</td><td>"+
    													"<a class='btn btn-success' onclick='aceptarTrueque("+trueque.id+")'>Aceptar</a></td><td>"+
    													"<a class='btn btn-danger' onclick='rechazarTrueque("+trueque.id+")'>Rechazar</a></td></tr>";
    		$("#solicitudesTable > tbody").append(row);
    	});
		});
	}

	function getThumbnail(item){
		return "<img src='"+item.ml.thumbnail+"class='img-thumbnail' width='100 height='100' data-toggle='tooltip'"+
		"title='"+item.title+"' href='"+"'>";
	}

	function aceptarTrueque(truequeId){
		$.ajax({
		  type: "POST",
		  url: "truequeLibre/trueques/accept/"+truequeId,
		  success: function(){
		  	setTruequeAlert('Aceptado');
		  	getMySolicitudes();
		  }
		});
	}

	function rechazarTrueque(truequeId){
		$.ajax({
		  type: "POST",
		  url: "truequeLibre/trueques/reject/"+truequeId,
		  success: function(){
		  	setTruequeAlert('Rechazado');
		  	getMySolicitudes();
		  }
		});
	}

	function setTruequeAlert(status){
		$("#notificationsRow").append("<div class='alert alert-success' role='alert'>Trueque "+status+
			"!<button type='button' class='close' data-dismiss='alert'>&times;</span></div>");
	}

	function actualizarModalHacerTrueque(idItemSolicitado,idAmigo){
		//En realidad ahora el idUsuario no lo uso, pero mas adelante creo que lo necesito
		actualizarItemDeModalHacerTrueque(idItemSolicitado);
		//limpio el select
		$("#selectDeModalHacerTrueque").find('option').remove().end();
		$.ajax({
	        type: "GET",
	        dataType: "json",
	        url: "truequeLibre/items",
	        success: function (data) {
	        	//Agregar los options al select por cada item
	        	data.forEach( function(el){
	        		var unOption = sprintf("<option value=\"%s\">%s</option>",el.id,el.title);
	        		$("#selectDeModalHacerTrueque").append( unOption );
	        	});
	        }
	    });

		$("#postularTruequeButton").attr("onclick", "postularTrueque("+idAmigo+","+idItemSolicitado+")");
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
		$("#deleteItemButton").attr("onclick","deleteItem("+idItem+")");
	}
	
	//Me da los items de todo el sistema menos los mios
	//El 1 que hardcodee es el id del usuario con el que se logueo una persona
	function getOtherItems(){
		$( "#mainTitle" ).html( "Bienvenido a Trueque Libre!" );
		$.ajax({
	        type: "GET",
	        dataType: "json",
	        url: "truequeLibre/amigos/",
	        success: function (data) {
	        	var items = "";
	        	data.forEach( function(amigo){
    	        	amigo.items.forEach( function(it){
    	        			items += generarVistaOtherItem(it, amigo);
    	        	});    	       
	        	});
	            $('#dynamicRow').html(items);
	            $('.img-thumbnail').tooltip();
	        }
	    });
	}
	//El id del usuario, lo hardcodee, le puse un Uno, arreglarlo despues
	function generarVistaOtherItem(item, amigo){	
		var vista = sprintf("<div class=\"col-md-4\" id=\"%s\">",item.id);
		vista += sprintf("<h4>%s</h4>",item.title);
		vista += sprintf("<img src=\"%s\" alt=\"%s\" class=\"img-thumbnail\" width=\"100\" height=\"100\" " + 
				" data-toggle=\"tooltip\" data-placement=\"right\" title=\"%s\" data-html=\"true\" >"
				,item.ml.thumbnail,item.description,"Nombre de Usuario:<br>" + item.description);
		vista += sprintf("<p><button class=\"btn btn-primary btn-sm\" data-toggle=\"modal\" " + 
		"data-target=\"#modalHacerTrueque\" onclick=\"actualizarModalHacerTrueque(%s,%s);\">Postular Trueque</button></p>",item.id, amigo.id);
		vista += "</div>";
		return vista;
	}
	
	function getMyItems(){
		$( "#mainTitle" ).html( "Mis Items!" );
		$.ajax({
	        type: "GET",
	        dataType: "json",
	        url: "truequeLibre/miPerfil/items",
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
//		console.log(item);
		var vista = sprintf("<div class=\"col-md-4\" id=\"%s\">",item.id);
		vista += sprintf("<h4>%s</h4>",item.title);
		vista += sprintf("<img src=\"%s\" alt=\"%s\" class=\"img-thumbnail\" width=\"100\" height=\"100\" " + 
				" data-toggle=\"tooltip\" data-placement=\"right\" title=\"%s\" data-html=\"true\" >"
				,item.ml.thumbnail,item.description,"Nombre de Usuario:<br>" + item.description);
		vista += sprintf("<p><button class=\"btn btn-primary btn-sm\" data-toggle=\"modal\" " + 
		"data-target=\"#modalDeleteItem\" onclick=\"actualizarModalDeleteItem(%s,%s);\">Borrar Item</button></p>",item.id, "1");
		vista += "</div>";
		return vista;
	}
	
	function deleteItem(idDeItem){
		$("#modalDeleteItem").modal('hide');
		//Me crea bien el deleteRequest pero llega mal al servidor
		//Stringify funciona bien
		$.ajax({
	        type: "DELETE",
					contentType: 'application/json', 
	        url: "truequeLibre/miPerfil/items/"+idDeItem,
	        dataType:"json",
	    });
  	$("#descripcionResultadoOperacion").html('Operacion Exitosa!');
		getMyItems();
	}

	function postularTrueque(idAmigo, idItemSolicitado){
		var idItemOfrecido = Number($("#selectDeModalHacerTrueque").val());
		$.ajax({
	        type: "POST",
	        data: JSON.stringify({"idAmigo": idAmigo, "idItemSolicitado": idItemSolicitado, "idItemOfrecido":idItemOfrecido}),
	        url: "truequeLibre/miPerfil/trueques",
					contentType: 'application/json', 
	        dataType:"json",
	    });
						getMyTrueques();	
	}
	
	
	
	function sprintf( format )
	{
	  for( var i=1; i < arguments.length; i++ ) {
	    format = format.replace( /%s/, arguments[i] );
	  }
	  return format;
	}

