define(["d3.v3.min"
       ]
,
function(d3) {

      return function(json){
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

          function formatTime(seconds){
            var someDate = new Date(2016, 1, 1);
            var unixEpochStyle = +someDate;
            var withOurSeconds = unixEpochStyle+(seconds*1000);
            var formatter = d3.time.format("%M:%S");
            return formatter(new Date(withOurSeconds));
          }
      }
});
