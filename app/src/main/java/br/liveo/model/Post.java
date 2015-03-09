package br.liveo.model;

import java.util.ArrayList;

public class Post {

    String imageProfileUrl;
    String name;
    String date;
    String loveCount;
    String commentCount;
    String shareCount;
    String shortMessage;
    String message;
    String viewCount;
    String imagePostUrl;
    String mediaType;


    private Author author;
    private ArrayList<Love> love = new ArrayList<>();
    private ArrayList<Comment> comments = new ArrayList<>();
    private ArrayList<Follow> follows = new ArrayList<>();
    private Youtube youtubes = null ;
    private Media media = null;




    public Post(String imageProfileUrl, String name, String date, String loveCount, String commentCount,
                String shareCount, String message, String shortMessage, String viewCount, String imagePostUrl
                ,String mediaType) {

        this.imageProfileUrl = imageProfileUrl;
        this.name = name;
        this.date = date;
        this.loveCount = loveCount;
        this.commentCount = commentCount;
        this.shareCount = shareCount;
        this.message = message;
        this.shortMessage = shortMessage;
        this.viewCount = viewCount;
        this.imagePostUrl = imagePostUrl;
        this.mediaType = mediaType;

    }

    public Post(String imageProfileUrl, String name, String date, String loveCount, String commentCount,
                String shareCount, String message, String shortMessage, String viewCount, String imagePostUrl
                ,String mediaType,Youtube youtubes,Media media) {

        this.imageProfileUrl = imageProfileUrl;
        this.name = name;
        this.date = date;
        this.loveCount = loveCount;
        this.commentCount = commentCount;
        this.shareCount = shareCount;
        this.message = message;
        this.shortMessage = shortMessage;
        this.viewCount = viewCount;
        this.imagePostUrl = imagePostUrl;
        this.mediaType = mediaType;
        this.youtubes = youtubes;
        this.media = media;

    }


    public String getImageProfileUrl() {
        return imageProfileUrl;
    }

    public void setImageProfileUrl(String imageProfileUrl) {
        this.imageProfileUrl = imageProfileUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLoveCount() {
        return loveCount;
    }

    public void setLoveCount(String loveCount) {
        this.loveCount = loveCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getShareCount() {
        return shareCount;
    }

    public void setShareCount(String shareCount) {
        this.shareCount = shareCount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getImagePostUrl() {
        return imagePostUrl;
    }

    public void setImagePostUrl(String imagePostUrl) {
        this.imagePostUrl = imagePostUrl;
    }

    public Author getAuthor() {
        return author;
    }

    public String getShortMessage() {
        return shortMessage;
    }

    public void setShortMessage(String shortMessage) {
        this.shortMessage = shortMessage;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public ArrayList<Love> getLove() {
        return love;
    }

    public void setLove(ArrayList<Love> love) {
        this.love = love;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public ArrayList<Follow> getFollows() {
        return follows;
    }

    public void setFollows(ArrayList<Follow> follows) {
        this.follows = follows;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String medieType) {
        this.mediaType = medieType;
    }

    public Youtube getYoutubes() {
        return youtubes;
    }

    public void setYoutubes(Youtube youtubes) {
        this.youtubes = youtubes;
    }

    public Media getMedias() {
        return media;
    }

    public void setMedias(Media medias) {
        this.media = medias;
    }
}
