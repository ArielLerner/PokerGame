package org.IndiePapafritaCraft.ClasesDeJugador.ClasesJugadorMaquina;

import org.IndiePapafritaCraft.ValoresJuntados.FullProb;
import org.IndiePapafritaCraft.ValoresJuntados.MapaFullProbs;
import org.IndiePapafritaCraft.ValoresJuntados.ValorDeMano;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class LeerProbs {
    public static MapaFullProbs leerProbGenerales(int cantDeCartasDelMazo) {
        HashMap<ValorDeMano, FullProb[]> posibilidadesGenerales = new HashMap<>();
        try {
            String filePath = "C:\\Users\\Gamer\\OneDrive\\Escritorio\\PokerGame\\src\\main\\java\\org\\IndiePapafritaCraft\\ClasesDeJugador\\ClasesJugadorMaquina\\UtilidadesCpu\\EstadisticasDelJuegoPoker\\Files\\probsEstrategia\\52CartasProbs";
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (LeerProbs.stringConNada(line) == false) {
                ValorDeMano key = ValorDeMano.valueOf(line);
                int nroDeFullProbs = Integer.parseInt(reader.readLine());
                FullProb[] fullProbs = leerFullProbs(nroDeFullProbs,reader);
                posibilidadesGenerales.put(key,fullProbs);
                line = reader.readLine();
            }
        } catch (IOException x) {
            System.out.println("Ha habido un error en lector de posibilidades" + x);
        }
        return new MapaFullProbs(posibilidadesGenerales);
    }

    /**
     * @return Devuelve true si el string esta null o estÃ¡ lleno de espacios
     */
    private static boolean stringConNada(String x) {
        if (x == null) {
            return true;
        }
        if (x.isBlank() == true) {
            return true;
        }
        return false;
    }

    /**
     * lee un fullProb
     */
    private static FullProb leerFullProb(BufferedReader reader) {
        double[] probs = new double[10];
        String nombre ="error";
        try {
            nombre  = reader.readLine();
            for (int x = 0; x < 10; x++) {
                probs[x] = Double.parseDouble(reader.readLine());
            }
        } catch (IOException x) {
            System.out.println("ha habido un error en leerFullprob");
        }
        return new FullProb (probs, nombre);
    }
    private static FullProb[] leerFullProbs(int cantDeFullProbs, BufferedReader reader){
        FullProb[] fullProbs = new FullProb[cantDeFullProbs];
        for (int x=0;x<cantDeFullProbs;x++){
            fullProbs[x] = leerFullProb(reader);
        }
        return fullProbs;
    }
}

