package org.IndiePapafritaCraft.ClasesDeLaCpu.UtilidadesCpu.EstadisticasDelJuegoPoker;

import org.IndiePapafritaCraft.ClasesDeLaCpu.UtilidadesCpu.MetodosDeApuestas;
import org.IndiePapafritaCraft.ClasesJuegoPoker.JuegoPoker;

public class DatosEstadisticos {
    DatosEstadisticos(int estimacionDeRondas2){
        estimacionDeRondas = estimacionDeRondas2;
        rondasJugadas = estimacionDeRondas;
        rondasJugadas = 0;
    }
    public JuegoPoker juego;
    public int estimacionDeRondas;
    public int rondasRestantes;
    public int rondasJugadas;
    // Los metodos de actualizacion van a ser métodos que actualicen las variables
    public void ActualizacionFinDeMano(boolean seguirJugando){
        rondasJugadas++;
        rondasRestantes = estimacionDeRondas - rondasJugadas ;
        //anota las manos jugadas en el file si se acaba el juego
        if (seguirJugando==false) MetodosDeApuestas.writerDeManosJugadas(rondasJugadas);
    }
}
