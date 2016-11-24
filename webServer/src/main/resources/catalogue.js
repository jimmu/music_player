define(["d3.v3.min"]
,
function(d3) {
      var onPlayHandler = function(url){
        console.log("Clicked play on "+url);
      }

      function catalogue(selection){
        selection.each(function(data,i){
            var thisSelection = this;
            d3.json(data.listActionUrl, function(error, json){
                if (error) return console.warn(error);
                var thisRow = d3.select(thisSelection)
                                .selectAll("div").data(json)
                                .enter()
                                .append("div")
                                .classed("artist", function(d){return d.isArtist})
                                .classed("album", function(d){return d.isAlbum})
                                .classed("song", function(d){return d.isSong});
                thisRow.append("span").text(function(d){return d.name});
                var playButton = thisRow.append("span").text(function(d){return d.playActionUrl? " - play" : ""});
                playButton.on("click", function(d){
                                    d3.event.stopPropagation();
                                    onPlayHandler(d.playActionUrl);
                                });
                thisRow.on("click", (function(d){
                                d3.event.stopPropagation();
                                if (d.listActionUrl){
                                    if (!this.getAttribute("expanded")){
                                        this.setAttribute("expanded", true);
                                        d3.select(this).call(catalogue);
                                    }
                                    else{
                                        d3.select(this).selectAll("div").remove();
                                        this.removeAttribute("expanded");
                                    }
                                }
                            }));


            });
        });
      }

      catalogue.onPlay=function(value){
        if (!arguments.length) return onPlayHandler;
        onPlayHandler = value
        return catalogue;
      }

      return catalogue;
});
