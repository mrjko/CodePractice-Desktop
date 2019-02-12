package com.company;
import java.sql.Timestamp;
import java.util.*;

class Twitter {

    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    private HashMap<Integer, LinkedList<Integer>> userDict;
    private HashMap<Integer, Long> tweetDict;
    private HashMap<Integer, LinkedList<Integer>> followDict;

    /** Initialize your data structure here. */
    public Twitter() {
        userDict = new HashMap<>(); // user -> list of their tweets
        tweetDict = new HashMap<>(); // tweet -> its id
        followDict = new HashMap<>(); // user -> list of their followees (followings)
    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        LinkedList<Integer> listOfTweets = userDict.get(userId);
        if (listOfTweets == null) {
            listOfTweets = new LinkedList<>();
            listOfTweets.add(tweetId);
            userDict.put(userId, listOfTweets);
        } else {
            // appending it to keep first being the most recent
            listOfTweets.add(0, tweetId);
        }
        tweetDict.put(tweetId, timestamp.getTime()); // althoguht this will overwrite, we assume the id is unique for each tweet
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> newsFeed = new LinkedList<Integer>();

        List<Integer> myTweets = userDict.get(userId);
        List<Integer> myFollowees = followDict.get(userId);


        for (Integer myTweet : myTweets) {
            tweetDict.get(myTweet);
        }

        return newsFeed;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        LinkedList<Integer> listOfFollowees = userDict.get(followerId);
        if (listOfFollowees == null) {
            listOfFollowees = new LinkedList<>();
            listOfFollowees.add(followeeId);
            followDict.put(followerId, listOfFollowees);
        } else {
            listOfFollowees.add(followeeId);
        }
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        LinkedList<Integer> listOfFollowees = userDict.get(followerId);
        Iterator iter = listOfFollowees.listIterator();
        while (iter.hasNext()) {
            int id = (int) iter.next();
            if (id == followeeId) {
                iter.remove();
            }
        }
    }
}
