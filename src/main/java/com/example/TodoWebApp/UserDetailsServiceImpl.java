package com.example.TodoWebApp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        try {
            String sql = "SELECT * FROM user WHERE userid = ?";
            Map<String, Object> map = jdbcTemplate.queryForMap(sql, userid);
            String username = (String)map.get("name");
            String password = (String)map.get("password");
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority((String)map.get("authority")));
            return new UserDetailsImpl(userid, username, password, authorities);
        } catch (Exception e) {
            throw new UsernameNotFoundException("user not found.", e);
        }
    }
    
    @Transactional
    public void register(String userid, String username, String password, String authority) {
        String sql = "INSERT INTO user(userid, name, password, authority) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(sql, userid, username, passwordEncoder.encode(password), authority);
    }

    public boolean isExistUser(String userid) {
        String sql = "SELECT COUNT(*) FROM user WHERE userid = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, new Object[] { userid });
        if (count == 0) {
            return false;
        }
        return true;
    }
}

