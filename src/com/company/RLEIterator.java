package com.company;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

class RLEIterator {

    public List<Integer> list = new LinkedList<>();

    public RLEIterator(int[] A) {
        for (int i = 0; i < A.length ; i=i+2) {
            if (A[i] <= 0) {
                continue;
            }
            int num = A[i];
            while (num >= 1) {
                list.add(A[i+1]);
                num--;
            }
        }
    }

    public int next(int n) {

        if (list.size() < n) {
            return -1;
        }
        if (list.size() == 0) {
            return -1;
        }
        int tmp = 0;
        while (n > 0) {
            tmp = list.get(0);
            list.remove(0);
            n--;
        }
        return tmp;
    }
}