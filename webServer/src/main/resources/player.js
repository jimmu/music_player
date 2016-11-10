function hello(url, thingToSelect) {
  d3.json(url, function(error, json){
    if (error) return console.warn(error);

    var thisRow =
    d3.select(thingToSelect)
    .selectAll("div").data(json)
      .enter()
      .append("div")
      .classed("artist", function(d){return d.isArtist})
      .classed("album", function(d){return d.isAlbum})
      .classed("song", function(d){return d.isSong})

    thisRow.on("click", (function(d){
        d3.event.stopPropagation();
        if (d.listActionUrl){
          hello(d.listActionUrl, this);
        }
      }));

    thisRow.append("span").text(function(d){
        return d.name;
      });

    thisRow.append("span").text(" - play")
      .on("click", (function(d){
           d3.event.stopPropagation();
           if (d.playActionUrl){
             play(d.playActionUrl);
           }
         }));
  });
}

function play(url){
  d3.json(url, function(error, json){
    if (error) return console.warn(error);
    console.log("Playing this... "+JSON.stringify(json));
    d3.select("#nowPlaying").datum(json).append("span").text(function(d){return d.name});
    renderVolume(json);
  });
}

function volume(url){
    d3.json(url, function(error, json){
        if (error) return console.warn(error);
        renderVolume(json);
    });
}

function renderVolume(json){
    var volumeSection = d3.select("#volume").datum(json);
    volumeSection.html(""); //TODO. This feels a bit non-d3. But works for me.
    volumeSection.append("span").text(" - ")
        .on("click", (function(d){
            volume(d.volumeDownUrl);
        }));
    volumeSection.append("span").text(function(d){return d.volume})
    volumeSection.append("span").text(" + ")
        .on("click", (function(d){
            volume(d.volumeUpUrl);
        }));
}