Gyökérelem: EtelRendeloRendszer

 Vásárlók (2) 

[Vásárló] vevo_id=V001
  Név: Balla Levente
  Email: ballalevented@gmail.com
  Telefonszám: +36301234567
  Telefonszám: +36709876543
  Cím #1: 3530, Miskolc, Szent István utca 67.
  Cím #2: 3515, Miskolc, Egyetem utca 23.

[Vásárló] vevo_id=V002
  Név: Kun Dávid
  Email: kundavid@gmail.com
  Telefonszám: +36205551122
  Cím #1: 3551, Ónod, Honvéd utca 5.

 Éttermek (2) 

[Étterm] etterem_id=E001
  Név: Lángocska
  Cím: 3525, Miskolc, Búza tér 62.
  Kategória: Magyaros
  Kategória: Lángos
  Nyitvatartás: H-P: 6:00-17:00

[Étterm] etterem_id=E002
  Név: Pizza Király
  Cím: 3532, Miskolc, Jó utca 13.
  Kategória: Olasz
  Kategória: Pizza
  Nyitvatartás: K-V: 12:00-22:00

Futárok (2)

[Futár] futar_id=F001
  Név: Benke Gábor
  Telefonszám: +36703456789
  Rendszám: XSD-111
  Értékelés: 4.8

[Futár] futar_id=F002
  Név: Kun Damján
  Telefonszám: +36304666842
  Rendszám: XML-001
  Értékelés: 4.5

==== Ételek (4) ====

[Étel] etel_id=ET001, etterem_idref=E001
  Név: Sajtos Lángos
  Ár: 1400
  Leírás: Sajt, lángos tészta

[Étel] etel_id=ET002, etterem_idref=E001
  Név: Sajtos-tejfölös lángos
  Ár: 1800
  Leírás: Sajt, tejföl, lángos tészta
  Allergen: Glutén
  Allergen: Laktóz

[Étel] etel_id=ET003, etterem_idref=E002
  Név: Sonkás pizza
  Ár: 2800
  Leírás: Paradicsomos alap, sonka, sajt
  Allergen: Glutén
  Allergen: Laktóz

[Étel] etel_id=ET004, etterem_idref=E002
  Név: Hawaii pizza
  Ár: 3000
  Leírás: Paradicsomos alap, sonka, ananász, sajt
  Allergen: Glutén
  Allergen: Laktóz

Rendelések (2) 

[Rendelés] rendeles_id=R001, vevo_idref=V001, etterem_idref=E001, futar_idref=F001
  Időpont: 2025-10-27T19:30:00
  Státusz: Kiszállítva
  Szállítási cím: 3530, Miskolc, Szent István utca 1.
  Végösszeg: 3200
    [Tétel] etel_idref=ET001
      Darabszám: 1
      Vásárláskori ár: 1400
      Extra kérések: Extra sajttal
    [Tétel] etel_idref=ET002
      Darabszám: 1
      Vásárláskori ár: 1800

[Rendelés] rendeles_id=R002, vevo_idref=V002, etterem_idref=E002, futar_idref=F002
  Időpont: 2025-10-27T20:15:00
  Státusz: Kiszállítás alatt
  Szállítási cím: 3529, Ónod, Honvéd utca 5.
  Végösszeg: 5800
    [Tétel] etel_idref=ET003
      Darabszám: 2
      Vásárláskori ár: 2800

