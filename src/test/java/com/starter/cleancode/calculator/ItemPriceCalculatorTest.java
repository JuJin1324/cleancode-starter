package com.starter.cleancode.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.starter.cleancode.calculator.ItemPriceCalculator.ItemStatus.*;
import static com.starter.cleancode.calculator.ItemPriceCalculator.ItemType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemPriceCalculatorTest {
    ItemPriceCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new ItemPriceCalculator();
    }

    @Test
    void testCalculatePriceForBook() {
        // Test NEW book
        assertEquals(100.0, calculator.calculatePrice(BOOK, NEW, 100.0));

        // Test USED book
        assertEquals(80.0, calculator.calculatePrice(BOOK, USED, 100.0));

        // Test DAMAGED book
        assertEquals(50.0, calculator.calculatePrice(BOOK, DAMAGED, 100.0));
    }

    @Test
    void testCalculatePriceForElectronics() {
        ItemPriceCalculator calculator = new ItemPriceCalculator();

        // Test NEW electronics
        assertEquals(110.0, calculator.calculatePrice(ELECTRONICS, NEW, 100.0));

        // Test USED electronics
        assertEquals(70.0, calculator.calculatePrice(ELECTRONICS, USED, 100.0));

        // Test DAMAGED electronics
        assertEquals(0.0, calculator.calculatePrice(ELECTRONICS, DAMAGED, 100.0));
    }

    @Test
    void testCalculatePriceForClothing() {
        ItemPriceCalculator calculator = new ItemPriceCalculator();

        // Test NEW clothing
        assertEquals(100.0, calculator.calculatePrice(CLOTHING, NEW, 100.0));

        // Test USED clothing
        assertEquals(90.0, calculator.calculatePrice(CLOTHING, USED, 100.0));

        // Test DAMAGED clothing
        assertEquals(90.0, calculator.calculatePrice(CLOTHING, DAMAGED, 100.0));
    }

    @Test
    void testCalculatePriceForBook_before() {
        // Test NEW book
        assertEquals(100.0, calculator.get_price_before(1,0, 100.0));

        // Test USED book
        assertEquals(80.0, calculator.get_price_before(1, 1, 100.0));

        // Test DAMAGED book
        assertEquals(50.0, calculator.get_price_before(1, 2, 100.0));
    }

    @Test
    void testCalculatePriceForElectronics_before() {
        ItemPriceCalculator calculator = new ItemPriceCalculator();

        // TODO: 기존 코드의 오류 검출
        // Test NEW electronics
        assertEquals(110.0, calculator.get_price_before(2, 0, 100.0));

        // Test USED electronics
        assertEquals(70.0, calculator.get_price_before(2, 1, 100.0));

        // Test DAMAGED electronics
        assertEquals(0.0, calculator.get_price_before(2, 2, 100.0));
    }

    @Test
    void testCalculatePriceForClothing_before() {
        ItemPriceCalculator calculator = new ItemPriceCalculator();

        // Test NEW clothing
        assertEquals(100.0, calculator.get_price_before(3, 0, 100.0));

        // Test USED clothing
        assertEquals(90.0, calculator.get_price_before(3, 1, 100.0));

        // Test DAMAGED clothing
        assertEquals(90.0, calculator.get_price_before(3, 2, 100.0));
    }
}