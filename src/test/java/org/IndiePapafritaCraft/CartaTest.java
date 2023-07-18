package org.IndiePapafritaCraft;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartaTest {
    @Test
    public void TestOrdenar() {
        Carta b = new Carta(5, 3);
        Carta a = new Carta(1, 0);
        Carta c = new Carta(7, 0);
        Carta[] ordenado = new Carta[]{a, b, c, };
        Carta[] desordenado =  new Carta[]{b,c,a};
        Carta.ordenar(desordenado);
        for (int x = 0;x<3;x++){
            Assertions.assertEquals(ordenado[x].getNumero(),desordenado[x].getNumero());
        }


    }
}