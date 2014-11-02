
/*Listado de las solicitudes que me hicieron, donde puedo aceptar o rechazar*/
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

	function aceptarTrueque(truequeId){
		$.ajax({
		  type: "POST",
		  url: "truequeLibre/trueques/accept/"+truequeId,
		  success: function(data){
		  	console.log(data);
		  	setTruequeAlert('Aceptado');
		  	getMySolicitudes();
		  	publishTruequeOnFB(data);
		  	
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


  	function publishTruequeOnFB(trueque){
		FB.ui({
			method: 'feed',
			name: "Trueque aceptado a "+trueque.usuarioSolicitante.nombre+"!",
			picture: trueque.itemOfrecido.ml.thumbnail,
			caption: trueque.itemOfrecido.title,
			description: "Obtuve "+trueque.itemOfrecido.title+" por "+trueque.itemSolicitado.title

		}, function(response){});
  	}