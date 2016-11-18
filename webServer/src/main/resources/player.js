define(["d3.v3.min"
        ,"catalogue"
	,"trackTime"
        ,"track"
        ,"volume"
        ,"controls"
       ]
,
function(d3, catalogue, trackTime, currentTrack, volume, controls) {
  return function(url, containerNode){
      function setup(){
	  // There may already be music playing. If so, find out and show it.
          controls.onPlay(playerControl)
		.onPause(playerControl)
		.onStop(playerControl)
		.onNext(playerControl)
		.onPrevious(playerControl);

	  d3.json("play/status", function(error, json){
	      if (error) return console.warn(error);
	      currentTrack(json);
	      controls(json);
	      trackTime(json);
	      volume(json);
	  });
          catalogue.onPlay(playerControl)(url, containerNode);
	  //Now set up push event listener.
	  console.log("About to set up event source");
	  var source = new EventSource("play/events");
	  source.addEventListener('player-state-change', function(event){
	      var json = JSON.parse(event.data);
	      //console.log(JSON.stringify(json));
	      currentTrack(json);
	      controls(json);
	      trackTime(json);
	      volume(json);
	  });


      function playerControl(url){
	  d3.json(url, function(error, json){
	      if (error) return console.warn(error);
	      controls(json);
	      trackTime(json);
	      currentTrack(json);
	      volume(json);
	  });
      }

    }
    return setup;
  }
});
