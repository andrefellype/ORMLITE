<?php

set_time_limit(2000);

header('Content-type:application/json');
$arquivo = fopen('dados.csv', 'r');
$x = 0;
$lista = array();

while (!feof($arquivo)) {
	
	$linha = fgets($arquivo, 2014);
    $x++;
	$dados = explode(',', $linha);
	if ($x >= 2) {
		if ($dados[0] != "") {
			$l  = array('UF'=>$dados[0],'CodUF'=>$dados[1],'CodMun'=>$dados[2],'NomeMunic'=>$dados[3],'Populacao'=>$dados[4]);
			array_push($lista, $l);
		}
    }
}
echo json_encode($lista);