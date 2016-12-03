define(["d3.v3.min", "button"]
,
function(d3, button) {
      var onClickHandler = function(url){
        console.log("clicked on "+url)
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
                            .onClick(function(d){onClickHandler(d.previousTrackActionUrl)})
                            .classed(function(){return "playerButton previousButton"});
              controlsSection.call(buttonRenderer);

              buttonRenderer.path(playPath)
                            .onClick(function(d){onClickHandler(d.playActionUrl);})
                            .classed(function(d){return "playerButton" + (d.isPlaying? "" : " playButton")})
                            .width(30);
              controlsSection.call(buttonRenderer);

              buttonRenderer.path(pausePath)
                            .onClick(function(d){onClickHandler(d.pauseActionUrl)})
                            .classed(function(d){return "playerButton" + (d.isPlaying? " pauseButton" : "")});
              controlsSection.call(buttonRenderer);

              buttonRenderer.path(stopPath)
                            .onClick(function(d){onClickHandler(d.stopActionUrl)})
                            .classed(function(d){return "playerButton" + (d.isPlaying? " stopButton" : "")})
                            .width(40);
              controlsSection.call(buttonRenderer);

              buttonRenderer.path(ffwPath)
                            .onClick(function(d){onClickHandler(d.nextTrackActionUrl)})
                            .classed(function(){return "playerButton nextButton"});
              controlsSection.call(buttonRenderer);

          });
      }

      renderControls.onClick=function(value){
        if (!arguments.length) return onClickHandler;
        onClickHandler = value
        return renderControls;
      }

      return renderControls;
});
