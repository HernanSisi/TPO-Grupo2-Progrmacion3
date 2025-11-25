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
        int bateriaInicial = 100;

        //############# CREACION DE ESTACIONES
        ArrayList<Estacion> lugaresDisponibles = new ArrayList<Estacion>();
        // Estación origen y fin
        Estacion estacionInicial = new Estacion("Aula 633", 633, true);
        lugaresDisponibles.add(estacionInicial);

        // Estaciones que se pueden visitar
        Estacion e1 = new Estacion("Starbucks planta baja", 1, false);
        lugaresDisponibles.add(e1);

        Estacion e2 = new Estacion("Sala de Profesores", 2, false);
        lugaresDisponibles.add(e2);

        Estacion e3 = new Estacion("Aula 365", 365, true);
        lugaresDisponibles.add(e3);

        Estacion e4 = new Estacion("Aula 126", 126, true);
        lugaresDisponibles.add(e4);

        Estacion e5 = new Estacion("Aula 613", 613, true);
        lugaresDisponibles.add(e5);

        Estacion e6 = new Estacion("Aula 918", 918, true);
        lugaresDisponibles.add(e6);

        Estacion e7 = new Estacion("biblioteca", 3, false);
        lugaresDisponibles.add(e7);

        Estacion e8 = new Estacion("oficina de alumnos", 4, false);
        lugaresDisponibles.add(e8);

        Estacion e9 = new Estacion("Aula 759", 759, true);
        lugaresDisponibles.add(e9);

        Estacion e10 = new Estacion("Aula 522", 522, true);
        lugaresDisponibles.add(e10);

        Estacion e11 = new Estacion("Aula 236", 236, true);
        lugaresDisponibles.add(e11);

        Estacion e12 = new Estacion("Aula 899", 899, true);
        lugaresDisponibles.add(e12);


        //######## LUGARES QUE HAY QUE VISITAR #######
        ArrayList<Estacion> lugaresObligatorios = new ArrayList<Estacion>();
        lugaresObligatorios.add(e1);
        lugaresObligatorios.add(e4);
        lugaresObligatorios.add(e6);
        lugaresObligatorios.add(e10);
        lugaresObligatorios.add(e8);
        lugaresObligatorios.add(e2);

        //######### CREACION DE LOS POSIBLES MOVIMIENTOS
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


        //####### CREACION DE LOS CAMINOS ##########
        ArrayList<Desplazamiento> desplazamientos = new ArrayList<Desplazamiento>();
        Desplazamiento d0 = new Desplazamiento(estacionInicial,e1,CS,900);
        desplazamientos.add(d0);
        Desplazamiento d1 = new Desplazamiento(estacionInicial,e5, SS,100);
        desplazamientos.add(d1);
        Desplazamiento d2 = new Desplazamiento(e5,estacionInicial,SP,120);
        desplazamientos.add(d2);
        Desplazamiento d3 = new Desplazamiento(e5,e6,SC,300);
        desplazamientos.add(d3);
        Desplazamiento d4 = new Desplazamiento(e6,e3,SC,500);
        desplazamientos.add(d4);
        Desplazamiento d5 = new Desplazamiento(e6,e2,CP,960);
        desplazamientos.add(d5);
        Desplazamiento d6 = new Desplazamiento(e2,e3,SS,550);
        desplazamientos.add(d6);
        Desplazamiento d7 = new Desplazamiento(e2,e6,SP,100);
        desplazamientos.add(d7);
        Desplazamiento d8 = new Desplazamiento(e3,estacionInicial,SC,180);
        desplazamientos.add(d8);
        Desplazamiento d9 = new Desplazamiento(e3,e1,SC,550);
        desplazamientos.add(d9);
        Desplazamiento d10 = new Desplazamiento(e3,e4,CS,500);
        desplazamientos.add(d10);
        Desplazamiento d11 = new Desplazamiento(e1,estacionInicial,CS,900);
        desplazamientos.add(d11);
        Desplazamiento d12 = new Desplazamiento(e1,e4,CP,420);
        desplazamientos.add(d12);
        Desplazamiento d13 = new Desplazamiento(e4,e2,CS,400);
        desplazamientos.add(d13);
        Desplazamiento d14 = new Desplazamiento(e4,e3,SC,540);
        desplazamientos.add(d14);
        Desplazamiento d15 = new Desplazamiento(e4,e1,CS,140);
        desplazamientos.add(d15);
        Desplazamiento d16 = new Desplazamiento(estacionInicial,e7,CSP,340);
        desplazamientos.add(d16);
        Desplazamiento d17 = new Desplazamiento(e7,estacionInicial,CSP,340);
        desplazamientos.add(d17);
        Desplazamiento d18 = new Desplazamiento(e7,e11,CP,540);
        desplazamientos.add(d18);
        Desplazamiento d19 = new Desplazamiento(e11,e1,SPA,150);
        desplazamientos.add(d19);
        Desplazamiento d20 = new Desplazamiento(e11,e8,SS,450);
        desplazamientos.add(d20);
        Desplazamiento d21 = new Desplazamiento(e8,e11,SC,120);
        desplazamientos.add(d21);
        Desplazamiento d22 = new Desplazamiento(e4,e8,SC,633);
        desplazamientos.add(d22);
        Desplazamiento d23 = new Desplazamiento(e8,e9,SPA,330);
        desplazamientos.add(d23);
        Desplazamiento d24 = new Desplazamiento(e9,e8,CP,450);
        desplazamientos.add(d24);
        Desplazamiento d25 = new Desplazamiento(e11,e8,SPA,200);
        desplazamientos.add(d25);
        Desplazamiento d26 = new Desplazamiento(e4,e9,SS,990);
        desplazamientos.add(d26);

        Desplazamiento d27 = new Desplazamiento(e10,e12,SS,684);
        desplazamientos.add(d27);
        Desplazamiento d28 = new Desplazamiento(e10,e6,CS,125);
        desplazamientos.add(d28);
        Desplazamiento d29 = new Desplazamiento(e10,e2,SPA,752);
        desplazamientos.add(d29);
        Desplazamiento d30 = new Desplazamiento(e10,e3,CP,428);
        desplazamientos.add(d30);

        Desplazamiento d31 = new Desplazamiento(e9,e10,CSP,785);
        desplazamientos.add(d31);
        Desplazamiento d32 = new Desplazamiento(e12,e10,SPA,159);
        desplazamientos.add(d32);

        Desplazamiento d33 = new Desplazamiento(e12,e6,CSP,458);
        desplazamientos.add(d33);
        Desplazamiento d34 = new Desplazamiento(e12,e7,SPA,632);
        desplazamientos.add(d34);

        Desplazamiento d35 = new Desplazamiento(e5,e12,SS,759);
        desplazamientos.add(d35);
        Desplazamiento d36 = new Desplazamiento(e7,e5,SC,852);
        desplazamientos.add(d36);





        long startTime = System.nanoTime();
        EncontrarRecorridoUadaImp recorridoUada = new EncontrarRecorridoUadaImp();
        ArrayList<Decision> secuenciaDecisiones = recorridoUada.encontrarSecuenciaRecorridoUada(
                bateriaInicial,
                estacionInicial,
                lugaresDisponibles,
                lugaresObligatorios,
                desplazamientos);

        //EVALUAR EL TIEMPO DEL ALGOTIRMO
        long endTime = System.nanoTime();
        long durationNano = (endTime - startTime);
        double durationMilli = durationNano / 1_000_000.0;
        System.out.println("Tiempo de ejecución: " + durationNano + " nanosegundos");
        System.out.println("Tiempo de ejecución: " + durationMilli + " milisegundos");
        imprimirSecuenciaDecisiones(secuenciaDecisiones);
    }
    public static void imprimirSecuenciaDecisiones(ArrayList<Decision> secuenciaDecisiones) {
        if(secuenciaDecisiones.isEmpty()){
            System.err.println("El el mapa actual no tiene una solucion.");
            return;
        }

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
