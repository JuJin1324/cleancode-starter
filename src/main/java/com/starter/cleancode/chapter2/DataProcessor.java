package com.starter.cleancode.chapter2;

import java.util.ArrayList;
import java.util.List;

public class DataProcessor {
    // 문제 1: 데이터 처리 함수 리팩토링
    // 다음 코드는 어떤 데이터를 받아 처리하고 결과를 반환하는 함수야. 
    // 변수명, 함수명 등이 모호하니 명확하게 바꿔보자.
    // Before
    public List<String> manip(List<String> data, int threshold) {
        List<String> res = new ArrayList<>(); // result? response?
        for (String item : data) {
            if (item.length() > threshold) {
                String processedItem = item.toUpperCase() + "_processed";
                res.add(processedItem);
            }
        }
        return res;
    }

    // After
    public List<String> getLengthFilteredData(List<String> data, int lengthThreshold) {
        return data.stream()
                .filter(item -> item.length() > lengthThreshold)
                .map(item -> item.toUpperCase() + "_processed")
                .toList();
    }
}
