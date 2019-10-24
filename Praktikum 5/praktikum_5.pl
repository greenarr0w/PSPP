:-style_check(- discontiguous).

% Aufgabe 1
modulKuerzel(assesmentIT).
modulBezeichnung(assesmentIT, 'Assessment IT').
etcsPunkte(assesmentIT, 60).
semester(assesmentIT, 2).

modulKuerzel(sisy1).
modulBezeichnung(sisy1,'Signale Systeme 1').
etcsPunkte(sisy1,4).
semester(sisy1,3).
vorraussetzung(assesmentIT,sisy1).

modulKuerzel(ood).
modulBezeichnung(ood, 'Objektorientiertes Design').
etcsPunkte(ood,4).
semester(ood,3).
vorraussetzung(assesmentIT,ood).

modulKuerzel(tin1).
modulBezeichnung(tin1, 'Technische Informatik 1').
etcsPunkte(tin1,4).
semester(tin1,3).
vorraussetzung(assesmentIT,tin1).

modulKuerzel(dnet).
modulBezeichnung(dnet,'Dot-net-Technologie').
etcsPunkte(dnet,4).
semester(dnet,4).
vorraussetzung(ood,dnet).

modulKuerzel(sep).
modulBezeichnung(sep,'Softwareentwicklungspraxis').
etcsPunkte(sep,4).
semester(sep,4).
vorraussetzung(ood,sep).

modulKuerzel(swe).
modulBezeichnung(swe,'Softwareengineering').
etcsPunkte(swe,4).
semester(swe,4).
vorraussetzung(ood,swe).

modulKuerzel(vsy).
modulBezeichnung(vsy,'Verteilte Systeme').
etcsPunkte(vsy,4).
semester(vsy,4).
vorraussetzung(ood,vsy).

modulKuerzel(reesy).
modulBezeichnung(reesy,'Entwurf und Realisierung von Systemen').
etcsPunkte(reesy,4).
semester(reesy,4).
vorraussetzung(tin1,reesy).

modulKuerzel(tin2).
modulBezeichnung(tin2,'Technische Informatik 2').
etcsPunkte(tin2,4).
semester(tin2,4).
vorraussetzung(tin1,tin2).

modulKuerzel(dd1).
modulBezeichnung(dd1,'DigitaleDatenübertragung 1').
etcsPunkte(dd1,4).
semester(dd1,5).
vorraussetzung(sisy1,dd1).

modulKuerzel(jff).
modulBezeichnung(jff,'Java für Fortgeschrittene 1').
etcsPunkte(jff,4).
semester(jff,5).
vorraussetzung(swe,jff).

modulKuerzel(cgr1).
modulBezeichnung(cgr1,'Computergrafik').
etcsPunkte(cgr1,4).
semester(cgr1,5).
vorraussetzung(tin2,cgr1).

modulKuerzel(pra).
modulBezeichnung(pra,'Prozessorarchitekturen').
etcsPunkte(pra,4).
semester(pra,5).
vorraussetzung(tin2,pra).

modulKuerzel(mc).
modulBezeichnung(mc,'Mikrokontroller').
etcsPunkte(mc,4).
semester(mc,5).
vorraussetzung(tin2,mc).

modulKuerzel(psp).
modulBezeichnung(psp,'Programmiersprachen').
etcsPunkte(psp,4).
semester(psp,5).
vorraussetzung(assesmentIT, psp).

modulKuerzel(dd2).
modulBezeichnung(dd2,'Digitale Datenübertragung 2').
etcsPunkte(dd2,4).
semester(dd2,6).
vorraussetzung(dd1, dd2).

modulKuerzel(eesy).
modulBezeichnung(eesy,'Embedded Systems').
etcsPunkte(eesy,4).
semester(eesy,6).
vorraussetzung(pra, eesy).

% Aufgabe 2
direkteVoraussetzung(X,Y) :- vorraussetzung(Y,X).
alleVorraussetzungen(X,Y) :- direkteVoraussetzung(X,Y).
alleVorraussetzungen(X,Y) :- direkteVoraussetzung(X,Z), alleVorraussetzungen(Z,Y).

% Aufgabe 3
direkterNachfolger(X,Y) :- direkteVoraussetzung(Y,X).
alleNachfolger(X,Y) :- direkterNachfolger(X,Y).
alleNachfolger(X,Y) :- direkterNachfolger(X,Z), alleNachfolger(Z,Y).

% Aufgabe 4  --> Beispiel: waehlbareModuleImSemster(3,S)
waehlbareModuleImSemster(X,S) :- setof(Y,semester(Y,X),S).

% Aufgabe 5 --> Beispiel: summeETCSvonFaechern([tin1,tin2,ood],S)
summeETCSvonFaechern([],S) :- S is 0.
summeETCSvonFaechern([X|Y],S) :- summeETCSvonFaechern(Y,S1), etcsPunkte(X,Z), S is S1 + Z.

% Aufgabe 6
main:- current_prolog_flag(argv, Argv), format('Called with ~q~n', [Argv]), format(' Summe der Liste ', summeETCSvonFaechern([Argv],S)), halt.
main:- halt(1).
