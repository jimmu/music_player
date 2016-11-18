define(["d3.v3.min"
       ]
,
function(d3) {
      var onClickHandler = function(url){
        d3.json(url, function(error, json){
          if (error) return console.warn(error);
          renderVolume(json);
        });
      }

      function renderVolume(json){
	  var volumeSection = d3.select("#volume").datum(json);
	  volumeSection.html(""); 
	  volumeSection.append("span").classed("volumeUpDown", true).text(" - ")
	      .on("click", (function(d){
		  onClickHandler(d.volumeDownUrl);
	      }));
	  volumeSection.append("span").classed("volume", true).text(function(d){return d.volume})
	  volumeSection.append("span").classed("volumeUpDown", true).text(" + ")
	      .on("click", (function(d){
		  onClickHandler(d.volumeUpUrl);
	      }));
      }

    renderVolume.onClick=function(value){
        if (!arguments.length) return onClickHandler;
        onClickHandler = value
        return renderVolume;
      }

    return renderVolume;
});
