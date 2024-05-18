package company;

public class Expenditure extends Deal {
    public Expenditure(String purchGood, int price) {
        super("Покупка " + purchGood + " на " + price + " руб.", price, 0);
    }
}
