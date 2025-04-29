package com.starter.cleancode.calculator;

import java.math.BigDecimal;

/**
 * 문제 1: 아이템 가격 계산 함수 리팩토링
 * 다음 코드는 아이템 종류(type)와 상태(status)에 따라 최종 가격을 계산하는 함수야.
 * 매직 넘버, 변수명, 중첩 조건문 등을 개선해보자.
 */
public class ItemPriceCalculator {
    // Before
    // type: 1=Book, 2=Electronics, 3=Clothing
    // status: 0=New, 1=Used, 2=Damaged
    // basePrice: 기본 가격
    public double get_price_before(int type, int status, double basePrice) {
        double final_p = basePrice;
        if (type == 1) { // Book
            if (status == 1) final_p = basePrice * 0.8; // Used book 20% off
            else if (status == 2) final_p = basePrice * 0.5; // Damaged book 50% off
        } else if (type == 2) { // Electronics
            final_p = basePrice * 1.1; // Electronics base markup 10%
            if (status == 1) final_p = final_p * 0.7; // Used electronics further 30% off
            else if (status == 2) final_p = 0; // Damaged electronics worthless
        } else if (type == 3) { // Clothing
            if (status != 0) final_p = basePrice * 0.9; // Used or damaged clothing 10% off
        }
        return final_p;
    }

    // 의미가 더 드러나도록 메서드 명 변경: get_price -> calculatePrice
    // ItemType enum 도입으로 매직 넘버 제거 및 타입 의미 명확화
    // ItemStatus enum 도입으로 매직 넘버 제거 및 타입 의미 명확화
    public double calculatePrice(ItemType type, ItemStatus status, double basePrice) {
        // 값은 할 수 있으면 변하지 않도록 불변 상태 유지를 위해서 값이 변경될 수 있는 변수인
        // final_p 와 같은 변수들은 사용 자제
//        double final_p = basePrice;

        // if/else if 가 3개 이상인 경우 switch-case 도입으로 가시성 증가
        // if 중첩 제거를 위해서 각 Type 별 계산 메서드 추가
        return switch (type) {
            case BOOK -> calculateBookPrice(status, basePrice);
            case ELECTRONICS -> calculateElectronicsPrice(status, basePrice);
            case CLOTHING -> calculateClothingPrice(status, basePrice);
        };
    }

    // 특별히 메서드가 멤버 변수에 의존성이 없다면 static 을 붙여서 멤버 변수에 의존하지 않음을 표시
    private static double calculateBookPrice(ItemStatus status, double basePrice) {
        // Switch 문으로 각 status 에 따른 의미 명확화
        return switch (status) {
            case NEW -> basePrice;
            case USED -> multiplyExactly(basePrice, 0.8);
            case DAMAGED -> multiplyExactly(basePrice, 0.5);
        };
    }

    private static double calculateElectronicsPrice(ItemStatus status, double basePrice) {
        return switch (status) {
            case NEW -> multiplyExactly(basePrice, 1.1);
            case USED -> multiplyExactly(basePrice, 0.7);
            case DAMAGED -> multiplyExactly(basePrice, 0.0);
        };
    }

    private static double calculateClothingPrice(ItemStatus status, double basePrice) {
        return switch (status) {
            case NEW -> basePrice;
            case USED, DAMAGED -> multiplyExactly(basePrice, 0.9);
        };
    }

    public enum ItemType {
        BOOK,
        ELECTRONICS,
        CLOTHING,
    }

    public enum ItemStatus {
        NEW, USED, DAMAGED
    }

    private static double multiplyExactly(double value1, double value2) {
        // 의미가 확실한 변수는 var 도입
        var exactValue1 = BigDecimal.valueOf(value1);
        var exactValue2 = BigDecimal.valueOf(value2);

        return exactValue1
                .multiply(exactValue2)
                .doubleValue();
    }
}
