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

  //Now set up push event listener.
  console.log("About to set up event source");
  var source = new EventSource("play/events");
  source.addEventListener('player-state-change', function(event){
    var json = JSON.parse(event.data);
    console.log(JSON.stringify(json));
    //TODO. Now re-render parts of the GUI using this info.
  });
}

function play(url){
  d3.json(url, function(error, json){
    if (error) return console.warn(error);
    console.log("Playing this... "+JSON.stringify(json));
    d3.select("#nowPlaying").datum(json).html("").append("span").classed("playing", true).text(function(d){return d.name});
    renderControls(json);
    renderVolume(json);
  });
}

function volume(url){
    d3.json(url, function(error, json){
        if (error) return console.warn(error);
        renderVolume(json);
    });
}

function playerControl(url){
    d3.json(url, function(error, json){
        if (error) return console.warn(error);
        renderControls(json);
    });
}

function renderVolume(json){
    var volumeSection = d3.select("#volume").datum(json);
    volumeSection.html(""); //TODO. This feels a bit non-d3. But works for me. Prolly better to get the enter set and so forth.
    volumeSection.append("span").classed("volumeUpDown", true).text(" - ")
        .on("click", (function(d){
            volume(d.volumeDownUrl);
        }));
    volumeSection.append("span").classed("volume", true).text(function(d){return d.volume})
    volumeSection.append("span").classed("volumeUpDown", true).text(" + ")
        .on("click", (function(d){
            volume(d.volumeUpUrl);
        }));
}

function renderControls(json){
    var controlsSection = d3.select("#controls").datum(json);
    controlsSection.html(""); // As above.
    controlsSection.append("span")
                    .classed("pauseButton", function(d){return d.isPlaying})
                    .text("Pause")
                    .on("click", (function(d){
                        playerControl(d.pauseActionUrl);
                    }));
    controlsSection.append("span")
                    .classed("stopButton", function(d){return d.isPlaying})
                    .text("Stop")
                    .on("click", (function(d){
                        playerControl(d.stopActionUrl);
                    }));

}
