import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

public class ExchangeService {
    private static final String API_KEY = "442d7f7c9bb245f6adea0af1";
    private static final String BASE_URI = "https://v6.exchangerate-api.com/v6/";
    private static String baseCode = "COP";
    private static JsonObject rates;

    /**
     * Obtiene las tasas de conversión desde la API externa utilizando la moneda base actual.
     *
     * @throws IOException si ocurre un error al procesar la respuesta o si no se encuentran tasas válidas.
     * @throws InterruptedException si la solicitud HTTP es interrumpida.
     */
    public static void getRatesByBaseCode() throws IOException, InterruptedException {
        String uri = String.format("%s%s/latest/%s", BASE_URI, API_KEY, baseCode);

        String responseBody = MyHttpClient.getResponseBody(uri);
        JsonObject root = parseJsonResponse(responseBody);

        JsonObject conversionRates = root.getAsJsonObject("conversion_rates");
        if (conversionRates == null || conversionRates.isEmpty()) {
            throw new IOException("No se encontraron tasas de conversión para la base " + baseCode);
        }

        rates = conversionRates;
    }

    /**
     * Parsea el cuerpo JSON de la respuesta de la API y valida que sea un objeto.
     *
     * @param responseBody cuerpo JSON como texto
     * @return JsonObject raíz del JSON
     * @throws IOException si el contenido no es un JSON válido o no es un objeto
     */
    private static JsonObject parseJsonResponse(String responseBody) throws IOException {
        JsonElement parsed = JsonParser.parseString(responseBody);

        if (!parsed.isJsonObject()) {
            throw new IOException("Respuesta inválida del servidor: no es un objeto JSON");
        }

        return parsed.getAsJsonObject();
    }

    /**
     * Establece la nueva moneda base.
     */
    public static void setBaseCode(String newBaseCode) {
        baseCode = newBaseCode.trim().toUpperCase();
    }

    /**
     * Devuelve la moneda base actual.
     */
    public static String getBaseCode() {
        return baseCode;
    }

    /**
     * Devuelve el objeto con todas las tasas actuales.
     */
    public static JsonObject getRates() {
        return rates;
    }

    /**
     * Verifica si un código de moneda no es válido según las tasas de cambio actuales.
     * <p>
     * Se considera inválido si el objeto de tasas es nulo o si no contiene el código de moneda proporcionado.
     */

    public static boolean isInvalidRate(String currencyCode) {
        return rates == null || !rates.has(currencyCode.toUpperCase());
    }

    /**
     * Convierte un monto desde la moneda base actual a una moneda destino específica.
     * <p>
     * Utiliza la tasa de conversión correspondiente al código de moneda destino y multiplica
     * dicho valor por el monto proporcionado.
     *
     * @param targetCurrencyCode Código de la moneda destino (por ejemplo, "USD", "EUR").
     * @param amount Monto numérico que se desea convertir desde la moneda base.
     * @return El valor convertido en la moneda destino.
     * @throws IllegalArgumentException si el código de moneda destino no es válido o no está disponible en las tasas actuales.
     */
    public static double convertAmountByCode(String targetCurrencyCode, double amount) {
        if (isInvalidRate(targetCurrencyCode)) {
            throw new IllegalArgumentException("Moneda no soportada: " + targetCurrencyCode);
        }
        double rate = rates.get(targetCurrencyCode.toUpperCase()).getAsDouble();
        return rate * amount;
    }
}
