package org.IndiePapafritaCraft.ClasesDeJugador.ClasesJugadorMaquina.UtilidadesCpu.EstadisticasDelJuegoPoker;

import org.IndiePapafritaCraft.ClasesDeJugador.ClasesJugadorMaquina.UtilidadesCpu.MetodosDeApuestas;
import org.IndiePapafritaCraft.ClasesDeJugador.Jugador;
import org.IndiePapafritaCraft.ClasesJuegoPoker.JuegoPoker;
import org.IndiePapafritaCraft.ClasesRestantes.Mano;
import org.IndiePapafritaCraft.ValoresJuntados.TipoDeJugador;

public class SingletonDatos {
    SingletonDatos(int estimacionDeRondas2, String nombreClave2,JuegoPoker juegoPoker) {
        estimacionDeRondas = estimacionDeRondas2;
        rondasJugadas = estimacionDeRondas;
        rondasJugadas = 0;
        nombreClave = nombreClave2;
        juego = juegoPoker;
    }

    public JuegoPoker juego;
    public int estimacionDeRondas;
    public int rondasRestantes;
    public int rondasJugadas;
    private String nombreClave;

    // Los metodos de actualizacion van a ser métodos que actualicen las variables
    // Los metodos de actualizacion publicos son los que debe actualizar el JugadorMaquina que tenga la clave
    public void actualizacionEntreManos(boolean seguirJugando,Jugador x) {
        if (nombreClave == x.getNombre()){
            this.actualizarManosJugadas(seguirJugando);
        }
    }
    public void actualizacionFinDeMano(Jugador x){
        if (nombreClave == x.getNombre()){
            this.actualizarPuntosXY();
        }
    }
    private void actualizarManosJugadas(boolean seguirJugando){
        rondasJugadas++;
        rondasRestantes = estimacionDeRondas - rondasJugadas;
        //anota las manos jugadas en el file si se acaba el juego
        if (seguirJugando == false) MetodosDeApuestas.writerDeManosJugadas(rondasJugadas);
    }

    /**
     * Este metodo solo debe ser actualizado al momento de terminar el juego
     */
    private void actualizarPuntosXY(){
        Jugador jReal = null;
        for (Jugador j : juego.getJugadores()) {
            if (j.claseDeJugador() == TipoDeJugador.JUGADOR_REAL) jReal = j;
        }
        if (jReal!=null && jReal.getEstarEnElJuego()==true){
            Mano mano = jReal.getMano();
            double probDeGanar = mano.valorDeLaMano(juego.numeroMayorDelMazo(),false).getProbDeGanarUnaMano();
            double probDeGanarTotal = probDeGanar * juego.getJugadores().length-1;
            int apuestaRival = jReal.getDineroApostado();
            MetodosDeApuestas.writerDePuntos(apuestaRival,probDeGanarTotal);
        }
    }
}
