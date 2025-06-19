import java.time.LocalDate;

public class Retrait extends Transaction{
    public Retrait(String typeTransaction, double montant, CompteBancaire compteSource) {
        super(typeTransaction, montant, compteSource);
    }

    @Override
    public void executer() {
        System.out.println("Retrait de "+montant+" sur le compte "+compteSource);
    }
}
