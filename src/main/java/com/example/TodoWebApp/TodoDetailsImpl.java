package com.example.TodoWebApp;

public class TodoDetailsImpl implements TodDetails {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String username;
	private String title;
	private String details;
    private String tododate;
    private String todotime;
    private String done;

    public TodoDetailsImpl(String id, String username, String title, String details,
    		String tododate, String todotime, String done) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.details = details;
        this.tododate = tododate;
        this.todotime = todotime;
        this.done = done;
    }

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getDetails() {
		return details;
	}

	@Override
	public String getTododate() {
		return tododate;
	}

	@Override
	public String getTodotime() {
		return todotime;
	}

	@Override
	public String getDone() {
		return done;
	}
}
