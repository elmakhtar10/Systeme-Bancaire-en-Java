import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Transaction {
    protected String id;
    protected String typeTransaction;
    protected double montant;
    protected LocalDate dateTransaction;
    protected CompteBancaire compteSource;
    protected List<String> historique = new ArrayList<>();

    public Transaction(String typeTransaction, double montant, CompteBancaire compteSource) {
        this.id = generateUniqueId();
        this.typeTransaction = typeTransaction;
        this.montant = montant;
        this.dateTransaction = LocalDate.now();
        this.compteSource = compteSource;
    }
    String generateUniqueId(){
        return UUID.randomUUID().toString().substring(0,8).toUpperCase();
    }
    public abstract void executer();




}
