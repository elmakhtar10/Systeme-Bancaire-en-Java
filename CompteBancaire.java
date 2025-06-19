import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class CompteBancaire {
    protected String numeroCompte;
    protected LocalDate dateOuverture;
    protected double solde;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    protected Client client;

    public String getTypeDeCompte() {
        return typeDeCompte;
    }

    public void setTypeDeCompte(String typeDeCompte) {
        this.typeDeCompte = typeDeCompte;
    }

    protected String typeDeCompte;
    CompteBancaire(String numeroCompte, double solde, Client client, String typeDeCompte){
        this.numeroCompte = numeroCompte;
        this.dateOuverture = LocalDate.now();
        this.solde = solde;
        this.client = client;
        this.typeDeCompte = typeDeCompte;
    }
    public String getNumeroCompte(){
        return numeroCompte;
    }
    public LocalDate getDateOuverture(){
        return dateOuverture;
    }
    public double getSolde(){
        return solde;
    }
    public void setNumeroCompte(String numeroCompte){
        this.numeroCompte = numeroCompte;
    }
    public void setDateOuverture(LocalDate dateOuverture){
        this.dateOuverture = dateOuverture;
    }
    public void setSolde(double solde){
        this.solde = solde;
    }
    public abstract void crediter(double montant);
    public abstract void debiter(double montant) throws ExceptionSoldeInsuffisant;
    public abstract void calculerInterets();
    public abstract void virement(double montant,CompteBancaire compteSource, CompteBancaire compteDestinataire);

}
