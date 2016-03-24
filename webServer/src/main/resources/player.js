function hello() {
  d3.json("files", function(error, json){
    if (error) return console.warn(error);

    d3.select("body").selectAll("div").data(json)
      .enter()
      .append("div").text(function(d){
        return d.name;
      });
  });
}
