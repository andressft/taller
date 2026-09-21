package automovil;

import java.util.Vector;

public class Automovil {

    public enum TipoCombustible {
        GASOLINA, BIOETANOL, DIESEL, BIODIESEL, GAS_NATURAL
    }

    public enum TipoAutomovil {
        CARRO_DE_CIUDAD, SUBCOMPACTO, COMPACTO, FAMILIAR, EJECUTIVO, SUV
    }

    public enum Color {
        BLANCO, NEGRO, ROJO, NARANJA, AMARILLO, VERDE, AZUL, VIOLETA
    }

    private static int totalAutomoviles = 0;

    private static int contadorRegistros = 0;

    public static final int LIMITE_LEGAL = 120;

    public static final double INCREMENTO_DEFECTO = 10.0;

    private String marca;
    private int modelo;
    private double motor;
    private TipoCombustible tipoCombustible;
    private TipoAutomovil tipoAutomovil;
    private int numeroPuertas;
    private int cantidadAsientos;
    private double velocidadMaxima;
    private Color color;
    private double velocidadActual;

    private Vector<RegistroViaje> bitacora = new Vector<>();

    public Automovil(String marca, int modelo, double motor, TipoCombustible tipoCombustible,
                      TipoAutomovil tipoAutomovil, int numeroPuertas, int cantidadAsientos,
                      double velocidadMaxima, Color color) {
        this.marca = marca;
        this.modelo = modelo;
        this.motor = motor;
        this.tipoCombustible = tipoCombustible;
        this.tipoAutomovil = tipoAutomovil;
        this.numeroPuertas = numeroPuertas;
        this.cantidadAsientos = cantidadAsientos;
        this.velocidadMaxima = velocidadMaxima;
        this.color = color;
        this.velocidadActual = 0.0;

        totalAutomoviles++;
    }

    public Automovil(String marca, int modelo, double velocidadMaxima) {
        this(marca, modelo, 1.6, TipoCombustible.GASOLINA, TipoAutomovil.COMPACTO,
                4, 5, velocidadMaxima, Color.BLANCO);
    }

    public Automovil(Automovil otro) {
        this(otro.marca, otro.modelo, otro.motor, otro.tipoCombustible, otro.tipoAutomovil,
                otro.numeroPuertas, otro.cantidadAsientos, otro.velocidadMaxima, otro.color);

        this.velocidadActual = otro.velocidadActual;
    }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public int getModelo() { return modelo; }
    public void setModelo(int modelo) { this.modelo = modelo; }

    public double getMotor() { return motor; }
    public void setMotor(double motor) {

        if (motor <= 0) {
            System.out.println("[ERROR] El cilindraje del motor debe ser mayor que cero.");
            return;
        }
        this.motor = motor;
    }

    public TipoCombustible getTipoCombustible() { return tipoCombustible; }
    public void setTipoCombustible(TipoCombustible tipoCombustible) { this.tipoCombustible = tipoCombustible; }

    public TipoAutomovil getTipoAutomovil() { return tipoAutomovil; }
    public void setTipoAutomovil(TipoAutomovil tipoAutomovil) { this.tipoAutomovil = tipoAutomovil; }

    public int getNumeroPuertas() { return numeroPuertas; }
    public void setNumeroPuertas(int numeroPuertas) {
        if (numeroPuertas <= 0) {
            System.out.println("[ERROR] El numero de puertas debe ser positivo.");
            return;
        }
        this.numeroPuertas = numeroPuertas;
    }

    public int getCantidadAsientos() { return cantidadAsientos; }
    public void setCantidadAsientos(int cantidadAsientos) {
        if (cantidadAsientos <= 0) {
            System.out.println("[ERROR] La cantidad de asientos debe ser positiva.");
            return;
        }
        this.cantidadAsientos = cantidadAsientos;
    }

    public double getVelocidadMaxima() { return velocidadMaxima; }
    public void setVelocidadMaxima(double velocidadMaxima) {
        if (velocidadMaxima <= 0) {
            System.out.println("[ERROR] La velocidad maxima debe ser positiva.");
            return;
        }
        this.velocidadMaxima = velocidadMaxima;
    }

    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }

    public double getVelocidadActual() { return velocidadActual; }

    public boolean setVelocidadActual(double velocidadActual) {
        if (velocidadActual < 0) {
            System.out.println("[ERROR] La velocidad no puede ser negativa.");
            return false;
        }
        if (velocidadActual > this.velocidadMaxima) {
            System.out.println("[ERROR] La velocidad " + velocidadActual +
                    " km/h supera la velocidad maxima del vehiculo (" + this.velocidadMaxima + " km/h).");
            return false;
        }
        this.velocidadActual = velocidadActual;
        return true;
    }

    private void registrarEvento() {
        RegistroViaje registro = new RegistroViaje();
        bitacora.add(registro);
        contadorRegistros++;
    }

    public void acelerar() {
        acelerar(INCREMENTO_DEFECTO);
    }

    public void acelerar(double incremento) {
        if (incremento < 0) {
            System.out.println("[ERROR] El incremento de aceleracion no puede ser negativo (use desacelerar).");
            registrarEvento();
            return;
        }
        double nuevaVelocidad = this.velocidadActual + incremento;
        boolean aplicado = setVelocidadActual(nuevaVelocidad);

        registrarEvento();
        if (!aplicado) {

        }
    }

    public void acelerar(double incremento, int veces) {
        for (int i = 0; i < veces; i++) {
            acelerar(incremento);
        }
    }

    public void desacelerar() {
        desacelerar(INCREMENTO_DEFECTO);
    }

    public void desacelerar(double decremento) {
        if (decremento < 0) {
            System.out.println("[ERROR] El decremento no puede ser negativo (use acelerar).");
            registrarEvento();
            return;
        }
        double nuevaVelocidad = this.velocidadActual - decremento;
        setVelocidadActual(nuevaVelocidad);
        registrarEvento();
    }

    public void frenar() {
        this.velocidadActual = 0.0;
        registrarEvento();
    }

    public double tiempoEstimadoLlegada(double distanciaKm) {
        if (this.velocidadActual <= 0) {
            System.out.println("[AVISO] El vehiculo esta detenido: no es posible estimar un tiempo de llegada.");
            return Double.POSITIVE_INFINITY;
        }
        return distanciaKm / this.velocidadActual;
    }

    public double tiempoEstimadoLlegada(double distanciaKm, double velocidadCrucero) {
        if (velocidadCrucero <= 0) {
            System.out.println("[AVISO] La velocidad de crucero debe ser mayor que cero.");
            return Double.POSITIVE_INFINITY;
        }
        return distanciaKm / velocidadCrucero;
    }

    public double tiempoEstimadoLlegada(double distanciaKm, int paradas, double minutosPorParada) {
        double tiempoViajeHoras = tiempoEstimadoLlegada(distanciaKm);
        double tiempoParadasHoras = (paradas * minutosPorParada) / 60.0;
        return tiempoViajeHoras + tiempoParadasHoras;
    }

    public void mostrar() {
        mostrar(false);
    }

    public void mostrar(boolean detallado) {
        System.out.println("---------------------------------------------");
        System.out.println("Marca:              " + marca);
        System.out.println("Modelo (anio):       " + modelo);
        System.out.println("Motor (litros):      " + motor);
        System.out.println("Combustible:         " + tipoCombustible);
        System.out.println("Tipo:                " + tipoAutomovil);
        System.out.println("Puertas:             " + numeroPuertas);
        System.out.println("Asientos:            " + cantidadAsientos);
        System.out.println("Color:               " + color);
        System.out.println("Velocidad maxima:    " + velocidadMaxima + " km/h");
        System.out.println("Velocidad actual:    " + velocidadActual + " km/h");
        if (detallado) {
            System.out.println("Bitacora (" + bitacora.size() + " registros):");
            if (bitacora.isEmpty()) {
                System.out.println("  (sin eventos registrados)");
            } else {
                for (RegistroViaje r : bitacora) {
                    System.out.println("  - " + r.describir());
                }
            }
        }
        System.out.println("---------------------------------------------");
    }

    public Vector<RegistroViaje> getBitacora() {
        return bitacora;
    }

    public class RegistroViaje {
        private final String marcaRegistrada;
        private final int modeloRegistrado;
        private final double velocidadRegistrada;

        public RegistroViaje() {

            this.marcaRegistrada = Automovil.this.marca;
            this.modeloRegistrado = Automovil.this.modelo;
            this.velocidadRegistrada = Automovil.this.velocidadActual;
        }

        public double getVelocidadRegistrada() {
            return velocidadRegistrada;
        }

        public String describir() {
            String alerta = (velocidadRegistrada > LIMITE_LEGAL) ? "  >>> EXCESO DE VELOCIDAD" : "";
            return marcaRegistrada + " (" + modeloRegistrado + ") -> " +
                    velocidadRegistrada + " km/h" + alerta;
        }
    }

    public static int[] contarPorTipo(Automovil[] flota) {
        int[] conteo = new int[TipoAutomovil.values().length];

        if (flota == null) {
            System.out.println("[ERROR] contarPorTipo: la flota recibida es null.");
            return conteo;
        }

        for (Automovil auto : flota) {
            if (auto == null) {

                continue;
            }
            int indice = auto.getTipoAutomovil().ordinal();
            if (indice < 0 || indice >= conteo.length) {

                System.out.println("[ERROR] contarPorTipo: indice fuera de rango (" + indice + ").");
                continue;
            }
            conteo[indice]++;
        }
        return conteo;
    }

    public static Automovil[] filtrar(Automovil[] flota, TipoCombustible combustible) {
        int cantidad = 0;
        for (Automovil auto : flota) {
            if (auto != null && auto.getTipoCombustible() == combustible) cantidad++;
        }
        Automovil[] resultado = new Automovil[cantidad];
        int pos = 0;
        for (Automovil auto : flota) {
            if (auto != null && auto.getTipoCombustible() == combustible) {
                resultado[pos++] = auto;
            }
        }
        return resultado;
    }

    public static Automovil[] filtrar(Automovil[] flota, TipoAutomovil tipo) {
        int cantidad = 0;
        for (Automovil auto : flota) {
            if (auto != null && auto.getTipoAutomovil() == tipo) cantidad++;
        }
        Automovil[] resultado = new Automovil[cantidad];
        int pos = 0;
        for (Automovil auto : flota) {
            if (auto != null && auto.getTipoAutomovil() == tipo) {
                resultado[pos++] = auto;
            }
        }
        return resultado;
    }

    public static Automovil[] filtrar(Automovil[] flota, Color color) {
        int cantidad = 0;
        for (Automovil auto : flota) {
            if (auto != null && auto.getColor() == color) cantidad++;
        }
        Automovil[] resultado = new Automovil[cantidad];
        int pos = 0;
        for (Automovil auto : flota) {
            if (auto != null && auto.getColor() == color) {
                resultado[pos++] = auto;
            }
        }
        return resultado;
    }

    public static int getTotalAutomoviles() {
        return totalAutomoviles;
    }

    public static int getContadorRegistros() {
        return contadorRegistros;
    }

    public static Automovil masRapido(Automovil[] flota) {
        if (flota == null || flota.length == 0) return null;
        Automovil mejor = null;
        for (Automovil auto : flota) {
            if (auto == null) continue;
            if (mejor == null || auto.getVelocidadActual() > mejor.getVelocidadActual()) {
                mejor = auto;
            }
        }
        return mejor;
    }

    public static double promedioVelocidad(Automovil[] flota) {
        if (flota == null) return 0.0;
        double suma = 0.0;
        int cantidad = 0;
        for (Automovil auto : flota) {
            if (auto == null) continue;
            suma += auto.getVelocidadActual();
            cantidad++;
        }
        return cantidad == 0 ? 0.0 : suma / cantidad;
    }

    public static int infraccionesPorExcesoDeVelocidad(Automovil[] flota) {
        if (flota == null) return 0;
        int infracciones = 0;
        for (Automovil auto : flota) {
            if (auto == null) continue;
            for (RegistroViaje registro : auto.getBitacora()) {
                if (registro.getVelocidadRegistrada() > LIMITE_LEGAL) {
                    infracciones++;
                }
            }
        }
        return infracciones;
    }

    public static double promedioMotorPorCombustible(Automovil[] flota, TipoCombustible combustible) {
        if (flota == null) {
            System.out.println("[ERROR] promedioMotorPorCombustible: la flota recibida es null.");
            return 0.0;
        }
        double suma = 0.0;
        int cantidad = 0;
        for (Automovil auto : flota) {
            if (auto != null && auto.getTipoCombustible() == combustible) {
                suma += auto.getMotor();
                cantidad++;
            }
        }
        if (cantidad == 0) {
            System.out.println("[INFO] No existen vehiculos con combustible " + combustible +
                    " en la flota; no es posible calcular un promedio.");
            return 0.0;
        }
        return suma / cantidad;
    }
}
