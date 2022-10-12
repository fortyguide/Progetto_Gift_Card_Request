import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class DataManagement {

    private static DataManagement instance;
    HashMap<String, String> mapEanGiftCardAndActivationCodeDB = new HashMap<>();
    HashMap<String, String> mapEanGiftCardAndStatusDB = new HashMap<>();

    public static DataManagement getInstance() {
        if (instance == null) {
            instance = new DataManagement();
        }
        return instance;
    }

    public void createDirectoryAndFile(String path, String nameFile) {

        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdir();
        }

        File file = new File(path + nameFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void write(String path, String nameFile, String eanGiftCard, String activationCode, double balance, String store, String status) {

        File file = new File(path + nameFile);
        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(file, true));
            bw.write("eanGiftCard = " + eanGiftCard + "; "
                    + "codice attivazione = " + activationCode + "; "
                    + "saldo = " + balance + "; "
                    + "azienda = " + store + "; "
                    + "stato tessera = " + status + ";\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public HashMap<String, String> readEanGiftCardAndActivationCode(String path, String nameFile) {

        if (!mapEanGiftCardAndActivationCodeDB.isEmpty()) {
            clear();
        }

        File file = new File(path + nameFile);
        if (file.exists()) {
            try {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String row;
                    while ((row = br.readLine()) != null) {
                        StringBuilder sb1 = new StringBuilder(row);
                        StringBuilder sb2 = new StringBuilder(row);
                        String eanGiftCardDB = sb1.delete(0, sb1.indexOf("=") + 1).delete(sb1.indexOf(";"), sb1.length()).toString().trim();
                        String activationCodeDB = sb2.delete(0, sb2.indexOf(";") + 2).delete(0, sb2.indexOf("=") + 2).delete(sb2.indexOf(";"), sb2.length()).toString().trim();
                        mapEanGiftCardAndActivationCodeDB.put(eanGiftCardDB, activationCodeDB);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mapEanGiftCardAndActivationCodeDB;
    }

    public HashMap<String, String> readEanGiftCardAndStatus(String path, String nameFile) {

        if (!mapEanGiftCardAndStatusDB.isEmpty()) {
            clear();
        }

        File file = new File(path + nameFile);
        if (file.exists()) {
            try {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String row;
                    while ((row = br.readLine()) != null) {
                        StringBuilder sb1 = new StringBuilder(row);
                        StringBuilder sb2 = new StringBuilder(row);
                        String eanGiftCardDB = sb1.delete(0, sb1.indexOf("=") + 1).delete(sb1.indexOf(";"), sb1.length()).toString().trim();
                        String statusDB = sb2.reverse().delete(sb2.indexOf("="), sb2.length()).reverse().toString().replaceAll(";", "").trim();
                        mapEanGiftCardAndStatusDB.put(eanGiftCardDB, statusDB);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mapEanGiftCardAndStatusDB;
    }

    public void update(String path, String nameFile, String eanGiftCard, String activationCode, String status) throws IOException {

        List<String> tessere = new ArrayList<>();
        String balance = "";
        String store = "";
        String filePath = path + nameFile;
        Scanner sc = new Scanner(new File(filePath));
        StringBuffer buffer = new StringBuffer();
        while (sc.hasNextLine()) {
            String tessera = sc.nextLine();
            buffer.append(tessera + System.lineSeparator());
            tessere.add(tessera);
        }
        String fileContents = buffer.toString();
        sc.close();
        for (String tessera : tessere) {
            if (tessera.startsWith("eanGiftCard = " + eanGiftCard)) {
                StringBuilder sb1 = new StringBuilder(tessera);
                StringBuilder sb2 = new StringBuilder(tessera);
                balance = sb1.delete(0, sb1.indexOf("saldo")).delete(0, sb1.indexOf("= ")).delete(sb1.indexOf(";"), sb1.length()).delete(sb1.indexOf("="), sb1.indexOf(" ")).toString().trim();
                store = sb2.delete(0, sb2.indexOf("azienda")).delete(0, sb2.indexOf("= ")).delete(sb2.indexOf(";"), sb2.length()).delete(sb2.indexOf("="), sb2.indexOf(" ")).toString().trim();
            }
        }
        String oldLine = "eanGiftCard = " + eanGiftCard + "; "
                + "codice attivazione = " + activationCode + "; "
                + "saldo = " + balance + "; "
                + "azienda = " + store + "; "
                + "stato tessera = " + status + ";";
        String newLine = "eanGiftCard = " + eanGiftCard + "; "
                + "codice attivazione = " + activationCode + "; "
                + "saldo = " + balance + "; "
                + "azienda = " + store + "; "
                + "stato tessera = " + "TESSERA ATTIVA" + ";";
        fileContents = fileContents.replace(oldLine, newLine);
        FileWriter writer = new FileWriter(filePath);
        writer.append(fileContents);
        writer.flush();
    }

    public String readGiftCard(String path, String nameFile, String eanGiftCard) throws FileNotFoundException {
        List<String> tessere = new ArrayList<>();
        String giftCardData = "";
        String filePath = path + nameFile;
        Scanner sc = new Scanner(new File(filePath));
        while (sc.hasNextLine()) {
            String tessera = sc.nextLine();
            tessere.add(tessera);
        }
        sc.close();
        for (String tessera : tessere) {
            if (tessera.startsWith("eanGiftCard = " + eanGiftCard)) {
                giftCardData = tessera;
                break;
            }
        }
        return giftCardData;
    }

    public void clear() {
        mapEanGiftCardAndActivationCodeDB.clear();
        mapEanGiftCardAndStatusDB.clear();
    }
}

