package com.example.TodoWebApp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TodoDetailForm {

    private String id;
    private String username;
	
	@NotBlank
    @Size(min = 1, max = 100, message = "タイトルは1文字以上100文字以内でお願いします。")
    private String title;

    private String details;

    private String tododate;
    private String todotime;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getTododate() {
		return tododate;
	}
	public void setTododate(String tododate) {
		this.tododate = tododate;
	}
	public String getTodotime() {
		return todotime;
	}
	public void setTodotime(String todotime) {
		this.todotime = todotime;
	}
}
