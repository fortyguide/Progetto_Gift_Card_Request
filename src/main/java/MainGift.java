import java.util.Scanner;

public class MainGift {
    public static void main(String[] args) {
        MainGift mainGift = new MainGift();
        mainGift.choiceOption();
    }

    public void choiceOption (){
        while(true) {
            Scanner scan = new Scanner(System.in);

            System.out.println();
            System.out.println("Inserisci un'opzione:");
            System.out.println("1.Crea Gift Card");
            System.out.println("2.Attiva Gift Card");
            System.out.println("3.Visualizza dati Gift Card");
            int opzione = scan.nextInt();

            switch (opzione) {
                case 1:
                    break;

                case 2:
                    break;

                case 3:
                    break;

                default:
                    System.out.println("Opzione non valida, riprova");
                    System.out.println();
                    break;
            }
        }
    }
}
