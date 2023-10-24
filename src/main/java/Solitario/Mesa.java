package Solitario;

import java.io.*;
import java.util.ArrayList;

public class Mesa implements Serializable{
    private final Mazo baraja;
    private Mazo barajaDescarte;
    private final ArrayList<Columna> columnasMesa;
    private final ArrayList<Columna> columnasFinales;

    public Mesa(Mazo baraja) {
        this.baraja = baraja;
        this.columnasMesa = new ArrayList<>();
        this.columnasFinales = new ArrayList<>();
    }

    public void crearBarajaDescarte() {
        this.barajaDescarte = new Mazo();
    }

    public void inicializarColumnaMesa(Columna columna) {
        columnasMesa.add(columna);
    }

    public void inicializarColumnaFinal(Columna columna) {
        columnasFinales.add(columna);
    }

    public Columna columnaMesaEnPosicion(Integer indice) {
        return columnasMesa.get(indice);
    }

    public Columna columnaFinalEnPosicion(Integer indice) {
        return columnasFinales.get(indice);
    }

    public Integer sizeColumnaMesa() {
        return columnasMesa.size();
    }

    public Integer sizeColumnaFinal() {
        return columnasFinales.size();
    }


    public Carta sacarCartaMazo() {
        if (baraja.estaVacio()) {
            return null;
        }
        return baraja.sacarCarta();
    }

    public void insertarCartaMazo(Carta carta) {
        baraja.agregarCarta(carta);
    }

    public void insertarCartaDescarte(Carta carta) {
        barajaDescarte.agregarCarta(carta);
    }

    public Carta sacarCartaDescarte() {
        if ((barajaDescarte == null) || barajaDescarte.estaVacio()) {
            return null;
        }
        return barajaDescarte.sacarCarta();
    }


    /**
     * Guarda el estado de la Mesa (con todas sus columnas, cartas y mazos correspondientes)
     * en el stream indicado por el parametro. Puede lanzar IOException en caso de error.
     */
    public void serializar(OutputStream os) throws IOException {
        ObjectOutputStream bos = new ObjectOutputStream(os);
        bos.writeObject(this);
        bos.flush();
    }

    /**
     * Copia el estado de la Mesa (con todas sus columnas, cartas y mazos correspondientes)
     * desde el stream indicado por parametro. Devuelve la instancia de la mesa leida.
     * Puede lanzar IOException o ClassNotFoundException en caso de error.
     */
    public static Mesa deserializar(InputStream is) throws IOException, ClassNotFoundException {
        ObjectInputStream bis = new ObjectInputStream(is);
        Mesa mesaLeida = (Mesa) bis.readObject();
        bis.close();
        return mesaLeida;
    }
}
