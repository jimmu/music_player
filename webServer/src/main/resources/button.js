define(["d3.v3.min"]
,
function(d3) {

    var iconPath = "";
    var iconWidth = 32;
    var iconHeight = 32;
    var iconClass = function(){return ""};
    var onClickHandler = function(){console.log("Clicked!")};

    function renderButton(selection){
        selection.each(function(d,i){
            var buttonSection = d3.select(this);
            var svg=buttonSection.select("svg");
            //if (svg.empty()){
                svg = buttonSection.append("svg")
                                    .attr("width", iconWidth)
                                    .attr("height", iconHeight);
            //}
            var lineFunction = d3.svg.line()
                                .x(function(d) { return d.x; })
                                .y(function(d) { return d.y; })
                                .interpolate("linear");
            var lineGraph = svg.append("path")
                                .attr("d", lineFunction(iconPath));
            svg.on("click", onClickHandler);
            svg.attr("class", iconClass);
        });
    }

    renderButton.path=function(value){
        if (!arguments.length) return iconPath;
        iconPath = value;
        return renderButton;
    }
    renderButton.width=function(value){
        if (!arguments.length) return iconWidth;
        iconWidth = value;
        return renderButton;
    }
    renderButton.height=function(value){
        if (!arguments.length) return iconHeight;
        iconHeight = value;
        return renderButton;
    }
    renderButton.classed=function(value){
        if (!arguments.length) return iconClass;
        iconClass = value;
        return renderButton;
    }
    renderButton.onClick=function(value){
        if (!arguments.length) return onClickHandler;
        onClickHandler = value;
        return renderButton;
    }
    return renderButton;
});