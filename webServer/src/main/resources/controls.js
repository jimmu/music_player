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

              var playPath = [{"x": 15,  "y": 15},
                              {"x": 5,  "y": 25}, {"x": 5,   "y": 5},
                              {"x": 15,  "y": 15}];
              var ffwPath = [{"x": 5,   "y": 5},  {"x": 15,  "y": 15},
                             {"x": 5,   "y": 25}, {"x": 5,   "y": 5},
                             {"x": 15,  "y": 15},
                             {"x": 15,  "y": 5},  {"x": 25,  "y": 15},
                             {"x": 15,   "y": 25},{"x": 15,  "y": 5}];
              var rwPath =  [{"x": 15,   "y": 15},  {"x": 25,  "y": 25},
                             {"x": 25,   "y": 5}, {"x": 15,   "y": 15},
                             {"x": 15,  "y": 5},  {"x": 5,  "y": 15},
                             {"x": 15,   "y": 25},{"x": 15,  "y": 15}];
              var stopPath =  [{"x": 5,   "y": 5},  {"x": 25,  "y": 5},
                             {"x": 25,   "y": 25}, {"x": 5,   "y": 25},
                             {"x": 5,  "y": 5}];
              var pausePath = [{"x": 5,   "y": 5},  {"x": 5,  "y": 25},
                             {"x": 15,   "y": 25}, {"x": 15,   "y": 5}];
              var buttonRenderer = button.width(40)
                                         .height(30);
              buttonRenderer.path(rwPath)
                            .onClick(function(d){onPreviousHandler(d.previousTrackActionUrl)})
                            .classed("previousButton", function(){return true});
              controlsSection.call(buttonRenderer);

              buttonRenderer.path(playPath)
                            .onClick(function(d){onPlayHandler(d.playActionUrl);})
                            .classed("playButton", function(d){return !d.isPlaying});
              controlsSection.call(buttonRenderer);

              buttonRenderer.path(pausePath)
                            .onClick(function(d){onPauseHandler(d.pauseActionUrl)})
                            .classed("pauseButton", function(d){return d.isPlaying})
              controlsSection.call(buttonRenderer);

              buttonRenderer.path(stopPath)
                            .onClick(function(d){onStopHandler(d.stopActionUrl)})
                            .classed("stopButton", function(d){return d.isPlaying});
              controlsSection.call(buttonRenderer);

              buttonRenderer.path(ffwPath)
                            .onClick(function(d){onNextHandler(d.nextTrackActionUrl)})
                            .classed("nextButton", function(){return true});
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
