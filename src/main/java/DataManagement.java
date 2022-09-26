import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataManagement {

    private static DataManagement instance;

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

    public void write(String path, String nameFile, String eanGiftCard, String activationCode, double balance, String store){

        File file = new File(path + nameFile);
        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(file));
            bw.write("eanGiftCard = " + eanGiftCard + " " + "saldo = " + balance + " " + "azienda= " + store);
            bw.newLine();
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
}
