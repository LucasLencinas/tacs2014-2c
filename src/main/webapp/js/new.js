/*Clase MLSearch. Recibe la tabla en la que debe cargar los resultados y el formato de los elementos*/
var MlSearch = function(table, template, after_results){
	this.template = template; 
	this.table = $(table);
	this.after_results = after_results;
	var that = this;

	/*
		query: String a buscar
		limit: Elementos por pagina
		page: Página
	*/
	this.search = function(query, limit, page){
		$.ajax({
		  dataType: "json",
		  url: "https://api.mercadolibre.com/sites/MLA/search",
		  data: {q:query, offset: limit*page, limit: limit},
		  success: fill_table									
		});
	}


	this.set_page = function(page){
		if(page<0){
			page=0
		}else{
			var max_page = that.query_result.paging.total / that.query_result.paging.limit;
		 	if(page>max_page) page = max_page;	
		}
		that.search(that.query_result.query, that.query_result.paging.limit, page)
	}

	this.next_page = function(){
		that.set_page(that.query_result.paging.offset/that.query_result.paging.limit + 1);
	}
	this.prev_page = function(){
		that.set_page(that.query_result.paging.offset/that.query_result.paging.limit - 1);
	}


	/*Private*/
	function fill_table (data){
		that.table.html("");
		that.query_result = data;
		$.each(data.results, function(i,r){
			that.table.append(fill_item(r));
		});
		that.after_results();
	}

	function fill_item(ml_item){
		var item =  $(this.template
						.replace(/%TITLE%/g, ml_item.title)
						.replace(/%THUMBNAIL%/g, ml_item.thumbnail)
						.replace(/%PERMALINK%/g, ml_item.permalink));
		item.data(
				{	
					"id": ml_item.id,
					"permalink": ml_item.permalink, 
					"thumbnail": ml_item.thumbnail,
					"title": ml_item.title,
				}
			);
		return item;
	}

	return this;

}

	function publishCreacionOnFB(item){
		FB.ui({
			method: 'feed',
			name: "Item creado:  "+item.title +"!",
			picture: item.ml.thumbnail,
			caption: item.description,
			description: "Mira el nuevo item que cree en TruequeLibre"

		}, function(response){});
	}

			



/* Crea una instancia de MLSearch y setea los handlers a los botones */
$(document).ready(function(){
	
	var ml = MlSearch("#ml-results", $("#ml-item-template").html(), showNavigationLinks);
	$('#title').on('keyup', function(e) {
	    if (e.which == 13) {
	        e.preventDefault();
	        $("#ml-search-btn").click();
	    }
	});
	function showNavigationLinks(){
		$(".page-navigator").removeClass("hidden");
	}
	$("#ml-search-btn").click(
		function(e){
			e.preventDefault();
			ml.search( $("#title").val(), 3);
		}
	);

	$("#ml-prev-page").click(function(e){
		e.preventDefault();
		ml.prev_page();
	});

	$("#ml-next-page").click(function(e){
		e.preventDefault();
		ml.next_page();
	});

	/* Guarda en campos del form el permalink y la id del item seleccionado */
	$(document).on("click","#ml-results .ml-item-radio",function(e){
		var ml_item = $(e.target).closest(".ml-item-row");
		$("#ml_permalink").val(ml_item.data().permalink);
		$("#ml_id").val(ml_item.data().id);
		$("#title").val(ml_item.data().title);
		$("#ml_thumbnail").val(ml_item.data().thumbnail);

	});

	$("#create-item").click(function(e){
			var item = {
						"title": $("#title").val(), 
						"description": $("#description").val() ,
						"ml":{
								"id": $("#ml_id").val(),
								"permalink": $("#ml_permalink").val(),
								"thumbnail": $("#ml_thumbnail").val()
							}
					};
			$.ajax(
				{
					url:"/truequeLibre/items", 
					type: "POST", 
					data: JSON.stringify(item), 
					contentType: 'application/json', 
					success: function(e){
						$("#modalResultadoOperacion").modal('show');
						$("#descripcionResultadoOperacion").html('Operacion Exitosa!<br>Item creado.<br>' );
						publishCreacionOnFB(item);		
					},
			   	dataType: 'json'
			  }

			);
		});
});