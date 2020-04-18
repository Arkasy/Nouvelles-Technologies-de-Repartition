import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Json {

    public static void getInformationFromCustomerAsJSON(int idCustomer) throws IOException, InterruptedException {
        String uri = "http://localhost:8080/";
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri + "api/customer/" + idCustomer))
                .build();
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() == 200) {
            System.out.println("Affichage des informations en JSON du client avec l'id " + idCustomer);
            System.out.println(response.body().toString());
        }
    }

    public static void getListOfAccount() throws IOException, InterruptedException {
        String uri = "http://localhost:8080/";
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri + "api/customer/"))
                .build();
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() == 200) {
            System.out.println("Affichage de la liste des comptes");
            System.out.println(response.body().toString());
        }
    }

    public static void getOperationsFromBankAccountAsJSON(int idCustomer, int accountBank) throws IOException, InterruptedException {
        String uri = "http://localhost:8080/";
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri + "api/customer/" + idCustomer + "/account/" + accountBank + "/operations"))
                .build();
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() == 200) {
            System.out.println("Affichage des transactions du compte " + accountBank + " de l'utilisateur :" + idCustomer);
            System.out.println(response.body().toString());
        }
    }
}
