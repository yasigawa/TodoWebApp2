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

    public List<TodDetails> select(String userid) {
        String sql = "SELECT * FROM todo WHERE userid = ?";
        List<Map<String, Object>> dlist = jdbcTemplate.queryForList(sql, userid);
        List<TodDetails> list = new ArrayList<TodDetails>();

		for (Map<String, Object> map : dlist) {
		    String id = String.valueOf(map.get("id"));
		    String title = (String)map.get("title");
		    String details = (String)map.get("details");
		    String done = (String)map.get("done");
		    
//		    LocalDateTime localDateTime = (LocalDateTime)map.get("datetime");
//		    String datetime = "";
//		    if (localDateTime != null) {
//		    	datetime = localDateTime.format(formatter);
//		    }

		    Date date = (Date)map.get("tododate");
		    String tododate = "";
		    if (date != null) {
//		    	tododate = localdate.format(frmtd);
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
		    
		    list.add(new TodoDetailsImpl(id, userid, title, details, tododate, todotime, done));
		}
		return list;
    }
    
    @Transactional
    public void register(String userid, String username, String password, String authority) {
        String sql = "INSERT INTO user(userid, name, password, authority) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(sql, userid, username, authority);
    }

    public boolean isExistUser(String userid) {
        String sql = "SELECT COUNT(*) FROM user WHERE userid = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, new Object[] { userid });
        if (count == 0) {
            return false;
        }
        return true;
    }

	public TodDetails selectById(String id) {
        String sql = "SELECT * FROM todo WHERE id = ?";
        Map<String, Object> map = jdbcTemplate.queryForMap(sql, id);

        String userid = (String)map.get("userid");
	    String title = (String)map.get("title");
	    String details = (String)map.get("details");
	    String done = (String)map.get("done");

//	    String datetime = "";
//	    LocalDateTime localDateTime = (LocalDateTime)map.get("datetime");
//	    if (localDateTime != null) {
//	    	datetime = localDateTime.format(formatter);
//	    }

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
	    
	    TodDetails tododetail = new TodoDetailsImpl(id, userid, title, details, tododate, todotime, done);

        return tododetail;
	}

	public void update(String id, String userid, String title, String details, String tododate, String todotime) {
        String sql = "UPDATE todo SET title = ?, details = ?, tododate = ?,  todotime = ? "
        		+ "WHERE id = ? and userid = ? ";

        Date date = null;
        try {
            date = Date.valueOf(tododate.replace("/", "-"));
        } catch (Exception e) {}
	    Time time = null;
        try {
        	time = Time.valueOf(todotime + ":00");
        } catch (Exception e) {}
        
        jdbcTemplate.update(sql, title, details, date, time, id, userid);
	}

	public void delete(String id, String userid) {
        String sql = "DELETE FROM todo WHERE id = ? and userid = ?";
        jdbcTemplate.update(sql, id, userid);
	}

	public void insert(String userid, String title, String details, String tododate, String todotime) {
        String sql = "INSERT INTO todo (userid, title, details, tododate, todotime) "
        		+ "VALUES (?, ?, ?, ?, ?)";

        Date date = null;
        try {
//            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
//            date = sdFormat.parse(tododate);
            date = Date.valueOf(tododate.replace("/", "-"));
            
        } catch (Exception e) {}
	    Time time = null;
        try {
        	time = Time.valueOf(todotime + ":00");
        } catch (Exception e) {}

        jdbcTemplate.update(sql, userid, title, details, date, time);
	}

	public void updateDone(String id, String userid, String done) {
        String sql = "UPDATE todo SET done = ? "
        		+ "WHERE id = ? and userid = ? ";
        
        jdbcTemplate.update(sql, done, id, userid);
	}


	
}

