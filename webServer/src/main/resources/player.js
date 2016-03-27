function hello(url, thingToSelect) {
  d3.json(url, function(error, json){
    if (error) return console.warn(error);

    d3.select(thingToSelect)
    .selectAll("div").data(json)
      .enter()
      .append("div")
      .classed("file", function(d){return d.isLeaf})
      .classed("directory", function(d){return !d.isLeaf})
      .on("click", (function(d){
        d3.event.stopPropagation();
        hello(d.listActionUrl, this);
      }))
      .text(function(d){
        return d.name;
      })
  });
}
