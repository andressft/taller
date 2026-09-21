package automovil;

public class AutoFlotaApp {

    public static void main(String[] args) {

        System.out.println("=================================================");
        System.out.println(" AUTOFLOTA S.A.S. - Prototipo de gestion de flota");
        System.out.println("=================================================\n");

        System.out.println(">>> PARTE E.a) Demostracion de un vehiculo individual\n");

        Automovil auto1 = new Automovil(
                "Chevrolet", 2023, 1.4, Automovil.TipoCombustible.GASOLINA, Automovil.TipoAutomovil.COMPACTO,
                4, 5, 180.0, Automovil.Color.ROJO);

        auto1.setVelocidadActual(100);
        System.out.println("Velocidad fijada en 100 km/h -> " + auto1.getVelocidadActual() + " km/h");

        auto1.acelerar(20);
        System.out.println("Tras acelerar(20)            -> " + auto1.getVelocidadActual() + " km/h");

        double tiempo300km = auto1.tiempoEstimadoLlegada(300);
        System.out.printf("Tiempo estimado para 300 km  -> %.2f horas%n", tiempo300km);

        auto1.desacelerar(50);
        System.out.println("Tras desacelerar(50)          -> " + auto1.getVelocidadActual() + " km/h");

        auto1.frenar();
        System.out.println("Tras frenar()                 -> " + auto1.getVelocidadActual() + " km/h");

        System.out.println();

        System.out.println(">>> PARTE E.b) Flota de 5 vehiculos y estadisticas\n");

        Automovil[] flota = new Automovil[5];
        flota[0] = auto1;
        flota[1] = new Automovil("Renault", 2021, 1.6, Automovil.TipoCombustible.GASOLINA, Automovil.TipoAutomovil.FAMILIAR,
                4, 5, 170.0, Automovil.Color.BLANCO);
        flota[2] = new Automovil("Toyota", 2022, 2.0, Automovil.TipoCombustible.DIESEL, Automovil.TipoAutomovil.SUV,
                5, 7, 190.0, Automovil.Color.NEGRO);

        flota[3] = new Automovil("Chevrolet", 2020, 1.0, Automovil.TipoCombustible.BIOETANOL, Automovil.TipoAutomovil.CARRO_DE_CIUDAD,
                3, 4, 150.0, Automovil.Color.AMARILLO);
        flota[4] = new Automovil("Renault", 2024, 1.3, Automovil.TipoCombustible.BIOETANOL, Automovil.TipoAutomovil.SUBCOMPACTO,
                4, 5, 165.0, Automovil.Color.AZUL);

        flota[1].setVelocidadActual(90);
        flota[2].acelerar(140);
        flota[3].setVelocidadActual(60);
        flota[4].acelerar(50, 2);

        System.out.println("Total de automoviles construidos (static): " + Automovil.getTotalAutomoviles());
        System.out.println("Total de registros de bitacora (static):   " + Automovil.getContadorRegistros());

        Automovil masVeloz = Automovil.masRapido(flota);
        System.out.println("Vehiculo mas rapido de la flota: " +
                (masVeloz != null ? masVeloz.getMarca() + " a " + masVeloz.getVelocidadActual() + " km/h" : "N/A"));

        System.out.printf("Promedio de velocidad de la flota: %.2f km/h%n", Automovil.promedioVelocidad(flota));
        System.out.println("Infracciones (registros > " + Automovil.LIMITE_LEGAL + " km/h): " +
                Automovil.infraccionesPorExcesoDeVelocidad(flota));

        int[] conteoPorTipo = Automovil.contarPorTipo(flota);
        System.out.println("Conteo por Automovil.TipoAutomovil:");
        Automovil.TipoAutomovil[] tipos = Automovil.TipoAutomovil.values();
        for (int i = 0; i < conteoPorTipo.length; i++) {
            System.out.println("  " + tipos[i] + ": " + conteoPorTipo[i]);
        }

        System.out.println();

        System.out.println(">>> PARTE E.c) Demostracion de las 4 familias sobrecargadas\n");

        System.out.println("-- Familia: Constructores --");
        Automovil reducido = new Automovil("Mazda", 2023, 175.0);
        Automovil copia = new Automovil(reducido);
        reducido.mostrar();
        copia.mostrar();

        System.out.println("-- Familia: acelerar --");
        reducido.acelerar();
        System.out.println("acelerar()          -> " + reducido.getVelocidadActual() + " km/h");
        reducido.acelerar(15);
        System.out.println("acelerar(15)        -> " + reducido.getVelocidadActual() + " km/h");
        reducido.acelerar(10, 3);
        System.out.println("acelerar(10, 3)     -> " + reducido.getVelocidadActual() + " km/h");

        System.out.println("-- Familia: tiempoEstimadoLlegada --");
        System.out.printf("tiempoEstimadoLlegada(120)            -> %.2f horas%n",
                reducido.tiempoEstimadoLlegada(120));
        System.out.printf("tiempoEstimadoLlegada(120, 80)         -> %.2f horas (a velocidad de crucero)%n",
                reducido.tiempoEstimadoLlegada(120, 80));
        System.out.printf("tiempoEstimadoLlegada(120, 2, 15)      -> %.2f horas (con 2 paradas de 15 min)%n",
                reducido.tiempoEstimadoLlegada(120, 2, 15));

        System.out.println("-- Familia: filtrar (estatico) --");
        Automovil[] soloDiesel = Automovil.filtrar(flota, Automovil.TipoCombustible.DIESEL);
        Automovil[] soloSUV = Automovil.filtrar(flota, Automovil.TipoAutomovil.SUV);
        Automovil[] soloBlancos = Automovil.filtrar(flota, Automovil.Color.BLANCO);
        System.out.println("Vehiculos DIESEL: " + soloDiesel.length);
        System.out.println("Vehiculos SUV:    " + soloSUV.length);
        System.out.println("Vehiculos BLANCO: " + soloBlancos.length);

        System.out.println("\nBitacora detallada del vehiculo flota[2] (Toyota):");
        flota[2].mostrar(true);

        System.out.println();

        System.out.println(">>> PARTE E.d) Ruta individual (segun cedula)\n");

        int N = 1;
        int r = N % 4;
        System.out.println("N = " + N + "   r = N mod 4 = " + r + "  ->  Ruta " + r);

        int ordinalCombustible = N % 5;
        Automovil.TipoCombustible combustibleRuta = Automovil.TipoCombustible.values()[ordinalCombustible];
        System.out.println("Combustible seleccionado (ordinal " + ordinalCombustible + "): " + combustibleRuta);

        double promedioMotor = Automovil.promedioMotorPorCombustible(flota, combustibleRuta);
        System.out.printf("Reporte Ruta 1 -> promedioMotorPorCombustible(flota, %s) = %.2f litros%n",
                combustibleRuta, promedioMotor);

        System.out.println("\nCaso de control (combustible sin vehiculos en la flota):");
        Automovil.promedioMotorPorCombustible(flota, Automovil.TipoCombustible.GAS_NATURAL);

        System.out.println("\n=================================================");
        System.out.println(" Fin de la demostracion");
        System.out.println("=================================================");
    }
}
