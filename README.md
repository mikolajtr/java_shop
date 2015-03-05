Projekt zaliczeniowy na Javę.

Serwis internetowy pozwala zarejestrowanym użytkownikom na dodawanie własnych ogłoszeń na różne tematy, chociaż był tworzony głównie z myślą o sprzedaży. 
Każdy może przeglądać zamieszczone ogłoszenia, jednak tylko zarejestrowany użytkownik może dodawać nowe. 
Do rejestracji nie jest wymagany adres e-mail. 
Użytkownik ma możliwość zmiany hasła. 

Nazwy kontrolerów są nieco mylące - kontroler DatabaseController odpowiada za rejestrację, zaś MainController za logowanie. To pozostałość z wczesnej fazy projektu, kiedy uczyliśmy się operować na bazie danych.
Użyliśmy bazy danych SQLite.
Do mechanizmu logowania posłużyliśmy się cookies. Cookie o nazwie "rupieciarnia" przechowuje aktualne ID sesji wyliczone z funkcji skrótu, co pozwala na bezpieczną sesję.
Próbowaliśmy również ustawić hashowanie haseł, ale były problemy przy logowaniu - za każdym razem z hasła wpisywanego była wyliczana inna suma kontrolna(używaliśmy algorytmu obliczania sumy MD5 dostępnego w Javie).
