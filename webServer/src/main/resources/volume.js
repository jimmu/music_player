define(["d3.v3.min"]
,
function(d3) {

      var originalSelection;

      var onClickHandler = function(url){
        d3.json(url, function(error, json){
          if (error) return console.warn(error);
            originalSelection.datum(json);
            renderVolume(originalSelection);
        });
      }

      function renderVolume(selection){
        originalSelection = selection;
        selection.each(function(data,i){
          var volumeSection = d3.select(this);
          volumeSection.html("");
          volumeSection.append("span").classed("volumeUpDown", true).text(" - ")
              .on("click", (function(d){
                onClickHandler(d.volumeDownUrl);
              }));
          //volumeSection.append("span").classed("volume", true).text(function(d){return d.volume})

            //Doing this with svg - overkill? DIVs would probably do fine.
          var barHeight = 16;
          var barWidth = 150;
          var maxVolume = 100;
          var svg = volumeSection.append("svg").attr("width", barWidth).attr("height", barHeight);
            svg.append("rect").attr("x", 0).attr("y",0)
            .attr("width", function(d){return barWidth*d.volume/maxVolume})
            .attr("height", barHeight)
            .classed("volumeBarFilled", true);
            svg.append("rect").attr("x", function(d){return barWidth*d.volume/maxVolume})
            .attr("y",0)
            .attr("width", function(d){return barWidth*(Math.max(0, maxVolume-d.volume))/maxVolume})
            .attr("height", barHeight)
            .classed("volumeBarUnfilled", true);
            var volumeClickTarget = svg.append("rect").attr("x", 0)
                        .attr("y",0)
                        .attr("width", barWidth)
                        .attr("height", barHeight)
                        .attr("pointer-events", "all")
                        .attr("fill", "none"); //Invisible. Just to collect mouse clicks.

          volumeSection.append("span").classed("volumeUpDown", true).text(" + ")
              .on("click", (function(d){
                                onClickHandler(d.volumeUpUrl);
                              }));

          volumeClickTarget.on("click", (function(d){
                              var clickEvent = d3.event;
                              var clickTarget = d3.event.target;
                              var dimensions = clickTarget.getBoundingClientRect();
                              var clickX = d3.event.clientX - dimensions.left;
                              //Now convert clickX to something in the range 0-maxVolume
                              var clickedVolume = Math.round(maxVolume*clickX/barWidth);
                              //And now call the set-volume URL
                              onClickHandler(d.volumeUrl+clickedVolume);
                           }));

          });

      }

    renderVolume.onClick=function(value){
        if (!arguments.length) return onClickHandler;
        onClickHandler = value
        return renderVolume;
      }

    return renderVolume;
});
