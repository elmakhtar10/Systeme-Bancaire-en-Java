import javax.swing.text.DateFormatter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Banque {
    public static Scanner scanner = new Scanner(System.in);
    public static List<Transaction> historiques = new ArrayList<>();
    public static String readStringInput(String message){
        System.out.print(message);
        return scanner.nextLine();
    }
    public static int readIntegerInput(String message){
        System.out.print(message);
        return scanner.nextInt();
    }
    public static LocalDate readLocalDateInput(String message){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while(true) {
            System.out.print(message);
            String date = scanner.nextLine();
            try{
                return LocalDate.parse(date, formatter);
            }catch (DateTimeException e){
                System.out.println("Date invalid. Veuillez reessayer!");
            }
        }
    }
    public static Client readClientsInformations(){
        String lastName = readStringInput("Entrez le nom du client: ");
        while(lastName.length() > 50){
            System.out.println("Nom max 50 caractères");
            lastName = readStringInput("Entrez le nom du client: ");
        }
        String firstName = readStringInput("Entrez le prenom du client: ");
        while(firstName.length() > 50){
            System.out.println("Prénom  max 50 caractères");
            firstName = readStringInput("Entrez le prenom du client: ");
        }
        String address = readStringInput("Entrez l'adresse du client: ");
        String phoneNumber = readStringInput("Entrez le numero de telephone du client: ");
        LocalDate birthDate = readLocalDateInput("Entrez la date de naissance du client format(dd/MM/yyyy): ");
        return new Client(lastName,firstName,birthDate,address,phoneNumber);
    }
    public static boolean isClientExist(ArrayList<Client> clients, Client client1){
        for(Client client : clients){
            if(client.getPhoneNumber().equalsIgnoreCase(client1.getPhoneNumber())){
                return true;
            }
        }
        return false;
    }
    public static void addClient(ArrayList<Client> clients){
        Client client = readClientsInformations();
        if(isClientExist(clients, client)){
            System.out.println(client.getFirstName()+ " " + client.getLastName()+" existe Deja!");
            return;
        }
        clients.add(client);
        System.out.println("Client "+client.getFirstName()+ " " + client.getLastName()+ " Ajoutee avec succes.");
    }
    public static void afficherClient(ArrayList<Client> clients){
        if(clients.isEmpty()){
            System.out.println("Il n'y a pas encore de client.");
        }
        for(Client client : clients){
            System.out.println("id: "+client.getId());
            System.out.println("Nom: "+client.getLastName());
            System.out.println("Prenom: "+client.getFirstName());
            System.out.println("Date de Naissance: "+client.getBirthDate());
            System.out.println("Adresse: "+client.getAddress());
            System.out.println("Téléphone: " + client.getPhoneNumber());
            System.out.println("---------------------------------------");

        }
    }
    public static boolean isIdClientExist(ArrayList<Client> clients, String id){
        for(Client client : clients){
            if(client.getId().equalsIgnoreCase(id)){
                return true;
            }
        }
        return false;
    }
    public static Client researchClientById(ArrayList<Client> clients){
        String id = readStringInput("Entrez l'id du client : ");
        if(isIdClientExist(clients, id)){
            for(Client client : clients){
                if(client.getId().equalsIgnoreCase(id)){
                    return client;
                }

            }
        }
        return null;
    }
    public static CompteBancaire rechercherCompteByNumero(ArrayList<CompteBancaire> compteBancaires, String numeroCompte){
        for(CompteBancaire compteBancaire : compteBancaires){
            if(compteBancaire.getNumeroCompte().equalsIgnoreCase(numeroCompte)){
                return compteBancaire;
            }
        }
        return null;
    }
    public static void effectuerRetrait(ArrayList<CompteBancaire> compteBancaires) throws ExceptionSoldeInsuffisant {
        System.out.print("Entrez le numero du compte source: ");
        String numeroCompte = scanner.nextLine();
        CompteBancaire compteBancaire = rechercherCompteByNumero(compteBancaires, numeroCompte);
        if(compteBancaire == null){
            System.out.println("Le compte n'existe pas.");
            return;
        }
        System.out.print("Entrez le montant a debiter: ");
        double montant = scanner.nextDouble();
        while (montant < 0){
            System.out.println("Le montant doit etre positif.");
            System.out.print("Entrez le montant a virer: ");
            montant = scanner.nextDouble();
        }
        try {
            compteBancaire.debiter(montant);
            System.out.println("Un montant de " + montant + " a ete retirer dans le compte " + compteBancaire.getNumeroCompte());
            System.out.println("Date Retrait: " + LocalDate.now());
            System.out.println("Nouveau Solde: " + compteBancaire.getSolde());
            Retrait retrait = new Retrait("Retrait",montant,compteBancaire);
            historiques.add(retrait);
        }catch (ExceptionSoldeInsuffisant e){
            System.out.println("Solde insuffisant");
        }

    }
    public static void effectuerDepot(ArrayList<CompteBancaire> compteBancaires){
        System.out.print("Entrez le numero du compte source: ");
        String numeroCompte = scanner.nextLine();
        CompteBancaire compteBancaire = rechercherCompteByNumero(compteBancaires, numeroCompte);
        if(compteBancaire == null){
            System.out.println("Le compte n'existe pas.");
            return;
        }
        System.out.print("Entrez le montant a crediter: ");
        double montant = scanner.nextDouble();
        while (montant < 0){
            System.out.println("Le montant doit etre positif.");
            System.out.print("Entrez le montant a virer: ");
            montant = scanner.nextDouble();
        }
        compteBancaire.crediter(montant);
        System.out.println("Un montant de " + montant + " a ete deposer dans le compte " + compteBancaire.getNumeroCompte());
        System.out.println("Date Depot: " + LocalDate.now());
        System.out.println("Nouveau Solde: " + compteBancaire.getSolde());
        Depot depot = new Depot("Depot", montant, compteBancaire);
        historiques.add(depot);

    }
    public static void effectuerVirement(ArrayList<CompteBancaire> compteBancaires){
        System.out.print("Entrez le numero du compte source: ");
        String numeroCompteSource = scanner.nextLine();
        System.out.print("Entrez le numero du compte destinataire: ");
        String numeroCompteDestinataire = scanner.nextLine();
        CompteBancaire compteSource = rechercherCompteByNumero(compteBancaires, numeroCompteSource);
        CompteBancaire compteDestinataire = rechercherCompteByNumero(compteBancaires, numeroCompteDestinataire);
        if(compteSource == null){
            System.out.println("Le compte n'existe pas.");
            return;
        }
        if(compteDestinataire == null){
            System.out.println("Le compte n'existe pas.");
            return;
        }
        System.out.print("Entrez le montant a virer: ");
        double montant = scanner.nextDouble();
        while (montant < 0){
            System.out.println("Le montant doit etre positif.");
            System.out.print("Entrez le montant a virer: ");
            montant = scanner.nextDouble();
        }
        compteSource.virement(montant,compteSource,compteDestinataire);
        System.out.println("Un montant de " + montant + " a ete debiter dans le compte " + compteSource.getNumeroCompte()
                            +" et crediter vers le compte "+compteDestinataire.getNumeroCompte());
        System.out.println("Date Virement: " + LocalDate.now());
        System.out.println("Nouveau Solde du compte "+compteSource.getNumeroCompte()+" : " + compteSource.getSolde());
        System.out.println("Nouveau Solde du compte "+compteDestinataire.getNumeroCompte()+" : " + compteDestinataire.getSolde());
        Virement virement = new Virement("Virement",montant,compteSource,compteDestinataire);
        historiques.add(virement);

    }

    public static CompteBancaire rechercherCompteParNumero(ArrayList<CompteBancaire> compteBancaires, String numeroCompte){
        for(CompteBancaire compteBancaire : compteBancaires){
            if(compteBancaire.getNumeroCompte().equalsIgnoreCase(numeroCompte)){
                return compteBancaire;
            }
        }
        return null;
    }
    public static void consulterUnCompte(ArrayList<CompteBancaire> compteBancaires){
        System.out.print("Entrez le numero de compte du Compte a consulter: ");
        String numeroCompte = scanner.nextLine();
        CompteBancaire compteBancaire = rechercherCompteParNumero(compteBancaires, numeroCompte);
        if(compteBancaire == null){
            System.out.println("Ce compte bancaire n'existe pas.");
            return;
        }
        System.out.println("Numero Compte: "+compteBancaire.getNumeroCompte());
        System.out.println("Date Ouverture: "+compteBancaire.getDateOuverture());
        System.out.println("Type De Compte: "+compteBancaire.getTypeDeCompte());
        System.out.println("Solde: "+compteBancaire.getSolde());
        System.out.println("---------------------------------------");

    }
    public static void genererRelever(ArrayList<CompteBancaire> compteBancaires){
        System.out.print("Entrez le numero de compte du Compte a consulter: ");
        String numeroCompte = scanner.nextLine();
        CompteBancaire compteBancaire = rechercherCompteParNumero(compteBancaires, numeroCompte);
        if(compteBancaire == null){
            System.out.println("Ce compte bancaire n'existe pas.");
            return;
        }
        File dossier = new File("relever");
        if(!dossier.exists()){
            if(dossier.mkdirs()){
                System.out.println("Dossier 'relever' creer avec succes.");
            }else {
                System.out.println("Erreur lors de la creation du dossier.");
                return;
            }
        }
        String nomFichier = "ReleverCompte_"+compteBancaire.getNumeroCompte()+".txt";
        File file = new File(dossier, nomFichier);
        try(FileWriter fichier = new FileWriter(file)){
            fichier.write("\n===== 🧾 RELEVÉ DE COMPTE =====\n");
            fichier.write("Titulaire: "+compteBancaire.getClient().getFirstName()+" "+compteBancaire.getClient().getLastName()+"\n");
            fichier.write("Numero Compte: "+compteBancaire.getNumeroCompte()+"\n");
            fichier.write("Type de Compte: "+compteBancaire.getTypeDeCompte()+"\n");
            fichier.write("Date Ouverture: "+compteBancaire.getDateOuverture()+"\n");
            fichier.write("Solde Actuel: "+compteBancaire.getSolde()+"\n");
            fichier.write("Transaction: \n");
            if(historiques.isEmpty()){
                fichier.write("Aucune Transaction n'a ete faite.\n");
            }else {
                for(Transaction historique : historiques){
                    fichier.write(historique.dateTransaction.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))+" - "+historique.typeTransaction+" : "+historique.montant+"\n");
                }
            }
            fichier.write("---------------------------------------\n");
            System.out.println("Relever de Compte generer avec succes: "+nomFichier);
        }catch (IOException e){
            System.out.println("Erreur lors de la creation du fichier.");
        }
    }


}
