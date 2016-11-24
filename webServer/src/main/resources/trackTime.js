define(["d3.v3.min", "timeFormat", "progressBar"]
,
function(d3, timeFormat, progressBar) {

      var originalSelection;

      var onClickHandler = function(url){
        d3.json(url, function(error, json){
          if (error) return console.warn(error);
            originalSelection.datum(json);
            renderTrackTime(originalSelection);
        });
      }

      function renderTrackTime(selection){
        originalSelection = selection;
        selection.each(function(data,i){
          var barRenderer = progressBar()
		  .getValue(function(d){return d.elapsedTime})
		  .maxValue(data.songLength)
		  .getUrl(function(d){return d.seekPositionUrl})
		  .classFilledBar("trackTimeBarFilled")
		  .classUnfilledBar("trackTimeBarUnfilled")
		  .width(100)
		  .height(16);

          var timeSection = d3.select(this);
	  timeSection.call(barRenderer);

	  //TODO - Make this update/enter stuff a bit cleaner.
          var clocks = timeSection.selectAll("span");
          if (clocks.empty()){
            timeSection.insert("span").classed("elapsedTime", true)
		.text(function(d){return timeFormat(d.elapsedTime)});

            timeSection.append("span").classed("trackLength", true)
		.text(function(d){return timeFormat(Math.max(0, data.songLength-data.elapsedTime))});
          }
          else {
            clocks.data([{"time": data.elapsedTime},
                         {"time": Math.max(0, data.songLength-data.elapsedTime)}])
	    .text(function(d){return timeFormat(d.time)});
          }


          });
      };

      return renderTrackTime;
});
