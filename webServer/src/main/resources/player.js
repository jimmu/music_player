define(["d3.v3.min"
        ,"catalogue"
	    ,"trackTime"
        ,"track"
        ,"volume"
        ,"controls"
        ,"playlist"
       ]
,
function(d3, catalogue, trackTime, currentTrack, volume, controls, playlist) {
  return function(){
      controls.onClick(playerControl);

	  // There may already be music playing. If so, find out and show it.
	  d3.json("play/status", function(error, json){
	      if (error) return console.warn(error);
          drawTopSection(json);
      });
      drawBottomSection();
      drawPlaylist();
	  setupEventListener();

      function playerControl(url){
          d3.json(url, function(error, json){
              if (error) return console.warn(error);
              drawTopSection(json);
          });
          // This method gets called when play is clicked in the catalogue section.
          // And when controls are clicked, so both are things that could affect the
          // playlist or the position within it. SO redraw that now.
          drawPlaylist();
      }

      function drawTopSection(json){
        d3.selectAll("#controls").datum(json).call(controls);
        d3.selectAll("#trackTime").datum(json).call(trackTime);
        d3.selectAll("#nowPlaying").datum(json).call(currentTrack);
        d3.selectAll("#volume").datum(json).call(volume);
      }

      function drawBottomSection(){
          var catalogueRenderer = catalogue.onPlay(playerControl);
          d3.selectAll("#tracks").datum({"listActionUrl": "list"}).call(catalogueRenderer);
      }

      function drawPlaylist(){
        d3.json("playlist", function(error, json){
          if (error) return console.warn(error);
	      renderPlaylist(json);
        });
      }

      function renderPlaylist(listData){
          d3.selectAll("#playlist").datum(listData).call(playlist);
      }

      function setupEventListener(){
          console.log("About to setup the event listener");
          var source = new EventSource("play/events");
          source.onError = function(e){
            console.log("Error in event source "+e);
          }
          source.addEventListener('player-state-change', function(event){
              var json = JSON.parse(event.data);
              drawTopSection(json);
          });
          source.addEventListener('playlist-change', function(event){
              var json = JSON.parse(event.data);
              renderPlaylist(json);
          });
      }
  }
});
