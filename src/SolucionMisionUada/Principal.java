package SolucionMisionUada;

import MisionUada.Decision;
import MisionUada.Desplazamiento;
import MisionUada.Estacion;
import MisionUada.Movimiento;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Principal {
    public static void main(String[] args) {

        // Ejemplo de datos de entrada

        int bateriaInicial = 100;

        ArrayList<Estacion> lugaresDisponibles = new ArrayList<Estacion>();
        ArrayList<Estacion> lugaresObligatorios = new ArrayList<Estacion>();

        // Estación origen y fin
        Estacion e = new Estacion("Aula 633", 633, true);

        Estacion e1 = new Estacion("Starbucks planta baja", 1, false);
        Estacion e2 = new Estacion("Sala de Profesores", 2, false);
        Estacion e3 = new Estacion("Aula 365", 365, true);
        Estacion e4 = new Estacion("Aula 126", 126, true);
        Estacion e5 = new Estacion("Aula 613", 613, true);
        Estacion e6 = new Estacion("Aula 918", 918, true);

        lugaresDisponibles.add(e);
        lugaresDisponibles.add(e1);
        lugaresDisponibles.add(e2);
        lugaresDisponibles.add(e3);
        lugaresDisponibles.add(e4);
        lugaresDisponibles.add(e5);
        lugaresDisponibles.add(e6);

        lugaresObligatorios.add(e1);
        lugaresObligatorios.add(e4);
        lugaresObligatorios.add(e6);

        ArrayList<Desplazamiento> desplazamientos = new ArrayList<Desplazamiento>();

        ArrayList<Movimiento> CSP = new ArrayList<Movimiento>();
        CSP.add(Movimiento.CAMINAR);
        CSP.add(Movimiento.SALTAR);
        CSP.add(Movimiento.PATAS_ARRIBA);

        ArrayList<Movimiento> CS = new ArrayList<Movimiento>();
        CS.add(Movimiento.CAMINAR);
        CS.add(Movimiento.SALTAR);

        ArrayList<Movimiento> CP = new ArrayList<Movimiento>();
        CP.add(Movimiento.CAMINAR);
        CP.add(Movimiento.PATAS_ARRIBA);

        ArrayList<Movimiento> SPA = new ArrayList<Movimiento>();
        SPA.add(Movimiento.SALTAR);
        SPA.add(Movimiento.PATAS_ARRIBA);

        ArrayList<Movimiento> SC = new ArrayList<Movimiento>();
        SC.add(Movimiento.CAMINAR);

        ArrayList<Movimiento> SP = new ArrayList<Movimiento>();
        SP.add(Movimiento.PATAS_ARRIBA);

        ArrayList<Movimiento> SS = new ArrayList<Movimiento>();
        SS.add(Movimiento.SALTAR);

        Desplazamiento d0 = new Desplazamiento(e,e1,CS,900);
        Desplazamiento d1 = new Desplazamiento(e,e5, SS,100);
        Desplazamiento d2 = new Desplazamiento(e5,e,SP,120);
        Desplazamiento d3 = new Desplazamiento(e5,e6,SC,300);
        Desplazamiento d4 = new Desplazamiento(e6,e3,SC,500);
        Desplazamiento d5 = new Desplazamiento(e6,e2,CP,960);
        Desplazamiento d6 = new Desplazamiento(e2,e3,SS,550);
        Desplazamiento d7 = new Desplazamiento(e2,e6,SP,100);
        Desplazamiento d8 = new Desplazamiento(e3,e,SC,180);
        Desplazamiento d9 = new Desplazamiento(e3,e1,SC,550);
        Desplazamiento d10 = new Desplazamiento(e3,e4,CS,500);
        Desplazamiento d11 = new Desplazamiento(e1,e,CS,900);
        Desplazamiento d12 = new Desplazamiento(e1,e4,CP,420);
        Desplazamiento d13 = new Desplazamiento(e4,e2,CS,4000);
        Desplazamiento d14 = new Desplazamiento(e4,e3,SC,540);
        Desplazamiento d15 = new Desplazamiento(e4,e1,CS,140);


        desplazamientos.add(d0);
        desplazamientos.add(d1);
        desplazamientos.add(d2);
        desplazamientos.add(d3);
        desplazamientos.add(d4);
        desplazamientos.add(d5);
        desplazamientos.add(d6);
        desplazamientos.add(d7);
        desplazamientos.add(d8);
        desplazamientos.add(d9);
        desplazamientos.add(d10);
        desplazamientos.add(d11);
        desplazamientos.add(d12);
        desplazamientos.add(d13);
        desplazamientos.add(d14);
        desplazamientos.add(d15);

        EncontrarRecorridoUadaImp recorridoUada = new EncontrarRecorridoUadaImp();
        ArrayList<Decision> secuenciaDecisiones = recorridoUada.encontrarSecuenciaRecorridoUada(bateriaInicial, e, lugaresDisponibles, lugaresObligatorios, desplazamientos);
        imprimirSecuenciaDecisiones(secuenciaDecisiones);
    }

    public static void imprimirDesplzamientos(ArrayList<Decision> desplazamientos){
        for(Decision d: desplazamientos){
            System.out.println(d.getDestino().getNombre());
        }
    }
    public static void imprimirSecuenciaDecisiones(ArrayList<Decision> secuenciaDecisiones) {
        StringBuilder sb = new StringBuilder();

        for (int i=0; i < secuenciaDecisiones.size();i++) {
            Decision decision = secuenciaDecisiones.get(i);
            int indice = i+1;
            String decisionString = "Decision numero "+indice+"\n"+"Origen: "+decision.getOrigen().getNombre()+"\n"+"Destino: "+decision.getDestino().getNombre()+"\n"+"Movimiento empleado: "+decision.getMovimientoEmpleado().toString()+"\n"+"Bateria Remanente: "+decision.getBateriaRemanente()+"\n"+"Tiempo Acumulado: "+decision.getTiempoAcumulado()+" segundos \n\n";
            sb.append(decisionString);
        }

        // Guardar en archivo al mismo nivel del programa
        String rutaArchivo = Paths.get(System.getProperty("user.dir"), "output.txt").toString();

        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            writer.write(sb.toString());
            System.out.println("Array de decisiones guardado en: " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo TXT: " + e.getMessage());
        }
    }
}
