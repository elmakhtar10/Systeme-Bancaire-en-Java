import java.time.LocalDate;

public class ComptEpargne extends CompteBancaire{
    private double tauxInteret;
    ComptEpargne(String numeroCompte, double solde, double tauxInteret, Client client, String typeDeCompte) {
        super(numeroCompte, solde, client, typeDeCompte);
        if(tauxInteret < 0 || tauxInteret > 10){
            throw new IllegalArgumentException("Le taux d'interet doit etre entre 0 et 10%.");
        }
        this.tauxInteret = tauxInteret;

    }

    @Override
    public void crediter(double montant) {
        if(montant <= 0){
            System.out.println("Le montant a crediter doit etre positif.");
            return;
        }
        solde += montant;
    }

    @Override
    public void debiter(double montant) throws ExceptionSoldeInsuffisant {
        if(montant <= 0){
            System.out.println("Le montant a debiter doit etre positif.");
            return;
        }
        if(solde - montant < 0){
            throw new ExceptionSoldeInsuffisant("Le solde est Insuffisant.");
        }
        solde -= montant;
    }

    public void calculerInterets(){
        solde += (solde * 5) / 100;
        System.out.println("Taux Interets calcules et ajoutes: "+(solde * 5) / 100);
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
