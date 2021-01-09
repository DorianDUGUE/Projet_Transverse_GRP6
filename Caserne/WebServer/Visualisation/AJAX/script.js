$(document).ready(function(){
    $.ajax({
        url: 'ajax.php?q=*',
        type: 'get',
        dataType: 'JSON',
        success: function(response){
            incomingJSON = JSON.parse(response);
            var len = incomingJSON.length;
            for(var i=0; i<len; i++){
                var id = incomingJSON[i].id;
                var x = incomingJSON[i].x;
                var y = incomingJSON[i].y;
                var valeur = incomingJSON[i].valeur;

                var tr_str = "<tr>" +
                    "<td align='center'>" + id + "</td>" +
                    "<td align='center'>" + x + "</td>" +
                    "<td align='center'>" + y + "</td>" +
                    "<td align='center'>" + valeur + "</td>" +
                    "</tr>";


                $("#userTable tbody").append(tr_str);
            }

        },
        error : function(resultat, status, erreur){
               alert(resultat);
        }
    });
});