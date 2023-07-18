package org.IndiePapafritaCraft.ValoresJuntados;

import java.util.HashMap;

public class MapaFullProbs {
    public HashMap<ValorDeMano, FullProb[]> mapa = new HashMap<>();
    public MapaFullProbs(HashMap<ValorDeMano,FullProb[]> x ){
        mapa = x;
    }
}

