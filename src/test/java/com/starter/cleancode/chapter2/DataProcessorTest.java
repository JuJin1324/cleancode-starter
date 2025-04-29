package com.starter.cleancode.chapter2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DataProcessorTest {
    DataProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new DataProcessor();
    }

    @Test
    void whenValidInput() {
        // Given
        List<String> input = List.of("apple", "banana", "kiwi", "grape");
        int threshold = 5;

        // When
        List<String> result = processor.getLengthFilteredData(input, threshold);

        // Then
        assertEquals(List.of("BANANA_processed"), result);
    }

    @Test
    void whenEmptyInput() {
        // Given
        List<String> input = List.of();
        int threshold = 3;

        // When
        List<String> result = processor.getLengthFilteredData(input, threshold);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void whenNoMatchingItems() {
        // Given
        List<String> input = List.of("cat", "dog", "bat");
        int threshold = 5;

        // When
        List<String> result = processor.getLengthFilteredData(input, threshold);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void whenAllMatchingItems() {
        // Given
        List<String> input = List.of("elephant", "giraffe", "dolphin");
        int threshold = 3;
        
        // When
        List<String> result = processor.getLengthFilteredData(input, threshold);
        
        // Then
        assertEquals(List.of("ELEPHANT_processed", "GIRAFFE_processed", "DOLPHIN_processed"), result);
    }

    @Test
    void whenValidInput_before() {
        // Given
        List<String> input = List.of("apple", "banana", "kiwi", "grape");
        int threshold = 5;

        // When
        List<String> result = processor.manip(input, threshold);

        // Then
        assertEquals(List.of("BANANA_processed"), result);
    }

    @Test
    void whenEmptyInput_before() {
        // Given
        List<String> input = List.of();
        int threshold = 3;

        // When
        List<String> result = processor.manip(input, threshold);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void whenNoMatchingItems_before() {
        // Given
        List<String> input = List.of("cat", "dog", "bat");
        int threshold = 5;

        // When
        List<String> result = processor.manip(input, threshold);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void whenAllMatchingItems_before() {
        // Given
        List<String> input = List.of("elephant", "giraffe", "dolphin");
        int threshold = 3;

        // When
        List<String> result = processor.manip(input, threshold);

        // Then
        assertEquals(List.of("ELEPHANT_processed", "GIRAFFE_processed", "DOLPHIN_processed"), result);
    }
}