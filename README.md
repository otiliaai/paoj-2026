# Sistem de gestionare Biblioteca

## Descriere

Aplicatia modeleaza un sistem de gestionare a unei biblioteci. Sistemul permite administrarea cartilor, autorilor, cititorilor, abonamentelor, imprumuturilor si evenimentelor literare.

Proiectul este dezvoltat in doua etape:

* **Etapa I**: modelare orientata pe obiecte, servicii Singleton, colectii Java si exceptii custom;
* **Etapa II**: persistenta in baza de date folosind JDBC, repository-uri, tranzactii JDBC, interogari cu JOIN si serviciu de audit.

Aplicatia este organizata in pachete logice si poate fi rulata din clasa `Main`.

---

# 1. Definirea sistemului

## 1.1 Lista de actiuni / interogari posibile in sistem

Aplicatia permite urmatoarele actiuni:

1. Adauga o carte noua in biblioteca
2. Sterge o carte din biblioteca
3. Inregistreaza un cititor nou
4. Sterge un cititor din sistem
5. Imprumuta o carte unui cititor
6. Returneaza o carte
7. Cauta carti dupa autor
8. Cauta carte dupa titlu
9. Listeaza toate cartile dintr-o sectiune
10. Afiseaza toate cartile disponibile
11. Afiseaza istoricul imprumuturilor unui cititor
12. Verifica disponibilitatea unei carti
13. Creeaza un eveniment literar
14. Adauga participanti la un eveniment literar

---

## 1.2 Tipuri de obiecte din domeniu

Principalele clase folosite in proiect sunt:

1. `Carte`
2. `Autor`
3. `Cititor`
4. `Sectiune`
5. `Abonament`
6. `Imprumut`
7. `Eveniment`
8. `Persoana`
9. `Angajat`
10. `Manager`
11. `ISBN`

Pe langa acestea, proiectul foloseste si enum-uri pentru valori controlate:

* `GenLiterar`
* `TipAbonament`
* `TipEveniment`
* `StatusImprumut`
* `RolAngajat`

---

# 2. Etapa I — Modelare si implementare OOP

## 2.1 Clase si principii OOP

Proiectul contine clase definite in pachetul:

```text
com.pao.proiect.biblioteca.model
```

Clasele au atribute `private` sau `protected`, metode de acces prin getteri/setteri si metode `toString()` suprascrise.

Exemple de clase:

* `Carte`
* `Autor`
* `Cititor`
* `Sectiune`
* `Abonament`
* `Imprumut`
* `Eveniment`
* `Persoana`
* `Angajat`
* `Manager`
* `ISBN`

---

## 2.2 Mostenire si clasa abstracta

Proiectul foloseste o ierarhie de mostenire pornind de la clasa abstracta `Persoana`.

```text
Persoana
├── Autor
├── Cititor
└── Angajat
    └── Manager
```

Clasa `Persoana` contine atribute comune, precum:

* `id`
* `numeComplet`
* `email`

De asemenea, clasa `Persoana` defineste metoda abstracta:

```java
public abstract String getRol();
```

Aceasta metoda este implementata diferit in clasele copil, de exemplu:

* `Autor` returneaza `"Autor"`
* `Cititor` returneaza `"Cititor"`

---

## 2.3 Clasa imutabila

Clasa `ISBN` este folosita ca obiect imutabil pentru identificarea unei carti.

Caracteristici:

* atribute `final`
* fara setteri
* initializare completa prin constructor

In clasa `Carte`, un obiect `ISBN` este folosit pentru informatiile de identificare ale cartii.

---

## 2.4 Interfata folosita

Proiectul contine interfata:

```java
public interface Displayable {
    void afiseaza();
}
```

Clasa `Carte` implementeaza aceasta interfata si defineste metoda:

```java
@Override
public void afiseaza() {
    System.out.println(this);
}
```

---

## 2.5 Exceptii custom

Proiectul foloseste exceptii custom pentru tratarea situatiilor specifice domeniului bibliotecii:

* `CarteIndisponibilaException`
* `CititorNegasitException`

Exemple de situatii tratate:

* incercarea de a imprumuta o carte indisponibila;
* incercarea de a sterge sau cauta un cititor care nu exista.

---

# 3. Colectii folosite

Proiectul foloseste mai multe tipuri de colectii Java:

## `List`

Folosita pentru liste ordonate de obiecte.

Exemple:

```java
List<Carte>
List<Imprumut>
List<Cititor>
```

## `Set`

Folosita pentru evitarea duplicatelor.

Exemplu:

```java
Set<Cititor>
```

## `Map`

Folosita pentru gruparea sau indexarea obiectelor.

Exemplu:

```java
Map<String, List<Carte>>
```

## `TreeSet`

Folosita pentru sortarea cartilor dupa titlu.

Clasa `Carte` implementeaza:

```java
Comparable<Carte>
```

Metoda folosita pentru sortare:

```java
@Override
public int compareTo(Carte carte) {
    return this.titlu.compareToIgnoreCase(carte.titlu);
}
```

---

# 4. Servicii Singleton

Logica aplicatiei este organizata in clase de serviciu, implementate folosind pattern-ul Singleton.

Exemple:

* `CarteService`
* `CititorService`
* `AuditService`

Serviciile expun operatii precum:

* adaugare obiect;
* stergere obiect;
* cautare dupa ID / nume / titlu;
* listare obiecte;
* imprumutare carte;
* returnare carte;
* afisare istoric imprumuturi.

Exemplu de structura Singleton:

```java
private static CarteService instance;

private CarteService() {}

public static CarteService getInstance() {
    if (instance == null) {
        instance = new CarteService();
    }
    return instance;
}
```

---

# 5. Etapa II — Persistenta JDBC, tranzactii si audit

Etapa II extinde proiectul din Etapa I prin adaugarea unei baze de date relationale si a unui strat de repository-uri JDBC.

Aplicatia nu este rescrisa de la zero. Clasele de model si serviciile existente sunt pastrate, iar peste acestea se adauga:

* `schema.sql`
* `db.properties`
* `DatabaseConnection`
* interfata generica `Repository<T, ID>`
* repository-uri concrete
* tranzactii JDBC
* interogari SQL cu JOIN
* serviciu de audit thread-safe

---

# 6. Baza de date

## 6.1 Entitati persistate

Pentru Etapa II, sunt persistate urmatoarele entitati principale:

1. `Autor`
2. `Sectiune`
3. `Cititor`
4. `Abonament`
5. `Carte`
6. `Eveniment`
7. `Imprumut`

In plus, exista o tabela asociativa pentru participarea cititorilor la evenimente:

8. `participanti_evenimente`

---

## 6.2 Tabelele bazei de date

Schema bazei de date este definita in fisierul:

```text
resources/schema.sql
```

Tabelele folosite sunt:

```text
autori
sectiuni
cititori
abonamente
carti
evenimente
imprumuturi
participanti_evenimente
```

---

## 6.3 Relatii intre tabele

Relatiile principale sunt:

```text
autori 1 -------- N carti
sectiuni 1 ------ N carti

cititori 1 ------ 0..1 abonamente

autori 1 -------- N evenimente

cititori 1 ------ N imprumuturi
carti 1 --------- N imprumuturi

evenimente N ---- N cititori
        prin participanti_evenimente
```

---

## 6.4 Chei straine

Schema contine urmatoarele relatii prin `FOREIGN KEY`:

```text
carti.autor_id -> autori.id
carti.sectiune_id -> sectiuni.id

abonamente.cititor_id -> cititori.id

evenimente.autor_id -> autori.id

imprumuturi.cititor_id -> cititori.id
imprumuturi.carte_id -> carti.id

participanti_evenimente.eveniment_id -> evenimente.id
participanti_evenimente.cititor_id -> cititori.id
```

Astfel, cerinta privind existenta a cel putin doua relatii `FOREIGN KEY` este indeplinita.

---

## 6.5 Observatii despre modelarea bazei de date

Enum-urile Java nu sunt salvate in tabele separate. Acestea sunt salvate ca text, folosind coloane de tip `VARCHAR`.

Exemple:

```text
GenLiterar       -> sectiuni.gen_literar
TipAbonament     -> abonamente.tip_abonament
TipEveniment     -> evenimente.tip_eveniment
StatusImprumut   -> imprumuturi.status
```

Clasa `ISBN` nu are tabela separata, deoarece este un value object. Informatiile sale sunt salvate direct in tabela `carti`:

```text
isbn_cod
isbn_tara
isbn_an_publicare
```

---

# 7. Configurarea conexiunii la baza de date

Datele de conectare la baza de date sunt definite in fisierul:

```text
resources/db.properties
```

Exemplu:

```properties
db.url=jdbc:mysql://localhost:3306/paoj_proiect
db.user=root
db.password=parola_ta
```

Credentialele nu sunt hardcodate direct in codul Java.

---

# 8. Clasa DatabaseConnection

Conexiunea la baza de date este gestionata prin clasa:

```text
com.pao.proiect.biblioteca.util.DatabaseConnection
```

Aceasta clasa este implementata ca Singleton si are urmatoarele responsabilitati:

* citeste configuratia din `db.properties`;
* creeaza conexiunea JDBC;
* expune un obiect `Connection` reutilizabil;
* centralizeaza logica de conectare la baza de date.

---

# 9. Repository generic

Proiectul foloseste o interfata generica pentru operatiile CRUD:

```java
public interface Repository<T, ID> {
    void save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    void update(T entity);
    void delete(ID id);
}
```

Aceasta interfata se afla in pachetul:

```text
com.pao.proiect.biblioteca.repository
```

---

# 10. Repository-uri concrete

Pentru accesul la baza de date, sunt implementate repository-uri concrete pentru entitatile persistate.

Repository-uri planificate / implementate:

```text
AutorRepository
SectiuneRepository
CititorRepository
AbonamentRepository
CarteRepository
EvenimentRepository
ImprumutRepository
```

Fiecare repository foloseste:

* `PreparedStatement`
* `try-with-resources`
* metode CRUD:

  * `save`
  * `findById`
  * `findAll`
  * `update`
  * `delete`

Nu se foloseste concatenare de string-uri pentru interogarile SQL.

---

# 11. Tranzactii JDBC

Proiectul include cel putin o operatie executata intr-o tranzactie JDBC explicita.

Operatia recomandata este imprumutarea unei carti.

Aceasta operatie afecteaza mai multe date:

1. se insereaza un rand in tabela `imprumuturi`;
2. se actualizeaza disponibilitatea cartii in tabela `carti`.

Structura tranzactiei:

```java
connection.setAutoCommit(false);

try {
    // INSERT in imprumuturi
    // UPDATE carti SET disponibilitate = false
    connection.commit();
} catch (SQLException e) {
    connection.rollback();
    throw e;
} finally {
    connection.setAutoCommit(true);
}
```

Astfel, daca una dintre operatii esueaza, modificarile sunt anulate prin `rollback`.

---

# 12. Interogari SQL cu JOIN

Proiectul include cel putin trei interogari SQL cu `JOIN`.

Exemple de interogari:

## 12.1 Carti cu autor si sectiune

Afiseaza cartile impreuna cu autorul si sectiunea din care fac parte.

```sql
SELECT c.id, c.titlu, a.nume_complet, s.nume
FROM carti c
JOIN autori a ON c.autor_id = a.id
JOIN sectiuni s ON c.sectiune_id = s.id;
```

## 12.2 Istoricul imprumuturilor unui cititor

Afiseaza imprumuturile unui cititor impreuna cu datele cartilor.

```sql
SELECT i.id, c.titlu, i.data_imprumut, i.data_returnare, i.status
FROM imprumuturi i
JOIN carti c ON i.carte_id = c.id
JOIN cititori ct ON i.cititor_id = ct.id
WHERE ct.id = ?;
```

## 12.3 Participanti la evenimente

Afiseaza participantii inscrisi la fiecare eveniment.

```sql
SELECT e.titlu, c.nume_complet, c.email
FROM participanti_evenimente pe
JOIN evenimente e ON pe.eveniment_id = e.id
JOIN cititori c ON pe.cititor_id = c.id;
```

---

# 13. Serviciu de audit

Proiectul foloseste clasa:

```text
AuditService
```

Aceasta scrie actiunile executate intr-un fisier CSV:

```text
audit.csv
```

Formatul fisierului:

```csv
nume_actiune,timestamp
adauga_carte,2026-04-21T10:35:42
cauta_carte,2026-04-21T10:36:01
imprumuta_carte,2026-04-21T10:36:15
```

Caracteristici:

* fisierul este deschis in mod append;
* actiunile nu suprascriu continutul existent;
* metoda de scriere este thread-safe;
* fiecare actiune importanta din sistem apeleaza `AuditService`.

---

# 14. Structura proiectului

Structura generala a proiectului este:

```text
biblioteca/
├── README.md
├── audit.csv
├── resources/
│   ├── schema.sql
│   └── db.properties
└── src/
    └── com/
        └── pao/
            └── proiect/
                └── biblioteca/
                    ├── Main.java
                    ├── model/
                    │   ├── Abonament.java
                    │   ├── Angajat.java
                    │   ├── Autor.java
                    │   ├── Carte.java
                    │   ├── Cititor.java
                    │   ├── Eveniment.java
                    │   ├── Imprumut.java
                    │   ├── ISBN.java
                    │   ├── Manager.java
                    │   ├── Persoana.java
                    │   └── Sectiune.java
                    ├── service/
                    │   ├── AuditService.java
                    │   ├── CarteService.java
                    │   └── CititorService.java
                    ├── repository/
                    │   ├── Repository.java
                    │   ├── AutorRepository.java
                    │   ├── SectiuneRepository.java
                    │   ├── CititorRepository.java
                    │   ├── AbonamentRepository.java
                    │   ├── CarteRepository.java
                    │   ├── EvenimentRepository.java
                    │   └── ImprumutRepository.java
                    ├── exception/
                    │   ├── CarteIndisponibilaException.java
                    │   └── CititorNegasitException.java
                    └── util/
                        ├── DatabaseConnection.java
                        └── Displayable.java
```

---

# 15. Rulare proiect

Pentru rulare:

1. se creeaza baza de date MySQL `paoj_proiect`;
2. se ruleaza scriptul `resources/schema.sql`;
3. se completeaza datele reale in `resources/db.properties`;
4. se ruleaza clasa `Main`.

Exemplu pentru crearea bazei de date:

```sql
CREATE DATABASE IF NOT EXISTS paoj_proiect;
USE paoj_proiect;
```

---

# 16. Branch GitHub

Etapa II este dezvoltata pe branch-ul:

```text
proiect-etapa2
```

Comenzi utile:

```bash
git switch proiect-etapa2
git add .
git commit -m "Proiect Etapa II: JDBC, repository, tranzactii si audit"
git push origin proiect-etapa2
```
