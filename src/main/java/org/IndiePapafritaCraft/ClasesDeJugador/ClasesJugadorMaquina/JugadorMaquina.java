package org.IndiePapafritaCraft.ClasesDeJugador.ClasesJugadorMaquina;

import org.IndiePapafritaCraft.ClasesDeJugador.ClasesJugadorMaquina.UtilidadesCpu.EstadisticasDelJuegoPoker.Singleton;
import org.IndiePapafritaCraft.ClasesDeJugador.ClasesJugadorMaquina.UtilidadesCpu.MetodosDeApuestas;
import org.IndiePapafritaCraft.ClasesDeJugador.ClasesJugadorMaquina.UtilidadesCpu.utilidades.*;
import org.IndiePapafritaCraft.ClasesDeJugador.Jugador;
import org.IndiePapafritaCraft.ClasesJuegoPoker.JuegoPoker;
import org.IndiePapafritaCraft.ClasesRestantes.Mano;
import org.IndiePapafritaCraft.ValoresJuntados.*;

import java.util.ArrayList;

public class JugadorMaquina extends Jugador {
    public JugadorMaquina(Mano mano, int balanceInicial, JuegoPoker juegoDePoker, String nombre, double toleracionDeEstrategia2) {
        super(juegoDePoker,mano,balanceInicial,nombre);
        toleracionDeEstrategia = toleracionDeEstrategia2;
    }

    /**
     * La estrategia no empieza estando inicializada sino que se inicializa cuando se llama al metodo getEst
     */
     private Estrategia est;

    public double getToleracionDeEstrategia() {
        return toleracionDeEstrategia;
    }

    public void setToleracionDeEstrategia(double toleracionDeEstrategia) {
        this.toleracionDeEstrategia = toleracionDeEstrategia;
    }

    private double toleracionDeEstrategia;

    public void verApuesta() {
        int cantAsubir = this.getEst().singletonApuestaMax(this);
        this.apostarXcantidad(cantAsubir);
    }
    public boolean[] cambioCartas(){
        return est.getCartasParaCambiar();
    }
    public void cambiarMano (Mano x){manoDeJugador = x;}
    //Los metodos de aviso no necesitan ser implementados en la màquina
    public void pagarLuzAviso(){}
    public void repartirCartasAviso(){}

    /**
     * Para diferenciar si es la primera apuesta o la segunda apuesta voy a ver cual es la ParteDelJuego en la que esta
     */
    public void jugadorVeApuestaAviso(Jugador x){}
    public void cambioCartasAviso(Jugador x, int cartasCambiadas){}
    public void finalDelJuegoAviso(ArrayList<Mano> mostrarCartas, ArrayList<Jugador> jugadoresGanadores , int pozo){
        Singleton.get(this).actualizacionFinDeMano(this);}
    public void entreManosAviso(boolean[] seguirConElJuego){
        Singleton.get(this).actualizacionEntreManos(seguirConElJuego[0],this);}
    public TipoDeJugador claseDeJugador(){return TipoDeJugador.JUGADOR_DE_LA_MAQUINA;}
    /**
     *
     * @param toleracionDeEstrategiasPeores debe contener un valor entre 0 y 1 que es el margen que se tolera para elegir estrategias
     * @return inicializa apuestaMax en -1
     */
    public Estrategia elegirEstrategia(double toleracionDeEstrategiasPeores) {
        //estrategias
        Estrategia[] estrategias = new Estrategia[5];
        MapaFullProbs probs  = LeerProbs.leerProbGenerales(juego.cantDeCartasDelMazo());
        estrategias[0] = this.estrategiaParPiernaPoker(probs);
        estrategias[1] = this.estrategiaParDoble(probs);
        estrategias[2] = this.estrategiaEscalera(probs);
        estrategias[3] = this.estrategiaColor(probs);
        estrategias[4] = this.estrategiaServida();
        //probsDeGanar
        int indexProbMasAlta = UtilidadesGenerales.indexProbMasAlta(estrategias);
        ArrayList<Estrategia> mejoresEstrategias = UtilidadesGenerales.mejoresEstrategias(estrategias,indexProbMasAlta,toleracionDeEstrategiasPeores);
        //nroRandom
        double nroMaximo = 0;
        for (int posMejoresEst = 0; posMejoresEst<mejoresEstrategias.size();posMejoresEst++){
            nroMaximo = nroMaximo +  mejoresEstrategias.get(posMejoresEst).getProbDeGanarObjetiva();
        }
        double nroRandom = Math.random() * nroMaximo;
        double a=0;
        int indexEstrategiaElegida = -1;
        for (int x=0;x<mejoresEstrategias.size();x++){
            a = a + mejoresEstrategias.get(x).getProbDeGanarObjetiva();
            if (nroRandom<a){
                indexEstrategiaElegida=x;
                break;
            }
        }
        this.est = mejoresEstrategias.get(indexEstrategiaElegida);
        this.est.setApuestaMaxEnMenos1();
        return mejoresEstrategias.get(indexEstrategiaElegida);
    }
    public double probDeGanar(ValorYProbabilidad[] x){
        double probDeGanar = 0;
         ValorYProbabilidad[] arrayDeValores = x;
         int valoresLenght = arrayDeValores.length;
        for (int nroDeValor =0;nroDeValor<valoresLenght;nroDeValor++){
            ValorYProbabilidad valor = arrayDeValores[nroDeValor];
            double probDeSalir = valor.getProbabilidad();
            double probDeGanarActual =valor.getValor().getProbDeGanarUnaMano()*1.0*(juego.cantDeJugadores()-1);
            probDeGanar= probDeGanar + probDeSalir*probDeGanarActual;
        }
        return probDeGanar;
    }
    public Estrategia estrategiaColor(MapaFullProbs probs){
        int[] conteoDePalos = UtilidadesParaColor.conteoDePalos(manoDeJugador);
        int paloConMasCartas = UtilidadesParaColor.NumeroDePaloConMasCartas(conteoDePalos);
        ValorYProbabilidad[] probabilidades = UtilidadesParaColor.probabilidadColor(paloConMasCartas,conteoDePalos,juego, probs);
        boolean[] cambioCartas = UtilidadesParaColor.cambiarCartasParaColor(paloConMasCartas,manoDeJugador);
        double probDeGanar = probDeGanar(probabilidades);
        return new Estrategia(probabilidades,cambioCartas,probDeGanar,this.manoDeJugador);
    }
    public Estrategia estrategiaEscalera(MapaFullProbs probs) {
        int[] memorizarResultados = UtilidadesParaEscalera.memorizarResultadosDeCadaEscaleras(juego, manoDeJugador);
        int contadorCartasParaEsc = UtilidadesGenerales.nroMasGrandeEnUnArray(memorizarResultados);
        ValorYProbabilidad[] probsFinales =
                {       new ValorYProbabilidad(0,ValorDeMano.NADA),
                        new ValorYProbabilidad(0,ValorDeMano.PAR),
                        new ValorYProbabilidad(0,ValorDeMano.DOBLEPAR),
                        new ValorYProbabilidad(0, ValorDeMano.PIERNA),
                        new ValorYProbabilidad(0, ValorDeMano.ESCALERA)  };
        boolean[] cambioCartasFinal = {false, false, false, false, false};
        int cantDeEscaleras = memorizarResultados.length;
        for (int escaleraActual = 0; escaleraActual < cantDeEscaleras; escaleraActual++) {
            if (memorizarResultados[escaleraActual] == contadorCartasParaEsc) {
                boolean[] cambioCartasDeEsteCaso = UtilidadesParaEscalera.cambiarCartasParaEscalera(escaleraActual, manoDeJugador);
                ValorYProbabilidad[] probsDeEsteCaso = UtilidadesParaEscalera.posibilidadesDeEscalera
                        (juego.cantDeNumerosDelMazo(), contadorCartasParaEsc, cambioCartasDeEsteCaso, manoDeJugador, juego,probs.mapa.get(ValorDeMano.ESCALERA));
                if (probsDeEsteCaso[4].getProbabilidad() >= probsFinales[4].getProbabilidad()) {
                    probsFinales = probsDeEsteCaso;
                    cambioCartasFinal = cambioCartasDeEsteCaso;
                }
            }
        }
        double probDeGanar = probDeGanar(probsFinales);
        return new Estrategia(probsFinales, cambioCartasFinal,probDeGanar,this.manoDeJugador);
    }
    public Estrategia estrategiaParDoble(MapaFullProbs probs){
        boolean[] indexesDeNumerosRepetidos = UtilidadesGenerales.IndexDeNumerosQueSeRepiten(manoDeJugador);
        int nroDeParesOPiernas = UtilidadesGenerales.nroDeParesPiernasPokerQueHayEnLaMano(manoDeJugador,indexesDeNumerosRepetidos);
        int indexDeCartaMasGrandeSobrante = UtilidadesParDoble.indexDeNroMasGrandeQueNoSeRepite(manoDeJugador,indexesDeNumerosRepetidos);
        boolean[] cambioCartas =UtilidadesParDoble.cambioCartasParaParDoble(manoDeJugador,juego.numeroMayorDelMazo(),nroDeParesOPiernas,indexDeCartaMasGrandeSobrante);
        ValorYProbabilidad[] valoresYprob = UtilidadesParDoble.probabilidadesParDoble
                (nroDeParesOPiernas, juego.numeroMayorDelMazo(),cambioCartas,indexDeCartaMasGrandeSobrante,manoDeJugador,probs.mapa.get(ValorDeMano.DOBLEPAR));
        double probDeGanar = probDeGanar(valoresYprob);
        return new Estrategia(valoresYprob,cambioCartas,probDeGanar,this.manoDeJugador);
    }
    public Estrategia estrategiaParPiernaPoker(MapaFullProbs probs){
        boolean[]cambioCartas = UtilidadesParaParPiernaPoker.cambiarCartasParaPoker(manoDeJugador);
        int nroCartIguales  = UtilidadesParaParPiernaPoker.nroDeCartasIguales(cambioCartas);
                FullProb[]  fullProb = probs.mapa.get(ValorDeMano.POKER);
                ValorYProbabilidad[] valorYprob  = fullProb[nroCartIguales-1].getProb();
        double probDeGanar = probDeGanar(valorYprob);
        return new Estrategia(valorYprob,cambioCartas,probDeGanar,this.manoDeJugador);
    }
    public Estrategia estrategiaServida(){
        double probabilidad = 1.0;
        boolean[] cambioCartas =new boolean[]{false,false,false,false,false};
        ValorDeMano valor  = manoDeJugador.valorDeLaMano(juego.numeroMayorDelMazo(),true);
        //limito la probabilidad de la estrategia servida cuando se puede mejorar
        if (valor==ValorDeMano.NADA||valor == ValorDeMano.PAR||valor ==ValorDeMano.DOBLEPAR||valor ==ValorDeMano.PIERNA){
            probabilidad=probabilidad*0.1;
        }
        ValorYProbabilidad[] x = new ValorYProbabilidad[] { new ValorYProbabilidad(probabilidad,valor )};
        double probDeGanar = probDeGanar(x);
        return new Estrategia(x,cambioCartas,probDeGanar,this.manoDeJugador);
    }
    public Estrategia getEst(){
        if (this.est == null || est.getMano() != this.manoDeJugador) {est = this.elegirEstrategia(toleracionDeEstrategia);}
        return est;
    }
}

