package com.pao.laboratory08.exercise2;

import java.io.*;
import java.util.*;
import com.pao.laboratory08.exercise1.Student;
import com.pao.laboratory08.exercise1.Adresa;

public class Main {
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește studenții din FILE_PATH cu BufferedReader
        // 2. Citește pragul de vârstă din stdin cu Scanner
        // 3. Filtrează studenții cu varsta >= prag
        // 4. Scrie filtrații în "rezultate.txt" cu BufferedWriter
        // 5. Afișează sumarul la consolă

        Scanner sc = new Scanner(System.in);

        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\ionel\\IdeaProjects\\programare_orientata_java\\src\\com\\pao\\laboratory08\\exercise1\\tests\\studenti.txt"));
        String linie;
        ArrayList<Student> Studenti = new ArrayList<>();
        BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\ionel\\IdeaProjects\\programare_orientata_java\\src\\com\\pao\\laboratory08\\tests\\rezultate.txt"));


        while ((linie = br.readLine()) != null) {
            String[] elemente = linie.split(",");
            Adresa adresa = new Adresa(elemente[2], elemente[3]);
            String nume = elemente[0];
            int varsta = Integer.parseInt(elemente[1]);
            Student student_nou = new Student(nume, varsta, adresa);
            Studenti.add(student_nou);
//          System.out.println(student_nou);
        }

        int prag = sc.nextInt();
        ArrayList<Student> Studenti_filtrati = new ArrayList<>();
        for (var st : Studenti) {
            if (st.getVarsta() >= prag) {
                Studenti_filtrati.add(st);
                bw.write(st.toString());
                bw.newLine();
            }
        }

        System.out.println(String.format("Filtru: varsta >= %s", prag));
        System.out.println(String.format("Rezultate: %d studenti", Studenti_filtrati.toArray().length));
        System.out.println();
        for (var st : Studenti_filtrati) {
            System.out.println(st);
        }
        br.close();
        bw.close();
    }
}

