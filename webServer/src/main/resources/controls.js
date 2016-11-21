define(["d3.v3.min"
       ]
,
function(d3) {
      var onPlayHandler = function(url){
        console.log("Clicked on "+url);
      }
      var onStopHandler = function(url){
        console.log("Clicked on "+url);
      }
      var onPauseHandler = function(url){
        console.log("Clicked on "+url);
      }
      var onNextHandler = function(url){
        console.log("Clicked on "+url);
      }
      var onPreviousHandler = function(url){
        console.log("Clicked on "+url);
      }

      function renderControls(selection) {
          selection.each(function(data,i){
              var controlsSection = d3.select(this);
              controlsSection.html("");
              controlsSection.append("span")
                      .classed("previousButton", true)
                      .text("Previous")
                      .on("click", (function(d){
                          onPreviousHandler(d.previousTrackActionUrl);
                      }));
              controlsSection.append("span")
                      .classed("playButton", function(d){return !d.isPlaying})
                      .text("Play")
                      .on("click", (function(d){
                          onPlayHandler(d.playActionUrl);
                      }));
              controlsSection.append("span")
                      .classed("pauseButton", function(d){return d.isPlaying})
                      .text("Pause")
                      .on("click", (function(d){
                          onPauseHandler(d.pauseActionUrl);
                      }));
              controlsSection.append("span")
                      .classed("stopButton", function(d){return d.isPlaying})
                      .text("Stop")
                      .on("click", (function(d){
                          onStopHandler(d.stopActionUrl);
                      }));
              controlsSection.append("span")
                      .classed("nextButton", true)
                      .text("Next")
                      .on("click", (function(d){
                          onNextHandler(d.nextTrackActionUrl);
                      }));
              });
      }

      renderControls.onPlay=function(value){
        if (!arguments.length) return onPlayHandler;
        onPlayHandler = value
        return renderControls;
      }
      renderControls.onStop=function(value){
        if (!arguments.length) return onStopHandler;
        onStopHandler = value
        return renderControls;
      }
      renderControls.onPause=function(value){
        if (!arguments.length) return onPauseHandler;
        onPauseHandler = value
        return renderControls;
      }
      renderControls.onNext=function(value){
        if (!arguments.length) return onNextHandler;
        onNextHandler = value
        return renderControls;
      }
      renderControls.onPrevious=function(value){
        if (!arguments.length) return onPreviousHandler;
        onPreviousHandler = value
        return renderControls;
      }

      return renderControls;
});
