package com.pao.laboratory08.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    // Calea către fișierul cu date — relativă la rădăcina proiectului
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește studenții din FILE_PATH cu BufferedReader
        // 2. Citește comanda din stdin: PRINT, SHALLOW <nume> sau DEEP <nume>
        // 3. Execută comanda:
        //    - PRINT → afișează toți studenții
        //    - SHALLOW <nume> → shallow clone + modifică orașul clonei la "MODIFICAT" + afișează
        //    - DEEP <nume> → deep clone + modifică orașul clonei la "MODIFICAT" + afișează
        Scanner sc = new Scanner(System.in);
        String comanda = sc.nextLine();
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\ionel\\IdeaProjects\\programare_orientata_java\\src\\com\\pao\\laboratory08\\exercise1\\tests\\studenti.txt"));
        String linie;
        ArrayList<Student> Studenti = new ArrayList<>();

        while ((linie = br.readLine()) != null ) {
            String[] elemente = linie.split(",");
            Adresa adresa = new Adresa(elemente[2], elemente[3]);
            String nume = elemente[0];
            int varsta = Integer.parseInt(elemente[1]);
            Student student_nou = new Student(nume,varsta,adresa);
//            System.out.println(student_nou);
            Studenti.add(student_nou);
        }

        if (comanda.split(" ")[0].equals("PRINT") ) {
            for (var st : Studenti)
                System.out.println(st);
        }

        if (comanda.split(" ")[0].equals("SHALLOW") ){

            String nume_cautat = comanda.split(" ")[1];
            Student original = null;
            Student clone ;
            for (var st : Studenti) {
                if (st.getNume().equals(nume_cautat)){
                    original = st;
                }
            }
            clone = original.shallowclone();
            Adresa adresa_noua = clone.getAdresa();
            adresa_noua.setOras("MODIFICAT");
            clone.setAdresa(adresa_noua);
            System.out.println("Original: "+original);
            System.out.println("Clona: "+clone);
        }

        if (comanda.split(" ")[0].equals("DEEP")){
            String nume_cautat = comanda.split(" ")[1];
            Student original = null;
            Student clone ;
            for (var st : Studenti) {
                if (st.getNume().equals(nume_cautat)){
                    original = st;
                }
            }
            clone = original.deepclone();
            Adresa adresa_noua = clone.getAdresa();
            adresa_noua.setOras("MODIFICAT");
            clone.setAdresa(adresa_noua);
            System.out.println("Original: "+original);
            System.out.println("Clona: "+clone);
        }
        br.close();
    }

}
