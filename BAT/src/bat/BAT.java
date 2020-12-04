package BAT;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class BAT {

    public static void main(String[] args) {
        ensSuma(1, 2, "La suma es:", "+");
        CrearBat();
        EjecutarBat();
    }

    public static void EjecutarBat() {
        Runtime ejecutar = Runtime.getRuntime();
        try {
            ejecutar.exec("cmd.exe /c start C:\\Users\\danie\\Desktop\\Analizadores\\Crearbat.bat");

        } catch (Exception e) {
            System.out.println("e");
        }

    }

    public static void CrearBat() {
        File f;
        FileWriter fichero = null;
        try {
            f = new File("c:/BAT/ensamblado/suma.asm");
            f.delete();
            fichero = new FileWriter(f, true);
            BufferedWriter bw = new BufferedWriter(fichero);
            PrintWriter pw = new PrintWriter(bw);
            pw.println("cd\\");
            pw.println("cd c:/BAT/ensamblado/");
            pw.println("tasm suma.asm");
            pw.println("tlink suma.obj");
            pw.println("suma");
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public static void ensSuma(int av, int bv, String resp, String op) {

        String a = Integer.toHexString(av);
        String b = Integer.toHexString(bv);
        File f;
        FileWriter fichero = null;
        try {
            f = new File("c:/BAT/ensamblado/suma.asm");
            f.delete();
            fichero = new FileWriter(f, true);
            BufferedWriter bw = new BufferedWriter(fichero);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(".model small");
            pw.println(".stack");
            pw.println(".data");
            pw.println("mensaje db´" + resp + ":´,'$'");
            pw.println(".code");
            pw.println("mainproc");
            pw.println("model small");
            pw.println("mov ax, seg mensaje");
            pw.println("model small");
            pw.println("movds, ax");
            pw.println("model small");
            pw.println("mov ab,09");
            pw.println("int 21h");
            pw.println("MOV AL," + a.toUpperCase() + "\n");
            pw.println("MOV BL," + b.toUpperCase() + "\n");
            if ("+".equals(op)) {
                pw.println("ADD AL,BL\n");

            }
            pw.println("mov dl, al");
            pw.println("add dl,48");
            pw.println("mov ab,02");
            pw.println("int 21h");
            pw.println("mov ax, 4c00h");
            pw.println("int 21h");
            pw.println("main endp");
            pw.println("end main");
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
