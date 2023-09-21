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
        double[][] puntos  = {
        };
        double[] resultados = MetodosDeApuestas.regresionCuadratica(puntos);
        System.out.println("a: " + resultados[0]);
        System.out.println("b: " + resultados[1]);
        System.out.println("c: " + resultados[2]);
    }
}