package taxes;

import company.Company;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.typeCompatibleWith;

class TaxSystemTest {
    Company companyUsn6 = new Company("Рога", new Usn6());
    Company companyUsn15 = new Company("Копыта", new Usn15());

    @ParameterizedTest
    @CsvSource(value = {
            "10_000,5_000,750",
            "10_000,50_000,0",
            "0,0,0",
            "0,1000,0",
            "-1,0,0",
            "2,1,0",
            "1,1,0"
    })
    void calcTaxForUsn15(int debit, int credit, int expected) {
        int actual = companyUsn15.getTaxSystem().calcTaxFor(debit, credit);
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "100000,50000,6000",
            "0,100000,0",
            "-10000,100000,0",
            "1,0,0",
            "-1,10_000,0"
    })
    void calcTaxForUsn6(int debit, int credit, int expected) {
        int actual = companyUsn6.getTaxSystem().calcTaxFor(debit, credit);
        Assertions.assertEquals(expected, actual);
    }

    // same tests with Hamcrest

    @Test
    void usn6usn15InheritsFromTaxSystem() {
        assertThat(Usn6.class, typeCompatibleWith(TaxSystemTest.class));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "10_000,5_000,750",
            "10_000,50_000,0",
            "0,0,0",
            "0,1000,0",
            "-1,0,0",
            "2,1,0",
            "1,1,0"
    })
    void calcTaxForUsn15WithHamcrest(int debit, int credit, int expected) {
        int actual = companyUsn15.getTaxSystem().calcTaxFor(debit, credit);
        assertThat(expected, equalTo(actual));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "100000,50000,6000",
            "0,100000,0",
            "-10000,100000,0",
            "1,0,0",
            "-1,10_000,0"
    })
    void calcTaxForUsn6WithHamcrest(int debit, int credit, int expected) {
        int actual = companyUsn6.getTaxSystem().calcTaxFor(debit, credit);
        assertThat(expected, equalTo(actual));
    }
}