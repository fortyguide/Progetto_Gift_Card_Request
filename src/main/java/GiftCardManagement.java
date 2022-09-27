import java.nio.charset.Charset;
import java.util.*;

public class GiftCardManagement implements CardManagement {

    String prefixEanGiftCard = "49";
    HashMap<String, String> mapGiftCard = new HashMap<>();
    List<String> listGiftCard = new ArrayList<>();

    int quantityGiftCard;
    String eanGiftCard;
    String activationCode;
    double balance;
    String store;
    String stato;

    private static GiftCardManagement instance;

    public static GiftCardManagement getInstance() {
        if (instance == null) {
            instance = new GiftCardManagement();
        }
        return instance;
    }

    @Override
    public void createGiftCard() {

        System.out.println();
        System.out.println("Inserire numero Gift Card da creare: ");
        Scanner scan = new Scanner(System.in);
        quantityGiftCard = scan.nextInt();

        for (int i = 0; i < quantityGiftCard; i++){
            eanGiftCard = prefixEanGiftCard + getRandomString(24);
            activationCode =  getRandomString(9);
            listGiftCard.add(eanGiftCard);
            mapGiftCard.put(eanGiftCard, activationCode);
            System.out.println("Creata Gift Card: " + eanGiftCard + " " + "con codice attivazione: " + activationCode);
        }
        System.out.println();

        if (quantityGiftCard > 1) {
            System.out.println("Inserire saldo delle Gift Card create: ");
        } else {
            System.out.println("Inserire saldo della Gift Card creata: ");
        }
        Scanner scan2 = new Scanner(System.in);
        balance = scan2.nextInt();
        System.out.println();


        System.out.println("Inserire nome negozio: ");
        Scanner scan3 = new Scanner(System.in);
        store = scan3.next();
        stato = "TESSERA NON ATTIVA";
        System.out.println();

        DataManagement.getInstance().createDirectoryAndFile("C:\\Users\\spanico\\IdeaProjects\\Progetto_Gift_Card_Request\\Database\\",
                                                            "Tessere.txt");

        for (String eanGiftCardList : listGiftCard) {
            for (String eanGiftCardMap : mapGiftCard.keySet()) {
                if (eanGiftCardList == eanGiftCardMap) {
                    String ActivationCodeMap = mapGiftCard.get(eanGiftCardMap);
                    DataManagement.getInstance().write("C:\\Users\\spanico\\IdeaProjects\\Progetto_Gift_Card_Request\\Database\\",
                                                       "Tessere.txt",
                                                       eanGiftCardMap,
                                                       ActivationCodeMap,
                                                       balance,
                                                       store,
                                                       stato);
                }
            }
        }
        GiftCardManagement.getInstance().clear();
    }

    @Override
    public void activeGiftCard() {

    }

    @Override
    public void checkGiftCard() {

    }

    static String getRandomString(int i)
    {

        byte[] bytearray;
        bytearray = new byte[256];
        String myString;
        StringBuffer theBuffer;
        String theAlphaNumericS;

        new Random().nextBytes(bytearray);

        myString
                = new String(bytearray, Charset.forName("UTF-8"));

        theBuffer = new StringBuffer();

        //remove all spacial char
        theAlphaNumericS
                = myString
                .replaceAll("[^A-Z0-9]", "");

        //random selection
        for (int m = 0; m < theAlphaNumericS.length(); m++) {

            if (Character.isLetter(theAlphaNumericS.charAt(m))
                    && (i > 0)
                    || Character.isDigit(theAlphaNumericS.charAt(m))
                    && (i > 0)) {

                theBuffer.append(theAlphaNumericS.charAt(m));
                i--;
            }
        }

        // the resulting string
        return theBuffer.toString();
    }

    public void clear() {
        listGiftCard.clear();
        mapGiftCard.clear();
    }
}
