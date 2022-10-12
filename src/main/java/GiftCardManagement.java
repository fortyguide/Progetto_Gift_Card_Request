import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class GiftCardManagement implements CardManagement {

    String prefixEanGiftCard = "49";
    HashMap<String, String> mapGiftCard = new HashMap<>();
    List<String> listGiftCard = new ArrayList<>();
    HashMap<String, String> mapEanGiftCardAndActivationCodeDB = new HashMap<>();
    HashMap<String, String> mapEanGiftCardAndStatusDB = new HashMap<>();


    private String path;
    private String fileName;
    private int quantityGiftCard;
    private String eanGiftCard;
    private String activationCode;
    private double balance;
    private String store;
    private String status;
    private String eanGiftCardToActive;
    private String eanGiftCardToCheck;
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

        for (int i = 0; i < getQuantityGiftCard(); i++) {
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
        setStatus("TESSERA NON ATTIVA");
        System.out.println();

        setPath("C:\\Users\\spanico\\IdeaProjects\\Progetto_Gift_Card_Request\\Database\\");
        setFileName("Tessere.txt");
        DataManagement.getInstance().createDirectoryAndFile(getPath(),
                getFileName());

        for (String eanGiftCardList : listGiftCard) {
            for (String eanGiftCardMap : mapGiftCard.keySet()) {
                if (eanGiftCardList == eanGiftCardMap) {
                    String ActivationCodeMap = mapGiftCard.get(eanGiftCardMap);
                    DataManagement.getInstance().write(getPath(),
                            getFileName(),
                            eanGiftCardMap,
                            ActivationCodeMap,
                            getBalance(),
                            getStore(),
                            getStatus());
                }
            }
        }
        GiftCardManagement.getInstance().clear();
    }

    @Override
    public void activeGiftCard() {

        System.out.println();
        System.out.println("Inserire ean Gift Card da attivare: ");
        Scanner scan1 = new Scanner(System.in);
        setEanGiftCardToActive(scan1.next());

        setPath("C:\\Users\\spanico\\IdeaProjects\\Progetto_Gift_Card_Request\\Database\\");
        setFileName("Tessere.txt");
        mapEanGiftCardAndActivationCodeDB = DataManagement.getInstance().readEanGiftCardAndActivationCode(getPath(), getFileName());
        mapEanGiftCardAndStatusDB = DataManagement.getInstance().readEanGiftCardAndStatus(getPath(), getFileName());

        if (!mapEanGiftCardAndActivationCodeDB.containsKey(getEanGiftCardToActive()) &&
                !mapEanGiftCardAndStatusDB.containsKey(getEanGiftCardToActive())) {
            System.out.println();
            System.out.println("La Gift Card inserita non e' presente nel Database");
        } else {
            System.out.println();
            System.out.println("Confermare attivazione Gift Card? Y/N");
            Scanner scan2 = new Scanner(System.in);
            String confirmActivation = scan2.next();

            if (confirmActivation.equalsIgnoreCase("Y")) {
                System.out.println();
                System.out.println("Inserire codice attivazione della Gift Card: ");
                Scanner scan3 = new Scanner(System.in);
                String activationCodeInput = scan3.next();

                for (String eanGiftCardMap1 : mapEanGiftCardAndActivationCodeDB.keySet()) {
                    for (String eanGiftCardMap2 : mapEanGiftCardAndStatusDB.keySet()) {
                        if (eanGiftCardMap1.equalsIgnoreCase(eanGiftCardMap2) && eanGiftCardMap1.equalsIgnoreCase(eanGiftCardToActive)) {
                            String activationCodeMap = mapEanGiftCardAndActivationCodeDB.get(eanGiftCardMap1);
                            if (activationCodeMap.equals(activationCodeInput)) {
                                String statusMap = mapEanGiftCardAndStatusDB.get(eanGiftCardMap2);
                                if (statusMap.equalsIgnoreCase("TESSERA NON ATTIVA")) {
                                    System.out.println();
                                    try {
                                        DataManagement.getInstance().update(getPath(),
                                                getFileName(),
                                                eanGiftCardMap2,
                                                activationCodeMap,
                                                "TESSERA NON ATTIVA");
                                        System.out.println("Codice attivazione corretto! La Gift Card inserita è ATTIVA");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    System.out.println();
                                    System.out.println("La tessera è già ATTIVA");
                                }
                            } else {
                                System.out.println();
                                System.out.println("Codice attivazione non corretto! La Gift Card inserita è rimasta NON ATTIVA");
                            }
                            break;
                        }
                    }
                }
            }
        }
        clear();
    }

    @Override
    public void checkGiftCard() {

        String giftCardData = "";
        System.out.println();
        System.out.println("Inserire ean Gift Card per vederne i dettagli: ");
        Scanner scan1 = new Scanner(System.in);
        setEanGiftCardToCheck(scan1.next());
        setPath("C:\\Users\\spanico\\IdeaProjects\\Progetto_Gift_Card_Request\\Database\\");
        setFileName("Tessere.txt");

        try {
            giftCardData = DataManagement.getInstance().readGiftCard(getPath(), getFileName(), getEanGiftCardToCheck());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (!giftCardData.isEmpty()) {
            System.out.println();
            System.out.println("I dati della Gift Card con ean " + getEanGiftCardToCheck() + " sono i seguenti: ");
            StringBuilder sb1 = new StringBuilder(giftCardData);
            System.out.println("Codice attivazione = " + sb1.delete(0, sb1.indexOf("codice attivazione")).delete(0, sb1.indexOf("= ")).delete(sb1.indexOf(";"), sb1.length()).delete(sb1.indexOf("="), sb1.indexOf(" ")).toString().trim());
            StringBuilder sb2 = new StringBuilder(giftCardData);
            System.out.println("saldo = " + sb2.delete(0, sb2.indexOf("saldo")).delete(0, sb2.indexOf("= ")).delete(sb2.indexOf(";"), sb2.length()).delete(sb2.indexOf("="), sb2.indexOf(" ")).toString().trim());
            StringBuilder sb3 = new StringBuilder(giftCardData);
            System.out.println("azienda = " + sb3.delete(0, sb3.indexOf("azienda")).delete(0, sb3.indexOf("= ")).delete(sb3.indexOf(";"), sb3.length()).delete(sb3.indexOf("="), sb3.indexOf(" ")).toString().trim());
            StringBuilder sb4 = new StringBuilder(giftCardData);
            System.out.println("stato tessera = " + sb4.delete(0, sb4.indexOf("stato tessera")).delete(0, sb4.indexOf("= ")).delete(sb4.indexOf(";"), sb4.length()).delete(sb4.indexOf("="), sb4.indexOf(" ")).toString().trim());
        } else {
            System.out.println();
            System.out.println("La Gift Card con ean " + getEanGiftCardToCheck() + " non e' nel database");
        }

        clear();
    }

    static String getRandomString(int i) {

        byte[] bytearray;
        bytearray = new byte[256];
        String myString;
        StringBuffer theBuffer;
        String theAlphaNumericS;

        new Random().nextBytes(bytearray);

        myString
                = new String(bytearray, Charset.forName("UTF-8"));

        theBuffer = new StringBuffer();

        theAlphaNumericS
                = myString
                .replaceAll("[^A-Z0-9]", "");

        for (int m = 0; m < theAlphaNumericS.length(); m++) {

            if (Character.isLetter(theAlphaNumericS.charAt(m))
                    && (i > 0)
                    || Character.isDigit(theAlphaNumericS.charAt(m))
                    && (i > 0)) {

                theBuffer.append(theAlphaNumericS.charAt(m));
                i--;
            }
        }
        return theBuffer.toString();
    }

    public void clear() {
        listGiftCard.clear();
        mapGiftCard.clear();
        mapEanGiftCardAndStatusDB.clear();
        mapEanGiftCardAndActivationCodeDB.clear();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getEanGiftCardToCheck() {
        return eanGiftCardToCheck;
    }

    public void setEanGiftCardToCheck(String eanGiftCardToCeck) {
        this.eanGiftCardToCheck = eanGiftCardToCeck;
    }
}
