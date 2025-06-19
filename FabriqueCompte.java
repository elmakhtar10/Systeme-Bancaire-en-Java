import java.time.LocalDate;
import java.util.Random;

public class FabriqueCompte {
    public static String generateNumeroCompte(String typeCompte){
        Random random = new Random();
        return typeCompte + random.nextInt(100000,1000000);
    }

    public static CompteCourant creerCompteCourant(Client client, double solde){
        String numeroCompte = generateNumeroCompte("CC");
        return new CompteCourant(numeroCompte,solde,client,"Compte Courant");
    }
    public static ComptEpargne creerCompteEpargne(Client client, double solde, double tauxInteret){
        String numeroCompte = generateNumeroCompte("CE");
        return new ComptEpargne(numeroCompte,solde,tauxInteret,client,"Compte Epargne");
    }
}
