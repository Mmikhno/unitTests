package company;

import taxes.*;

public class Main {
    public static void main(String[] args) {

        Company company1 = new Company("First company", new Usn6());
        company1.shiftMoney(100_000);
        company1.shiftMoney(-50_000);
        company1.shiftMoney(10_000);
        System.out.println(company1.toString());
        company1.payTaxes();
        company1.setTaxSystem(new Usn15());
        company1.shiftMoney(100_000);
        company1.shiftMoney(-100_000);
        System.out.println(company1.toString());
        company1.payTaxes();

        Company company2 = new Company("Second company", new Usn15());
        Deal[] deals = {
                new Sale("товары", 1000_000),
                new Sale("услуги", 100_000),
                new Expenditure("всякое разное", 200_000)
        };
        company2.shiftMoney(100_000);
        System.out.println(company2.applyDeals(deals));
        for (Deal deal : deals) {
            System.out.println(deal.getComment());
        }
        System.out.println(company2.getCredit() + " " + company2.getDebit());

    }

}