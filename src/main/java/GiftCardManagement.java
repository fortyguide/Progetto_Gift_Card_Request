import java.nio.charset.Charset;
import java.util.*;

public class GiftCardManagement implements CardManagement {

    String prefixEanGiftCard = "49";
    HashMap<String, String> mapGiftCard = new HashMap<>();
    List<String> listGiftCard = new ArrayList<>();

    private int quantityGiftCard;
    private String eanGiftCard;
    private String activationCode;
    private double balance;
    private String store;
    private String stato;
    private String eanGiftCardToActive;

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
        setQuantityGiftCard(scan.nextInt());

        for (int i = 0; i < getQuantityGiftCard(); i++){
            setEanGiftCard(prefixEanGiftCard + getRandomString(24));
            setActivationCode(getRandomString(9));
            listGiftCard.add(getEanGiftCard());
            mapGiftCard.put(getEanGiftCard(), getActivationCode());
            System.out.println("Creata Gift Card: " + getEanGiftCard() + " " + "con codice attivazione: " + getActivationCode());
        }
        System.out.println();

        if (getQuantityGiftCard() > 1) {
            System.out.println("Inserire saldo delle Gift Card create: ");
        } else {
            System.out.println("Inserire saldo della Gift Card creata: ");
        }
        Scanner scan2 = new Scanner(System.in);
        setBalance(scan2.nextInt());
        System.out.println();


        System.out.println("Inserire nome negozio: ");
        Scanner scan3 = new Scanner(System.in);
        setStore(scan3.next());
        setStato("TESSERA NON ATTIVA");
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
                                                       getBalance(),
                                                       getStore(),
                                                       getStato());
                }
            }
        }
        GiftCardManagement.getInstance().clear();
    }

    @Override
    public void activeGiftCard() {

        System.out.println();
        System.out.println("Inserire ean Gift Card da attivare: ");
        Scanner scan = new Scanner(System.in);
        setEanGiftCardToActive(scan.next());

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

    public int getQuantityGiftCard() {
        return quantityGiftCard;
    }

    public void setQuantityGiftCard(int quantityGiftCard) {
        this.quantityGiftCard = quantityGiftCard;
    }

    public String getEanGiftCard() {
        return eanGiftCard;
    }

    public void setEanGiftCard(String eanGiftCard) {
        this.eanGiftCard = eanGiftCard;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getEanGiftCardToActive() {
        return eanGiftCardToActive;
    }

    public void setEanGiftCardToActive(String eanGiftCardToActive) {
        this.eanGiftCardToActive = eanGiftCardToActive;
    }

    public static void setInstance(GiftCardManagement instance) {
        GiftCardManagement.instance = instance;
    }
}
