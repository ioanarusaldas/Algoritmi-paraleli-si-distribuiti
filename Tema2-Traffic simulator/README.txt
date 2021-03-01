Savu Ioana Rusalda 335CB
	In implementarea temei am folosit urmatoarele clase din schelet:
		*IntersectionFactory
		*IntersectionHandlerFactory
		*ReaderHandlerFactory

*******************************************************************************
	IntersectionFactory
		Am adaugat in HashMap-ul "cache" cate o intrare pentru fiecare 
intersectie.
	ReaderHandlerFactory
		Am initializat intersectia din clasa Main si mi-am setat variabilele 
din fiecare intersectie.
	IntersectionHandlerFactory
		Am complentat metoda handle pentru a descrie comportamentul fiecarei 
masini in momentul in care a ajuns in intersectie.
*******************************************************************************


Cerinta 1: simple_semaphore
***************************
In implementarea acestei cerinte am folosit metoda "sleep" din clasa Thread si 
am afisat mesajele corespunzatoare.

Cerinta 2: simple_n_roundabout
******************************
Pentru a limita numarul masinilor din intersectie am folosit un semafor declarat
in clasa SimpleNRoundabout.
	Am initializat semaforul cu X(numarul primit in fisierul de input). Cand
ajunge la semafor masina va face acquire() pentru marca faptul ca a intrat in 
intersectie si release() pentru a marca faptul ca a iesit din intersectie.
	Astfel, se poate intampla ca semaforul sa opreasca masinile pentru a nu 
depasi numarul de masini permise in intersectie.


Cerinta 3: simple_strict_1_car_roundabout
****************************************
Pentru a permite accesul unei singure masini de pe fiecare sens am creat un vector de semafoare, initializand fiecare semafor cu 1.
	Fiecare masina va accesa semaforul corespunzator liniei de pe care a venit,
fiind permisa astfel intrarea unei singure masini in intersectie.

Cerinta 4: simple_strict_x_car_roundabout
***************************************
Pentru a permite accesul in intersectie a x masini am folosit un vector de 
semafoare si 2 bariere.Acestea le-am declarat ca si variabile in clasa
SimpleStrictXRoundabout.
	Prima bariera am initializat-o cu numarul total de masini. Cea de-a doua 
bariera am initializat-o cu x * numarul de linii.
	Am folosit prima bariera pentru a astepta ca toate masinile sa ajunga la
intersectie.Din aceste masini am folosit semaforul pentru a lasa maxim x masini
sa intre. 
	Am folosit cea de-a doua bariera pentru a ma asigura ca s-a terminat etapa
de selectie a masinilor ce urmeaza sa intre in intersectie si pentru a astepta 
ca acestea sa paraseasca intersectia.
	Dupa ce toate au afisat mesajul prin care marcheaza iesirea din intersectie
am facut release() pentru a lasa un nou numar de masini sa intre.

Cerinta 5: simple_max_x_car_roundabout
**************************************
Pentru aceasta cerinta am folosit un vector de semafoare initializat cu valoarea
x pentru fiecare semafor.Astfel nu se permite intrarea in intersectia a mai mult
de x masini.

Cerinta 6: priority_intersection
********************************
	In implementare am verificat prioritatea fiecarei masini, pentru a lasa 
masinile cu prioritate mai mare sa intre primele in intersectie. 
	Am folosit o coada pentru masinile cu prioritate mica si un vector pentru a
retine masinile aflate in intersectie la un moment de timp.
	Daca intersectia nu este goala cand o masina cu prioritate mica ajunge la
intersectie, atunci aceasta este adaugata in coada.
	Masinile cu prioritate mare sunt adaugate in vector cand intra in 
intersectie si eliminate cand ies. La eliminarea din coada masina curenta, va
afisa id-ul scos din coada, pentru a respecta ordinea.
	Toate operatiile realizate pe cele doua structuri de date sunt facute cu
ajutorul metodelor sincronizate din clasa PriorityIntersection.
	Am folosit mecanismul wait-notify pentru a face masinile cu prioritate mica
sa astepte pana cand se elibereaza intersectia. Am folosit monitor lock-ul
unui String pentru a realiza operatiile de wait si notify.


Cerinta 7: crosswalk
********************
 	In implemenatrea acestei cerinte am folosit un vector de stringuri pentru a
 retine mesajele ce trebuie afisate.
	Cand pietonii trec (isPass() == true) verific daca se mesajele se schimba 
fata de pasul anterior si setez mesajul cu  lumina rosie pentru masini. Cand
pietonii stau daca mesajul difera fata de cel anterior setez lumina verde si
il afisez.
	La finalul while-ului :while (!Main.pedestrians.isFinished()) mai verific
inca o data mesajele cu lumina rosie, pentru a vedea daca mai sunt masini in 
intersectie care nu au apucat sa treaca.

Cerinta 10: railroad
********************
 In implementare am folosit o coada pentru a retine ordinea in care ajung 
masinile la bariera si o bariera.
	Am pus mai intai bariera pentru a astepta ca toate masinile sa ajunga la
bariera. Am pus un singur thread sa afiseze mesajul care marcheaza trecerea
trenului.
	La iesirea din intersectie fiecare masina va scoate din coada un id si o
directie a masinilor in ordinea in care au ajuns, si le va afisa. 