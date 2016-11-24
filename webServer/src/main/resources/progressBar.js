define(["d3.v3.min"]
,
function(d3) {

    return function(){
	var originalSelection;

	var barWidth = 150;
	var barHeight = 16;
	var barMaxValue = 100;
	var filledBarClass = "barFilled";
	var unfilledBarClass = "barUnfilled";

	var valueGetter = function(d){
		return 10;
	}
	var urlGetter = function(d){
		return "/foo";
	}

	function renderBar(selection){
	  originalSelection = selection;
	  selection.each(function(dat,i){
	    var barSection = d3.select(this);
	    //Doing this with svg - overkill? DIVs would probably do fine.
	    var svg=barSection.select("svg");
            if (svg.empty()){
		svg = barSection.append("svg").attr("width", barWidth).attr("height", barHeight);
	    }
            var value = valueGetter(dat);
            var filledWidth = Math.min(barWidth, barWidth*value/barMaxValue);
	    var unfilledWidth = barWidth-filledWidth;
	    var rectangles=[
			{"x":0, "y":0, "width":filledWidth, "height":barHeight, "class":filledBarClass}
			,{"x":filledWidth, "y":0, "width":unfilledWidth, "height":barHeight, "class":unfilledBarClass}
			,{"x":0, "y":0, "width":100, "height":barHeight, events:"all", fill:"none"}
			];
	    var rects=svg.selectAll("rect").data(rectangles);
            rects.enter().append("rect") //.merge(rects)
		.attr("x", function(d){return d.x})
		.attr("y", function(d){return d.y})
		.attr("width", function(d){return d.width})
		.attr("height", function(d){return d.height})
                .attr("class", function(d){return d.class})
		.attr("width", function(d){return d.x})
		.attr("fill", function(d){return d.fill})
		.attr("pointer-events", function(d){return d.events})
		.on("click", function(d){barClicked(d, urlGetter(dat))});
	    rects.attr("width", function(d){return d.width})  //The update set. This is working!
		.attr("x", function(d){return d.x});

	     function barClicked(d, url){
				var clickEvent = d3.event;
				var clickTarget = d3.event.target;
				var dimensions = clickTarget.getBoundingClientRect();
				var clickX = d.x + d3.event.clientX - dimensions.left;
				//Now convert clickX to something in the range 0-barMaxValue
				var clickedValue = Math.round(barMaxValue*clickX/barWidth);
				//And now call the related URL
				d3.json(url+clickedValue, function(error, json){
				  if (error) return console.warn(error);
				    originalSelection.datum(json);
				    renderBar(originalSelection);
				});
			     };

	    });

	}

	renderBar.getValue=function(value){
	  if (!arguments.length) return valueGetter;
	  valueGetter = value
	  return renderBar;
	}
	renderBar.getUrl=function(value){
	  if (!arguments.length) return urlGetter;
	  urlGetter = value
	  return renderBar;
	}
	renderBar.classFilledBar=function(value){
	  if (!arguments.length) return filledBarClass;
	  filledBarClass = value
	  return renderBar;
	}
	renderBar.classUnfilledBar=function(value){
	  if (!arguments.length) return unfilledBarClass;
	  unfilledBarClass = value
	  return renderBar;
	}
	renderBar.width=function(value){
	  if (!arguments.length) return barWidth;
	  barWidth = value
	  return renderBar;
	}
	renderBar.height=function(value){
	  if (!arguments.length) return barHeight;
	  barHeight = value
	  return renderBar;
	}
	renderBar.maxValue=function(value){
	  if (!arguments.length) return barMaxValue;
	  barMaxValue = value
	  return renderBar;
	}

      return renderBar;
    }
});
