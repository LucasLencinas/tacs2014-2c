
	function getMyTrueques(){
		$.get("truequeLibre/trueques", function( data ) {
		  $( "#mainTitle" ).html( "Mis Trueques" );
		  $( "#mainData" ).load( "trueques.html" );
		});
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
	        		items += el.title + " " + el.id + " " + el.description + " " + el.objML.ml_permalink + " " + el.objML.ml_id + "<br> ";
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

