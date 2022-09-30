import java.io.*;
import java.util.HashMap;

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

        File directory = new File (path);
        if (!directory.exists()){
            directory.mkdir();
        }

        File file = new File(path + nameFile);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void write(String path, String nameFile, String eanGiftCard, String activationCode, double balance, String store, String status){

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

    public HashMap<String, String> readEanGiftCardAndActivationCode(String path, String nameFile){

        if (!mapEanGiftCardAndActivationCodeDB.isEmpty()){
            clear();
        }

        File file = new File(path + nameFile);
        if(file.exists()){
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

    public HashMap<String, String> readEanGiftCardAndStatus(String path, String nameFile){

        if (!mapEanGiftCardAndStatusDB.isEmpty()){
            clear();
        }

        File file = new File(path + nameFile);
        if(file.exists()){
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

    public void clear(){
        mapEanGiftCardAndActivationCodeDB.clear();
        mapEanGiftCardAndStatusDB.clear();
    }
}

