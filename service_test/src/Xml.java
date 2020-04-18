import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Xml {

    public static void addNewOperation(int idAccount, double amount) throws IOException, InterruptedException, URISyntaxException {
        String uri = "http://localhost:8080/";
        String xmlContent = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "                  xmlns:gs=\"http://istv.com/banque/operations_webservice\">\n" +
                "    <soapenv:Header/>\n" +
                "    <soapenv:Body>\n" +
                "        <gs:AddOperationRequest>\n" +
                "            <gs:name>test main XML</gs:name>\n" +
                "            <gs:idAccount>" + idAccount + "</gs:idAccount>\n" +
                "            <gs:amount>" + amount + "</gs:amount>\n" +
                "        </gs:AddOperationRequest>\n" +
                "    </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(new URI(uri + "ws"))
                .POST(HttpRequest.BodyPublishers.ofString(xmlContent))
                .build();

        HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() == 200) {
            System.out.println("Affichage de la réponse de l'ajout de l'opération");
            System.out.println(response.body().toString());
        }

        System.out.println(response.statusCode());
    }
}
