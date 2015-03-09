package br.liveo.model;

/**
 * Created by root1 on 2/22/15.
 */
public class Media {

    private String id;
    private String active;
    private String albumId;
    private String clipId;
    private String extension;
    private String name;
    private String postId;
    private String temp;
    private String timeline;
    private String type;
    private String url;


    public Media(String id,String active,String albumId,String clipId,String extension,String name
            ,String postId,String temp,String timeline,String type,String url){

        this.id = id;
        this.active =active;
        this.albumId = albumId;
        this.clipId =clipId;
        this.extension = extension;
        this.name = name;
        this.postId = postId;
        this.temp = temp;
        this.timeline = timeline;
        this.type = type;
        this.url = url;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getClipId() {
        return clipId;
    }

    public void setClipId(String clipId) {
        this.clipId = clipId;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTimeline() {
        return timeline;
    }

    public void setTimeline(String timeline) {
        this.timeline = timeline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
