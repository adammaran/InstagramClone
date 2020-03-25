package com.example.instagramclone.Common;

import com.example.instagramclone.Models.FeedItemModel;
import com.example.instagramclone.Models.UserModel;

import java.util.ArrayList;

public class Data {
    public static ArrayList<FeedItemModel> getFeedList() {
        ArrayList<FeedItemModel> feedItems = new ArrayList<>();

        feedItems.add(new FeedItemModel("123123", getUserList().get(1), 123, "https://picsum.photos/536/354", 32, "Opis slike lel", "12:32", "213213"));
        feedItems.add(new FeedItemModel("123123", getUserList().get(1), 123, "https://picsum.photos/536/354", 32, "Opis slike lel", "12:32", "213213"));
        feedItems.add(new FeedItemModel("123123", getUserList().get(1), 123, "https://picsum.photos/536/354", 32, "Opis slike lel", "12:32", "213213"));
        feedItems.add(new FeedItemModel("123123", getUserList().get(1), 123, "https://picsum.photos/536/354", 32, "Opis slike lel", "12:32", "213213"));
        feedItems.add(new FeedItemModel("123123", getUserList().get(1), 123, "https://picsum.photos/536/354", 32, "Opis slike lel", "12:32", "213213"));
        feedItems.add(new FeedItemModel("123123", getUserList().get(1), 123, "https://picsum.photos/536/354", 32, "Opis slike lel", "12:32", "213213"));
        feedItems.add(new FeedItemModel("123123", getUserList().get(1), 123, "https://picsum.photos/536/354", 32, "Opis slike lel", "12:32", "213213"));
        feedItems.add(new FeedItemModel("123123", getUserList().get(1), 123, "https://picsum.photos/536/354", 32, "Opis slike lel", "12:32", "213213"));

        return feedItems;
    }

    public static ArrayList<UserModel> getUserList() {
        ArrayList<UserModel> userList = new ArrayList<>();

        userList.add(new UserModel("123", "adammaran", "adam@adam.com", "https://picsum.photos/536/354", "Adam Maran", "nesto o sebi sta znam", 123, 321, 123));
        userList.add(new UserModel("123", "adammaran", "adam@adam.com", "https://picsum.photos/536/354", "Adam Maran", "nesto o sebi sta znam", 123, 321, 123));
        userList.add(new UserModel("123", "adammaran", "adam@adam.com", "https://picsum.photos/536/354", "Adam Maran", "nesto o sebi sta znam", 123, 321, 123));
        userList.add(new UserModel("123", "adammaran", "adam@adam.com", "https://picsum.photos/536/354", "Adam Maran", "nesto o sebi sta znam", 123, 321, 123));
        userList.add(new UserModel("123", "adammaran", "adam@adam.com", "https://picsum.photos/536/354", "Adam Maran", "nesto o sebi sta znam", 123, 321, 123));
        userList.add(new UserModel("123", "adammaran", "adam@adam.com", "https://picsum.photos/536/354", "Adam Maran", "nesto o sebi sta znam", 123, 321, 123));

        return userList;
    }
}
