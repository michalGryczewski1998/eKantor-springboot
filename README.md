<h2>Opis projektu</h2>
<hr>
<h2>Program oblicza kursy krzyżowe dla walut które posiada European Central Bank</h2>
<h2>Dane są pobierane do bazy H2 o godzinie 16:10 z pliku XML udostępnionego przez ECB - https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml</h2>
<Za pomocą RequestMapping podajemy convert/walutaBazowa/WalutaDocelowa/data, w odpowiedzi dostajemy obliczony aktualny kurs</h2>
<img src="https://github.com/michalGryczewski1998/projektowanie-serwisow-www-21712-185ic/blob/main/Lab_3%20Obs%C5%82uga%20zdarze%C5%84/Photos/Zrzut%20ekranu%202021-06-25%20000226.jpg" a>
<hr>
<h2>Klasa Download</h2>
<img src="https://github.com/michalGryczewski1998/mgryczewski/blob/main/Zdj%C4%99cia%20kodu/Zrzut%20ekranu%202021-06-29%20145804.jpg?raw=true" a>
<h2>
Ta klasa służy do pobierania i dodawania danych do bazy danych. 
Klasa ta zawiera metodę która jest wykonywana codziennnie o godzinie 16:10, od poniedziałku do piątku. Ponieważ ECB aktualizuje swoje dane o godzinie 16:00 i robi to tylko w dni robocze. Za pomocą metody save() są dodawane elementy do bazy danych H2. 
</h2>
<hr>
<img src="https://github.com/michalGryczewski1998/mgryczewski/blob/main/Zdj%C4%99cia%20kodu/Zrzut%20ekranu%202021-06-29%20145842.jpg?raw=true" a>
<h2>
Ta klasa tworzy encję, która reprezentuje tabelę w bazie danych. Każde jej wystąpienie, reprezentuje wiersz w tabeli. Za pomocą adnotacji @Id określony został klucz podstawowy. 
</h2>
<hr>
<img src="https://github.com/michalGryczewski1998/mgryczewski/blob/main/Zdj%C4%99cia%20kodu/Zrzut%20ekranu%202021-06-29%20145900.jpg?raw=true" a>
<h2>
Repozytorium służące do operowania na bazie danych. Są tu umieszczone zapytania SQL, które zwracają w odpowiedzi aktualne dane dotyczące kursu walut na szczególny dzień.
</h2>
<hr>
<img src="https://github.com/michalGryczewski1998/mgryczewski/blob/main/Zdj%C4%99cia%20kodu/Zrzut%20ekranu%202021-06-29%20172755.jpg?raw=true" a>
<h2>
Przetwarza żądania użytkownika. Ta klasa zawiera trzy RequestMapping, w zależności od tego jakie obliczenia krzyżowe będzie chciał wykonać użytkownik.
</h2>


