/*Clase MLSearch. Recibe la tabla en la que debe cargar los resultados y el formato de los elementos*/
var MlSearch = function(table, template){
	this.template = template; 
	this.table = $(table);
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


/* Crea una instancia de MLSearch y setea los handlers a los botones */
$(document).ready(function(){
	var ml = MlSearch("#ml-results", "<tr class='ml-item' style='background-color: lightgray'>" + $("#ml-item-template").html()+"</tr>");

	$("#ml-search-btn").click(
		function(){
			ml.search( $("#ml-search-input").val(), 5);
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
		var ml_item = $(e.target).closest("tr.ml-item");
		$("#ml_permalink").val(ml_item.data().permalink);
		$("#ml_id").val(ml_item.data().id);
		$("#title").val(ml_item.data().title);
		$("#description").val("Descripcion hardcodeada");
	});
});