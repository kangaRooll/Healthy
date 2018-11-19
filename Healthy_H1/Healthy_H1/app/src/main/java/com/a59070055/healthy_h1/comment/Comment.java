package com.a59070055.healthy_h1.comment;

public class Comment {
    private int postid;
    private int id;
    private String body;
    private String name;
    private String email;

    public Comment(){

    }
    public Comment(int postid, int id, String body, String name, String email){
        this.setPostid(postid);
        this.setId(id);
        this.setBody(body);
        this.setName(name);
        this.setEmail(email);
    }

    public int getPostid() {
        return postid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
