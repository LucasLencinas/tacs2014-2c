
/*Listado de todos mis trueques, tanto solicitados como los que me solicitaron*/
  function getMyTrueques(){
    $( "#dynamicRow" ).load( "trueques.html" );
    $.get("truequeLibre/miPerfil/trueques", function( data ) {
      $( "#mainTitle" ).html( "Mis Trueques" );
      data.forEach( function(trueque){
        var row = "<tr><td>"+ trueque.usuarioSolicitante.nombre+"</td><td>"+
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



