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
            ArrayList<Desplazamiento> desplazamientos,//no se copia, queda igual
            ArrayList<Decision> mejorDecision,
            ArrayList<Estacion> estacionesVisitadas,
            ArrayList<Estacion> lugaresObligatorios,
            int interaccion
    ) {
        //evalua si se regreso a la estacion inicial, si lo es devuleve el recorrido actual. evalua si esta vacio para evitar errores en la primera llamada
        if (!recorridoActual.isEmpty() && estacionInicial.getIdentificadorNumerico() == estacionActual.getIdentificadorNumerico()) {
            boolean esSolucionRecorridoActual = esSolucion(recorridoActual,lugaresObligatorios);
            if (!mejorDecision.isEmpty()) {
                if (!esSolucionRecorridoActual || (esSolucionRecorridoActual &&
                        recorridoActual.get(recorridoActual.size() - 1).getTiempoAcumulado() >
                                mejorDecision.get(mejorDecision.size() - 1).getTiempoAcumulado())) {
                    return mejorDecision;
                }
            }
            return recorridoActual;
        }

        //busco los caminos habitados para esta estacion
        ArrayList<Desplazamiento> caminosHabilitados = filtrarPosiblesCaminos(
                buscarCaminosDesdeEstacion(estacionActual, desplazamientos),
                estacionesVisitadas,
                lugaresObligatorios,
                estacionInicial,
                estacionActual);
        if (caminosHabilitados.isEmpty()){
            imprimirEstacios(interaccion);
            System.out.print(" Sin posibles Caminos desde " + estacionActual.getNombre() + "\n");
            if (!mejorDecision.isEmpty()) {
                return mejorDecision;
            } else {
                return recorridoActual;
            }
        }

        //se recorre cada posible camino
        for (Desplazamiento camino: caminosHabilitados) {
            imprimirEstacios(interaccion);
            System.out.print("; B: " + bateriaActual + " T: " + tiempoActual + "   ");
            if (recorridoActual.isEmpty()) {
                System.out.print(estacionActual.getNombre() + " >> ");
            }
            imprimirRecorido(recorridoActual);

            //se recorre cada forma de moverse que tiene
            for (Movimiento movimientoSeleccionado: camino.getMovimientosPermitidos()){
                imprimirEstacios(interaccion);
                System.out.print(" evaluando: " + camino.getDestino().getNombre() + "; Modo: " + movimientoSeleccionado + "\n");
                //crear copias de las variables para no editarlas
                Estacion copiaestacionActual = new Estacion(estacionActual.getNombre(),estacionActual.getIdentificadorNumerico(),estacionActual.getEsAula());
                ArrayList<Decision> copiarecorridoActual = new ArrayList<>(recorridoActual);
                ArrayList<Estacion> copiaEstacionesVisitadas = new ArrayList<>(estacionesVisitadas);
                ArrayList<Estacion> copiaLugaresObligatorios = new ArrayList<>(lugaresObligatorios);
                //agrego la estacion actual a la lista de visitados
                if (estacionInicial.getIdentificadorNumerico() != estacionActual.getIdentificadorNumerico()) {
                estacionesVisitadas.add(estacionActual);
                }
                //elimino la estacion actual de corresponder de las estaciones obligatorias a visitar
                esEstacionObligatoria(estacionActual,lugaresObligatorios);


                ArrayList<Float> costos = calcularCostosMovimiento(camino.getTiempoBase(), movimientoSeleccionado);
                float bateriaTemp = actualizarBateriaActual(camino.getDestino(), bateriaActual,costos.get(1));
                //PODA POR BATERIA
                if (bateriaTemp < 0){
                    imprimirEstacios(interaccion);
                    System.out.print(" PODA POR BATERIA "+ bateriaTemp + " // " + camino.getOrigen().getNombre() + " >>> " + camino.getDestino().getNombre()+"\n");
//                    break;
                } else {

                    // poda por tiempo
                    if (!mejorDecision.isEmpty() && mejorDecision.get(mejorDecision.size() - 1).getTiempoAcumulado() < tiempoActual + costos.get(0)) {
                        imprimirEstacios(interaccion);
                        System.out.print(" PODA POR TIEMPO " + mejorDecision.get(mejorDecision.size() - 1).getTiempoAcumulado() + " < " + (tiempoActual + costos.get(0)) + " // " + camino.getOrigen().getNombre() + " >>> " + camino.getDestino().getNombre() + "\n");
//                        break;
                    } else {

                        //guardamos la decision que se tomo
                        Decision decisionTomada = new Decision(
                                estacionActual,
                                camino.getDestino(),
                                movimientoSeleccionado,
                                bateriaTemp,
                                tiempoActual + costos.get(0)
                        );
                        recorridoActual.add(decisionTomada);

                        //actualizo la estacion actual
                        estacionActual = new Estacion(camino.getDestino().getNombre(), camino.getDestino().getIdentificadorNumerico(), camino.getDestino().getEsAula());

                        //itero sobre el backtraking para recorrer desde la siguiente estacion
                        ArrayList<Decision> decisionObtenida = backTraking(
                                estacionInicial,
                                estacionActual,
                                recorridoActual,
                                bateriaTemp,
                                tiempoActual + costos.get(0),
                                desplazamientos,
                                mejorDecision,
                                estacionesVisitadas,
                                lugaresObligatorios,
                                interaccion + 1
                        );

                        if (esSolucion(decisionObtenida, lugaresObligatorios)) {
                            //imprimirRecorido(decisionObtenida);
                            if (mejorDecision.isEmpty()) {
                                mejorDecision = decisionObtenida;
                                imprimirEstacios(interaccion);
                                System.out.print(" Se guardo un recorido como mejor decision x estar vacio; " + mejorDecision.size() + "\n");
                            } else if (mejorDecision.get(mejorDecision.size() - 1).getTiempoAcumulado() > decisionObtenida.get(decisionObtenida.size() - 1).getTiempoAcumulado()) {
                                mejorDecision = decisionObtenida;
                                imprimirEstacios(interaccion);
                                System.out.print(" Se guardo un recorido como mejor decision x mejor tiempo; " + mejorDecision.size() + "\n");
                            }
                        }
                    }
                }

                //revertir desiciones
                estacionesVisitadas = copiaEstacionesVisitadas;
                lugaresObligatorios = copiaLugaresObligatorios;
                estacionActual = copiaestacionActual;
                recorridoActual = copiarecorridoActual;
                imprimirEstacios(interaccion);
                System.out.print(" CM/FM\n");
            }
            imprimirEstacios(interaccion);
            System.out.print(" FIN\n");
        }
        return mejorDecision;
    }






    public boolean esSolucion(ArrayList<Decision> recoridoAEvaluar, ArrayList<Estacion> lugaresObligatorios){
        if (recoridoAEvaluar.isEmpty()) {
            return false;
        }
        if (recoridoAEvaluar.get(0).getOrigen().getIdentificadorNumerico() !=
                recoridoAEvaluar.get(recoridoAEvaluar.size()-1).getDestino().getIdentificadorNumerico()){
            return false;
        }
        for(Estacion lugarObligatorio : lugaresObligatorios) {
            boolean temp = true; //Verdadero indica que no se visito el lugar
            for (Decision lugarVisitado : recoridoAEvaluar) {
                if (lugarVisitado.getDestino().getIdentificadorNumerico() == lugarObligatorio.getIdentificadorNumerico()) {
                    temp = false;
                }
            }
            if (temp) {
                return false;
            }
        }
        return true;
    }
    public void esEstacionObligatoria(Estacion estacionActual, ArrayList<Estacion> lugaresObligatorios) {
        for (int i = lugaresObligatorios.size() - 1; i >= 0; i--) {
            Estacion lugarObligatorio = lugaresObligatorios.get(i);
            if (lugarObligatorio.getIdentificadorNumerico() == estacionActual.getIdentificadorNumerico()) {
                lugaresObligatorios.remove(i);
                return;
            }
        }
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
    public float actualizarBateriaActual(Estacion estacionActual, float bateriaActual, float costoBateriaMovimiento){
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
    public ArrayList<Desplazamiento> buscarCaminosDesdeEstacion(Estacion estacionActual, ArrayList<Desplazamiento> desplazamientos){
        ArrayList<Desplazamiento> posiblesCaminos = new ArrayList<>();
        for (Desplazamiento desplazamiento : desplazamientos) {
            if (desplazamiento.getOrigen().getIdentificadorNumerico() == estacionActual.getIdentificadorNumerico()){
                posiblesCaminos.add(desplazamiento);
            }
        }
        return posiblesCaminos;
    }
    public ArrayList<Desplazamiento> filtrarPosiblesCaminos(ArrayList<Desplazamiento> posiblesCaminos,
                                                            ArrayList<Estacion> estacionesVisitadas,
                                                            ArrayList<Estacion> lugaresObligatorios,
                                                            Estacion estacionOrigen,
                                                            Estacion estacionActual){

        for (int i = posiblesCaminos.size() - 1; i >= 0; i--) {

            Desplazamiento temp = posiblesCaminos.get(i);
            Estacion analizandoEstacion = temp.getDestino();
            boolean debeEliminarse = false;

            //el destino es igual al origen actual
            if (analizandoEstacion.getIdentificadorNumerico() == estacionOrigen.getIdentificadorNumerico()) {
                // Solo se elimina si todavía hay lugares obligatorios por visitar
                if (!lugaresObligatorios.isEmpty()) {
                    debeEliminarse = true;
                }
                if ((lugaresObligatorios.size() == 1) && (lugaresObligatorios.get(0).getIdentificadorNumerico() == estacionActual.getIdentificadorNumerico())) {
                    debeEliminarse = false;
                }
            }

            // la estación de destino ya fue visitada
            for (Estacion visitada : estacionesVisitadas) {
                // Asumiendo que dos Estacion son iguales si su identificador numérico es igual.
                if (visitada.getIdentificadorNumerico() == temp.getDestino().getIdentificadorNumerico()) {
                    debeEliminarse = true;
                }

            }

            // Eliminación
            if (debeEliminarse) {
                posiblesCaminos.remove(i);
            }
        }

        return posiblesCaminos;
    }

    public void imprimirRecorido(ArrayList<Decision> desiciones){
        if (desiciones.isEmpty()) {
            System.out.print("\n");
            return;
        }
        System.out.print("R: " + desiciones.get(0).getOrigen().getNombre() + " >> ");
        for (Decision desicion : desiciones) {
            System.out.print(desicion.getDestino().getNombre() + " >> ");
        }
        System.out.print("\n");
    }
    public void imprimirEstacios(int interaccion){
        for (int i = 0; i<interaccion; i++) {
            System.out.print("    ");
        }
        System.out.print("i=" + interaccion);
    }
}