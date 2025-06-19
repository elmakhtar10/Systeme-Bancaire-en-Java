import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.time.LocalDateTime;

public class Client{
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private String id;
    private String lastName, firstName;
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;
    Client(String lastName, String firstName, LocalDate birthDate, String address, String phoneNumber){
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.id = generateId();
    }

    public static String generateNumeroCompte(String typeCompte){
        Random random = new Random();
        return typeCompte + random.nextInt(100000,1000000);
    }

    public String generateId(){
        Random random = new Random();
        String getLastName = lastName.length() >= 3 ? lastName.substring(0,3) : lastName;
        return  getLastName + random.nextInt(1000, 10000);
    }
    public static void menuTypeCompte(){
        System.out.println("\n1. Compte Courant");
        System.out.println("\n2. Compte Epargne");
    }
    public static Scanner scanner = new Scanner(System.in);

    public void ajouterCompte(ArrayList<CompteBancaire> compteBancaires, Client client){
        menuTypeCompte();
        System.out.print("\nQue voulez vous creer: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if(choice == 1){
            System.out.print("Voulez-vous ajouter un solde de depart(y/n) ? : ");
            String answer = scanner.nextLine();
            double solde;
            if(answer.equalsIgnoreCase("y")) {
                System.out.print("Entrez le solde de depart pour votre compte Courant: ");
                solde = scanner.nextDouble();
            }else solde = 0;
            CompteBancaire compteBancaire = FabriqueCompte.creerCompteCourant(client, solde);
            compteBancaires.add(compteBancaire);
            System.out.println("Compte Courant creer avec succes.");
        }
        else if(choice == 2){
            double tauxInteret = 10;
            System.out.print("Voulez-vous ajouter un solde de depart(y/n) ? : ");
            String answer = scanner.nextLine();
            double solde;
            if(answer.equalsIgnoreCase("y")) {
                System.out.print("Entrez le solde de depart pour votre compte Courant: ");
                solde = scanner.nextDouble();
            }else solde = 0;
            CompteBancaire compteBancaire = FabriqueCompte.creerCompteEpargne(client, solde, tauxInteret);
            compteBancaires.add(compteBancaire);
            System.out.println("Compte Epargne creer avec succes.");
        }else System.out.println("Choix invalide.");


    }

    public static void afficherInfosCompte(ArrayList<CompteBancaire> compteBancaires) {
        File dossier = new File("comptes");
        if (!dossier.exists()) {
            if (dossier.mkdirs()) {
                System.out.println("Dossier créé avec succès.");
            } else {
                System.out.println("Erreur lors de la création du dossier.");
                return;
            }
        }

        for (CompteBancaire compteBancaire : compteBancaires) {
            String nomFichier = "CompteClient_" + compteBancaire.getClient().getId() + ".txt";
            File fichier = new File(dossier, nomFichier);

            try (FileWriter fileWriter = new FileWriter(fichier)) {
                fileWriter.write("\n========== Informations Compte ==========\n");
                fileWriter.write("Titulaire: " + compteBancaire.getClient().getFirstName() + " " + compteBancaire.getClient().getLastName() + "\n");
                fileWriter.write("Numero Compte: " + compteBancaire.getNumeroCompte() + "\n");
                fileWriter.write("Type de Compte: " + compteBancaire.getTypeDeCompte() + "\n");
                fileWriter.write("Date Ouverture: " + compteBancaire.getDateOuverture() + "\n");
                fileWriter.write("Solde: " + compteBancaire.getSolde() + "\n");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                fileWriter.write("Date Generation: " + LocalDateTime.now().format(formatter) + "\n");
                fileWriter.write("---------------------------------------\n");

                System.out.println("Informations enregistrées dans " + fichier.getName());
            } catch (IOException e) {
                System.out.println("Erreur lors de la génération des informations du compte : " + e.getMessage());
            }
        }
    }

}
