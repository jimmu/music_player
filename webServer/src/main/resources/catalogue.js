define(["d3.v3.min"
       ]
,
function(d3) {
      var onPlayHandler = function(url){
        console.log("Clicked play on "+url);
      }

      function showList(url, containerNode) {
	d3.json(url, function(error, json){
	  if (error) return console.warn(error);

	  var thisRow =
	  d3.select(containerNode)
	  .selectAll("div").data(json)
	    .enter()
	    .append("div")
	    .classed("artist", function(d){return d.isArtist})
	    .classed("album", function(d){return d.isAlbum})
	    .classed("song", function(d){return d.isSong})

	  thisRow.on("click", (function(d){
	      d3.event.stopPropagation();
	      if (d.listActionUrl){
		if (! this.getAttribute("expanded")){
		  this.setAttribute("expanded", true);
		  showList(d.listActionUrl, this);
		}
		else{
		  d3.select(this).selectAll("div").remove();
		  this.removeAttribute("expanded");
		}
	      }
	    }));

	  thisRow.append("span").text(function(d){
	      return d.name;
	    });

	  thisRow.append("span").text(function(d){
	      return d.playActionUrl? " - play" : "";
	      })
	    .on("click", function(d){
		 onPlayHandler(d.playActionUrl);
	       });
	});
      }

      showList.onPlay=function(value){
        if (!arguments.length) return onPlayHandler;
        onPlayHandler = value
        return showList;
      }

      return showList;
});
