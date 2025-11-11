package SolucionMisionUada;

import MisionUada.Decision;
import MisionUada.Desplazamiento;
import MisionUada.Estacion;
import MisionUada.Movimiento;

import java.util.ArrayList;

public class BackTraking {
    public ArrayList<Decision> backTraking(
            Estacion estacionInicial,
            Estacion estacionActual,
            ArrayList<Decision> recorridoActual,
            float bateriaActual,
            float tiempoActual,
            ArrayList<Desplazamiento> desplazamientos,
            ArrayList<Decision> mejorDecision,
            ArrayList<Estacion> estacionesVisitadas,
            ArrayList<Estacion> lugaresObligatorios,
            ArrayList<Estacion> lugaresDisponibles
    ) {
        return mejorDecision;
    }
    public boolean esSolucion(ArrayList<Decision> recoridoAEvaluar, ArrayList<Estacion> lugaresObligatorios){
        if (recoridoAEvaluar.get(0).getOrigen().getIdentificadorNumerico() !=
                recoridoAEvaluar.get(recoridoAEvaluar.size()-1).getDestino().getIdentificadorNumerico()){
            return false;
        } else {
            for(Estacion lugarObligatorio : lugaresObligatorios) {
                boolean temp = true; //Verdadero indica que no se visito el lugar
                for (Decision lugarVisitado : recoridoAEvaluar) {
                    if (lugarVisitado.getDestino().getIdentificadorNumerico() == lugarObligatorio.getIdentificadorNumerico()) {
                        temp = false;
                        recoridoAEvaluar.remove(lugarVisitado);
                        break;
                    }
                }
                if (temp) {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean esEstacionObligatoria(Estacion estacionActual, ArrayList<Estacion> lugaresObligatorios){
        for (Estacion lugarObligatorio : lugaresObligatorios) {
            if (lugarObligatorio.getIdentificadorNumerico() == estacionActual.getIdentificadorNumerico()) {
                return true;
            }
        }
        return false;
    }
    public ArrayList<Float> calcularCostosMovimiento(int tiempoBase, Movimiento movimiento){
        ArrayList<Float> resultado = new ArrayList<Float>();
        if (movimiento==Movimiento.SALTAR){
            resultado.add((float) (tiempoBase)/2);
            resultado.add(((float) (tiempoBase)/60)*(float)(2.2));
            return  resultado;
        } else if (movimiento==Movimiento.PATAS_ARRIBA) {
            resultado.add((float) (tiempoBase)*2);
            resultado.add(((float) (tiempoBase)/60)*(float)(0.45));
            return  resultado;
        }
        resultado.add((float) (tiempoBase));
        resultado.add(((float) (tiempoBase)/60));
        return resultado;
    }
    public float actualizarBateriaActual(Estacion estacionActual,float bateriaActual, float costoBateriaMovimiento){
        if (costoBateriaMovimiento > bateriaActual) {
            return bateriaActual-costoBateriaMovimiento;
        }
        bateriaActual = bateriaActual-costoBateriaMovimiento;
        if (estacionActual.getEsAula()) {
            int nroAula = estacionActual.getIdentificadorNumerico();
            float sumaAula=0;
            while(nroAula>0){
                sumaAula += nroAula%10;
                nroAula=(int)(nroAula/10);
            }
            bateriaActual += sumaAula/5;
            if(bateriaActual >= 100){
                return (float) (100);
            }
        }
        return bateriaActual;
    }

}
