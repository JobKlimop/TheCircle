package thecircle.seechange.domain;

import android.widget.ImageView;

public class User {
    public User(String username, String email, String slogan, ImageView avatar) {
        this.username = username;
        this.email = email;
        this.slogan = slogan;
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public ImageView getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageView avatar) {
        this.avatar = avatar;
    }

    private String username, email, slogan;
    private ImageView avatar;
}
