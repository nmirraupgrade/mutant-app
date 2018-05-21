# mutant-app
Application that analyzes DNA sequences and determines if someone is mutant or not 

In order to execute this app you should follow the instructions depending on the scenario

1) To know if a DNA belongs to a mutant or not

Make a POST call to the following endpoint: http://18.228.26.35:8080/mutant, you can use a tool to make this call such as Postman (https://www.getpostman.com/). The call should include a JSON formatted body. For example:

POST → /mutant/
{
“dna”:["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}

If the DNA belongs to a mutant, you'll get a HTTP OK (200), but if the DNA belongs to a human, you'll get a HTTP FORBIDDEN (403).

2) To get the stats

Make a GET call to the following endpoint: http://18.228.26.35:8080/stats, you can use a tool to make this call or just use a web browser and go to this address http://18.228.26.35:8080/stats. You'll get a JSON formatted response that will look like this:

{
    "countMutantDna": 3,
    "countHumanDna": 10,
    "ratio": 0.3
}

Español:

Para poder ejecutar esta applicacion debera seguir los pasos, dependiendo del escenario

1) Para saber si un ADN pertenece a un mutante

Realizar una llamada de tipo POST al siguiente endpoint: http://18.228.26.35:8080/mutant, se puede utilizar una applicacion similar a Postman (https://www.getpostman.com/). La llamada debera incluir un JSON como body. Por ejemplo:

POST → /mutant/
{
“dna”:["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}

Si el ADN pertenece a un mutante, la respuesta del servicio sera un HTTP OK (200), pero si el ADN pertecene a un humano, obtendra un HTTP FORBIDDEN (403).

2) Para obtener las estadisticas

Realizar una llamada del tipo GET al siguiente endpoint: http://18.228.26.35:8080/stats, se puede utilizar una aplicacion o un web browser para realizar esta llamada, ingresando http://18.228.26.35:8080/stats como url. La respuesta sera un JSON con el siguiente formato:

{
    "countMutantDna": 3,
    "countHumanDna": 10,
    "ratio": 0.3
}
