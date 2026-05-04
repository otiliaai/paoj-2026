package com.pao.laboratory09.exercise2;

import com.pao.laboratory09.exercise1.TipTranzactie;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex2.bin";
    private static final int RECORD_SIZE = 32;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine().trim());

        int[] ids = new int[n];
        double[] sume = new double[n];
        String[] date = new String[n];
        TipTranzactie[] tipuri = new TipTranzactie[n];

        for (int i = 0; i < n; i++) {
            String[] parts = sc.nextLine().split(" ");
            ids[i] = Integer.parseInt(parts[0]);
            sume[i] = Double.parseDouble(parts[1]);
            date[i] = parts[2];
            tipuri[i] = TipTranzactie.valueOf(parts[3]);
        }

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(OUTPUT_FILE))) {
            for (int i = 0; i < n; i++) {
                //id 4
                byte[] idBytes = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(ids[i]).array();
                dos.write(idBytes);

                //suma 8
                byte[] sumaBytes = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putDouble(sume[i]).array();
                dos.write(sumaBytes);

                // data-10
                String dataPadded = String.format("%-10s", date[i]);
                dos.write(dataPadded.getBytes("ASCII"));

                //tip-1
                dos.write(tipuri[i] == TipTranzactie.CREDIT ? 0 : 1);

                //status-1
                dos.write(0);

                //padding-8
                dos.write(new byte[8]);
            }
        }

        try (RandomAccessFile raf = new RandomAccessFile(OUTPUT_FILE, "rw")) {
            while (sc.hasNextLine()) {
                String linie = sc.nextLine().trim();
                String[] parts = linie.split(" ");
                String comanda = parts[0];

                if (comanda.equals("READ")) {
                    int idx = Integer.parseInt(parts[1]);
                    System.out.println(citesteInregistrare(raf, idx));

                } else if (comanda.equals("UPDATE")) {
                    int idx = Integer.parseInt(parts[1]);
                    String statusStr = parts[2];
                    byte statusByte = statusToByte(statusStr);
                    raf.seek((long) idx * RECORD_SIZE + 23);
                    raf.write(statusByte);
                    System.out.println("Updated [" + idx + "]: " + statusStr);

                } else if (comanda.equals("PRINT_ALL")) {
                    for (int i = 0; i < n; i++) {
                        System.out.println(citesteInregistrare(raf, i));
                    }
                }
            }
        }
    }

    private static String citesteInregistrare(RandomAccessFile raf, int idx) throws Exception {
        raf.seek((long) idx * RECORD_SIZE);
        byte[] bytes = new byte[RECORD_SIZE];
        raf.readFully(bytes);
        ByteBuffer bb = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);

        int id = bb.getInt();
        double suma = bb.getDouble();

        byte[] dataBytes = new byte[10];
        bb.get(dataBytes);
        String data = new String(dataBytes, "ASCII").trim();

        int tipByte = bb.get() ;
        int statusByte = bb.get() ;

        String tip = tipByte == 0 ? "CREDIT" : "DEBIT";
        String status = byteToStatus(statusByte);

        return String.format(Locale.US, "[%d] id=%d data=%s tip=%s suma=%.2f RON status=%s",
                idx, id, data, tip, suma, status);
    }

    private static byte statusToByte(String status) {
        switch (status) {
            case "PENDING":   return 0;
            case "PROCESSED": return 1;
            case "REJECTED":  return 2;
            default: throw new IllegalArgumentException("Status necunoscut: " + status);
        }
    }

    private static String byteToStatus(int b) {
        switch (b) {
            case 0: return "PENDING";
            case 1: return "PROCESSED";
            case 2: return "REJECTED";
            default: return "UNKNOWN";
        }
    }
}