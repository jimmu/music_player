define(["d3.v3.min"]
,
function(d3) {
      var onPlayHandler = function(url){
        console.log("Clicked play on "+url);
      }

      function playlist(selection){
        var listData = selection.datum();
        console.log("Playlist: "+JSON.stringify(listData));
        var currentTrackIndex = listData.currentTrackNumber;
        selection.each(function(data,i){
          var plist = d3.select(this)
            .selectAll("div")
            .data(listData.trackList);
          plist.enter()
            .append("div")
            .classed("playlistSong", true)
            .classed("currentTrack", function(d,i){return i==currentTrackIndex})
            .classed("futureTrack", function(d,i){return i>currentTrackIndex})
            .classed("pastTrack", function(d,i){return i<currentTrackIndex})
            .text(function(d){return d.name});
          plist.text(function(d){return d.name})   // The update section.
            .classed("currentTrack", function(d,i){return i==currentTrackIndex})
            .classed("futureTrack", function(d,i){return i>currentTrackIndex})
            .classed("pastTrack", function(d,i){return i<currentTrackIndex});
          plist.exit().remove();
        });
      }

      return playlist;
});
