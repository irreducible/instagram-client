package com.example.amore.instagramclient;

/**
 * Created by amore on 10/10/15.
 */
public class InstagramPhoto {

    public String username;
    public String caption;
    public String imageUrl;
    public int imageHeight;
    public int likes;
    public String userProfileUrl;
    public String latestCommentUser;
    public String latestComment;
    public int commentCount;

    public InstagramPhoto(String username, String caption, String imageUrl, int imageHeight,
                          int likes, String userProfileUrl, String latestCommentUser, String latestComment, int commentCount) {
        this.username = username;
        this.caption = caption;
        this.imageUrl = imageUrl;
        this.imageHeight = imageHeight;
        this.likes = likes;
        this.userProfileUrl = userProfileUrl;
        this.latestCommentUser = latestCommentUser;
        this.latestComment = latestComment;
        this.commentCount = commentCount;
    }
}
