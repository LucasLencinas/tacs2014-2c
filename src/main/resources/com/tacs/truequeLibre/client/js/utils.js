
	function getThumbnail(item){
		return "<img src='"+item.ml.thumbnail+"class='img-thumbnail' width='100 height='100' data-toggle='tooltip'"+
		"title='"+item.title+"' href='"+"'>";
	}
	
	function sprintf( format )
	{
	  for( var i=1; i < arguments.length; i++ ) {
	    format = format.replace( /%s/, arguments[i] );
	  }
	  return format;
	}

