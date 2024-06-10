package ucr.proyecto1.domain.data;

import java.util.Objects;
public class Course implements Comparable<Course> {
    private int id;
    private String name;
    private String description;
    private String length;
    private String level;
    private int instructorId;

    public Course(int id, String name, String description, String length, String level, int instructorId) {
        setId(id);
        setName(name);
        setDescription(description);
        setLength(length);
        setLevel(level);
        setInstructorId(instructorId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be a positive number.");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be null or empty.");
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Course description cannot be null or empty.");
        }
        this.description = description;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        if (length == null || length.isEmpty()) {
            throw new IllegalArgumentException("Course length cannot be null or empty.");
        }
        this.length = length;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        if (!level.equalsIgnoreCase("low") && !level.equalsIgnoreCase("medium") && !level.equalsIgnoreCase("high")) {
            throw new IllegalArgumentException("Invalid level. Level must be one of the following: low, medium, high.");
        }
        this.level = level;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        if (instructorId <= 0) {
            throw new IllegalArgumentException("Instructor ID must be a positive number.");
        }
        this.instructorId = instructorId;
    }

    @Override
    public int compareTo(Course otherCourse) {
        return Integer.compare(this.id, otherCourse.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return id == course.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", length='" + length + '\'' +
                ", level='" + level + '\'' +
                ", instructorId=" + instructorId +
                '}';
    }
}
