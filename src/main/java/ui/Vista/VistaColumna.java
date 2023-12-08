package ui.Vista;

import Solitario.Columna;

public interface VistaColumna {
    void actualizar(Columna columna);
    void pintar(int indice);
    void despintar();
    int getIndice();
}
