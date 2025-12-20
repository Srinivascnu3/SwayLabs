package pojo;
//{id=1, title=Activity 1, dueDate=2025-12-19T04:20:55.4373722+00:00, completed=false}

public class ActivitiesPojo {
	 int id;
	 String title;
	 String dueDate;
	 public ActivitiesPojo() {
		super();
		// TODO Auto-generated constructor stub
	}
	 public ActivitiesPojo(int id, String title, String dueDate, Boolean completed) {
		super();
		this.id = id;
		this.title = title;
		this.dueDate = dueDate;
		this.completed = completed;
	}
	 Boolean completed;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public Boolean getCompleted() {
		return completed;
	}
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
	
}