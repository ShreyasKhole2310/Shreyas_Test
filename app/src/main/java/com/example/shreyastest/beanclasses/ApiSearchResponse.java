package com.example.shreyastest.beanclasses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApiSearchResponse {

    /*"id": "II6dz6g",
            "title": "Mocha,vanilla sandwiches",
            "description": null,
            "datetime": 1593992465,
            "cover": "8U8aJ4E",
            "cover_width": 696,
            "cover_height": 696,
            "account_url": "sensoryoverload83",
            "account_id": 103869321,
            "privacy": "hidden",
            "layout": "blog",
            "views": 749,
            "link": "https://imgur.com/a/II6dz6g",
            "ups": 15,
            "downs": 0,
            "points": 15,
            "score": 15,
            "is_album": true,
            "vote": null,
            "favorite": false,
            "nsfw": false,
            "section": "",
            "comment_count": 3,
            "favorite_count": 1,
            "topic": "No Topic",
            "topic_id": 29,
            "images_count": 1,
            "in_gallery": true,
            "is_ad": false,
            "tags": [],
            "ad_type": 0,
            "ad_url": "",
            "in_most_viral": false,
            "include_album_ads": false,*/

    @SerializedName("data")
    public ArrayList<Data> data;

    public class Data {

        @SerializedName("images")
        public ArrayList<Images> imagesArrayList;

        @SerializedName("id")
        private String id;

        @SerializedName("title")
        private String title;

        @SerializedName("date")
        private long Date;

        public Data() {
        }
    }
}
