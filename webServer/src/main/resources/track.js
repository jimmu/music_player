define(["d3.v3.min"]
,
function(d3) {
      return function(selection){
        selection.each(function(data,i){
	        d3.select(this)
	        .html("")
	        .append("span")
	        .classed("playing", true)
	        .text(function(d){return d.name});
	    });
      }
});
