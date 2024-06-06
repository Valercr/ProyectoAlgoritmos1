package ucr.proyecto1.domain.data;

import java.util.Objects;

public class Course implements Comparable<Course> {
    private int id;
    private String name;
    private String description;
    private String courseLength;
    private String level;
    private int instructorId;

    public Course(int id, String name, String description, String courseLength, String level, int instructorId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.courseLength = courseLength;
        setLevel(level); // Validación de nivel en el constructor
        this.instructorId = instructorId;
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
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourseLength() {
        return courseLength;
    }

    public void setCourseLength(String courseLength) {
        this.courseLength = courseLength;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        // Validar que el nivel sea uno de los valores predefinidos
        if (!level.equalsIgnoreCase("bajo") && !level.equalsIgnoreCase("medio") && !level.equalsIgnoreCase("dificil")) {
            throw new IllegalArgumentException("Invalid level. Level must be one of the following: bajo, medio, dificil.");
        }
        this.level = level;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }

    // Método compareTo para ordenar por nombre
    @Override
    public int compareTo(Course otherCourse) {
        return this.name.compareTo(otherCourse.name);
    }

    // Método equals para comparar por id
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return id == course.id;
    }

    // Método hashCode para garantizar consistencia en la comparación
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Método toString para representar el curso como una cadena de texto
    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", courseLength='" + courseLength + '\'' +
                ", level='" + level + '\'' +
                ", instructorId=" + instructorId +
                '}';
    }
}

