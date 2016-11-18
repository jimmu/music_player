define(["d3.v3.min"
       ]
,
function(d3) {
      return function(json){
	  d3.select("#nowPlaying").datum(json).html("").append("span").classed("playing", true).text(function(d){return d.name});
      }
});
