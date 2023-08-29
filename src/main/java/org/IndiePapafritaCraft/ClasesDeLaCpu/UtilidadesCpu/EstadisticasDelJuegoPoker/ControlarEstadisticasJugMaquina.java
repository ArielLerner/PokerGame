package org.IndiePapafritaCraft.ClasesDeLaCpu.UtilidadesCpu.EstadisticasDelJuegoPoker;

import org.IndiePapafritaCraft.ClasesDeLaCpu.UtilidadesCpu.MetodosDeApuestas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class ControlarEstadisticasJugMaquina {
    public static void anotarManosJugadasCompleto(boolean seguirJugando){
        //primero sumo la mano que acaba de pasar
        try {
            String filePath = "C:\\Users\\Gamer\\OneDrive\\Escritorio\\PokerGame\\src\\main\\java\\org\\IndiePapafritaCraft\\ClasesDeLaCpu\\UtilidadesCpu\\EstadisticasDelJuegoPoker\\manosJugadas";
            BufferedReader reader =new BufferedReader(new FileReader(filePath));
            BufferedWriter writer =new BufferedWriter(new FileWriter(filePath));
            int manosJugadas =  Integer.parseInt(reader.readLine());
            writer.write(manosJugadas+1);
            if (seguirJugando==false) MetodosDeApuestas.anotarCantManosJugadas(manosJugadas+1);
        } catch (Exception x) {System.out.println("Ha habido un error en ControlarEstadisticasJugMaquina" + x);}

}
}
