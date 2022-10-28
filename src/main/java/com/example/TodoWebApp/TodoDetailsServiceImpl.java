package com.example.TodoWebApp;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TodoDetailsServiceImpl {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<TodDetails> select(String username) {
        String sql = "SELECT * FROM todo WHERE username = ?";
        List<Map<String, Object>> dlist = jdbcTemplate.queryForList(sql, username);
        List<TodDetails> list = new ArrayList<TodDetails>();

		for (Map<String, Object> map : dlist) {
		    String id = String.valueOf(map.get("id"));
		    String title = (String)map.get("title");
		    String details = (String)map.get("details");
		    String done = (String)map.get("done");
		    
		    Date date = (Date)map.get("tododate");
		    String tododate = "";
		    if (date != null) {
		    	tododate = date.toString();
		    }
		    
		    Time localtime = (Time)map.get("todotime");
		    String todotime = "";
		    if (localtime != null) {
		    	todotime = localtime.toString();
		    	if (todotime != null && todotime.length() == 8) {
		    		todotime = todotime.substring(0, 5);
		    	}
		    }
		    
		    // TODOリストを取得
		    list.add(new TodoDetailsImpl(id, username, title, details, tododate, todotime, done));
		}
		return list;
    }
    
    @Transactional
    public void register(String username, String usernamejp, String password, String authority) {
        String sql = "INSERT INTO user(username, name, password, authority) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(sql, username, usernamejp, authority);
    }

    public boolean isExistUser(String username) {
        String sql = "SELECT COUNT(*) FROM user WHERE username = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, new Object[] { username });
        if (count == 0) {
            return false;
        }
        return true;
    }

	public TodDetails selectById(String id) {
	    // idからTODO情報を取得
		String sql = "SELECT * FROM todo WHERE id = ?";
        Map<String, Object> map = jdbcTemplate.queryForMap(sql, id);

        String username = (String)map.get("username");
	    String title = (String)map.get("title");
	    String details = (String)map.get("details");
	    String done = (String)map.get("done");

	    Date date = (Date)map.get("tododate");
	    String tododate = "";
	    if (date != null) {
	    	tododate = date.toString();
	    }
	    
	    Time localtime = (Time)map.get("todotime");
	    String todotime = "";
	    if (localtime != null) {
	    	todotime = localtime.toString();
	    	if (todotime != null && todotime.length() == 8) {
	    		todotime = todotime.substring(0, 5);
	    	}
	    }

	    // テーブルの情報からTODO情報を作成
	    TodDetails tododetail = new TodoDetailsImpl(id, username, title, details, tododate, todotime, done);

        return tododetail;
	}

	public void insert(String username, String title, String details, String tododate, String todotime) {
        String sql = "INSERT INTO todo (username, title, details, tododate, todotime) "
        		+ "VALUES (?, ?, ?, ?, ?)";

        Date date = null;
        try {
            date = Date.valueOf(tododate.replace("/", "-"));
            
        } catch (Exception e) {}
	    Time time = null;
        try {
        	time = Time.valueOf(todotime + ":00");
        } catch (Exception e) {}

        jdbcTemplate.update(sql, username, title, details, date, time);
	}

	public void update(String id, String username, String title, String details, String tododate, String todotime) {
        String sql = "UPDATE todo SET title = ?, details = ?, tododate = ?,  todotime = ? "
        		+ "WHERE id = ? and username = ? ";

        Date date = null;
        try {
            date = Date.valueOf(tododate.replace("/", "-"));
        } catch (Exception e) {}
	    Time time = null;
        try {
        	time = Time.valueOf(todotime + ":00");
        } catch (Exception e) {}
        
        jdbcTemplate.update(sql, title, details, date, time, id, username);
	}

	public void delete(String id, String username) {
        String sql = "DELETE FROM todo WHERE id = ? and username = ?";
        jdbcTemplate.update(sql, id, username);
	}

	public void updateDone(String id, String username, String done) {
		// ステータス変更
        String sql = "UPDATE todo SET done = ? "
        		+ "WHERE id = ? and username = ? ";
        
        jdbcTemplate.update(sql, done, id, username);
	}
}

