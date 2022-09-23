import java.nio.charset.Charset;
import java.util.*;

public class GiftCardManagement implements CardManagement {

    String prefixEanGiftCard = "49";
    HashMap<String, String> listGiftCard = new HashMap<>();

    private static GiftCardManagement instance;

    public static GiftCardManagement getInstance() {
        if (instance == null) {
            instance = new GiftCardManagement();
        }
        return instance;
    }

    @Override
    public void createGiftCard() {

        System.out.println("Quante Gift Card vuoi creare? ");
        Scanner scan = new Scanner(System.in);
        int quantityGiftCard = scan.nextInt();

        for (int i = 0; i < quantityGiftCard; i++){
            String eanGiftCard = prefixEanGiftCard + getRandomString(24);
            String activationCode =  getRandomString(9);
            listGiftCard.put(eanGiftCard, activationCode);
            System.out.println("Creata Gift Card: " + eanGiftCard + " " + "con codice attivazione: " + activationCode);
        }
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
}
