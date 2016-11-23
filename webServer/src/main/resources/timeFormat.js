define(["d3.v3.min"]
,
function(d3) {

   return function(seconds){
      var someDate = new Date(2016, 1, 1);
      var unixEpochStyle = +someDate;
      var withOurSeconds = unixEpochStyle+(seconds*1000);
      var formatter = d3.time.format("%M:%S");
      return formatter(new Date(withOurSeconds));
   }
});
