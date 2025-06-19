import java.time.LocalDate;
import java.util.Scanner;

public class Depot extends Transaction{
    Depot(String typeTransaction, double montant, CompteBancaire compteSource){
        super(typeTransaction, montant, compteSource);
    }
    @Override
    public void executer(){
        System.out.println("Depot de "+montant+" sur le compte "+compteSource);
    }
}
