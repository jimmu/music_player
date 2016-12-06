define(["d3.v3.min", "timeFormat", "progressBar"]
,
function(d3, timeFormat, progressBar) {

      var originalSelection;

      function renderTrackTime(selection){
        originalSelection = selection;
        selection.each(function(data,i){
          var barRenderer = progressBar()
		  .getValue(function(d){return d.elapsedTime})
		  .maxValue(data.songLength)
		  .getUrl(function(d){return d.seekPositionUrl})
		  .classFilledBar("trackTimeBarFilled")
		  .classUnfilledBar("trackTimeBarUnfilled")
		  .width(200)
		  .height(16);

          var timeSection = d3.select(this);
          timeSection.call(barRenderer);

          var clocks = timeSection.selectAll("span");
          if (clocks.empty()){
            timeSection.insert("span", ":first-child").classed("elapsedTime", true)
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
