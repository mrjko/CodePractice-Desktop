package com.company;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class StockSpanner {

    List<Integer> list = new ArrayList<>();

    public StockSpanner() {

    }

    public int next(int price) {
        list.add(price);
        int counter = 0;
        for (int i = list.size(); i > 0; i--) {
            if (price >= list.get(i-1)) {
                counter++;
            } else {
                return counter;
            }
        }
        return counter;
    }
}