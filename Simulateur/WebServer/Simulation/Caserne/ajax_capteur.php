<?php

  try {
      $dbh = new PDO('mysql:dbname=DBCaserne;host=127.0.0.1' , 'simu', 'simu');
  } catch (PDOException $e) {
      echo 'Connexion échouée : ' . $e->getMessage();
  }
   
  $return_arr = array();

/*  $arg = mysql_real_escape_string($_GET['arg']);
  if ($arg = ""){
    $arg = '*';
  }*/

  $sql = "SELECT * FROM Capteur";
  
  $nb = 0;

  foreach  ($dbh->query($sql) as $row) {
    $nb = $nb + 1 ;
    $id = $row['id'];
    $x  = $row['x'];
    $y  = $row['y'];
    $valeur = $row['valeur'];

    $return_arr[] = array(
                    $nb => $nb,
                    "id" => $id,
                    "x" => $x,
                    "y" => $y,
                    "valeur" => $valeur
                    );

  }
  // Encoding array in JSON format
  echo json_encode($return_arr);
   
  $dbh = null;

?>