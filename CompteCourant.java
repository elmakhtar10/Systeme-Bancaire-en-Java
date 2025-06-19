import java.time.LocalDate;

public class CompteCourant extends CompteBancaire{
    CompteCourant(String numeroCompte,double solde, Client client,String typeDeCompte){
        super(numeroCompte, solde, client, typeDeCompte);
    }
    @Override
    public void crediter(double montant) {
        if(montant < 1){
            System.out.println("Le montant a crediter doit etre positif.");
            return;
        }
        solde += montant;
    }

    @Override
    public void debiter(double montant) throws ExceptionSoldeInsuffisant{
        if(montant < 1){
            System.out.println("Le montant a crediter doit etre positif.");
            return;
        }
        if(solde - montant < 0){
            throw new ExceptionSoldeInsuffisant("Solde Insuffisant.");
        }
        solde -= montant;
    }

    @Override
    public void calculerInterets() {
        System.out.println("On ne calcul pas d'interet dans un compte courant");
    }

    @Override
    public void virement(double montant, CompteBancaire compteSource, CompteBancaire compteDestinataire) {
        if(montant > compteSource.getSolde()){
            System.out.println("Solde insuffisant.");
            return;
        }
        compteSource.setSolde(compteSource.getSolde() - montant);
        compteDestinataire.setSolde(compteDestinataire.getSolde() + montant);
    }


}
