function hello() {
  d3.json("files", function(error, json){
    if (error) return console.warn(error);

    d3.select("body").selectAll("div").data(json)
      .enter()
      .append("div")
      .on("click", (function(d){
        getStuff(d3.select(this), d);
      }))
      .text(function(d){
        return d.name;
      })
  });
}

function getStuff(selection, d){
  d3.json(d.listActionUrl, function(error, json){
    if (error) return console.warn(error);

    console.log(json);
    var me = selection;
    me.attr("color", "red");

    me.selectAll("div").data(json)
      .enter()
      .append("div")
      .on("click", (function(d){
        getStuff(d3.select(this), d);
      }))
      .text(function(d){
        return d.name;
      })
  });
}
