package com.company;

import java.util.*;

class Twitter {

    static int timestamp = 0;

    public class Tweet {
        int tweetId;
        int timestamp;

        public Tweet(int id, int time) {
            this.tweetId = id;
            this.timestamp = time;
        }

    }

    HashMap<Integer, List<Tweet>> userIdToTweet;
    HashMap<Integer, HashSet<Integer>> userIdToFollowers;

    /** Initialize your data structure here. */
    public Twitter() {
        userIdToTweet = new HashMap<>();
        userIdToFollowers = new HashMap<>();
    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if (userIdToTweet.containsKey(userId)) {
            userIdToTweet.get(userId).add(new Tweet(tweetId, timestamp++));
        } else {
            ArrayList<Tweet> tweets = new ArrayList<>();
            tweets.add(new Tweet(tweetId, timestamp++));
            userIdToTweet.put(userId, tweets);
        }
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> results = new ArrayList<>();

        HashSet<Integer> followersToCheck = new HashSet<>();
        if (userIdToFollowers.containsKey(userId)) {
            followersToCheck = userIdToFollowers.get(userId);
        }

        List<Tweet> selfTweets = new ArrayList<>();
        if (userIdToTweet.containsKey(userId)) {
            selfTweets = userIdToTweet.get(userId);
        }

        PriorityQueue<Tweet> maxHeap = new PriorityQueue<Tweet>(10, new Comparator<Tweet>() {
            public int compare(Tweet t1, Tweet t2) {
                return t1.timestamp - t2.timestamp;
            }
        });

        for (Tweet t : selfTweets) {
            maxHeap.offer(t);
        }

        for (Integer followerId : followersToCheck) {
            List<Tweet> followerTweets = new ArrayList<>();
            if (userIdToTweet.containsKey(followerId)) {
                followerTweets = userIdToTweet.get(followerId);
            }

            for (Tweet t : followerTweets) {
                maxHeap.offer(t);
            }
        }

        while (!maxHeap.isEmpty()) {
            results.add(maxHeap.poll().tweetId);
        }

        Collections.reverse(results);



        return (results.size() > 10) ? results.subList(0, 10) : results;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followeeId, int followerId) {
        if (followeeId == followerId) return;
        if (userIdToFollowers.containsKey(followeeId)) {
            userIdToFollowers.get(followeeId).add(followerId);
        } else {
            HashSet<Integer> followers = new HashSet<>();
            followers.add(followerId);
            userIdToFollowers.put(followeeId, followers);
        }
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followeeId, int followerId) {
        if (followeeId == followerId) return;
        if (userIdToFollowers.containsKey(followeeId)) {
            removeFollowerIfExists(userIdToFollowers.get(followeeId), followerId);
        }

    }

    private void removeFollowerIfExists(HashSet<Integer> set, int id) {
        Iterator<Integer> iter = set.iterator();
        while (iter.hasNext()) {
            Integer curr = iter.next();
            if (curr.equals(id)) {
                iter.remove();
            }
        }
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */