package org.IndiePapafritaCraft;

import jdk.jshell.execution.Util;
import org.IndiePapafritaCraft.ClasesDeLaCpu.UtilidadesCpu.MetodosDeApuestas;
import org.IndiePapafritaCraft.ClasesJuegoPoker.JuegoPoker;
import org.IndiePapafritaCraft.ClasesJuegoPoker.UtilidadesJuegoPoker;
import org.IndiePapafritaCraft.ClasesJuegoPoker.UtilidadesJuegoPokerTerminal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {
        try {
            String filepath = "C:\\Users\\Gamer\\OneDrive\\Escritorio\\PokerGame\\src\\main\\java\\org\\IndiePapafritaCraft\\ClasesDeLaCpu\\UtilidadesCpu\\EstadisticasDelJuegoPoker\\CantDeManosJugadasEn10UltimasPartidas.txt";
            System.out.println(MetodosDeApuestas.estimacionDeRondas());

        } catch (Exception x){}
    }
}