function windowInit() {
  var scrWidth = document.documentElement.clientWidth;
  var scrHeight = document.documentElement.clientHeight;

  var tmpWidth = scrWidth * 5 / 16;
  var obj = document.getElementById("mygrap");
  obj.style.width = tmpWidth + 'px';
  obj.style.height = tmpWidth + 'px';

  var obj = document.getElementById("mygrapg");
  obj.style.width = tmpWidth + 'px';
  obj.style.height = tmpWidth + 'px';

  var tmpWidth = scrWidth * 21 / 80;
  var tmpHeight = scrHeight - (scrWidth * 27 / 80);
  var obj = document.getElementById("mylink");
  obj.style.left = (scrWidth / 40) + 'px';
  obj.style.top = (scrWidth * 27 / 80) + 'px';
  obj.style.width = tmpWidth + 'px';
  //obj.style.height = tmpHeight + 'px';

  var tmpWidth = scrWidth * 45 / 80;
  var obj = document.getElementById("mybody");
  obj.style.left = (scrWidth * 27 / 80) + 'px';
  obj.style.top = ((scrWidth * 7 / 16) * (14 / 35)) + 'px';
  obj.style.width = tmpWidth + 'px';

  var tmpWidth = scrWidth * 7 / 16;
  var tmpHeight = tmpWidth * 12 / 35;

  var obj = document.getElementById("myname");
  obj.style.left = (scrWidth * 5 / 16) + 'px';
  obj.style.top = '0px';
  obj.style.width = tmpWidth + 'px';
  obj.style.height = tmpHeight + 'px';

  var obj = document.getElementById("mynameg");
  //obj.style.left = (scrWidth * 5 / 16) + 'px';
  //obj.style.top = '0px';
  obj.style.width = tmpWidth + 'px';
  obj.style.height = tmpHeight + 'px';

}

window.onload = function() {
  windowInit();
  var ttt = document.body.scrollHeight;
  //window.alert("" + ttt);
}

window.onresize = function() {
  windowInit();
}
