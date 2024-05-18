package taxes;

public class Usn15 extends TaxSystem {
    @Override
    public int calcTaxFor(int debit, int credit) {
        return (debit - credit > 0) ? (debit - credit) * 15 / 100 : 0;
    }

    @Override
    public String toString() {
        return "УСН 15";
    }
}
