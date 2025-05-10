import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MainService {

    // Scanner global utilizado para leer la entrada del usuario
    private static final Scanner scanner = new Scanner(System.in);
    // history para guardar información de cada conversión realizada
    private static final List<String> history = new ArrayList<>();
    /**
     * Ejecuta el ciclo principal del conversor de moneda.
     * Permite al usuario seleccionar una moneda base, ingresar un valor,
     * elegir una moneda destino, realizar la conversión y repetir según la opción elegida.
     */
    public static void runCurrencyConverter() throws IOException, InterruptedException {
        String option = "";
        String baseCurrency = "";

        while (!option.equals("9")) {
            // Si es la primera vez o se eligió cambiar la base
            if (ExchangeService.isInvalidRate(baseCurrency) || option.equals("2")) {
                baseCurrency = askForCurrency("base");
                ExchangeService.setBaseCode(baseCurrency);
                ExchangeService.getRatesByBaseCode();
            }

            double amount = askForAmount(); // valor a convertir
            String targetCurrency = askForCurrency("destino"); // moneda destino

            // Realiza la conversión
            double result = ExchangeService.convertAmountByCode(targetCurrency, amount);
            showConversionResult(amount, baseCurrency, result, targetCurrency);
            addToHistory(amount, result, baseCurrency, targetCurrency);

            option = askNextAction(baseCurrency); // siguiente paso del usuario
        }
    }

    /**
     * Agrega la información de una conversión realizada al historial
     * @param amount monto convertido
     * @param result resultado de la conversión
     * @param baseCurrency mondeda base
     * @param targetCurrency moneda destino
     */
    private static void addToHistory(double amount, double result, String baseCurrency, String targetCurrency) {
        var now = LocalDateTime.now();
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String convertion = String.format(
                "%s - Convertiste %.2f [%s] a [%s] ===> %.2f",
                now.format(formatter),
                amount, baseCurrency, targetCurrency, result
        );

        history.add(convertion);
    }

    /**
     * Solicita al usuario que ingrese una moneda válida según el tipo (base o destino).
     *
     * @param type Indica si se está solicitando la moneda "base" o "destino"
     * @return Código de moneda válido en mayúsculas
     */
    private static String askForCurrency(String type) {
        String currency;
        do {
            showRates(type.equals("destino"));
            System.out.printf("\nDigita el tipo de moneda %s: ", type);
            currency = scanner.nextLine().trim().toUpperCase();
        } while (ExchangeService.isInvalidRate(currency));
        return currency;
    }

    /**
     * Solicita al usuario un valor numérico a convertir.
     * Repite la solicitud hasta que el usuario ingrese un número válido.
     *
     * @return Valor ingresado por el usuario
     */
    private static double askForAmount() {
        double amount;
        while (true) {
            System.out.print("Digita el valor a convertir: ");
            try {
                amount = scanner.nextDouble();
                scanner.nextLine(); // limpiar buffer de entrada
                break;
            } catch (InputMismatchException e) {
                System.out.println("Por favor ingresa un valor válido!");
                scanner.nextLine(); // limpiar entrada inválida
            }
        }
        return amount;
    }

    /**
     * Muestra el menú de opciones luego de una conversión
     * y solicita al usuario que seleccione una opción válida.
     *
     * @param base Moneda base actual, mostrada en el menú
     * @return Opción seleccionada por el usuario (1, 2 o 9)
     */
    private static String askNextAction(String base) {
        String opt;
        while (true) {
            showOptions(base);
            opt = scanner.nextLine().trim();
            if (opt.equals("1") || opt.equals("2") || opt.equals("9")) {
                break;
            }
            System.out.println("Ingrese una opción válida.");
        }
        return opt;
    }

    /**
     * Muestra el resultado de la conversión de moneda en consola.
     *
     * @param amount Valor original
     * @param from   Moneda de origen
     * @param result Valor convertido
     * @param to     Moneda destino
     */
    private static void showConversionResult(double amount, String from, double result, String to) {
        System.out.printf("""
                
                El valor %.1f [%s] corresponde al valor final de ==> %.2f [%s]
                
                """, amount, from, result, to);
    }

    /**
     * Muestra el menú de opciones tras una conversión.
     *
     * @param base Moneda base actual, utilizada en el mensaje
     */
    private static void showOptions(String base) {
        System.out.printf("""
                Elije una opción:
                1. Convertir otro valor con la misma base [%s]
                2. Cambiar la base
                9. Salir
                """, base);
    }

    /**
     * Muestra el encabezado de bienvenida del sistema.
     */
    public static void showHeader() {
        System.out.println("""
                **************************************************************
                Sea bienvenido/a al conversor de Moneda =]
                """);
    }

    /**
     * Muestra la lista de monedas disponibles.
     * Si excludeBase es verdadero, omite la moneda base actual.
     *
     * @param excludeBase Si es true, excluye la moneda base de la lista
     */
    private static void showRates(boolean excludeBase) {
        String baseCode = ExchangeService.getBaseCode();
        System.out.print("[");
        int count = 0;
        for (String key : ExchangeService.getRates().keySet()) {
            if (excludeBase && key.equalsIgnoreCase(baseCode)) {
                continue;
            }
            System.out.print(key + " ");
            count++;
            if (count >= 26) {
                System.out.println();
                count = 0;
            }
        }
        System.out.print("]");
    }

    /**
     * Muestra un mensaje de despedida al usuario al salir del programa.
     */
    public static void exitMessage() {
        System.out.println("Gracias por usar nuestro servicio!");
        System.out.println("**********************************************************");
    }

    /**
     * Muestra el historial de conversiones hechas en la sesión
     */
    public static void showHistory() {
        System.out.println();
        System.out.println("*********************** Historial **************************************");
        history.forEach(System.out::println);
        System.out.println("************************************************************************");
    }
}
