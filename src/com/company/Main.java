package com.company;

import javax.print.attribute.standard.PrinterResolution;
import java.lang.reflect.Array;
import java.util.*;

import static java.lang.Double.compare;


public class Main {

    public enum Size {S, M, L, XL}

    static int min = Integer.MAX_VALUE;
    static Integer prev = null;
    static Integer previous = null;
    static int prevValue;

    // comparator example
    // sort by longer string length
    public static Comparator<String> stringLengthComparator = (str1, str2) -> {
        if (str1.length() == str2.length()) return 0;
        return (str1.length() > str2.length()) ? 1 : -1;
    };

    public static void main(String[] args) {
        echo(topKFrequent(new int[]{1,2,3,3,3,2}, 2));
    }

    public static class NumFreq {
        int val;
        int freq;

        public NumFreq(int v, int f) {
            this.val = v;
            this.freq = f;
        }
    }


    public static List<Integer> topKFrequent(int[] nums, int k) {
        List<Integer> res = new ArrayList<Integer>();
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        // populate the map O(n) time base on freq
        for (int i : nums) {
            map.putIfAbsent(i, 0);
            map.computeIfPresent(i, (key,val) -> val + 1);
        }

        PriorityQueue<NumFreq> minHeap = new PriorityQueue<>((a,b) -> (b.freq - a.freq));
        int counter = k;

        // eveyrthing in the minHeap is unique now, because we already aggregated them from the hashmap
        for (Map.Entry<Integer, Integer> es : map.entrySet()) {
            minHeap.add(new NumFreq(es.getKey(), es.getValue()));
        }


        while (counter > 0) {
            res.add(minHeap.poll().val);
            counter--;
        }

        return res;
    }

    public static int fib(int n) {
        if (n == 1) return 1;
        if (n < 1) return 0;

        return fib(n-2) + fib(n-1);
    }

    public static void testFunc(int n) {

        long startTime = System.nanoTime();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                }
            }
        }

        long endTime = System.nanoTime();


        System.out.println((endTime - startTime) / 1000000 );
    }

    public void notes() {
        String[] strArr = new String[]{"jimmy ko", "apple", "google"};
        Arrays.sort(strArr, stringLengthComparator); // if using a custom sort, just define one above and plug it in
        echo(strArr);

        HashMap<String, Integer> test = new HashMap<>();
        String[] testList = new String[]{"dog", "dog", "cat", "duck"};


        for (String s : testList) {
            // beautiful! :D
            test.computeIfPresent(s, (k,v) -> v + 1);
//            test.computeIfAbsent(s, v -> 1); // same as test.putIfAbsent(s, 1), but overkill because using lambda when its redundant
//            test.putIfAbsent(s, 1); // we can computeIfAbsent only if we passing in functions for it

            test.computeIfAbsent(s, k -> specialLambdaFunction(k));

            // less beautiful
//            if (test.containsKey(s)) {
//                test.put(s, test.get(s) + 1);
//            } else {
//                test.put(s, 1);
//            }
        }

        for (Map.Entry<String, Integer> entry : test.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println("key: " + key + ", value: " + value);
        }
    }

    public static int findTilt(TreeNode root) {

        int right = 0;
        int left = 0;

        if (root == null)
            return 0;

        if (root.left == null && root.right == null) {
            return 0;
        }

        if (root.left != null) {
            left = getTreeSum(root.left);
        }

        if (root.right != null) {
            right = getTreeSum(root.right);
        }

        return Math.abs(left - right) + findTilt(root.left) + findTilt(root.right);
    }

    public static List<String> summaryRanges(int[] nums) {
        List<String> res = new ArrayList<>();

        if (nums.length == 0) return res;

        int start = (nums[0] == 0) ? 0 : nums[0];
        int end = start + 1;

        for (int i = 1; i < nums.length; i++) {

            if (nums[i] == end) {
                end = nums[i] + 1;
            } else {
                if (start == (end - 1))
                    res.add(Integer.toString(start));
                else
                    res.add(start + "->" + Integer.toString(end - 1));

                start = nums[i];
                end = nums[i] + 1;
            }
        }

        if ((end - 1) == start) {
            res.add(Integer.toString(end - 1));
        } else {
            res.add(start + "->" + Integer.toString(end - 1));
        }

        return res;
    }

    public static int getTreeSum(TreeNode root) {
        if (root == null)
            return 0;
        return root.val + getTreeSum(root.left) + getTreeSum(root.right);
    }

    public static int specialLambdaFunction(String key) {
        return key.length() * 1;
    }

    // sort by longer string length
    public Comparator<String> comparator = (str1, str2) -> {
        if (str1.length() == str2.length()) return 0;
        return (str1.length() > str2.length()) ? 1 : -1;
    };

    public static int maxDistToClosest(int[] seats) {
        boolean aisle = false;
        double counter = 0.0;
        int max = 0;

        for (int i = 0; i < seats.length; i++) {

            if (i == 0 || i == seats.length - 1) {
                if (seats[i] == 0) {
                    aisle = true;
                }
            }

            if (seats[i] == 1) {
                if (!aisle) {
                    counter = Math.ceil(counter / 2);
                } else {
                    aisle = false;
                }
                if (max < counter) {
                    max = (int) counter;
                }
                counter = 0;
            } else {
                counter++;
            }
        }

        if (aisle) {
            if (counter > max) {
                return (int) counter;
            }
        }

        return max;
    }

    public static boolean circularArrayLoop(int[] nums) {
        int k = 0;
        int i = 0;
        int j = 0;
        while (j < nums.length) {

            k = i + nums[i];
            if (k > nums.length - 1) {
                k = k - nums.length;
            } else if (k < 0) {
                k = k + nums.length;
            }

            if (k == 0) {
                return true;
            }

            i = k;
            j++;
        }
        return false;
    }

    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        int min = Integer.MAX_VALUE;

        boolean possible = false;

        for (String s : wordList) {
           if (s.equals(endWord)) {
               possible = true;
               break;
           }
        }

        if (!possible) return 0;

        for (String s : wordList) {
            if (isOneLetterAway(s, beginWord)) {
                if (min > getLengthFromEndWord(s, wordList) + 1) {
                    min = getLengthFromEndWord(s, wordList) + 1;
                }
            }
        }
        return (min == Integer.MAX_VALUE) ? 0 : min;
    }

    public static int getLengthFromEndWord(String s, List<String> wordList) {
        int counter = 0;
        List<String> visited = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        stack.push(s);

        HashMap<String, List<String>> map = new HashMap<>();
        populateMap(map, wordList);

        while (!stack.isEmpty()) {
            counter++;
            String curr = stack.pop();

            List<String> next = map.get(curr);

            for (String str : next) {
                if (!visited.contains(str)) {
                    stack.push(s);
                }
            }
        }

        return counter;
    }

    public static int ladderLength2(String beginWord, String endWord, List<String> wordList) {
        HashMap<String, List<String>> map = new HashMap<>();
        HashSet<String> visited = new HashSet<>();
        populateMap(map, wordList);

        int min = Integer.MAX_VALUE;

        Stack<String> stack = new Stack<>();
        Stack<String> tracker = new Stack<>();

        for (String s : wordList) {
            if (isOneLetterAway(s, beginWord)) {
                tracker.push(s);
            }
        }

        int counter;
        while (!tracker.isEmpty()) {
            String trackerCurr = tracker.peek();
            for (String s : map.get(trackerCurr)) {
                stack.push(s);
            }
            counter = 0;
            visited.add(trackerCurr);

            while (!stack.isEmpty()) {
                counter++;
                String curr = stack.pop();
                int stackSize = stack.size();
                visited.add(curr);

                if (curr.equals(endWord)) {
                    counter++;
                    if (min > counter) {
                        min = counter;
                        echo("min is : " + min);
                    }
                    visited = new HashSet<>();
                    visited.add(trackerCurr);
                    counter = 0;
                    continue;
                }

                List<String> next = map.get(curr);

                if (next.size() == 0) { // no more path and it is not the endWord, should abandon
                    visited = new HashSet<>();
                    visited.add(trackerCurr);
                    counter = 0;
                    continue;
                } else {
                    for (String s : next) {
                        if (!visited.contains(s)) {
                            stack.push(s);
                        }
                    }
                    if (stackSize == stack.size()) {
                        counter = 0;
                    }
                }

            }

            tracker.pop();
        }


        return (min == Integer.MAX_VALUE) ? 0 : min;
    }

    public static void populateMap(HashMap<String, List<String>> map, List<String> dict) {
        for (int i = 0; i < dict.size(); i++) {
            int k = 0;
            int j = i + 1;
            List<String> next = new ArrayList<String>();

            while (k < dict.size() - 1) {
                j = (j == dict.size()) ? 0 : j;
                if (isOneLetterAway(dict.get(i), dict.get(j))) {
                    next.add(dict.get(j));
                }
                j++;
                k++;
            }

            map.put(dict.get(i), next);
        }
    }

    public static boolean isOneLetterAway(String first, String second) {
        boolean foundDiff = false;
        int i = 0;
        for (char c : first.toCharArray()) {
            if (c != second.charAt(i)) {
                if (foundDiff) return false;
                foundDiff = true;
            }
            i++;
        }
        return true;
    }

    public static int uniquePaths(int m, int n) {
        int[][] maze = new int[n][m];
        getAllNumberOfPaths(maze);
        return maze[n-1][m-1];
    }

    public static void getAllNumberOfPaths(int[][] maze) {
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                if (col == 0 || row == 0) {
                    maze[row][col] = 1;
                } else {
                    maze[row][col] = maze[row-1][col] + maze[row][col-1];
                }
            }
        }
    }

    public static int getMinimumDifference4(TreeNode root) {
        if (root == null) {
            return 0;
        }
        min = Integer.MAX_VALUE;
        prevValue = Integer.MAX_VALUE;
        getMinimumDifferenceCore(root);
        return min;
    }

    private static void getMinimumDifferenceCore(TreeNode root) {
        if (root == null) {
            return;
        }
        getMinimumDifferenceCore(root.left);
        min = Math.min(min, Math.abs(prevValue - root.val));
        prevValue = root.val;
        getMinimumDifferenceCore(root.right);
    }

    public static int getMinimumDifference3(TreeNode root) {
        helper(root);
        return min;
    }

    public static void helper(TreeNode root) {
        if (root == null) {
            return;
        }
        helper(root.left);
        if (previous == null) {
            previous = root.val;
        } else {
            min = Math.min(min, root.val - previous);
            previous = root.val;
        }
        helper(root.right);
    }

    public static int getMinimumDifference2(TreeNode root) {
        if (root == null) return min;

        getMinimumDifference2(root.left);

        if (prev != null) {
            min = Math.min(min, root.val - prev);
        }
        prev = root.val;

        getMinimumDifference2(root.right);

        return min;
    }

    public static int getMinimumDifference(TreeNode root) {

        int leftSubMin = Integer.MAX_VALUE;
        int rightSubMin = Integer.MAX_VALUE;
        int left = Integer.MAX_VALUE;
        int right = Integer.MAX_VALUE;

        if (root.left != null) {
            left = getMinimumDifference(root.left, root.val);
            leftSubMin = getMinimumDifference(root.left);
        }
        if (root.right != null) {
            right = getMinimumDifference(root.right, root.val);
            rightSubMin = getMinimumDifference(root.right);
        }

        int min = Math.min(left, right);
        int subMin = Math.min(leftSubMin, rightSubMin);

        return Math.min(min, subMin);
    }

    private static int getMinimumDifference(TreeNode root, Integer val) {
        int min = Math.abs(root.val - val);
        Stack<TreeNode> stack = new Stack<>();
        if (root.left != null)
            stack.push(root.left);
        if (root.right != null)
            stack.push(root.right);

        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            if (Math.abs(curr.val - val) < min) {
                min = Math.abs(curr.val - val);
            }
            if (curr.left != null)
                stack.push(curr.left);
            if (curr.right != null)
                stack.push(curr.right);
        }

        return min;
    }

    public static boolean rotateString(String A, String B) {

        if (A.length() != B.length()) return false;

        if (A.length() == 0 && B.length() == 0) return true;

        boolean found;
        ArrayList<Integer> listOfIndexes = getPotentialStarts(A, B);
        if (listOfIndexes.size() == 0) return false;

        for (Integer i : listOfIndexes) {
            found = true;
            for (char c : A.toCharArray()) {
                i = (i == B.length()) ? 0 : i;
                if (c != B.charAt(i)) {
                    found = false;
                    break;
                }
                i++;
            }
            if (found) return true;
        }

        return false;
    }

    public static ArrayList<Integer> getPotentialStarts(String A, String B) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        for (int i = 0; i < B.length(); i++) {
            if (A.charAt(0) == B.charAt(i)) {
                res.add(i);
            }
        }
        return res;
    }

    public static TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) return null;

        int middle = (int) Math.floor((double) nums.length / 2);
        TreeNode root = new TreeNode(nums[middle]);

        try {
            root.left = sortedArrayToBST(Arrays.copyOfRange(nums, 0, middle));
            root.right = sortedArrayToBST(Arrays.copyOfRange(nums, middle + 1, nums.length));
        } catch (IllegalArgumentException e) {
            return root;
        }

        return root;
    }

    public static int networkDelayTime_Dijkstra(int[][] times, int N, int K) {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] edge: times) {
            graph.putIfAbsent(edge[0], new ArrayList<>());
            graph.get(edge[0]).add(new int[]{edge[1], edge[2]});
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (a[0] - b[0]));
        boolean[] visited = new boolean[N + 1];
        int[] minDis = new int[N + 1];
        Arrays.fill(minDis, Integer.MAX_VALUE);
        minDis[K] = 0;
        pq.offer(new int[]{0, K});
        int max = 0;
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int currNode = curr[1];
            if (visited[currNode]) continue;
            visited[currNode] = true;
            int currDis = curr[0];
            max = currDis;
            N--;
            if (!graph.containsKey(currNode)) continue;
            for (int[] next : graph.get(currNode)) {
                if (!visited[next[0]] && currDis + next[1] < minDis[next[0]])
                    pq.offer(new int[]{currDis + next[1], next[0]});
            }
        }
        return N == 0 ? max : -1;
    }

    public static int findMinArrowShots(int[][] points) {
        Arrays.sort(points, (a, b) -> Double.compare(a[0], b[0]));
        int arrows = 0;
        int i = 0;
        int j = 1;


        while (i < points.length || j < points.length - 1) {
            if (points[i][1] > points[j][0]) {
                if (points[i][1] > points[j][1]) {
                    arrows++;
                    i = j;
                } else {
                    j++;
                }
            } else {
                arrows++;
                i = j;
            }
            i++;
            j = i + 1;
        }
        return arrows;
    }

    public static int findContentChildren(int[] g, int[] s) {
        int i = 0;
        int j = 0;
        Arrays.sort(g);
        Arrays.sort(s);

        while (i < g.length && j < s.length) {
            if (g[i] <= s[j]) {
                i++;
            }
            j++;
        }

        return i;
    }

    public static List<String> letterCasePermutation(String S) {
        List<String> res = new ArrayList<>();
        if(S != null) h(S.toCharArray(), 0, res);
        return res;
    }

    public static void h(char[] s, int index, List<String> res){
        if(index == s.length) // default case, add to result
            res.add(new String(s));
        else{
            h(s, index + 1, res); // continue with character at the index as is

            char c = s[index];
            if(!Character.isDigit(c)){ // if character at index isn't a digit, change it's case
                s[index] = Character.isLowerCase(c) ? Character.toUpperCase(c) : Character.toLowerCase(c);
                h(s, index + 1, res);
            }
        }
    }

    public static boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        int leftDepth = getMaxDepth(root.left);
        int rightDepth = getMaxDepth(root.right);

        if (Math.abs(leftDepth - rightDepth) > 1) {
            return false;
        }

        return isBalanced(root.left) && isBalanced(root.right);
    }

    public static int getMaxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = 1 + getMaxDepth(root.left);
        int rightDepth = 1 + getMaxDepth(root.right);

        return (leftDepth > rightDepth) ? leftDepth : rightDepth;
    }


    public static int longestUnivaluePath(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return 0;
        }

        int max = -1;
        int val = 0;

        if (root.left != null) {
            if (root.left.val == root.val) {
                val += 1 + longestUnivaluePath(root.left);
            } else {
                val = longestUnivaluePath(root.left);
            }
            if (max < val) {
                max = val;
            }
        }

        if (root.right != null) {
            if (root.right.val == root.val) {
                val += 1 + longestUnivaluePath(root.right);
            } else {
                val = longestUnivaluePath(root.right);
            }
            if (max < val) {
                max = val;
            }
        }

        return max;
    }

    public static int findLHS(int[] nums) {

        if (nums.length == 0) {
            return 0;
        }

        Arrays.sort(nums);

        int max = 0;
        int first = 0;
        int second = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] - nums[second] == 1) {
                if (first == second) {
                    second = i;
                } else {
                    if (max < (i - first)) {
                        max = i - first;
                    }
                    first = second;
                    second = i;
                }
            } else if (nums[i] - nums[second] > 1) {
                if (max < (i - first) && first != second) {
                    max = i - first;
                }
                first = i;
                second = i;
            }
        }

        if (first != second) {
            return (max > nums.length - first) ? max : nums.length - first;
        }

        return max;
    }

    public static String longestWord(String[] words) {
        String result = "";
        HashSet<String> dict = new HashSet<String>();

        if (words.length == 0) {
            return "";
        }

        for (String s : words) { // O(n)
            dict.add(s);
        }

        Arrays.sort(words, (a, b) -> a.length() == b.length()
                ? a.compareTo(b) : b.length() - a.length());

        int currLen;
        for (String s : words) {   // O(n)
            currLen = 0;
            if (s.length() == 1) {
                currLen = 1;
            } else {
                if (dict.contains(s.substring(0, s.length()-1))) {
                    currLen = s.length();
                }
            }

            if (currLen > result.length() || (currLen == result.length() && s.compareTo(result) < 0)) {
                result = s;
            }


        }

        return result;
    }

    public static String longestWord2(String[] words) {
        String ans = "";
        Set<String> wordset = new HashSet();
        for (String word: words) wordset.add(word);
        for (String word: words) {
            if (word.length() > ans.length() ||
                    word.length() == ans.length() && word.compareTo(ans) < 0) {
                boolean good = true;
                for (int k = 1; k < word.length(); ++k) {
                    if (!wordset.contains(word.substring(0, k))) {
                        good = false;
                        break;
                    }
                }
                if (good) ans = word;
            }
        }
        return ans;
    }

    public static int removeElement(int[] nums, int val) {

        if (nums.length == 0) {
            return 0;
        }

        if (nums.length == 1) {
            if (nums[0] == val) {
                return 0;
            } else {
                return 1;
            }
        }

        int temp;
        int counter = 0;
        int i = 0;

        while (i < nums.length - counter) {
            if (nums[i] == val) {
                temp = nums[i];
                nums[i] = nums[nums.length - 1 - counter];
                nums[nums.length - 1 - counter] = temp;
                counter++;
                continue;
            }
            i++;
        }

        echo(nums);
        return nums.length - counter;
    }

    public static int rob(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return (nums[0] > nums[1]) ? nums[0] : nums[1];
        }

        return robHelper(nums, 0);
    }

    public static int robHelper(int[] nums, int index) {
        if (index >= nums.length - 2) {
            return nums[index];
        }

        int first = nums[index] + robHelper(nums, index+2);
        int second = robHelper(nums, index+1);
        int third = robHelper(nums, index+3);

        return (first > second) ? first : second;
    }

    public static long getWays(long n, long[] c) {
        return getWaysHelper(n, c);
    }

    public static long getWaysHelper(long n, long[] c) {
        if (n < 1) {
            return 0;
        }

        Arrays.sort(c);
        for (int i = c.length-1; i >= 0; i--) {
            return 1 + getWaysHelper(n-c[i], Arrays.copyOfRange(c,0, i));
        }

        return 0;
    }

    public static int reverse(int x) {
        char[] charArr = Integer.toString(x).toCharArray();
        StringBuilder sb = new StringBuilder();
        int res = 0;
        boolean isNeg = false;
        if (charArr[0] == '-') {
            isNeg = true;
            sb.append('-');
        }
        for (int i = charArr.length - 1; i >= 0; i--) {
            if (isNeg && i == 0) {
                break;
            }
            if (i == charArr.length - 1 && charArr[i] == '0') { // if the veyr last one is a zero, we do special
                int j = i;
                while (j >= 0 && charArr[j] == '0') {
                    j--;
                }
                i = j + 1;
            } else {
                sb.append(charArr[i]);
            }
        }

        try {
            res = Integer.parseInt(sb.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
        return res;
    }

    public static boolean buddyStrings(String A, String B) {
        if (A.length() != B.length()) {
            return false;
        }
        if (A.length() < 2) {
            return false;
        }


        int pointer = 0;
        Character a = null;
        Character b = null;
        HashMap<Character, Integer> hm = new HashMap<>();

        boolean hasDupe = false;
        boolean foundDiff = false;
        boolean completeSwap = false;
        while (pointer < A.length()) {

            // check if any letters are duplicates, if so, then without diff could also be true
            if (hm.containsKey(A.charAt(pointer))) {
                return true;
            } else {
                hm.put(A.charAt(pointer), 1);
            }

            if (A.charAt(pointer) != B.charAt(pointer)) {
                if (completeSwap) { // already completed the swap, if found another diff then return false;
                    return false;
                }
                if (foundDiff) { // found a diff we know we have the variables to match saved
                    if (a != B.charAt(pointer) || b != A.charAt(pointer)) { // the diff didnt match;
                        return false;
                    }
                    completeSwap = true;
                }
                a = A.charAt(pointer);
                b = B.charAt(pointer);
                foundDiff = true;
            }
            pointer++;
        }
        // if didnt find any, then return false as well
        return (!foundDiff && !hasDupe) ? false : true;
    }

    public static int thirdMax(int[] nums) {
        Integer first = null;
        Integer second = null;
        Integer third = null;
        int unique = 0;
        for (Integer i : nums) {
            if (i.equals(first) || i.equals(second) || i.equals(third)) {
                continue;
            }

            if (first == null || i > first.intValue()) {
                third = second;
                second = first;
                first = i;
            } else if (second == null || i > second.intValue()) {
                third = second;
                second = i;
            } else if (third == null || i > third.intValue()) {
                third = i;
            }
            unique++;
        }

        return (unique <= 2) ? first : third;
    }

    public static int countSegments(String s) {
        boolean allspaces = true;
        for (char c : s.toCharArray()) {
            if (c != ' ') {
                allspaces = false;
                break;
            }
        }

        if (allspaces) {
            return 0;
        }

        return (s.charAt(0) == ' ') ? s.split(" +").length - 1 : s.split(" +").length;
    }

    public static int search(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        int res = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                low = mid + 1;
            } else if (nums[mid] > target) {
                high = mid - 1;
            }
        }
        return res;
    }

    public static int calPoints(String[] ops) {
        Stack s = new Stack<Integer>();
        int res = 0;
        for (int i = 0; i < ops.length; i++) {
            if (ops[i].equals("C")) {
                int popped = Integer.parseInt(s.pop().toString());
                res -= popped;
            } else if (ops[i] == "D") {
                int val = 2 * Integer.parseInt(s.peek().toString());
                s.add(val);
                res += val;
            } else if (ops[i] == "+") {
                int popped = Integer.parseInt(s.pop().toString());
                int val = Integer.parseInt(s.peek().toString()) + popped;
                res += val;
                s.add(popped);
                s.add(val);
            } else {
                s.add(Integer.parseInt(ops[i]));
                res += Integer.parseInt(ops[i]);
            }
        }
        return res;
    }

    public static int islandPerimeter(int[][] grid) {
        int total = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {

                // if 1 then  call countPerimeter on them
                if (grid[row][col] == 1) {
                    total += (countPerimeter(grid, row-1, col) +  countPerimeter(grid, row, col+1)
                             + countPerimeter(grid, row+1, col) + countPerimeter(grid, row, col-1));
                }
            }
        }
        return total;
    }

    public static int countPerimeter(int[][] grid, int row, int col) {
        // boundary check for up and down
        if (row < 0 || row == grid.length) {
            return 1;
        }

        // boundary check for left and right
        if (col < 0 || col == grid[row].length) {
            return 1;
        }

        if (grid[row][col] == 0) {
            return 1;
        }

        return 0;
    }


    public static int solution2(int[] A) {
        // write your code in Java SE 8
        // she can go to any A, so we need to iterate A O(n) times to check all the paths?
        // iterate thru A and go right until finish check sum
        int max = 0;
        int amt = 0;
        for (int i = 0; i < A.length; i++) {
            HashMap<Integer, Integer> basket = new HashMap<>();
            amt = countAmount(i, A, basket);
            if (max < amt) {
                max = amt;
            }
        }

        return max;
    }

    // basket can hold two
    public static int countAmount(int i, int[] A, HashMap<Integer, Integer> basket) {
        while (i < A.length) {
            if (basket.containsKey(A[i])) {
                basket.put(A[i], basket.get(A[i])+1);
            } else {
                if (basket.size() == 2) {
                     return countFruits(basket); // to do
                }
                basket.put(A[i], 1);
            }
            i++;
        }
        return countFruits(basket);
    }

    public static int countFruits(HashMap<Integer, Integer> basket) {
        Iterator iter = basket.entrySet().iterator();
        int counter = 0;
        while (iter.hasNext()) {
            Map.Entry pair = (Map.Entry) iter.next();
            counter += (Integer) pair.getValue();
        }
        return counter;
    }

    // count number of duplicates in the email str, split on @

    public static int solution(String[] L) {
        if (L.length < 2) return 0;

        HashMap<String, Integer> hm = new HashMap<>();
        String[] splitStr = new String[2];
        for (String s : L) {
            splitStr = s.split("@");
            String addr = format(splitStr[0]) + "@" + splitStr[1];
            if (hm.containsKey(addr)) {
                hm.put(addr, hm.get(addr)+1);
            } else {
                hm.put(addr, 1);
            }
        }
        return countDuplicates(hm);
    }

    public static int countDuplicates(HashMap<String, Integer> hm) {
        Iterator iter = hm.entrySet().iterator();
        int counter = 0;
        while (iter.hasNext()) {
            Map.Entry pair = (Map.Entry) iter.next();
            if ((Integer) pair.getValue() >= 2) {
                counter++;
            }
        }
        return counter;
    }

    public static String format(String s) {
        // ignore.
        // ignore evyerthing after +
        StringBuilder res = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c == '.') {
                continue;
            }
            if (c == '+') {
                return res.toString();
            }
            res.append(c);
        }
        return res.toString();
    }

    public static boolean detectCapitalUse(String word) {
        int ASCII_BORDER = 96;
        if (word.length() == 1) {
            return true;
        }
        boolean all = ((int) word.charAt(0) < ASCII_BORDER) && ((int) word.charAt(1) < ASCII_BORDER);
        boolean none = ((int) word.charAt(0) > ASCII_BORDER) && ((int) word.charAt(1) > ASCII_BORDER);
        boolean normal = ((int) word.charAt(0) < ASCII_BORDER) && ((int) word.charAt(1) > ASCII_BORDER);

        if (!all && !none && !normal) {
            return false;
        }

        for (int i = 2; i < word.toCharArray().length; i++) {
            if (all && (int)word.charAt(i) > ASCII_BORDER) {
                return false;
            }
            if ((none || normal) && (int)word.charAt(i) < ASCII_BORDER) {
                return false;
            }
        }
        return true;
    }

    public static char findTheDifference(String s, String t) {

        char[] s_arr = s.toCharArray();
        char[] t_arr = t.toCharArray();

        Arrays.sort(s_arr);
        Arrays.sort(t_arr);

        char[] longer = (s_arr.length > t_arr.length) ? s_arr : t_arr;
        char[] shorter = (s_arr.length > t_arr.length) ? t_arr : s_arr;

        for (int i = 0; i < shorter.length; i++) {
            if (shorter[i] != longer[i]) {
                return longer[i];
            }
        }
        return longer[longer.length-1];
    }

    public static char findTheDifference2(String s, String t) throws Exception {

        HashMap<Character, Integer> s_hm = new HashMap<>();
        HashMap<Character, Integer> t_hm = new HashMap<>();

        for (char s_char : s.toCharArray()) { // O(n) space & time
            if (s_hm.containsKey(s_char)) {
                s_hm.put(s_char, s_hm.get(s_char) + 1);
            } else {
                s_hm.put(s_char, 1);
            }
        }

        for (char t_char : t.toCharArray()) { // O(n) space & time
            if (t_hm.containsKey(t_char)) {
                t_hm.put(t_char, t_hm.get(t_char) + 1);
            } else {
                t_hm.put(t_char, 1);
            }
        }

        return findDifferenceInMaps(s_hm, t_hm);
    }

    public static char findDifferenceInMaps(HashMap<Character, Integer> hm1, HashMap<Character, Integer> hm2) {
        HashMap<Character, Integer> bigger = (hm1.size() > hm2.size()) ? hm1 : hm2;
        for (Character c : bigger.keySet()) {
            if (hm1.get(c) != hm2.get(c)) {
                return c;
            }
        }
        for (Character c : bigger.keySet()) {
            if (!(hm1.containsKey(c) && hm2.containsKey(c))) {
                return c;
            }
        }
        return '-';
    }

    public static int getSum(int a, int b) {
        if (a==0) return b;
        if (b==0) return a;

        int carry = 0;
        while (b != 0) {
            carry = (a & b) << 1;
            a = a ^ b;
            b = carry;
        }

        return a;
    }

    public static boolean lemonadeChange(int[] bills) {
        if (bills == null) return true;
        int[] changes = {0,0}; // because all you need is 5 and 10, you can never use the 20 to give change
        for (int bill : bills) {
            if (bill == 5) changes[0]++;
            if (bill == 10) {
                changes[0]--; changes[1]++;
            }
            if (bill == 20) {
                if (changes[1] > 0) {
                    changes[0]--;
                    changes[1]--;
                } else {
                    changes[0] -= 3;
                }
            }
            if (changes[0] < 0 ) return false;
        }
        return true;
    }

    public static boolean getChange(int amt, HashMap<Integer, Integer> wallet) {
        if (amt == 5) {
            takeCash(5, wallet);
        }
        if (amt == 10) {
            if (!doesWalletHaveChange(10, wallet)) {
                return false;
            } else {
                takeCash(10, wallet);
                wallet.put(5, wallet.get(5)-1);
            }
        }
        if (amt == 20) {
            if (!(doesWalletHaveChange(20, wallet))) {
                return false;
            } else {
                takeCash(20, wallet);
                if (wallet.containsKey(10)) {
                    wallet.put(10, wallet.get(10)-1);
                    wallet.put(5, wallet.get(5)-1);
                } else {
                    wallet.put(5, wallet.get(5)-3);
                }
            }
        }
        return true;
    }

    public static boolean doesWalletHaveChange(int amt, HashMap<Integer, Integer> wallet) {
        if (amt == 10) {
            if (!wallet.containsKey(5) || wallet.get(5) == 0) {
                return false;
            }
        } else if (amt == 20) {
            if (!wallet.containsKey(10) || wallet.get(10) == 0) {
                if (!wallet.containsKey(5) || wallet.get(5) < 3) {
                    return false;
                }
            }
            if (!wallet.containsKey(5) || wallet.get(5) == 0) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    public static void takeCash(int amt, HashMap<Integer, Integer> wallet) {
        if (wallet.containsKey(amt)) {
            wallet.put(amt, wallet.get(amt)+1);
        } else {
            wallet.put(amt, 1);
        }
    }

    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();
        Arrays.sort(nums);
        return permuteHelper(results, nums, 0);
    }

    private static List<List<Integer>> permuteHelper(List<List<Integer>> results, int[] nums, int currentIdx) {

        if (currentIdx == nums.length) {
            List<Integer> tracking = new ArrayList<>();
            for(Integer num : nums) tracking.add(num);
            results.add(tracking);
            return results;
        }

        for(int i = currentIdx; i < nums.length; i++) {
            swap(nums, currentIdx, i);
            permuteHelper(results, nums, currentIdx + 1);
            swap(nums, currentIdx, i);
        }

        return results;
    }

    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] result = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            result[i] = getNextGreaterElement(nums1[i], nums2);
        }
        return result;
    }

    public static int getNextGreaterElement(int i, int[] arr) {
        boolean found = false;
        for (int j : arr) {
            if (found) {
                if (j > i) {
                    return j;
                }
            } else if (i == j) {
                found = true;
            }
        }
        return -1;
    }

    static String solutionDemo(String S, int K) {
        // write your code in Java SE 8
        StringBuilder sb = new StringBuilder();
        int size = getLengthWithoutDash(S); // get length without the '-';
        int mod = size % K;
        int counter = (mod == 0) ? K : mod;
        for (Character c : S.toCharArray()) {
            if (c.equals('-')) { //ignore all '-'
                continue;
            }
            if (counter == 0) { // this is when we have to append '-' and reset counter
                sb.append("-");
                counter = K;
            }
            sb.append(c.toString().toUpperCase());
            counter--;
        }
        return sb.toString();
    }

    static int getLengthWithoutDash(String s) {
        int res = 0;
        for (Character c : s.toCharArray()) {
            if (!c.equals('-')) {
                res++;
            }
        }
        return res;
    }

    // we take teh right side if even!
    static int peakIndexInMountainArray(int[] A) {
        int middle = A.length / 2;

        if (A.length < 3) {
            return -1;
        }

        if (A[middle-1] < A[middle] && A[middle+1] < A[middle]) {
            return middle;
        } else if (A[middle-1] > A[middle]) { // the peak is on the left side
            return peakIndexInMountainArray(Arrays.copyOfRange(A, 0, middle));
        } else if (A[middle+1] > A[middle]) {
            return peakIndexInMountainArray(Arrays.copyOfRange(A, middle, A.length));
        } else { // both are greater, just pick the side with the higher value
            int leftAttempt = peakIndexInMountainArray(Arrays.copyOfRange(A, 0, middle+1));
            return (leftAttempt == -1) ? peakIndexInMountainArray(Arrays.copyOfRange(A, middle, A.length+1)) : leftAttempt;
        }

    }

    static TreeNode mergeTrees(TreeNode t1, TreeNode t2) {

        if (t1 == null && t2 == null) {
            return null;
        }

        // t1 or t2 could be null here:
        int t1Value = (t1 == null) ? 0 : t1.val;
        int t2Value = (t2 == null) ? 0 : t2.val;
        TreeNode root = new TreeNode(t1Value + t2Value);


        // which means thered be issue if we try to access left or right
        root.left = mergeTrees((t1 != null) ? t1.left : null, (t2 != null) ? t2.left : null);
        root.right = mergeTrees((t1 != null) ? t1.right : null, (t2 != null) ? t2.right : null);

        return root;

    }

    static void copyTrees(TreeNode t1, TreeNode t2) {

    }

    static void mergeTreesHelper(TreeNode t1, TreeNode t2, TreeNode t3) {

    }

    static void moveZeroes(int[] nums) {
        int i = 0;
        while (!isMoved(nums)) {
            if (nums[i] == 0) {
                pushToEnd(nums, i);
            } else {
                i++;
            }
        }
    }

    static void pushToEnd(int[] nums, int index) {
        int tmp;
        for (int i = index; i < nums.length-1; i++) {
            tmp = nums[i+1];
            nums[i+1] = 0;
            nums[i] = tmp;
        }
//        if (nums[nums.length-1] != 0) {
//            nums[nums.length-2] = nums[nums.length-1];
//            nums[nums.length-1] = 0;
//        }
    }

    static Boolean isMoved(int[] nums) {
        Boolean foundZero = false;
        for (int i : nums) {
            if (i == 0) {
                if (!foundZero) {
                    foundZero = true;
                }
            }
            if (foundZero && i != 0) {
                return false;
            }
        }
        return true;
    }

    static String removeDuplicateLetters(String s) {
        if (s.length() == 0) {
            return "";
        }

        if (s.length() == 1) {
            return s;
        }

        Set<Character> set = new HashSet<>();

        for (Character c : s.toCharArray()) {
            set.add(c);
        }

        StringBuilder sb = new StringBuilder();

        Iterator iter = set.iterator();
        while(iter.hasNext()) {
            sb.append(iter.next().toString());
        }

        return sb.toString();
    }

    static void minimumBribes(int[] q) {

        int swap = 0;
        while (!isSorted(q)) {
            if (isChaotic(q)) {
                System.out.print("Too chaotic");
                return;
            }
            swap += bubbleSort(q);
        }

        System.out.print(swap);
        return;
    }

    static boolean isSorted(int[] q) {
        for (int i = 0; i < q.length-1; i++) {
            if (q[i+1] < q[i]) {
                return false;
            }
        }
        return true;
    }

    static boolean isChaotic(int[] q) {
        for (int i = 0; i < q.length; i++) {
            if ((q[i] - 1 - i) > 2) {
                return true;
            }
        }
        return false;
    }

    static int bubbleSort(int[] q) {
        int swap = 0;
        for (int i = 0; i < q.length-1; i++) {
            if (q[i+1] < q[i]) {
                int tmp = q[i+1];
                q[i+1] = q[i];
                q[i] = tmp;
                swap++;
            }
        }
        return swap;
    }

    static int helper(int[] q, int offset) {
        if (q.length <= 1 || offset >= q.length) {
            return 0;
        }

        if (q[offset] != offset+1) {
            for (int i = offset+1; i < q.length; i++) {
                if (offset+1 == q[i]) { // found the swap
                    int temp = q[offset];
                    q[offset] = q[i];
                    q[i] = temp;
                    break;
                }
            }
            return 1 + helper(q, offset+1);
        }
        return helper(q, offset+1);
    }

    static String reverseWords(String s) {
        StringBuilder sb = new StringBuilder();
        String trimmed = s.trim();

        String[] arr = trimmed.split("\\s+");
        if (arr.length == 0) {
            return "";
        }
        if (arr.length == 1) {
            return arr[0];
        }
        for (int i = arr.length-1; i > 0; i--) {
            sb.append(arr[i] + " ");
        }
        sb.append(arr[0]);
        return sb.toString();
    }

    static int maxDepth(TreeNode root) {
        int max = 0;
        int i = 0;

        if (root == null) {
            return max;
        }

        Stack<TreeNode> nodeStack = new Stack<>();
        nodeStack.push(root);
        i++;
        if (max < i) {
            max = i;
        }

        while(!nodeStack.isEmpty()) {
            TreeNode node = nodeStack.pop();
            i--;

            if (node.right != null) {
                nodeStack.push(node.right);
                i++;
                if (max < i) {
                    max = i;
                }
            }
            if (node.left != null) {
                nodeStack.push(node.left);
                i++;
                if (max < i) {
                    max = i;
                }
            }
        }

        return max;
    }

    static int singleNumber(int[] nums) {
        Arrays.sort(nums);
        echo(nums);
        for (int i = 0; i < nums.length-1; i=i+2) {
            if (nums[i] != nums[i+1]) {
                return nums[i];
            }
        }
        return nums[nums.length-1];
    }

    static List<String> fizzBuzz(int n) {
        List<String> res = new ArrayList<>();

        int i = 1;
        while (i <= n) {
            if ((i % 3 == 0) && (i % 5 == 0)) {
                res.add("FizzBuzz");
            } else if (i % 5 == 0) {
                res.add("Buzz");
            } else if (i % 3 == 0) {
                res.add("Fizz");
            } else {
                res.add(Integer.toString(i));
            }
            i++;
        }
        return res;
    }

    static String reverseString(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = s.length()-1; i >= 0; i--) {
            sb.append(s.charAt(i));
        }

        return sb.toString();
    }

    static void inorderTraversal(TreeNode root) {
        if (root.left != null) {
            inorderTraversal(root.left);
        }
        echo(root.val);
        if (root.right != null) {
            inorderTraversal(root.right);
        }
    }

    static void preorderTraversal(TreeNode root) {
        echo(root.val);
        if (root.left != null) {
            preorderTraversal(root.left);
        }
        if (root.right != null) {
            preorderTraversal(root.right);
        }
    }

    static void postorderTraversal(TreeNode root) {
        if (root.left != null) {
            postorderTraversal(root.left);
        }
        if (root.right != null) {
            postorderTraversal(root.right);
        }
        echo(root.val);
    }

    static void postorderTraversalIter(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        Stack<Integer> stack = new Stack();
        while (root != null) {
            while(root != null) {
                stack.push(root.left.val);
                root = root.left;
            }

            res.add(stack.pop());

        }
    }

    //
    static List<Integer> iterPreOrder(TreeNode root) {
        List <Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        while (curr != null || !stack.empty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            res.add(curr.val);
            curr = curr.right;
        }
        return res;
    }


        // Encodes a URL to a shortened URL.
    static String encode(String longUrl) {
        String encodedString = Base64.getEncoder().encodeToString(longUrl.getBytes());
        return encodedString;
    }

    // Decodes a shortened URL to its original URL.
    static String decode(String shortUrl) {
        byte[] decodedBytes = Base64.getDecoder().decode(shortUrl);
        String decodedString = new String(decodedBytes);
        return decodedString;
    }

    static boolean isMonotonic(int[] A) {

        if (A.length == 1) {
            return true;
        }

        boolean foundPattern = false;
        boolean isIncrease = false;

        for (int i = 0; i<A.length-1; i++) {
            if (A[i+1] > A[i]) {
                isIncrease = true;
                foundPattern = true;
            } else if (A[i+1] < A[i]) {
                isIncrease = false;
                foundPattern = true;
            }
        }

        if (!foundPattern) {
            return true;
        }

        for (int j = 0; j < A.length-1; j++) {
            if (isIncrease) {
                if (A[j+1] < A[j]) {
                    return false;
                }
            } else {
                if (A[j+1] > A[j]) {
                    return false;
                }
            }
        }
        return true;
    }

    static int hammingDistance(int x, int y) {
        String x_str = Integer.toBinaryString(x);
        String y_str = Integer.toBinaryString(y);
        String bigger = "";
        String smaller = "";

        if (x_str.length() > y_str.length()) {
            bigger = x_str;
            smaller = y_str;
        } else {
            bigger = y_str;
            smaller = x_str;
        }

        int result = 0;
        int j;
        for (j = 1; j <= smaller.length(); j++) {
            if (smaller.charAt(smaller.length() - j) != bigger.charAt(bigger.length() - j)) { // compare from the last element
                result++;
            }
        }

        for (int k = j; k <= bigger.length(); k++) {
            if (bigger.charAt(bigger.length() - k) == '1') {
                result++;
            }
        }

        return result;
    }

    // constraint: arr size at least 1, consecutive none duplicate values in arr
    static int minimumSwaps(int[] arr) {
        int result = 0;
        int index = 0;
        int runner = 1;
        boolean hasSwapped = false;
        int[] temp = Arrays.copyOf(arr, arr.length);

        while (index < arr.length) {
            if (!(arr[index] == index + 1)) { // this swap will ensure the order is still the same until we find the correct swap
                if (hasSwapped) { // if we swapped and is still not correct we need to switch it back
                    arr = Arrays.copyOf(temp, temp.length);;
                } else { // else we save a tmp of the arr, then swap it and increment runner
                    temp = Arrays.copyOf(arr, arr.length);;
                }
                swap(arr, index, runner);
                runner++;
                hasSwapped = true;
            } else if (hasSwapped) { // means it is correct and we have swapped, increment result;
                hasSwapped = false;
                result++;
                // reset the runner from previous iterations to its new index, and temp to new arr;
                temp = Arrays.copyOf(arr, arr.length);;
                index++;
                runner = index + 1;
            } else {
                index++;
            }
        }

        return result;
    }

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static void echo(Object obj) {
        if (obj.getClass().isArray()) {
            try {
                System.out.println(Arrays.toString((String[]) obj));
            } catch (ClassCastException e) {
                System.out.println(Arrays.toString((int[]) obj));
            }
        } else {
            System.out.println(obj);
        }
    }

    // count how many different ways one can run up the stairs (given 1, 2, 3 choices)
    static int tripleStep(int steps) {
        if (steps < 0) {
            return 0;
        } else if (steps == 0) {
            return 1;
        } else {
            return tripleStep(steps - 1) + tripleStep(steps - 2) + tripleStep(steps - 3);
        }
    }

    static int tripleStepMemo(int steps) {
        int[] memo = new int[steps + 1];
        Arrays.fill(memo, -1);
        return tripleStepMemo(steps, memo);
    }

    static int tripleStepMemo(int steps, int[] memo) {
        if (steps < 0) {
            return 0;
        } else if (steps == 0) {
            return 1;
        } else if (memo[steps] == -1) {
            memo[steps] = tripleStepMemo(steps-1, memo) + tripleStepMemo(steps-2, memo) + tripleStepMemo(steps-3, memo);
            return memo[steps];
        } else {
            return memo[steps];
        }
    }

    static List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<Integer>();
        HashMap<Character, String> map = new HashMap<Character, String>();
        for (int i = 0; i < words.length; i++) {
            if (!map.containsKey(words[i].charAt(0))) {
                map.put(words[i].charAt(0), words[i]);
            }
        }

        for (char c : s.toCharArray()) {
            if (map.containsKey(c)) {

            }
        }
        return res;
    }

    static List<String> getAllConcat(String[] words) {
        // 1 2 3 recursively iterate thru all the words
        // 1 3 2
        // 2 1 3
        // 2 3 1
        // 3 1 2
        // 3 2 1

        List<String> result = new ArrayList<String>();
        for (String word : words) {
            String perm = word + getAllPerms(Arrays.copyOfRange(words, 1, words.length));
            result.add(perm);
        }
        return result;
    }

    static ArrayList<String> getAllPerms(String[] strs) {
        if (strs == null) return null;

        ArrayList<String> permutations = new ArrayList<>();
        if (strs.length == 0) {
            permutations.add("");
            return permutations;
        }

        String first = strs[0];

        String[] remainder = Arrays.copyOfRange(strs, 1, strs.length);

        ArrayList<String> words = getAllPerms(remainder);

        for (int i = 0; i < words.size(); i++) {
            String s = insertStringAt(words, i, first);
            permutations.add(s);
        }

        return permutations;
    }

    static String insertStringAt(ArrayList<String> strs, int index, String word) {
        String res = "";
        for (int i = 0; i < strs.size(); i++) {
            if (i == index) {
                res += word;
            }
            res += strs.get(i);
        }
        return res;
    }

}
