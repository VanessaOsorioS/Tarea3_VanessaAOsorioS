package Archivo;
import javax.swing.*;

public class main {
    public static void main(String[] args) {

        String extension = JOptionPane.showInputDialog("ingresa la extension");
        Convertidor lector = new Convertidor(extension);
        System.out.println(lector.LeerCsv(extension));



        /*
        Convertidor lector = new Convertidor("C:/Users/vosrb/Documents/SegundoSemestre-2023/Software1/estudiantes.csv");
        System.out.println(lector.VerificarExtension("C:/Users/vosrb/Documents/SegundoSemestre-2023/Software1/estudiantes.csv"));
        */
    }
}
