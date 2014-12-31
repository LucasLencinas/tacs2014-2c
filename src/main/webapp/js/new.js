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
		var item =  $(that.template
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

}


var EbaySearch = function(table, template, after_results){
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
		that.query = query;
		$.ajax({
		  type: "GET",
		  dataType: "jsonp",
		  url: "https://svcs.ebay.com/services/search/FindingService/v1",
		  data: {
		  	"OPERATION-NAME": "findItemsAdvanced",
		  	"SERVICE-VERSION": "1.0.0",
		  	"SECURITY-APPNAME": "DanielaK-07ea-4682-bcd6-1c4b2907b7ea",
		  	"RESPONSE-DATA-FORMAT": "JSON",
		  	"REST-PAYLOAD": "true",
		  	"paginationInput.entriesPerPage": limit, 
		  	"paginationInput.pageNumber": page,
		  	"keywords": query,
		  	"descriptionSearch": "true",
		  	"GLOBAL-ID": that.language
			},
		  success: fill_table									
		});
	}


	this.set_language = function(language){
		that.language = language;
	}


	this.set_page = function(page){
		page = Math.max(1, Math.min(that.query_result.paging.totalPages, page));
		that.search(that.query_result.query, that.query_result.paging.limit, page);
	}

	this.next_page = function(){
		that.set_page(that.query_result.paging.page + 1);
	}
	this.prev_page = function(){
		that.set_page(that.query_result.paging.page - 1);
	}


	/*Private*/
	function fill_table (data){
		that.table.html("");
		var pagination = data.findItemsAdvancedResponse[0].paginationOutput[0];
		that.query_result = {"results": data.findItemsAdvancedResponse[0].searchResult[0].item, "paging": {"page": parseInt(pagination.pageNumber[0]), "limit": parseInt(pagination.entriesPerPage[0]), "totalPages": parseInt(pagination.totalPages[0]) }, "query": that.query} ;
		if(typeof(that.query_result.results)!="undefined"){
			$.each(that.query_result.results, function(i,r){
				that.table.append(fill_item(r));
			});
		}
		that.after_results();
	}

	function fill_item(item){
		var item_html =  $(that.template
						.replace(/%TITLE%/g, item.title[0])
						.replace(/%THUMBNAIL%/g, item.galleryURL[0])
						.replace(/%PERMALINK%/g, item.viewItemURL[0]));
		item_html.data(
				{	
					"id": item.itemId[0],
					"permalink": item.viewItemURL[0], 
					"thumbnail": item.galleryURL[0],
					"title": item.title[0],
				}
			);
		return item_html;
	}

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

			



$(document).ready(function(){

/* Crea una instancia de MLSearch y setea los handlers a los botones */
	
	var ml = new MlSearch("#ml-results", $("#ml-item-template").html(), showNavigationLinks);
	var ebay = new EbaySearch("#ml-results", $("#ml-item-template").html(), showNavigationLinks);
	
	$('#title').on('keyup', function(e) {
	    if (e.which == 13) {
	        e.preventDefault();
	        $("#ml-search-btn").click();
	    }
	});

	function showNavigationLinks(){
		$(".page-navigator").removeClass("hidden");
	}

	function hideNavigationLinks(){
		$(".page-navigator").addClass("hidden");
	}


	function getSearchObjBySource(){
		var source = $("#source").val();
		if(source=="mercadolibre") return ml;
		if(source=="ebay"){
			if($("#source :selected").text().indexOf("US")>-1){
				ebay.set_language("EBAY-US");	
			}else{
				ebay.set_language("EBAY-ES");
			}

			return ebay;	
		} 
	}


	$("#source").change(function(e){
		$("#ml-results").html("");
		hideNavigationLinks();
	});

	$("#ml-search-btn").click(
		function(e){
			e.preventDefault();
			getSearchObjBySource().search( $("#title").val(), 3);
		}
	);

	$("#ml-prev-page").click(function(e){
		e.preventDefault();
		getSearchObjBySource().prev_page();
	});

	$("#ml-next-page").click(function(e){
		e.preventDefault();
		getSearchObjBySource().next_page();
	});

	/* Guarda en campos del form el permalink y la id del item seleccionado */
	$(document).on("click","#ml-results .ml-item-radio",function(e){
		var ml_item = $(e.target).closest(".ml-item-row");
		$("#ml_permalink").val(ml_item.data().permalink);
		$("#ml_id").val(ml_item.data().id);
		$("#ml_thumbnail").val(ml_item.data().thumbnail);

	});


	$("#create-item").click(function(e){
			var item = {
						"title": $("#title").val(), 
						"description": $("#description").val() ,
						"ml":{
								"id": $("#ml_id").val(),
								"permalink": $("#ml_permalink").val(),
								"thumbnail": $("#ml_thumbnail").val(),
								"source": $("#source").val()
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