package a1klik.csp203.a1klik;

public class course {
    private String courseName;
    private int courseID;
    private String courseTitle;
    private int id;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public course(String courseName, int id) {
        this.courseName = courseName;
        this.id = id;

    }
    public course(String courseName, int courseID, String courseTitle, int id) {
        this.courseName = courseName;
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        this.id = id;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }
}
