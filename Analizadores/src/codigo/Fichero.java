package codigo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

class Fichero {

    //lista de Lexemas (Palabras Reservadas)
    private final String main = "Proceso";
    private final String indEntero = "LeerI";
    private final String indCadena = "LeerC";
    private final String buclefor = "for";
    private final String imprimir = "Escribir";
    private final String condicion = "if";
    private final String opLogicos = "(\\<|\\>|><|===|\\<==|\\==>|)";
    private final String opAritm = "(\\+|\\-|\\/|\\*)";
    private final String llaveA = "\\{";
    private final String llaveC = "\\}";
    private final String BraquetA = "\\(";
    private final String BraquetC = "\\)";
    private final String indTexto = "\"";
    private final String W = "([\\ \\f\\r\\t\\v]*)";
    private final String R = "([\\ \\f\\n\\r\\t\\v]*)";
    private final String num = "([0-9]+)";
    private final String identificador = "([a-zA-Z]+[0-9]+|[a-zA-Z]+)";
    private final String texto = "([a-z|A-Z|\\t|[0-9]|\\ ]*)";
    private final String finmain = "Fin";
    private final String verdadero = "boleano";
    private final String bucleMientras = "Mientras";

    private final String patronOperacionesL = W + "(" + num + "|" + identificador + ")" + W + opLogicos + W + "(" + num + "|" + identificador + ")" + R;
    private final String patronOperacionesA = W + identificador + W + "=" + W + "(" + num + "|" + identificador + ")" + W + opAritm + W + "(" + num + "|" + identificador + ")" + R;
    private final String patronDeclaraCadena1 = W + indCadena + W + identificador + R;
    private final String patronDeclaraNumero1 = W + indEntero + W + identificador + R;
    private final String patronIgualaCadena = W + identificador + W + "=" + W + indTexto + texto + indTexto + R;
    private final String patronIgualaNumero = W + identificador + W + "=" + W + num + R;
    private final String patronMostrar = W + imprimir + W + BraquetA + "(" + W + identificador + W + "||(" + W + indTexto + texto + indTexto + W + "))" + BraquetC + R;
    private final String patronContadorCiclo = W + "(" + patronDeclaraNumero1 + "=" + W + num + "|" + identificador + "=" + W + num + ")" + W + "," + patronOperacionesL + W + "," + W + identificador + "\\+\\+" + R;

    private final String patronCicloB = W + buclefor + W + BraquetA + patronContadorCiclo + BraquetC + W + llaveA + R;
    private final String patronCondicionB = W + condicion + W + BraquetA + patronOperacionesL + BraquetC + W + llaveA + R;

    private final String patronPatronesB = "(" + W + main + W + "|" + W + finmain + W + "|((" + W + patronCicloB + W + ")|(" + patronOperacionesA + ")|(" + W + patronDeclaraCadena1 + W + ")|(" + W + patronIgualaCadena + W + ")|(" + W + patronIgualaNumero + W + ")|(" + W + patronDeclaraNumero1 + W + ")|(" + W + patronCondicionB + W + ")|(" + W + patronMostrar + W + ")|" + W + "|[?\\}])*)";

    private final String patronCondicions = W + condicion + W + BraquetA + patronOperacionesL + BraquetC + W
            + llaveA
            + R + "(" + patronMostrar + "|" + patronOperacionesA + "|" + patronDeclaraCadena1 + "|" + patronDeclaraNumero1 + "|" + patronIgualaCadena + "|" + patronIgualaNumero + ")*"
            + llaveC + R;

    private final String patronCiclos = W + buclefor + W + BraquetA + patronContadorCiclo + BraquetC + W
            + llaveA
            + R + "(" + patronCondicions + "|" + patronMostrar + "|" + patronOperacionesA + "|" + patronDeclaraCadena1 + "|" + patronDeclaraNumero1 + "|" + patronIgualaCadena + "|" + patronIgualaNumero + ")*"
            + llaveC + R;

    private final String patronCiclo = W + buclefor + W + BraquetA + patronContadorCiclo + BraquetC + W
            + llaveA
            + R + "(" + patronCiclos + "|" + patronCondicions + "|" + patronMostrar + "|" + patronOperacionesA + "|" + patronDeclaraCadena1 + "|" + patronDeclaraNumero1 + "|" + patronIgualaCadena + "|" + patronIgualaNumero + ")*"
            + llaveC + R;

    private final String patronCondicion = W + condicion + W + BraquetA + patronOperacionesL + BraquetC + W
            + llaveA
            + R + "(" + patronCiclos + "|" + patronCiclo + "|" + patronCondicions + "|" + patronMostrar + "|" + patronOperacionesA + "|" + patronDeclaraCadena1 + "|" + patronDeclaraNumero1 + "|" + patronIgualaCadena + "|" + patronIgualaNumero + ")*"
            + llaveC + R;
    private final String patronWhile = W + bucleMientras + W + BraquetA + patronOperacionesA + BraquetC + W
            + llaveA
            + R + "(" + patronCiclos + "|" + patronCiclo + "|" + patronCondicions + "|" + patronMostrar + "|" + patronOperacionesA + "|" + patronDeclaraCadena1 + "|" + patronDeclaraNumero1 + "|" + patronIgualaCadena + "|" + patronIgualaNumero + ")*"
            + llaveC + R;
    ;
    
    
    private final String patronesEjecucion = "(" + patronCiclo + "|" + patronCondicion + "|" + patronOperacionesA + "|" + patronMostrar + "|" + patronDeclaraNumero1 + "|" + patronDeclaraCadena1 + "|" + patronIgualaNumero + "|" + patronIgualaCadena + ")*";

    private final String patronInicio = R + main + R + patronesEjecucion + R + finmain + R;

    private int llavesAbiertas;
    private int llavesCerradas;
    private int inicio;
    private int fin;
    private boolean err, calcular;
    public String Mensaje;

    public String compila(JTextArea Codigo) {

        ArrayList<String> ArrayVariablesNUM = new ArrayList();//Aray de variables Numericas declaradas (a,b,c,...)
        ArrayList<Integer> ArrayValoresNUM = new ArrayList();//Array que contiene los valores de las variables iniciadas(1,2,3,...)
        ArrayList<String> ArrayVariablesCAD = new ArrayList();//Array de las variables de texto declaradas (a,b,c,...)
        ArrayList<String> ArrayValoresCAD = new ArrayList();//Array del valor las variables de texto iniciadas (hola,mundo,..)
        String instruction, Operation, Var;

        int Val, a = 0, b = 0;
        String Vals, operando = null, r = null;
        Mensaje = "Resultado: \n";
        String miCodigo = Codigo.getText().toString();
        int fila = 0;
        err = false;
        calcular = false;
        llavesAbiertas = 0;
        llavesCerradas = 0;
        inicio = 0;
        fin = 0;
//------------------------parte lexico-------------------------

        StringTokenizer st = new StringTokenizer(miCodigo, "\n");

        while (st.hasMoreTokens()) {
            fila++;
            String line = st.nextToken();
            Pattern p = Pattern.compile("^" + R + "(\\s|" + main + "|" + finmain + "|" + indEntero + "|" + indCadena + "|" + llaveA + "|"
                    + llaveC + "|" + buclefor + "|" + condicion + "|" + imprimir + "|" + patronDeclaraNumero1
                    + "|" + patronDeclaraCadena1 + "|" + patronIgualaNumero + "|" + patronIgualaCadena
                    + "|" + opAritm + "|" + patronOperacionesA + ")");
            
            Matcher matcher = p.matcher(line);
            System.out.println("" + matcher.toString());
            if (matcher.find()) {
                               continue;              

            } else {
                Mensaje = Mensaje + "Error Léxico (" + line + ") en la línea -->" + fila + "\n";
                err = true;
            }

        }

//----------parte sintáctico--------------------------------------------------------
        fila = 0;
        StringTokenizer st1 = new StringTokenizer(miCodigo, "\n");
        while (st1.hasMoreTokens()) {
            fila++;
            String comands = st1.nextToken();

            Pattern p2 = Pattern.compile(patronPatronesB);
            Matcher matcher2 = p2.matcher(comands);
            if (matcher2.matches()) {
                continue;
            } else {
                Mensaje = Mensaje + "Error Sintáctico (" + comands + ") en la línea -->" + fila + "\n";
                err = true;
            }
        }
        
        StringTokenizer st5 = new StringTokenizer(miCodigo, "\n");
        while (st5.hasMoreTokens()) {
            String comands = st5.nextToken();
            for (int i = 0; i < comands.length(); i++) {
                if (comands.charAt(i) == '{') {
                    llavesAbiertas++;
                }
                if (comands.charAt(i) == '}') {
                    llavesCerradas++;
                }
            }
        }
        
        StringTokenizer st3 = new StringTokenizer(miCodigo, "(\\ |\n)");
        while (st3.hasMoreTokens()) {

            String comands = st3.nextToken();

            Pattern p2 = Pattern.compile(main);
            Matcher matcher2 = p2.matcher(comands);
            if (matcher2.find()) {
                inicio++;
            }
        }
        StringTokenizer st4 = new StringTokenizer(miCodigo, "(\\ |\n)");
        while (st4.hasMoreTokens()) {

            String comands = st4.nextToken();

            Pattern p2 = Pattern.compile(finmain);
            Matcher matcher2 = p2.matcher(comands);
            if (matcher2.find()) {
                fin++;
            }
        }

        //------------------CONDICION-LLAVES-ABIERTAS/CERRADAS-----------------------------------------------
        if (llavesAbiertas > llavesCerradas) {
            Mensaje = Mensaje + "Error Sintáctico. esperaba (\"}\")\n"; //comprueba con los contadores que todas las llaves hayan sido cerradas
            err = true;
        }
        if (llavesAbiertas < llavesCerradas) {
            Mensaje = Mensaje + "Error Sintáctico. esperaba (\"{\")\n";//comprueba con los contadores que no haya mas llaves cerradas de las que se abrieron
            err = true;
        }
        if ((inicio > fin) && (inicio > 1) && (fin < 1)) {
            Mensaje = Mensaje + "Error Sintáctico. esperaba (\"" + finmain + "\")\n";
            Mensaje = Mensaje + "Error Sintáctico(\"" + main + "\" declarado mas de una vez)\n";
            err = true;
        } else {
            if ((inicio >= 1) && (fin < 1)) {
                Mensaje = Mensaje + "Error Sintáctico. esperaba (\"" + finmain + "\")\n";
                err = true;
            }
        }
        if ((inicio > fin) && (inicio >= 1) && (fin == 1)) {
            Mensaje = Mensaje + "Error Sintáctico(\"" + main + "\" declarado mas de una vez)\n";
            err = true;
        }

        if ((inicio > fin) && (inicio >= 1) && (fin > 1)) {
            Mensaje = Mensaje + "Error Sintáctico(\"" + main + "\" declarado mas de una vez)\n";
            Mensaje = Mensaje + "Error Sintáctico(\"" + finmain + "\" declarado mas de una vez)\n";
            err = true;
        }

        if ((inicio < fin) && (fin > 1) && (inicio < 1)) {
            Mensaje = Mensaje + "Error Sintáctico. esperaba (\"" + main + "\")\n";
            Mensaje = Mensaje + "Error Sintáctico(\"" + finmain + "\" declarado mas de una vez)\n";
            err = true;
        } else {
            if ((fin >= 1) && (inicio < 1)) {
                Mensaje = Mensaje + "Error Sintáctico. esperaba (\"" + main + "\")\n";
                err = true;
            }
        }

        if ((fin > inicio) && (fin >= 1) && (inicio == 1)) {

            Mensaje = Mensaje + "Error Sintàctico(\"" + finmain + "\" declarado mas de una vez)\n";
            err = true;
        }

        if ((fin > inicio) && (fin >= 1) && (inicio > 1) || ((inicio == fin) && (inicio > 1))) {
            Mensaje = Mensaje + "Error Sintáctico(\"" + main + "\" declarado mas de una vez)\n";
            Mensaje = Mensaje + "Error Sintáctico(\"" + finmain + "\" declarado mas de una vez)\n";
            err = true;
        }

        if ((inicio == 0) && (fin == 0)) {
            Mensaje = Mensaje + "Error Sintáctico(no existe inicio de programa)\n";
            err = true;
        }
        //---------------VERIFICA-INICIO/FIN---------------------------
        Pattern p2 = Pattern.compile("^" + R + main);
        Matcher matcher2 = p2.matcher(miCodigo);
        if (!matcher2.find()) {
            Mensaje = Mensaje + "Error Sintáctico (\"" + main + "\" no se declaro al principio del programa) \n";
            err = true;
        }

        Pattern p3 = Pattern.compile(finmain + R + "$");
        Matcher matcher3 = p3.matcher(miCodigo);
        if (!matcher3.find()) {
            Mensaje = Mensaje + "Error Sintáctico (\"" + finmain + "\" no se declaro al final del programa) \n";
            err = true;
        }

        //----------------parte semantico------------------------------------
        fila = 0;
        StringTokenizer st6 = new StringTokenizer(miCodigo, "\n");
        while (st6.hasMoreTokens()) {
            fila++;
            String codeline = st6.nextToken();

            Pattern pNum = Pattern.compile(W + indEntero + W + identificador);
            Matcher matcherNum = pNum.matcher(codeline);
            while (matcherNum.find()) {

                instruction = matcherNum.group();
                Pattern p5 = Pattern.compile(identificador + "$");
                Matcher matcher5 = p5.matcher(instruction);
                if (matcher5.find()) {
                    Var = matcher5.group();
                    if ((ArrayVariablesNUM.contains(Var)) || (ArrayVariablesCAD.contains(Var))) {
                        Mensaje = Mensaje + "La variable \"" + Var + "\" ya está declarada. en la línea:" + fila + "\n";
                        err = true;
                    } else {
                        ArrayVariablesNUM.add(Var);
                        ArrayValoresNUM.add(null);
                    }
                }
            }
            //CADENAS
            Pattern pCad = Pattern.compile(W + indCadena + W + identificador);
            Matcher matcherCad = pCad.matcher(codeline);
            while (matcherCad.find()) {

                instruction = matcherCad.group();
                Pattern p5 = Pattern.compile(identificador + "$");
                Matcher matcher5 = p5.matcher(instruction);
                if (matcher5.find()) {
                    Var = matcher5.group();
                    if (ArrayVariablesCAD.contains(Var) || (ArrayVariablesNUM.contains(Var))) {
                        Mensaje = Mensaje + "La variable \"" + Var + "\" ya está declarada. en la  linea:" + fila + "\n";
                        err = true;
                    } else {
                        ArrayVariablesCAD.add(Var);
                        ArrayValoresCAD.add(null);
                    }
                }
            }

            Pattern pVarNum = Pattern.compile("^(" + identificador + W + "=" + W + "(" + num + "|" + indTexto + texto + indTexto + "))$");
            Matcher matcherVarNum = pVarNum.matcher(codeline);
            while (matcherVarNum.find()) {

                instruction = matcherVarNum.group();
                Pattern p5 = Pattern.compile("^" + identificador);
                Matcher matcher5 = p5.matcher(instruction);
                if (matcher5.find()) {
                    Var = matcher5.group();
                    if (ArrayVariablesNUM.contains(Var)) {
                        Pattern pvalor = Pattern.compile(num + "$");
                        Matcher matchervalor = pvalor.matcher(instruction);
                        if (matchervalor.find()) {
                            Val = Integer.parseInt(matchervalor.group());
                            if (Val >= 9) {
                                Mensaje = Mensaje + Var + ": Introduzca un valor menor a 10. en la línea:" + fila + "\n";
                                ArrayValoresNUM.set(ArrayVariablesNUM.indexOf(Var), Val);
                                err = true;
                            } else {
                                ArrayValoresNUM.set(ArrayVariablesNUM.indexOf(Var), Val);
                            }
                        } else {
                            ArrayValoresNUM.set(ArrayVariablesNUM.indexOf(Var), 0);
                            Mensaje = Mensaje + "Error en tipo de dato \"" + Var + "\" esperaba un digito. en la línea:" + fila + "\n";
                            err = true;
                        }

                    } else {
                        if (ArrayVariablesCAD.contains(Var)) {
                            Pattern pvalor = Pattern.compile(indTexto + texto + indTexto);
                            Matcher matchervalor = pvalor.matcher(instruction);
                            if (matchervalor.find()) {
                                Vals = matchervalor.group();
                                ArrayValoresCAD.set(ArrayVariablesCAD.indexOf(Var), Vals);
                            } else {
                                Mensaje = Mensaje + "Error de tipo de dato \"" + Var + "\" esperaba una cadena. en la línea:" + fila + "\n";
                                err = true;
                            }

                        }
                    }
                }
            }

            Pattern p5 = Pattern.compile(identificador + W + "=" + W + "(" + num + "|" + identificador 
                    + ")" + W + opAritm + W + "(" + num + "|" + identificador + ")");
            Matcher matcher5 = p5.matcher(codeline);
            while (matcher5.find()) {
                calcular = true;
                instruction = matcher5.group();
                Pattern pr = Pattern.compile("^" + identificador);
                Matcher matcherr = pr.matcher(instruction);
                if (matcherr.find()) {
                    Var = matcherr.group();
                    if (ArrayVariablesNUM.contains(Var)) {
                        r = Var;

                    } else {
                        if (ArrayVariablesCAD.contains(Var)) {
                            Mensaje = Mensaje + "La variable \"" + Var + "\" no es un digito. en la línea:" + fila + "\n";
                            err = true;
                        } else {
                            Mensaje = Mensaje + "La variable \"" + Var + "\" no ha sido declarada. en la línea:" + fila + "\n";
                            err = true;
                        }
                    }
                }

                Pattern p6 = Pattern.compile("(" + num + "|" + identificador + ")" + W + opAritm + W + "(" + num + "|" + identificador + ")");
                Matcher matcher6 = p6.matcher(instruction);
                while (matcher6.find()) {

                    Operation = matcher6.group();
                    Pattern p7 = Pattern.compile("^" + identificador);
                    Matcher matcher7 = p7.matcher(Operation);
                    Pattern p8 = Pattern.compile(identificador + "$");
                    Matcher matcher8 = p8.matcher(Operation);
                    Pattern p9 = Pattern.compile(opAritm);
                    Matcher matcher9 = p9.matcher(Operation);
                    if (matcher7.find()) {
                        Var = matcher7.group();
                        if (ArrayVariablesNUM.contains(Var)) {
                            if (ArrayValoresNUM.get(ArrayVariablesNUM.indexOf(Var)) == null) {
                                Mensaje = Mensaje + "La variable \"" + Var + "\" no ha sido iniciada.en la línea: " + fila + "\n";
                            } else {
                                a = ArrayValoresNUM.get(ArrayVariablesNUM.indexOf(Var));
                            }
                        } else {
                            if (ArrayVariablesCAD.contains(Var)) {
                                Mensaje = Mensaje + "La variable \"" + Var + "\" no es un digito.en la línea:" + fila + "\n";
                                err = true;
                            } else {
                                Mensaje = Mensaje + "La variable \"" + Var + "\" no ha sido declarada.en la línea:" + fila + "\n";
                                err = true;
                            }
                        }
                    }

                    if (matcher8.find()) {
                        Var = matcher8.group();
                        if (ArrayVariablesNUM.contains(Var)) {
                            if (ArrayValoresNUM.get(ArrayVariablesNUM.indexOf(Var)) == null) {
                                Mensaje = Mensaje + "La variable \"" + Var + "\" no ha sido iniciada.en la línea: " + fila + "\n";
                            } else {
                                b = ArrayValoresNUM.get(ArrayVariablesNUM.indexOf(Var));
                            }
                        } else {
                            if (ArrayVariablesCAD.contains(Var)) {
                                Mensaje = Mensaje + "La variable \"" + Var + "\" no es un digito.en la línea:" + fila + "\n";
                                err = true;
                            } else {
                                Mensaje = Mensaje + "La variable \"" + Var + "\" no ha sido declarada.en la línea:" + fila + "\n";
                                err = true;
                            }
                        }
                    }
                    if (matcher9.find()) {
                        operando = matcher9.group();
                    }
                }
                calcular = true;
            }
        }

        r = "El resultado de " + a + operando + b + " es " + r;
        if (!err) {
            Pattern p = Pattern.compile(patronInicio);
            Matcher matcher = p.matcher(miCodigo);

            if (matcher.matches()) {
                Mensaje = "Compilado satisfactoriamente \n";
                if (calcular) {
                    ensSuma(a, b, r, operando);
                    crearBat();
                    ejecutarBat();
                }
            }

        }

        return Mensaje;

    }

    public static void ensSuma(int av, int bv, String res, String op) {

        String a = Integer.toHexString(av);
        String b = Integer.toHexString(bv);

        File f;
        FileWriter fichero = null;
        try {
            f = new File("C:\\Users\\danie\\Desktop\\jajajajajajaja\\python.asm");
            f.delete();
            fichero = new FileWriter(f, true);
            BufferedWriter bw = new BufferedWriter(fichero);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(".model small");
            pw.println(".stack");
            pw.println(".data");
            pw.println("mensaje db '" + res + ":','$'");
            pw.println(".code");
            pw.println("main proc");
            pw.println("mov ax,seg mensaje");
            pw.println("mov ds,ax");
            pw.println("mov ah,09");
            pw.println("lea dx,mensaje");
            pw.println("int 21h");

            pw.println("MOV AL," + a.toUpperCase() + "\n");
            pw.println("MOV BL," + b.toUpperCase() + "\n");

            if ("+".equals(op)) {
                pw.println("ADD AL,BL\n");
            }
            if ("-".equals(op)) {
                pw.println("SUB AL,BL\n");
            }
            if ("*".equals(op)) {
                pw.println("MUL BL\n");
            }
            if ("/".equals(op)) {
                pw.println("XOR AX,AX\n");
                pw.println("MOV AL," + b.toUpperCase() + "\n");
                pw.println("MOV BL,AL\n");
                pw.println("MOV AL," + a.toUpperCase() + "\n");
                pw.println("DIV BL\n");
                pw.println("MOV BL,AL\n");

            }
            pw.println("mov dl,al");
            pw.println("add dl,48");
            pw.println("mov ah,02");
            pw.println("int 21h");
            pw.println("mov ax,4c00h");
            pw.println("int 21h");
            pw.println("main endp");
            pw.println("end main");

            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void crearBat() {
        File f;
        FileWriter fichero = null;
        try {

            f = new File("C:\\Users\\danie\\Desktop\\jajajajajajaja\\CrearBat.bat");
            f.delete();
            fichero = new FileWriter(f, true);
            BufferedWriter bw = new BufferedWriter(fichero);
            PrintWriter pw = new PrintWriter(bw);
            pw.println("cd C:\\Users\\danie\\Desktop\\jajajajajajaja\\python.asm");
            pw.println("tasm python.asm");
            pw.println("tlink python.obj");
            pw.println("python");
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public static void ejecutarBat() {
        Runtime ejecutar = Runtime.getRuntime();
        try {            
            ejecutar.exec("cmd.exe /c start C:\\Users\\danie\\Desktop\\jajajajajajaja\\Crearbat.bat");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
