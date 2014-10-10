
	function getMyTrueques(){
		$.get("truequeLibre/trueques", function( data ) {
		  $( "#mainTitle" ).html( "Mis Trueques" );
		  $( "#mainData" ).load( "trueques.html" );
		});
	}

	function getMyItems(){
		$( "#dynamicRow" ).css( "background", "yellow" );
		$( "#dynamicRow div" ).css( "color", "orange" );
		$( "#dynamicRow div > div" ).css( "background", "red" );
	}

