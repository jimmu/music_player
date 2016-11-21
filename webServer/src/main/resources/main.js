function main(){
  require.config({baseUrl: "gui"});
  require(["player"]
  ,
  function(playerThing){
    playerThing("list")();
  });
}
