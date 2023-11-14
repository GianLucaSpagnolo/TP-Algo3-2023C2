package Solitario;

import java.io.*;

public class ControladorArchivos {
    /**
     * Clase encargada de todas las acciones correspondientes al chequeo de estados, lectura y
     * escritura de archivos utilizados.
     */


    /**
     * Guarda el estado de la Mesa (con todas sus columnas, cartas y mazos correspondientes)
     * en el stream indicado por el parametro. Puede lanzar IOException en caso de error.
     */
    public static void serializarMesa(OutputStream os, Mesa mesa) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(mesa);
        oos.flush();
    }

    /**
     * Copia el estado de la Mesa (con todas sus columnas, cartas y mazos correspondientes)
     * desde el stream indicado por parametro. Devuelve la instancia de la mesa leida.
     * Puede lanzar IOException o ClassNotFoundException en caso de error.
     */
    public static Mesa deserializarMesa(InputStream is) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(is);
        Mesa mesaLeida = (Mesa) ois.readObject();
        ois.close();
        return mesaLeida;
    }

    /**
     * Borra el contenido en el archivo correspondiente al guardado de la mesa, del stream
     * indicado por parametro, y lo deja en blanco. Puede lanzar IOException en caso de error.
     */
    public static void vaciarArchivo(String ruta) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(ruta);
        pw.close();
    }

    /**
     * Verifica que el archivo correspondiente al guardado de la mesa este vacio.
     */
    public static boolean archivoEstaVacio(String ruta) {
        File file = new File(ruta);
        return file.length() == 0;
    }
}
