define(["d3.v3.min", "timeFormat"]
,
function(d3, timeFormat) {

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
          var timeSection = d3.select(this);
          timeSection.html("");
          timeSection.append("span")
                  .classed("elapsedTime", true)
                  .text(function(d){return timeFormat(d.elapsedTime)});

          //Doing this with svg - overkill? DIVs would probably do fine.
          var barHeight = 16;
          var barWidth = 100;
          var svg = timeSection.append("svg").attr("width", barWidth).attr("height", barHeight);
          svg.append("rect").attr("x", 0).attr("y",0)
              .attr("width", function(d){return barWidth*d.elapsedTime/d.songLength})
              .attr("height", barHeight)
              .classed("trackTimeBarFilled", true);
          svg.append("rect").attr("x", function(d){return barWidth*d.elapsedTime/d.songLength})
              .attr("y",0)
              .attr("width", function(d){return barWidth*(Math.max(0, d.songLength-d.elapsedTime))/d.songLength})
              .attr("height", barHeight)
              .classed("trackTimeBarUnfilled", true);
          var trackPositionClickTarget = svg.append("rect").attr("x", 0)
                        .attr("y",0)
                        .attr("width", barWidth)
                        .attr("height", barHeight)
                        .attr("pointer-events", "all")
                        .attr("fill", "none"); //Invisible. Just to collect mouse clicks.

          timeSection.append("span")
                  .classed("trackLength", true)
                  .text(function(d){return timeFormat(Math.max(0, d.songLength-d.elapsedTime))});

          trackPositionClickTarget.on("click", (function(d){
                              var clickEvent = d3.event;
                              var clickTarget = d3.event.target;
                              var dimensions = clickTarget.getBoundingClientRect();
                              var clickX = d3.event.clientX - dimensions.left;
                              //Now convert clickX to something in the range 0-songLength
                              var clickedSeconds = Math.round(d.songLength*clickX/barWidth);
                              //And now call the set-track-position URL
                              onClickHandler(d.seekPositionUrl+clickedSeconds);
                           }));
          });
      };

      renderTrackTime.onClick=function(value){
          if (!arguments.length) return onClickHandler;
          onClickHandler = value
          return renderTrackTime;
        }

      return renderTrackTime;
});
