
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
	        		items += el.title + " " + el.id + " " + el.description + " " + el.objML.ml_permalink + " " + el.objML.ml_id +
	        		"<img src=\"" + el.objML.ml_thumbnail +"\" alt=\""+ el.description + "\" class=\"img-thumbnail\" width=\"100\" height=\"100\">" +  "<br> ";
	        	});
	            $('#dynamicRow').html(items);
	        }
	    });
		
		/*Tengo que cargar algo de este estilo
		 
      <div class="col-md-4">
        <h4>Remera Adidas</h4>
        <img src="..." alt="lalala" class="img-thumbnail" width="100" height="100">
        <p><button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal"> 
          Ver Detalles
          </button></p>
      </div>
      */
	}

