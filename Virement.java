public class Virement extends Transaction{
    CompteBancaire compteDestinataire;
    Virement(String typeTransaction, double montant, CompteBancaire compteSource, CompteBancaire compteDestinataire){
        super(typeTransaction, montant, compteSource);
        this.compteDestinataire = compteDestinataire;
    }
    @Override
    public void executer(){

    }
}
