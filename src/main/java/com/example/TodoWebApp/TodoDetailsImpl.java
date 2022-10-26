package com.example.TodoWebApp;

public class TodoDetailsImpl implements TodDetails {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String userid;
	private String title;
	private String details;
    private String tododate;
    private String todotime;
    private String done;

    public TodoDetailsImpl(String id, String userid, String title, String details,
    		String tododate, String todotime, String done) {
        this.id = id;
        this.userid = userid;
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
	public String getUserid() {
		return userid;
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
