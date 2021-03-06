/*
 * Esto se carga y ejecuta para el login en pagina principal
 * */
 
    function checkLoginState() {
        FB.getLoginStatus(function(response) {
        	
        	statusChangeCallback(response);
        });
      }

    window.fbAsyncInit = function() {
    	  FB.init({
    	    appId      : '347575272090580',
    	    cookie     : true,  // enable cookies to allow the server to access the session
    	    xfbml      : true,  // parse social plugins on this page
    	    version    : 'v2.1' // use version 2.1
    	  });
    	  
    	  FB.Event.subscribe('auth.authResponseChange', function(response) {
		     if (response.status === 'connected') {
		    	document.cookie = "token" + '=;expires=Thu, 01 Jan 1970 00:00:01 GMT;';
		    	document.cookie = "id" + '=;expires=Thu, 01 Jan 1970 00:00:01 GMT;';

		        document.cookie = 'token=' + response.authResponse.accessToken;
		        document.cookie = 'id=' + response.authResponse.userID;
		     }   
		     else if (response.status === 'not_authorized') 
		    {/*FAILED*/} 
		     else{/*UNKNOWN*/}
			});
    	  
    	  FB.Event.subscribe('auth.statusChange', function(response) {
 		     if (response.status === 'connected') {
 		    	document.cookie = "token" + '=;expires=Thu, 01 Jan 1970 00:00:01 GMT;';
 		    	document.cookie = "id" + '=;expires=Thu, 01 Jan 1970 00:00:01 GMT;';

 		        document.cookie = 'token=' + response.authResponse.accessToken;
 		        document.cookie = 'id=' + response.authResponse.userID;
 		     }   
 		     else if (response.status === 'not_authorized') 
 		    {/*FAILED*/} 
 		     else{/*UNKNOWN*/}
 			});
    	  
    	  FB.Event.subscribe('auth.login', function() {
    		  location.reload();
    		});
    	  /*
    	  	Ahora que inicializamos el SDK de Javascript, llamamos a FB.getLoginStatus().
    	  	Esta funcion obtiene el estado de la persona que visita la pagina y puede devolver
    	  	alguno de los tres estados a la funcion callback que le das como parametro.
    	  	1-Loggueado en la app --> 'connected'
    	  	2-Loggueado en Facebook pero no en la app --> 'not_authorized'
    	  	3-No loggueado en Facebook y no lo puede reconocer --> 
    	  */
    	  FB.getLoginStatus(function(response) {
    		  statusChangeCallback(response);
    	  });
      };

      
  	function publishOnFB(){
		FB.ui({
			method: 'feed',
			name: "Cree un nuevo producto para poder hacer un trueque",
			picture: "unaImagen.jpg",
			caption: "Una definicion de TruequeLibre o algo asi",
			description: "cambie una silla por una cafetera"

		}, function(response){});
  	}

    function FacebookInviteFriends(){
      FB.ui({
        method: 'apprequests',
        message: 'Te invito a usar nuestra aplicacion para realizar trueques con tus amigos. TRUEQUE LIBRE!'
      });
    }
      
      
      
      
      function statusChangeCallback(response) {
        console.log('statusChangeCallback');
        if(response == null){
        	location.reload();
        }
        console.log(response);
        // El response tiene muchos datos del usuario loggueado, entre ellos el estado
        if (response.status === 'connected') {
        	
        	/*
        	 * ACA ya estoy loggueado y puedo empezar a modificar la pagina,cargar los datos y todo eso
        	 * */
			document.cookie = 'token=' + response.authResponse.accessToken;
		    document.cookie = 'id=' + response.authResponse.userID;
		    $('#main').load("mainData.html");
        	$('.img-thumbnail').tooltip();
        	FB.api('/me', function(response) {
              $('#nombreUsuarioDropdown').show();
              $('#nombreUsuario').html(response.name+" <span class='caret'></span>");
              document.cookie = 'nombre=' + response.name;
          });
          getOtherItems($.cookie("id"));
          
          $("#cerrarSesion").attr('onclick', 'cerrarSesion();');
          
            //testAPI();  // Logged en la app y en Facebook.
        } else if (response.status === 'not_authorized') {
          	$('#statusLogin').innerHTML = 'Please log into this app.';
          	
          	// Loggueado en Facebook pero no en la app
        } else {
          	// No esta loggueado en facebook por lo tanto no reconoce a la persona
          	$('#statusLogin').innerHTML = 'Please log into Facebook.';
        }
      }

      function cerrarSesion(){
    	  
    	  FB.logout(function(response) {
    		  window.location = 'index.html'; 
    	  });
      }
      
      
      // Carga el SDK de manera asincrona
      (function(d, s, id) {
        var js, fjs = d.getElementsByTagName(s)[0];
        if (d.getElementById(id)) return;
        js = d.createElement(s); 
        js.id = id;
        js.src = "//connect.facebook.net/en_US/sdk.js";
        fjs.parentNode.insertBefore(js, fjs);
      }
      (document, 'script', 'facebook-jssdk'));
