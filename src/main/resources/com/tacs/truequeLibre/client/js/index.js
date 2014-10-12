
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

	function getMyItems(){
		//$( "#dynamicRow" ).css( "background", "yellow" );
		//$( "#dynamicRow div" ).css( "color", "orange" );
		//$( "#dynamicRow div > div" ).css( "background", "red" );
		
		$.ajax({
	        type: "GET",
	        dataType: "json",
	        url: "truequeLibre/items",
	        success: function (data) {
	        	var items = "";
	        	data.forEach( function(el){
	        		items += generarVistaItem(el);
	        	});
	            $('#dynamicRow').html(items);
	            $('.img-thumbnail').tooltip();
	        }
	    });
	}
	
	function generarVistaItem(item){
		var vista = "<div class=\"col-md-4\">";
		vista += sprintf("<h4>%s</h4>",item.title);
		vista += sprintf("<img src=\"%s\" class=\"img-thumbnail\" width=\"100\" height=\"100\" " + 
				" data-toggle=\"tooltip\" data-placement=\"right\" title=\"%s\" data-html=\"true\" >"
				,item.ml.thumbnail,"Nombre de Usuario:<br>" + item.description);
		vista += "<p><button class=\"btn btn-primary btn-sm\" data-toggle=\"modal\" " + 
		"data-target=\"#myModal\">Ver Detalles</button></p>";
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

