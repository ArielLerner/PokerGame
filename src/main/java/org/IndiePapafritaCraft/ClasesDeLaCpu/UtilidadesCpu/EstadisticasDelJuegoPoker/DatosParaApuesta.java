package org.IndiePapafritaCraft.ClasesDeLaCpu.UtilidadesCpu.EstadisticasDelJuegoPoker;

import org.IndiePapafritaCraft.ClasesDeLaCpu.UtilidadesCpu.MetodosDeApuestas;

public class DatosParaApuesta {
    public static DatosEstadisticos datosEstadisticos;

    /**
     * Este metodo crea un objeto que contiene variables de valor para la apuesta
     */
    public static DatosEstadisticos singletonDatos() {
        if (datosEstadisticos == null) {
            datosEstadisticos = new DatosEstadisticos(MetodosDeApuestas.estimacionDeRondas());
        }
        return datosEstadisticos;
    }
}
