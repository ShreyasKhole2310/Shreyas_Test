package com.example.shreyastest.BeanClasses;

import com.google.gson.annotations.SerializedName;

public class Images {

    /* "id": "0bceGfe",
                    "title": null,
                    "description": null,
                    "datetime": 1593855052,
                    "type": "video/mp4",
                    "animated": true,
                    "width": 480,
                    "height": 854,
                    "size": 1406253,
                    "views": 1704,
                    "bandwidth": 2396255112,
                    "vote": null,
                    "favorite": false,
                    "nsfw": null,
                    "section": null,
                    "account_url": null,
                    "account_id": null,
                    "is_ad": false,
                    "in_most_viral": false,
                    "has_sound": false,
                    "tags": [],
                    "ad_type": 0,
                    "ad_url": "",
                    "edited": "0",
                    "in_gallery": false,
                    "link": "https://i.imgur.com/0bceGfe.mp4",
                    "mp4_size": 1406253,
                    "mp4": "https://i.imgur.com/0bceGfe.mp4",
                    "gifv": "https://i.imgur.com/0bceGfe.gifv",
                    "hls": "https://i.imgur.com/0bceGfe.m3u8",
                    "processing": {
                        "status": "completed"
                    },
                    "comment_count": null,
                    "favorite_count": null,
                    "ups": null,
                    "downs": null,
                    "points": null,
                    "score": null*/


        @SerializedName("id")
        private String id;

        @SerializedName("title")
        private String title;

        @SerializedName("type")
        private String type;

        @SerializedName("description")
        private String description;

        @SerializedName("width")
        private int width;

        @SerializedName("height")
        private int height;
//        private String name;

        @SerializedName("link")
        private String link;

        @SerializedName("comment_count")
        private int comment_count;

        @SerializedName("favorite_comment")
        private int favorite_count;


        public void setLink(String link) {
            this.link = link;
        }

        public String getLink(){ return link; }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Images(){

        }

        public Images(String id, String title, String type, String description, String link) {
            this.id = id;
            this.title = title;
            this.type = type;
            this.description = description;
            this.link = link;
        }

}
