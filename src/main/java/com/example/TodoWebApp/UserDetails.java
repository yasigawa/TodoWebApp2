package com.example.TodoWebApp;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public interface UserDetails extends Serializable {
    Collection<? extends GrantedAuthority> getAuthorities();  // 権限リストを返す
    String getPassword();  // パスワードを返す
    String getUsername();  // ユーザー名を返す
    String getUserid();  // ユーザーIDを返す
    boolean isAccountNonExpired();  // アカウントの有効期限の判定
    boolean isAccountNonLocked();  // アカウントのロック状態の判定
    boolean isCredentialsNonExpired();  // 資格情報の有効期限の判定
    boolean isEnabled();  // 有効なユーザーであるかの判定
}

