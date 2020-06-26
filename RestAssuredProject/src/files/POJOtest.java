package files;

public class POJOtest {
	
	private String url;
	private CoursesJson courses; //courses itself consist of a mini json hence a new pojo class created "CoursesJson"
	private String instructor;
	private String services;
	private String expertise;
	private String linkedIn;

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public CoursesJson getCourses() {
		return courses;
	}
	public void setCourses(CoursesJson courses) {
		this.courses = courses;
	}
	public String getInstructor() {
		return instructor;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	public String getExpertise() {
		return expertise;
	}
	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}
	public String getLinkedIn() {
		return linkedIn;
	}
	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}
	
}
