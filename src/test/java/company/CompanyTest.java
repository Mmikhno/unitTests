package company;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import taxes.Usn15;
import taxes.Usn6;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CompanyTest {
    Company company1 = new Company("Рога", new Usn6());
    Company company2 = new Company("Копыта", new Usn15());
    Deal purchase1 = new Expenditure("товары", 10_000);
    Deal purchase2 = new Expenditure("услуги", 20_000);
    Deal sale1 = new Sale("услуги", 5_000);
    Deal sale2 = new Sale("товары", 3_000);

    @Test
    void shiftMoneyDebit() {
        int amount1 = 100_000;
        int amount2 = 50_000;
        company1.shiftMoney(amount1);
        company1.shiftMoney(amount2);
        int actual = company1.getDebit();
        int expected = amount1 + amount2;
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0,0,0",
            "-1,0,1",
            "1, 1, 0",
            "10_000, 10_000, 0",
            "-10_000, 0, 10_000"
    })
    void shiftMoney(int amount, int debit, int credit) {
        company1.shiftMoney(amount);
        int actualCredit = company1.getCredit();
        int actualDebit = company1.getDebit();
        Assertions.assertEquals(actualDebit, debit);
        Assertions.assertEquals(actualCredit, credit);
    }

    @Test
    void payTaxesUsn6() {
        int amount = 100_000;
        company1.shiftMoney(amount);
        company1.payTaxes();
        Assertions.assertTrue(company1.getDebit() == 0 && company1.getCredit() == 0);
    }

    @Test
    void payTaxesUsn15() {
        int amount = 11_000;
        company2.shiftMoney(amount);
        company2.payTaxes();
        Assertions.assertTrue(company2.getDebit() == 0 && company2.getCredit() == 0);
    }

    @Test
    void applyDealsExpenditure() {
        int shiftAmount = 100_000;
        company1.shiftMoney(shiftAmount);
        Deal[] deals = {purchase1, purchase2};
        int expected = 70_000;
        int actual = company1.applyDeals(deals);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void applyDealsSales() {
        Deal[] deals = {sale1, sale2};
        int expected = 8_000;
        int actual = company1.applyDeals(deals);
        Assertions.assertEquals(expected, actual);
    }

    // same tests with Hamcrest

    @ParameterizedTest
    @CsvSource(value = {
            "0,0,0",
            "-1,0,1",
            "1, 1, 0",
            "10_000, 10_000, 0",
            "-10_000, 0, 10_000"
    })
    void shiftMoneyWithHamcrest(int amount, int debit, int credit) {
        company1.shiftMoney(amount);
        assertThat("debit", company1.getDebit(), equalTo(debit));
        assertThat("credit", company1.getCredit(), equalTo(credit));
    }

    @Test
    void payTaxesUsn6WithHamcrest() {
        int amount = 100_000;
        company1.shiftMoney(amount);
        company1.payTaxes();
        assertThat(company1.getDebit(), allOf(equalTo(0), equalTo(company1.getCredit())));
    }

    @Test
    void payTaxesUsn15WithHamcrest() {
        int amount = 11_000;
        company2.shiftMoney(amount);
        company2.payTaxes();
        assertThat(company2.getDebit(), allOf(equalTo(0), equalTo(company2.getCredit())));
    }

    @Test
    void applyDealsExpenditureWithHamcrest() {
        int shiftAmount = 100_000;
        company1.shiftMoney(shiftAmount);
        Deal[] deals = {purchase1, purchase2};
        int expected = 70_000;
        assertThat(expected, equalTo(company1.applyDeals(deals)));
    }

    @Test
    void applyDealsSalesWithHamcrest() {
        Deal[] deals = {sale1, sale2};
        int expected = 8_000;
        assertThat(expected, equalTo(company1.applyDeals(deals)));
    }

    @Test
    void negativeBalanceAfterPurchase() {
        Deal[] purchases = {purchase1, purchase2};
        assertThat(company1.applyDeals(purchases), lessThan(0));
    }
}