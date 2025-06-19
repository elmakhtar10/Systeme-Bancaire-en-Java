import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void menu(){
        System.out.println("\n1. Ajouter un Client");
        System.out.println("\n2. Afficher Clients");
        System.out.println("\n3. Creer un Compte");
        System.out.println("\n4. Effectuer une Transaction");
        System.out.println("\n5. Consulter un Compte");
        System.out.println("\n6. Générer un relevé");
        System.out.println("\n7. Quitter");

    }
    public static void menuTransaction(){
        System.out.println("\n1. Crediter");
        System.out.println("\n2. Debiter");
        System.out.println("\n3. Transfert");
    }
    public static void clearConsoleWindows() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println("Impossible de nettoyer la console.");
        }
    }



    public static void main(String[] args) throws ExceptionSoldeInsuffisant {
        ArrayList<Client> clients = new ArrayList<>();
        ArrayList<CompteBancaire> compteBancaire = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        while(true){
            menu();
            int choice;
            String choix;
            boolean isDigit = true;
            int choixe = 0;
            while(isDigit) {
                try {
                    System.out.print("\nFaite votre choix: ");
                    choix = scanner.nextLine();
                    choixe = Integer.parseInt(choix);
                    isDigit = false;
                }catch(Exception e){
                    System.out.println("Veuillez saisir un nombre.");
                }
            }
//            clearConsoleWindows();
            switch (choixe){
                case 1: Banque.addClient(clients);
                    break;
                case 2: Banque.afficherClient(clients);
                    break;
                case 3:
                    Client client = Banque.researchClientById(clients);
                    if(client == null){
                        System.out.println("Le client n'existe pas.");
                    }else {
                        client.ajouterCompte(compteBancaire, client);
                        Client.afficherInfosCompte(compteBancaire);
                    }
                    break;

                case 4:
                    menuTransaction();
                    System.out.print("Quelle Transaction voulez-vous faire: ");
                    choice = scanner.nextInt();
                    switch (choice){
                        case 1: Banque.effectuerDepot(compteBancaire);
                            break;
                        case 2: Banque.effectuerRetrait(compteBancaire);
                            break;
                        case 3: Banque.effectuerVirement(compteBancaire);
                            break;
                    }
                    break;
                case 5: Banque.consulterUnCompte(compteBancaire);
                    break;
                case 6: Banque.genererRelever(compteBancaire);
                    break;
                case 7:
                    System.out.println("Au revoir...");
                    return;
                default:
                    System.out.println("Choix invalide, Veuillez reessayer.");

            }
        }
    }
}
