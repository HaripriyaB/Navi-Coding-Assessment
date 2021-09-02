package com.haripriya;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AppTest {
    @Test
    public void shouldReturnZero() {
        App.addNewLoan("IDIDI", "Dale", 10000, 5, 4);
        assertEquals("IDIDI Dale 0 60", App.checkBalance("IDIDI", "Dale", 0));
    }

    @Test
    public void testForMultipleLumpPayments() {
        App.addNewLoan("IDIDI", "Dale", 10000, 5, 4);
        App.addLumpPayment("IDIDI", "Dale", 1000, 5);
        App.addLumpPayment("IDIDI", "Dale", 2000, 15);
        assertEquals("IDIDI Dale 6200 29", App.checkBalance("IDIDI", "Dale", 16));
    }

    @Test
    public void testZeroLumpPayment() {
        App.addNewLoan("IDIDI", "Dale", 10000, 5, 4);
        App.addLumpPayment("IDIDI", "Dale", 0, 5);
        App.addLumpPayment("IDIDI", "Dale", 10, 0);
        assertEquals("IDIDI Dale 1000 55", App.checkBalance("IDIDI", "Dale", 5));
    }

    @Test
    public void testHappyPath() {
        App.addNewLoan("IDIDI", "Dale", 10000, 5, 4);
        App.addLumpPayment("IDIDI", "Dale", 2000, 15);
        App.addNewLoan("IDIDI", "Harry", 1000, 1, 2);
        App.addLumpPayment("IDIDI", "Harry", 100, 3);
        assertEquals("IDIDI Dale 6000 30", App.checkBalance("IDIDI", "Dale", 20));
        assertEquals("IDIDI Harry 525 6", App.checkBalance("IDIDI", "Harry", 5));

    }
}
