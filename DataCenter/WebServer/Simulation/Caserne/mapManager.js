function request1(){
  $.ajax({
    url:"ajax_capteur.php",
    type:"GET",
    dataType:"json",
    crossDomain:true,
    success:function (data,status) {
      addCapteur(data)
    },
    error : function(resultat, status, erreur){
           console.log(resultat)
    },
    complete : function(resultat, statut){
                setTimeout(request1,5000);
    }
  })
}

function addCapteur(data){
  for(var i=0; i<data.length; i++){
    addOneCapt(data[i].x, data[i].y, data[i].valeur)
  }
}

function addOneCapt(x,y,val){
  if (val == 0) {
      var circle = L.circle([x, y], {
          color: 'bleu',
          fillColor: '#30f',
          fillOpacity: 0.5,
          radius: 100
      });
      circle.on('mouseover',function(e) {        
          circle.setStyle({color:"#0000ff", weight:3, fillColor:"#00ff00"});        
      });
      circle.on('mouseout',function(e) {        
          circle.setStyle({fillColor:"#30f"});        
      });
      var mes = "<p> X : ";
      mes = mes.concat(x);
      mes = mes.concat("</p><p>Y :");
      mes = mes.concat(y);
      mes = mes.concat("</p><p>Valeur :");
      mes = mes.concat(val);
      mes = mes.concat("</p>");
      circle.bindPopup(mes);
      circle.addTo(mymap);
  }else{
      var circle = L.circle([x, y], {
          color: 'red',
          fillColor: '#f03',
          fillOpacity: 0.5,
          radius: 100
      });
      circle.on('mouseover', function(e) {        
          circle.setStyle({color:"#ff0000", weight:3, fillColor:"#00ff00"});        
      });
      circle.on('mouseout',function(e) {        
          circle.setStyle({fillColor:"#f03"});        
      });
      var mes = "<p> X : ";
      mes = mes.concat(x);
      mes = mes.concat("</p><p>Y :");
      mes = mes.concat(y);
      mes = mes.concat("</p><p>Valeur :");
      mes = mes.concat(val);
      mes = mes.concat("</p>");
      circle.bindPopup(mes);
      circle.addTo(mymap);
  }
  
}

function request2(){
  $.ajax({
    url:"ajax_camion.php",
    type:"GET",
    dataType:"json",
    crossDomain:true,
    success:function (data,status) {
      addCamion(data)
    },
    error : function(resultat, status, erreur){
           console.log(resultat)
    },
    complete : function(resultat, statut){
                setTimeout(request2,5000);
    }
  })
}

function addCamion(data){
  for(var i=0; i<data.length; i++){
    addOneCam(data[i].x, data[i].y, data[i].capacite)
  }
}

function addOneCam(x,y,capacite){
  var circle = L.circle([x, y], {
      color: '#FF00E3',
      fillColor: '#FF00E3',
      fillOpacity: 1,
      radius: 25
  });
  circle.on('mouseover',function(e) {        
      circle.setStyle({color:"#9200FF", weight:3, fillColor:"#FF00E3"});        
  });
  circle.on('mouseout',function(e) {        
      circle.setStyle({fillColor:"#FF00E3"});        
  });
  var mes = "<p> X : ";
  mes = mes.concat(x);
  mes = mes.concat("</p><p>Y :");
  mes = mes.concat(y);
  mes = mes.concat("</p><p>Capacite :");
  mes = mes.concat(capacite);
  mes = mes.concat("</p>");
  circle.bindPopup(mes);
  circle.addTo(mymap);
  
}

var xMinGPS= 45.7131374;
var xMaxGPS = 45.7917481;
var yMinGPS = 4.80287;
var yMaxGPS = 4.9200286;

var xCenterMapGPS=(xMaxGPS+xMinGPS)/2
var yCenterMapGPS=(yMaxGPS+yMinGPS)/2


var bounds = [
    [xMinGPS,yMinGPS],
    [xMaxGPS, yMaxGPS]
  ];

var mymap = L.map('mapid', {
    center: [xCenterMapGPS,yCenterMapGPS],
    zoom: 14,
    zoomControl:true
});
L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
    maxZoom: 17,
    minZoom:14,
    id: 'mapbox/streets-v11',
    accessToken: 'pk.eyJ1IjoicGlxdWFyZGoiLCJhIjoiY2s0ZmNjczlwMGwxcTNkbjlvZ3A0ZGQ1ZSJ9.liw9xxDqFwX4VL54ZFkWRA'
}).addTo(mymap);

mymap.fitBounds(bounds);
mymap.setMaxBounds(bounds);

/*var mymap = L.map('mapid', {
    center: [xCenterMapGPS,yCenterMapGPS],
    zoom: 14,
    zoomControl:true
});

var map = L.map('map');
var osmUrl='http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
var osmAttrib='Map data © OpenStreetMap contributors';
var osm = new L.TileLayer(osmUrl, {attribution: osmAttrib, maxZoom: 17, minZoom:14});
map.addLayer(osm);
map.fitBounds(bounds);
map.setMaxBounds(bounds);*/

request1()
request2()