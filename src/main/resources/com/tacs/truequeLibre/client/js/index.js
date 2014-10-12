
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
	        		items += generarVistaItem(el);
	        	});
	            $('#dynamicRow').html(items);
	        }
	    });
	}
	
	function generarVistaItem(item){
		var vista = "<div class=\"col-md-4\">";
		vista += sprintf("<h4>%s</h4>",item.title);
		vista += sprintf("<p>%s</p>", item.description);
		vista += sprintf("<img src=\"%s\" class=\"img-thumgnail\" width=\"100\" height=\"100\" >",item.objML.ml_thumbnail);
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

