package com.pao.laboratory05.audit;

import com.pao.laboratory05.angajati.Angajat;

import java.time.LocalDateTime;
import java.util.Arrays;

public class AngajatService {
    private static AngajatService instance;
    private com.pao.laboratory05.angajati.Angajat[] angajati;
    private AuditEntry[] auditLog;

    private AngajatService(){

        angajati = new com.pao.laboratory05.angajati.Angajat[0];
        auditLog = new AuditEntry[0];
    }

    public static AngajatService getInstance(){
        if (instance == null)
            instance =  new AngajatService();
        return instance;
    }

    private void logAction(String action, String target) {
        AuditEntry ae = new AuditEntry(action, target, LocalDateTime.now().toString());
        AuditEntry[] alog = new AuditEntry[auditLog.length+1];
        System.arraycopy(auditLog,0,alog,0,auditLog.length);
        alog[auditLog.length] = ae;
        auditLog = alog;
    }

    void addAngajat(com.pao.laboratory05.angajati.Angajat a) {
        com.pao.laboratory05.angajati.Angajat[]  new_angajati = new com.pao.laboratory05.angajati.Angajat[angajati.length+1];
        System.arraycopy(angajati,0,new_angajati,0,angajati.length);
        new_angajati[angajati.length] = a;
        angajati = new_angajati;
        //System.out.println("Angajat adaugat: "+a.getNume());
        logAction("ADD", a.getNume());
    }

    void printAll() {
        for (var a:angajati) {
            System.out.println((a));
        }
    }

    void listBySalary() {
        Angajat[] copy = angajati.clone();
        Arrays.sort(copy);
        for (var a:copy) {
            System.out.println((a));
        }
    }

    void  findByDepartament(String numeDept){
        logAction("FIND_BY_DEPT", numeDept);
        int cnt = 0;
        for (var a : angajati) {
            if (a.getDepartament().nume().equalsIgnoreCase(numeDept)) {
                cnt +=1;
                System.out.println(a);
            }
        }
        if (cnt == 0) {
            System.out.println("Niciun angajat în departamentul: " + numeDept);
        }
    }

    public void printAuditLog() {
        for (var e : auditLog){
            System.out.println(e);
        }

    }
}
