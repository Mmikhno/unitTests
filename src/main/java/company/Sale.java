package company;

public class Sale extends Deal {
    public Sale(String salesGood, int price) {

        super("Продажа " + salesGood + " на " + price + " руб.", 0, price);
    }
}
