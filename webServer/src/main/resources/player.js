define(["d3.v3.min"
	,"trackTime"
        ,"track"
       ]
,
function(d3, trackTime, currentTrack) {
  return function(url, containerNode){
      function setup(){
	  // There may already be music playing. If so, find out and show it.
	  d3.json("play/status", function(error, json){
	      if (error) return console.warn(error);
	      currentTrack(json);
	      renderControls(json);
	      trackTime(json);
	      renderVolume(json);
	  });
	  showList(url, containerNode);
	  //Now set up push event listener.
	  console.log("About to set up event source");
	  var source = new EventSource("play/events");
	  source.addEventListener('player-state-change', function(event){
	      var json = JSON.parse(event.data);
	      console.log(JSON.stringify(json));
	      currentTrack(json);
	      renderControls(json);
	      trackTime(json);
	      renderVolume(json);
	  });

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
	      trackTime(json);
	      currentTrack(json);
	      renderVolume(json);
	  });
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

      function formatTime(seconds){
	var someDate = new Date(2016, 1, 1);
	var unixEpochStyle = +someDate;
	var withOurSeconds = unixEpochStyle+(seconds*1000);
	var formatter = d3.time.format("%M:%S");
	return formatter(new Date(withOurSeconds));
      }
    }
    return setup;
  }
});
