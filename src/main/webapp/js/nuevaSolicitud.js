
/*Solicitud de un nuevo trueque*/
	function actualizarModalHacerTrueque(idItemSolicitado,idAmigo){
		actualizarItemDeModalHacerTrueque(idItemSolicitado);
		$("#selectDeModalHacerTrueque").find('option').remove().end();
		$.ajax({
	        type: "GET",
	        dataType: "json",
	        url: "truequeLibre/miPerfil/items",
	        success: function (data) {
	        	//Agregar los options al select por cada item
	        	data.forEach( function(el){
	        		var unOption = sprintf("<option value=\"%s\">%s</option>",el.id,el.title);
	        		$("#selectDeModalHacerTrueque").append( unOption );
	        	});
	        }
	    });
		console.log("idItemSolicitado:"+idItemSolicitado+"idAmigo:"+idAmigo);
		$("#postularTruequeButton").removeAttr( "onclick" );
		$("#postularTruequeButton").attr("onclick", "postularTrueque('"+idAmigo+"','"+idItemSolicitado+"')");
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
	
	function postularTrueque(idAmigo, idItemSolicitado){
		var idItemOfrecido = Number($("#selectDeModalHacerTrueque").val());
		console.log({"idAmigo": idAmigo, "idItemSolicitado": idItemSolicitado, "idItemOfrecido":idItemOfrecido});
		$.ajax({
	        type: "POST",
	        data: JSON.stringify({"idAmigo": idAmigo, "idItemSolicitado": idItemSolicitado, "idItemOfrecido":idItemOfrecido}),
	        url: "truequeLibre/miPerfil/trueques",
					contentType: 'application/json', 
	        success: function (data){
				$('#modalHacerTrueque').modal('hide');
				$("#modalResultadoOperacion").modal('show');
	  	  		$("#descripcionResultadoOperacion").html('Operacion Exitosa!');
	 			getMyTrueques();	
	        }
	    });
	    console.log("HOLA");
	}