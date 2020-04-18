import java.io.IOException;

public class Main_JSON {

    public static void main(String[] args) throws IOException, InterruptedException {
        Json.getListOfAccount();
        Json.getInformationFromCustomerAsJSON(312688496);
        Json.getOperationsFromBankAccountAsJSON(336604429, 3);
    }


}
