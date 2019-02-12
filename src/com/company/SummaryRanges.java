package com.company;

import java.util.*;

/**
 * Definition for an interval.
 * public class Interval {
 *     int start;
 *     int end;
 *     Interval() { start = 0; end = 0; }
 *     Interval(int s, int e) { start = s; end = e; }
 * }
 */
class SummaryRanges {

    class Interval implements Comparable<Interval> {
        int start;
        int end;
        Interval() { start = 0; end = 0; }
        Interval(int s, int e) { start = s; end = e; };

        @Override
        public int compareTo(Interval o) {
            return this.start - o.start;
        }

        @Override
        public String toString() {
            return this.start + ", " + this.end;
        }

    }

    List<Interval> intervals;

    /** Initialize your data structure here. */
    public SummaryRanges() {
        intervals = new ArrayList<Interval>();
    }

    public void addNum(int val) {
        for (Interval interval : this.intervals) {
            if ((interval.start - 1 == val) || (interval.end + 1 == val)) {
                mergeInterval(interval, val);
                return;
            } else if (interval.start <= val && interval.end >= val) {
                return;
            }
        }
        intervals.add(new Interval(val, val));
        Collections.sort(intervals, new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                return o1.start-o2.start;
            }
        });
    }

    public List<Interval> getIntervals() {
        return this.intervals;
    }

    private void mergeInterval(Interval interval, int val) {
        if ((interval.start - 1 == val)) {
            intervals.add(new Interval(val, interval.start));
        } else {
            intervals.add(new Interval(interval.end, val));
        }
        joinIntervals();
    }

    // joins any continuous intervals
    private void joinIntervals() {
        Collections.sort(intervals, new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                return (o1.start - o2.start);
            }
        });

//        Collections.sort(intervals);

        for (int i = 0; i < intervals.size() - 1; i++) {
            if ((Math.abs(intervals.get(i+1).end - intervals.get(i).end) <= 1) || (Math.abs(intervals.get(i+1).start - intervals.get(i).end) <= 1)) {
                Interval joined = new Interval(intervals.get(i).start, intervals.get(i+1).end);
                intervals.remove(i);
                intervals.remove(i);
                intervals.add(i, joined);
                i--;
            }
        }
    }
}

/**
 * Your SummaryRanges object will be instantiated and called as such:
 * SummaryRanges obj = new SummaryRanges();
 * obj.addNum(val);
 * List<Interval> param_2 = obj.getIntervals();
 */