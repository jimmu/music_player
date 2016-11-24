define(["d3.v3.min", "progressBar"]
,
function(d3, progressBar) {

      var originalSelection;

      var onClickHandler = function(url){
	d3.json(url, function(error, json){
	  if (error) return console.warn(error);
	    originalSelection.datum(json);
	    renderVolume(originalSelection);
	});
      }

      function renderVolume(selection){
	var barRenderer = progressBar()
			  .getValue(function(d){return d.volume})
			  .maxValue(100)
			  .getUrl(function(d){return d.volumeUrl})
			  .classFilledBar("volumeBarFilled")
			  .classUnfilledBar("volumeBarUnfilled")
			  .width(150)
			  .height(16);
			  
	originalSelection = selection;
	selection.each(function(data,i){
	  var volumeSection = d3.select(this);

	  volumeSection.call(barRenderer);

	  //TODO - Make this update/enter stuff a bit cleaner.
	  var plusMinusButtons = volumeSection.selectAll("span");
	  if (plusMinusButtons.empty()){
	    volumeSection.insert("span").classed("volumeUpDown", true).text(" - ")
		.on("click", (function(d){
		  onClickHandler(d.volumeDownUrl);
		}));

	    volumeSection.append("span").classed("volumeUpDown", true).text(" + ")
		.on("click", (function(d){
		  onClickHandler(d.volumeUpUrl);
		}));
          }
	  else {
	    plusMinusButtons.data([{"url": data.volumeDownUrl, "label":"-"},
				   {"url": data.volumeUpUrl, "label":"+"}])
		.on("click", (function(d){
		  onClickHandler(d.url);
		}));
	  }


	  });

      }

    return renderVolume;
});
