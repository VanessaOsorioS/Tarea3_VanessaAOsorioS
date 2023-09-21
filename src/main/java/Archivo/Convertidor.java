package Archivo;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Convertidor {
    private String extension;


    public Convertidor(String extension) {
        this.extension = extension;
    }

    /**
     * Metodo para verificar si el archivo existe y tiene la extension .csv requerida
     *
     * @param extension es decir ruta completa del archivo , recibida como String
     * @return true o false , si el archivo tiene una extension valida o no
     * tambien retorna mensajes para el manejo de errores con try catch
     */
    public static boolean VerificarExtension(String extension) {
        try {
            File archivo = new File(extension);
            if (archivo.exists()) {
                String nombreAr = archivo.getName();
                if (nombreAr.endsWith(".csv")) {
                    return true;
                }

            } else {
                throw new IllegalArgumentException("El archivo no existe");
            }

        } catch (Exception e) {
            System.err.println("error al verificar la extension:" + e.getMessage());
        }
        return false;

    }

    /**
     * Este metodo unicamente lee el archivo con extension csv, lee linea por linea
     * y diviendo la cadena en partes por la coma , verifica que la linea tenga las 3 partes , que
     * serian los datos del estudiante
     *
     * @param Extension es decir ruta completa del archivo , recibida como String
     * @return devuelve una lista de mapas que almacena todos los estudiantes
     */
    public  List<Map<String, String>> LeerCsv(String Extension) {
        List<Map<String, String>> datosEstudiante = new ArrayList<>();

        if (VerificarExtension(Extension)) {


            try {

                BufferedReader br = new BufferedReader(new FileReader(Extension));

                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] partes = linea.split(",");
                    if (partes.length == 3) {
                        Map<String, String> estudiante = new HashMap<>();
                        estudiante.put("id", partes[0].trim());
                        estudiante.put("nombre", partes[1].trim());
                        estudiante.put("apellido", partes[2].trim());

                        datosEstudiante.add(estudiante);

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        PasarAJson(extension.substring(0,extension.length()-4),datosEstudiante);
        return datosEstudiante;

    }

    /**
     * Como bufferwriter solo recibe datos string este metodo se encarga de convertirlo
     * @param extension haciendo referencia a la ruta
     * @param datosEstudiante haciendo referencia a la lista de mapas que se creo en el metodo leercsv
     */
 public void PasarAJson(String extension, List<Map<String, String>> datosEstudiante){
     StringBuilder jsonBuilder = new StringBuilder();
     jsonBuilder.append("[\n");

     for (int i = 0; i < datosEstudiante.size(); i++) {
         Map<String, String> mapa = datosEstudiante.get(i);
         jsonBuilder.append("  {\n");
         jsonBuilder.append("    \"id\": \"").append(mapa.get("id")).append("\"\n");
         jsonBuilder.append("    \"nombre\": \"").append(mapa.get("nombre")).append("\",\n");
         jsonBuilder.append("    \"apellido\": \"").append(mapa.get("apellido")).append("\"\n");

         jsonBuilder.append("  }");
         if (i < datosEstudiante.size() - 1) {
             jsonBuilder.append(",");
         }
         jsonBuilder.append("\n");
     }

     jsonBuilder.append("]\n");

     // Escribir el JSON en un archivo
     try (FileWriter fileWriter = new FileWriter(extension + ".json")) {
         fileWriter.write(jsonBuilder.toString());
         System.out.println("Archivo JSON creado con éxito.");
     } catch (IOException e) {
         e.printStackTrace();
     }
 }
}
