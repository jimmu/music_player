define(["d3.v3.min"
        ,"catalogue"
	    ,"trackTime"
        ,"track"
        ,"volume"
        ,"controls"
       ]
,
function(d3, catalogue, trackTime, currentTrack, volume, controls) {
  return function(){
      controls.onPlay(playerControl)
	  .onPause(playerControl)
	  .onStop(playerControl)
	  .onNext(playerControl)
	  .onPrevious(playerControl);

	  // There may already be music playing. If so, find out and show it.
	  d3.json("play/status", function(error, json){
	      if (error) return console.warn(error);
          drawTopSection(json);
      });
      drawBottomSection();
	  setupEventListener();

      function playerControl(url){
          d3.json(url, function(error, json){
              if (error) return console.warn(error);
              drawTopSection(json);
          });
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

      function setupEventListener(){
          var source = new EventSource("play/events");
          source.addEventListener('player-state-change', function(event){
              var json = JSON.parse(event.data);
              drawTopSection(json);
          });
      }
  }
});
