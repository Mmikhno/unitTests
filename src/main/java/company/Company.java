package company;

import taxes.*;

public class Company {
    private String title;
    private int debit;
    private int credit;
    private TaxSystem taxSystem;

    public Company(String title, TaxSystem taxSystem) {
        this.title = title;
        this.taxSystem = taxSystem;
    }

    public int getDebit() {
        return debit;
    }

    public int getCredit() {
        return credit;
    }

    public void setTaxSystem(TaxSystem taxSystem) {
        this.taxSystem = taxSystem;
    }

    public TaxSystem getTaxSystem() {
        return taxSystem;
    }

    public void shiftMoney(int amount) {
        if (amount > 0) {
            this.debit += amount;
        } else if (amount < 0) {
            this.credit += Math.abs(amount);
        } else {
            return;
        }
    }

    public void payTaxes() {
        int tax = this.taxSystem.calcTaxFor(this.debit, this.credit);
        System.out.printf("Компания %s уплатила налог в размере: %d руб\n", this.title, tax);
        this.debit = 0;
        this.credit = 0;
    }

    public int applyDeals(Deal[] deals) {
        for (Deal deal : deals) {
            this.shiftMoney(deal.getCreditChange() * -1);
            this.shiftMoney(deal.getDebitChange());
        }
        int result = this.debit - this.credit;
        this.payTaxes();
        return result;
    }

    @Override
    public String toString() {
        return String.format("Наименование компании: %s\n" +
                "Налоговая система: %s, \n" +
                "Доходы составляют: %d, \n" +
                "Расходы составляют: %d\n", this.title, this.taxSystem.toString(), this.debit, this.credit);
    }
}
