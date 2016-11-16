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
	   playerControl(d.playActionUrl);
         });
  });
}

function setup(url, containerNode){
    // There may already be music playing. If so, find out and show it.
    d3.json("play/status", function(error, json){
        if (error) return console.warn(error);
        renderCurrentTrack(json);
        renderControls(json);
	renderTrackTime(json);
        renderVolume(json);
    });
    showList(url, containerNode);
    //Now set up push event listener.
    console.log("About to set up event source");
    var source = new EventSource("play/events");
    source.addEventListener('player-state-change', function(event){
        var json = JSON.parse(event.data);
        console.log(JSON.stringify(json));
        renderCurrentTrack(json);
        renderControls(json);
	renderTrackTime(json);
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
	renderTrackTime(json);
        renderCurrentTrack(json);
	renderVolume(json);
    });
}

function renderCurrentTrack(json){
    //console.log("Playing this... "+JSON.stringify(json));
    d3.select("#nowPlaying").datum(json).html("").append("span").classed("playing", true).text(function(d){return d.name});
}

function renderVolume(json){
    var volumeSection = d3.select("#volume").datum(json);
    volumeSection.html(""); 
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
    controlsSection.html(""); 
    controlsSection.append("span")
                    .classed("previousButton", true)
                    .text("Previous")
                    .on("click", (function(d){
                        playerControl(d.previousTrackActionUrl);
                    }));
    controlsSection.append("span")
                    .classed("playButton", function(d){return !d.isPlaying})
                    .text("Play")
                    .on("click", (function(d){
                        playerControl(d.playActionUrl);
                    }));
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
    controlsSection.append("span")
                    .classed("nextButton", true)
                    .text("Next")
                    .on("click", (function(d){
                        playerControl(d.nextTrackActionUrl);
                    }));
}

function renderTrackTime(json){
    var timeSection = d3.select("#trackTime").datum(json);
    timeSection.html(""); 
    timeSection.append("span")
		    .classed("elapsedTime", true)
		    .text(function(d){return formatTime(d.elapsedTime)});

    //Doing this with svg - overkill? DIVs would probably do fine.
    var barHeight = 16;
    var barWidth = 100;
    var svg = timeSection.append("svg").attr("width", barWidth).attr("height", barHeight);
    svg.append("rect").attr("x", 0).attr("y",0)
	.attr("width", function(d){return barWidth*d.elapsedTime/d.songLength})
	.attr("height", barHeight)
	.attr("fill", "teal");  //TODO: Can the colour be applied via css class instead?
    svg.append("rect").attr("x", function(d){return barWidth*d.elapsedTime/d.songLength})
	.attr("y",0)
	.attr("width", function(d){return barWidth*(d.songLength-d.elapsedTime)/d.songLength})
	.attr("height", barHeight)
	.attr("fill", "gray");

    timeSection.append("span")
		    .classed("trackLength", true)
		    .text(function(d){return formatTime(d.songLength)});
}

function formatTime(seconds){
  var someDate = new Date(2016, 1, 1);
  var unixEpochStyle = +someDate;
  var withOurSeconds = unixEpochStyle+(seconds*1000);
  var formatter = d3.time.format("%M:%S");
  return formatter(new Date(withOurSeconds));
}
