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
            svg = buttonSection.append("svg")
                                .attr("width", iconWidth)
                                .attr("height", iconHeight)
                                .attr("class", iconClass)
                                .on("click", onClickHandler);
            svg.append("path")
                .attr("d", iconPath);

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