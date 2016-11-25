define(["d3.v3.min", "button"]
,
function(d3, button) {
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

              var playPath  = "M 15 15 L 5 25  L 5 5   L 15 15";
              var ffwPath   = "M 15 15 L 5 25  L 5 5   L 15 15   M 28 15 L 18 25 L 18 5  L 28 15";
              var rwPath    = "M 18 15 L 28 25 L 28 5  L 18 15   M 15 5  L 5 15  L 15 25 L 15 5";
              var stopPath  = "M 5  5  L 25 5  L 25 25 L 5 25 L 5 5";
              var pausePath = "M 5  5  L 5 25  M 10 25 L 10 5";

              var buttonRenderer = button.width(40)
                                         .height(30);
              buttonRenderer.path(rwPath)
                            .onClick(function(d){onPreviousHandler(d.previousTrackActionUrl)})
                            .classed(function(){return "playerButton previousButton"});
              controlsSection.call(buttonRenderer);

              buttonRenderer.path(playPath)
                            .onClick(function(d){onPlayHandler(d.playActionUrl);})
                            .classed(function(d){return "playerButton" + (d.isPlaying? "" : " playButton")});
              controlsSection.call(buttonRenderer);

              buttonRenderer.path(pausePath)
                            .onClick(function(d){onPauseHandler(d.pauseActionUrl)})
                            .classed(function(d){return "playerButton" + (d.isPlaying? " pauseButton" : "")});
              controlsSection.call(buttonRenderer);

              buttonRenderer.path(stopPath)
                            .onClick(function(d){onStopHandler(d.stopActionUrl)})
                            .classed(function(d){return "playerButton" + (d.isPlaying? " stopButton" : "")});
              controlsSection.call(buttonRenderer);

              buttonRenderer.path(ffwPath)
                            .onClick(function(d){onNextHandler(d.nextTrackActionUrl)})
                            .classed(function(){return "playerButton nextButton"});
              controlsSection.call(buttonRenderer);

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
