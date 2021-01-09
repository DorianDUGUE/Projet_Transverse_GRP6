$(document).ready(function(){
    $.ajax({
        url: 'ajax_camion.php?q=*',
        type: 'get',
        dataType: 'JSON',
        success: function(response){
            incomingJSON = JSON.parse(response);
            var len = incomingJSON.length;
            for(var i=0; i<len; i++){
                var id = incomingJSON[i].id;
                var x = incomingJSON[i].x;
                var y = incomingJSON[i].y;
                var capacite = incomingJSON[i].capacite;

                var tr_str = "<tr>" +
                    "<td align='center'>" + id + "</td>" +
                    "<td align='center'>" + x + "</td>" +
                    "<td align='center'>" + y + "</td>" +
                    "<td align='center'>" + capacite + "</td>" +
                    "</tr>";


                $("#userTable2 tbody").append(tr_str);
            }

        },
        error : function(resultat, status, erreur){
               alert(resultat);
        }
    });
});