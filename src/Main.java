import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        MainService.showHeader();
        try {
            ExchangeService.getRatesByBaseCode();
            if (ExchangeService.getRates() != null) {
                MainService.runCurrencyConverter();
            }
            MainService.showHistory();
            MainService.exitMessage();
        } catch (IOException | InterruptedException e) {
            System.out.println("Error en el servidor de la API: " + e.getLocalizedMessage());
        }
    }
}
