package org.IndiePapafritaCraft.ClasesJuegoPoker;

import org.IndiePapafritaCraft.Carta;
import org.IndiePapafritaCraft.ClasesDeLaCpu.UtilidadesCpu.UtilidadesGenerales;
import org.IndiePapafritaCraft.Mano;
import org.IndiePapafritaCraft.ValoresJuntados.ValorDeMano;

import java.util.ArrayList;

public enum ComparacionDeManos {
        // Esto siempre es la primera mano en relacion a la segunda
        GANA,EMPATE,PIERDE;
        //Metodo que dice que mano gana de A y B
    public static ArrayList<Mano> mejoresManos(ArrayList<Mano> mostrarCartas, int nroMasAltoDelMazo){
        int mostrarCartasTamano = mostrarCartas.size();
        //buscar la mejor mano
        Mano mejorMano = mostrarCartas.get(0);
        for (int x=0;x<mostrarCartasTamano;x++){
            ComparacionDeManos resultadoMejorMano = ComparacionDeManos.comparar(mejorMano,mostrarCartas.get(x),nroMasAltoDelMazo);
            if (resultadoMejorMano==PIERDE) mejorMano = mostrarCartas.get(x);
        }
        //buscar cuantas manos empatan a la mejor mano
        ArrayList<Mano> mejoresManos = new ArrayList<Mano>();
        for (int x=0;x<mostrarCartasTamano;x++){
            if (ComparacionDeManos.comparar(mejorMano,mostrarCartas.get(x),nroMasAltoDelMazo)==EMPATE){
                mejoresManos.add(mostrarCartas.get(x));
            }
        }
        return mejoresManos;
    }
    public static ComparacionDeManos comparar(Mano A, Mano B, int nroMasAltoDelMazo){
        ValorDeMano valoeManoA= A.valorDeLaMano(nroMasAltoDelMazo,true);
       ValorDeMano valorManoB = B.valorDeLaMano(nroMasAltoDelMazo,true);
      int ordinalDeA = valoeManoA.ordinal();
      int ordinalDeB = valorManoB.ordinal();
      if (ordinalDeA>ordinalDeB){return GANA;}
      if (ordinalDeA<ordinalDeB){return PIERDE;}
      else {
                            // Caso de que sean iguales
          switch (ordinalDeA){ // como son iguales da lo mismo cual de los ordinales meto en el switch
              case 0: return NADA(A,B);
              case 1: return PAR(A,B);
              case 2: return DOBLEPAR(A,B);
              case 3: return PIERNA(A,B);
              case 4: return ESCALERA(A,B);
              case 5: return COLOR(A,B);
              case 6: return FULL(A,B);
              case 7: return POKER(A,B);
              case 8: return ESCALERADECOLOR(A,B);
              case 9: return ESCALERAREAL(A,B);
              default: System.out.println("error metodo: comparacionDeManos clase:ComparacionDeManos"); return EMPATE;
          }
      }
    }

                //Metodos en caso de que 2 jugadores tengan el mismo ValorDeMano
        public static ComparacionDeManos NADA(Mano A, Mano B){
            for (int x=4;x>=0;x--){
                ComparacionDeManos resultado = ComparacionDeManos.ComparacionDeCartas(A,B,x);
                if (resultado==PIERDE || resultado== GANA)
                    return resultado;
            }
            return EMPATE;
        }
        public static ComparacionDeManos PAR(Mano A , Mano B){
             int nroDeParA =nroQueSeRepite(A);
            int nroDeParB =nroQueSeRepite(B);
            if (nroDeParA>nroDeParB) return ComparacionDeManos.GANA;
            if (nroDeParA<nroDeParB) return ComparacionDeManos.PIERDE;
            //Caso de nro de par igual
            else {
                ArrayList<Carta> cartasNoRepetidasA= UtilidadesGenerales.cartasNoRepetidas(A);
                ArrayList<Carta> cartasNoRepetidasB = UtilidadesGenerales.cartasNoRepetidas(B);
                for (int pos=2; pos>=0;pos--){
                    ComparacionDeManos resultado = ComparacionDeManos.ComparacionDeCartas(A,B,pos);
                    if (resultado==PIERDE || resultado== GANA)
                        return resultado;
                }
            }
            return EMPATE;
        }
        public static ComparacionDeManos DOBLEPAR(Mano A , Mano B){
            //ver cual parGrande gana
            int nMasGrandeRepeA = ComparacionDeManos.nroRepetidoMasGrande(A);
            int nMasGrandeRepeB = ComparacionDeManos.nroRepetidoMasGrande(B);
            if (nMasGrandeRepeA>nMasGrandeRepeB){return GANA;}
            if (nMasGrandeRepeA<nMasGrandeRepeB){return PIERDE;}
            else {
                // ver que parChico es mas grande
                int nMasChicoRepeA = ComparacionDeManos.nroRepetidoMasChico(A);
                int nMasChicoRepeB = ComparacionDeManos.nroRepetidoMasChico(B);
                if (nMasChicoRepeA>nMasChicoRepeB){return GANA;}
                if (nMasChicoRepeA<nMasChicoRepeB){return PIERDE;}
                else {
                    //ver que nroNoRepetido es mas grande
                    ArrayList<Carta> cartasNoRepetidasA= UtilidadesGenerales.cartasNoRepetidas(A);
                    ArrayList<Carta> cartasNoRepetidasB = UtilidadesGenerales.cartasNoRepetidas(B);
                    if (cartasNoRepetidasA.get(0).getNumero()>cartasNoRepetidasB.get(0).getNumero()){return GANA;}
                    if (cartasNoRepetidasA.get(0).getNumero()<cartasNoRepetidasB.get(0).getNumero()){return PIERDE;}
                    else return EMPATE;
                }
            }
        }
        public static ComparacionDeManos PIERNA(Mano A , Mano B){
            int nroDePiernaA =nroQueSeRepite(A);
            int nroDePiernaB =nroQueSeRepite(B);
            if (nroDePiernaA>nroDePiernaB) return ComparacionDeManos.GANA;
            if (nroDePiernaA<nroDePiernaB) return ComparacionDeManos.PIERDE;
            else System.out.println("Hay un error en el método Pierna de ComparacionDeManos");
            return EMPATE;
        }
        public static ComparacionDeManos ESCALERA(Mano A, Mano B){
             int nroMasGrandeA = A.getNumero(4);
             int nroMasGrandeB = B.getNumero(4);
             if (nroMasGrandeA>nroMasGrandeB){return GANA;}
             if (nroMasGrandeA<nroMasGrandeB){return PIERDE;}
             return EMPATE;
        }
        public static ComparacionDeManos COLOR(Mano A, Mano B){
            ComparacionDeManos resultado = ComparacionDeManos.NADA(A,B);
            return resultado;
        }
        public static ComparacionDeManos FULL(Mano A, Mano B){
            //siempre la tercera carta es la de la pierna del full ya que deben ir las tres juntas
            int nroDePiernaA = A.getNumero(2);
            int nroDePiernaB = B.getNumero(2);
            if (nroDePiernaA>nroDePiernaB){return GANA;}
            if (nroDePiernaA<nroDePiernaB){return PIERDE;}
            else{ System.out.println("error metodo: FULL clase: ComparacionDeManos"); return EMPATE;}
        }
        public static ComparacionDeManos POKER(Mano A, Mano B){
            // siempre el nro distinto debe ser el primero o el ultimo ya que los iguales van juntos ordenados
            int nroDePokerA = A.getNumero(2);
            int nroDePokerB = B.getNumero(2);
            if (nroDePokerA>nroDePokerB){return GANA;}
            if (nroDePokerA<nroDePokerB){return PIERDE;}
            else{ System.out.println("error metodo: POKER clase: ComparacionDeManos"); return EMPATE;}
        }
        public static ComparacionDeManos ESCALERADECOLOR(Mano A, Mano B){
            ComparacionDeManos resultado = ComparacionDeManos.ESCALERA(A,B);
            return resultado;
        }
        public static ComparacionDeManos ESCALERAREAL(Mano A, Mano B){
            return EMPATE;
        }
                    //MetodosDeUtilidades
        /**
         * Solo funciona en manos con un par,pierna o poker
         */
        private static int nroQueSeRepite(Mano x){
           boolean[] nrosRepetidos =  UtilidadesGenerales.IndexDeNumerosQueSeRepiten(x);
           for (int a=0;a<5;a++){
               if (nrosRepetidos[a]==true){
                   return x.getNumero(a);
               }
           }
           System.out.println("Ha habido un error en el metodo nrosQueSeRepiten de ComparacionDeManos");
           return -1;
        }

    /**
     * @return este metodo es en relacion a carta a entonces
     * A>b devuelve Gana // A<b devuelve PIERDE // A=b devuelve EMPATE
     */
        private static ComparacionDeManos queCartaEsMayor (Carta A, Carta b){
            if (A.getNumero()>b.getNumero()) return ComparacionDeManos.GANA;
            if (A.getNumero()< b.getNumero()) return ComparacionDeManos.PIERDE;
            else return ComparacionDeManos.EMPATE;
        }

    /**
     * @return devuelve quien gana y si son  iguales devuelve empate
     */
        private static ComparacionDeManos ComparacionDeCartas(Mano A, Mano B, int pos){
            int nroA = A.getNumero(pos);
            int nroB = B.getNumero(pos);
            if (nroA>nroB){return GANA;}
            if (nroA<nroB){return PIERDE;}
            else return EMPATE;
        }
        private static int nroRepetidoMasGrande(Mano x) {
            boolean[] nrosRepetidos =  UtilidadesGenerales.IndexDeNumerosQueSeRepiten(x);
            int nroMasGrandeRepetido=-1;
            for (int a=0;a<5;a++){
                if (nrosRepetidos[a]==true){
                    if (nroMasGrandeRepetido<x.getNumero(a)){
                        nroMasGrandeRepetido = x.getNumero(a);
                    }
                }
            }
            return nroMasGrandeRepetido;
        }

    /**
     *
     * @param x
     * @return si no se repite ningun nro devuelve 100
     */
    private static int nroRepetidoMasChico(Mano x){
            boolean[] nrosRepetidos =  UtilidadesGenerales.IndexDeNumerosQueSeRepiten(x);
            int nroMasChicoRepetido=100;
            for (int a=0;a<5;a++){
                if (nrosRepetidos[a]==true){
                    nroMasChicoRepetido = x.getNumero(a);
                    break;
                }
            }
            return nroMasChicoRepetido;
        }
}

