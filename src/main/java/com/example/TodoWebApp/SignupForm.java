package com.example.TodoWebApp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SignupForm {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    @Size(min = 1, max = 20, message = "ユーザnameは1文字以上20文字以内でお願いします。")
    private String username;

    @NotBlank
    @Size(min = 1, max = 20, message = "日本語名は1文字以上20文字以内でお願いします。")
    private String usernamejp;

    @NotBlank
    @Size(min = 5, max = 20, message = "パスワードは5文字以上20文字以内でお願いします。")
    private String password;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsernamejp() {
        return usernamejp;
    }
    public void setUsernamejp(String usernamejp) {
        this.usernamejp = usernamejp;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
