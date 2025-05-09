import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Clase utilitaria para realizar solicitudes HTTP GET de manera sencilla.
 */
public class MyHttpClient {

    // Cliente HTTP reutilizable para evitar la creación de múltiples instancias.
    private static final HttpClient client = HttpClient.newHttpClient();

    /**
     * Realiza una solicitud HTTP GET a la URL proporcionada y retorna el cuerpo de la respuesta como texto.
     *
     * @param finalUrl URL a la que se hará la solicitud
     * @return El cuerpo de la respuesta como un String
     * @throws IOException si ocurre un error de entrada/salida durante la solicitud
     * @throws InterruptedException si la operación es interrumpida mientras espera la respuesta
     */
    public static String getResponseBody(String finalUrl) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(finalUrl))
                .GET()
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
}
