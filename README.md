# Sistem de gestionare Bibliotecă

## Descriere
Aplicația modelează un sistem de gestionare a unei biblioteci, permițând administrarea cărților, autorilor, cititorilor și împrumuturilor. Sistemul oferă funcționalități pentru evidența stocului de cărți și gestionarea interacțiunilor cu cititorii.

---

## 1. Definirea sistemului

### 1.1 Lista de acțiuni / interogări

1. Adaugă o carte nouă în bibliotecă  
2. Șterge o carte din bibliotecă  
3. Înregistrează un cititor nou  
4. Șterge un cititor din sistem  
5. Împrumută o carte unui cititor  
6. Returnează o carte  
7. Caută cărți după autor  
8. Caută carte după titlu  
9. Listează toate cărțile dintr-o secțiune  
10. Afișează toate cărțile disponibile  
11. Afișează istoricul împrumuturilor unui cititor  
12. Verifică disponibilitatea unei cărți  

---

### 1.2 Tipuri de obiecte din domeniu

1. Carte  
2. Autor  
3. Cititor  
4. Sectiune  
5. Imprumut  
6. Biblioteca  
7. Exemplar  
8. Rezervare  
9. Persoana (clasă abstractă)  
10. Angajat  

---

## 2. Implementare Java

### 2.1 Clase și OOP

- Minim 8 clase definite în pachetul `model`
- Atribute private/protected + getteri/setteri
- Metode suprascrise:
  - `toString()`
  - `equals()`
  - `hashCode()`
  (ex: pentru clasele `Carte` și `Cititor`)

#### Moștenire
- Ierarhie:
  - `Persoana` (abstractă)
    - `Cititor`
    - `Angajat`

#### Clasă abstractă
- `Persoana`:
  - metodă abstractă: `getRol()`

#### Clasă imutabilă
- `ISBN` sau `CodCarte`
  - atribute `final`
  - fără setteri
  - inițializare completă în constructor

#### Excepții custom
- `CarteNedisponibilaException`
- `CititorNegasitException`

---

### 2.2 Colecții

- `List<Carte>` — lista de cărți  
- `Set<Cititor>` — cititori unici  
- `Map<String, List<Carte>>` — cărți grupate după autor  
- `TreeSet<Carte>` — sortate după titlu (Comparable implementat)  

---

### 2.3 Servicii

Clase de serviciu (Singleton):

- `CarteService`
- `CititorService`

#### Funcționalități oferite:
- adaugă obiect
- șterge obiect
- caută după ID / nume
- listează toate obiectele

#### Implementare Singleton:
```java
private static CarteService instance;

private CarteService() {}

public static CarteService getInstance() {
    if (instance == null) {
        instance = new CarteService();
    }
    return instance;
}