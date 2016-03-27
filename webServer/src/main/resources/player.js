function hello(url, thingToSelect) {
  d3.json(url, function(error, json){
    if (error) return console.warn(error);

    var thisRow =
    d3.select(thingToSelect)
    .selectAll("div").data(json)
      .enter()
      .append("div")
      .classed("file", function(d){return d.isLeaf})
      .classed("directory", function(d){return !d.isLeaf});

    thisRow.on("click", (function(d){
        d3.event.stopPropagation();
        if (d.listActionUrl){
          hello(d.listActionUrl, this);
        }
      }));

    thisRow.append("span").text(function(d){
        return d.name;
      });

    thisRow.append("span").text(" - play")
      .on("click", (function(d){
           d3.event.stopPropagation();
           if (d.playActionUrl){
             play(d.playActionUrl);
           }
         }));
  });
}

function play(url){
  d3.json(url, function(error, json){
    if (error) return console.warn(error);
    console.log(json);
    });
}