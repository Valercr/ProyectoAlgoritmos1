package ucr.proyecto1.domain.XMLData;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import ucr.proyecto1.domain.data.Course;
import ucr.proyecto1.domain.tree.AVL;
import ucr.proyecto1.domain.tree.TreeException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CourseXMLData {
    private String filePath;
    private Element root;
    private Document document;
    public AVL<Course> courseTree;

    public CourseXMLData(String filePath) throws IOException, JDOMException {
        this.filePath = filePath;
        this.courseTree = new AVL<>();
        File file = new File(filePath);
        if (!file.exists()) {
            this.root = new Element("courses");
            this.document = new Document(root);
            save();
        } else {
            SAXBuilder saxBuilder = new SAXBuilder();
            this.document = saxBuilder.build(file);
            this.root = document.getRootElement();
        }
        loadCoursesToAVL();
    }

    private void save() throws IOException {
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(document, new PrintWriter(filePath));
    }

    private void loadCoursesToAVL() {
        List<Element> courseElements = root.getChildren("course");
        for (Element courseElement : courseElements) {
            Course course = buildCourseFromElement(courseElement);
            courseTree.add(course);
        }
    }

    public void addCourse(Course course) throws IOException {
        validateCourse(course);
        Element courseElement = createCourseElement(course);
        root.addContent(courseElement);
        courseTree.add(course);
        save();
    }

    public void updateCourse(Course updatedCourse) throws IOException, TreeException {
        validateCourse(updatedCourse);
        List<Element> courseElements = root.getChildren("course");
        boolean courseFound = false;
        for (Element courseElement : courseElements) {
            if (courseElement.getAttributeValue("id").equals(String.valueOf(updatedCourse.getId()))) {
                updateCourseElement(courseElement, updatedCourse);
                courseTree.remove(updatedCourse);
                courseTree.add(updatedCourse);
                save();
                courseFound = true;
                break;
            }
        }
        if (!courseFound) {
            throw new IllegalArgumentException("The course to update does not exist in the XML.");
        }
    }

    public void removeCourse(int courseId) throws IOException, TreeException {
        List<Element> courseElements = root.getChildren("course");
        for (Element courseElement : courseElements) {
            if (courseElement.getAttributeValue("id").equals(String.valueOf(courseId))) {
                root.removeContent(courseElement);
                courseTree.remove(new Course(courseId, null, null, null, null, 0));
                save();
                return;
            }
        }
        throw new IllegalArgumentException("The course to remove does not exist in the XML.");
    }

    public Course findCourseById(int courseId) {
        List<Element> courseElements = root.getChildren("course");
        for (Element courseElement : courseElements) {
            if (courseElement.getAttributeValue("id").equals(String.valueOf(courseId))) {
                return buildCourseFromElement(courseElement);
            }
        }
        return null;
    }

    public List<Course> getAllCourses() {
        try {
            return courseTree.inOrderList();
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

    private Element createCourseElement(Course course) {
        Element courseElement = new Element("course");
        courseElement.setAttribute("id", String.valueOf(course.getId()));

        Element nameElement = new Element("name");
        nameElement.addContent(course.getName());

        Element descriptionElement = new Element("description");
        descriptionElement.addContent(course.getDescription());

        Element lengthElement = new Element("length");
        lengthElement.addContent(course.getLength());

        Element levelElement = new Element("level");
        levelElement.addContent(course.getLevel());

        Element instructorIdElement = new Element("instructorId");
        instructorIdElement.addContent(String.valueOf(course.getInstructorId()));

        courseElement.addContent(nameElement);
        courseElement.addContent(descriptionElement);
        courseElement.addContent(lengthElement);
        courseElement.addContent(levelElement);
        courseElement.addContent(instructorIdElement);

        return courseElement;
    }

    private void updateCourseElement(Element courseElement, Course updatedCourse) {
        courseElement.getChild("name").setText(updatedCourse.getName());
        courseElement.getChild("description").setText(updatedCourse.getDescription());
        courseElement.getChild("length").setText(updatedCourse.getLength());
        courseElement.getChild("level").setText(updatedCourse.getLevel());
        courseElement.getChild("instructorId").setText(String.valueOf(updatedCourse.getInstructorId()));
    }

    private void validateCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course object cannot be null.");
        }
        if (course.getName() == null || course.getName().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be null or empty.");
        }
        if (course.getDescription() == null || course.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Course description cannot be null or empty.");
        }
        if (course.getLength() == null || course.getLength().isEmpty()) {
            throw new IllegalArgumentException("Course length cannot be null or empty.");
        }
        if (course.getLevel() == null || course.getLevel().isEmpty()) {
            throw new IllegalArgumentException("Course level cannot be null or empty.");
        }
    }

    private Course buildCourseFromElement(Element courseElement) {
        int id = Integer.parseInt(courseElement.getAttributeValue("id"));
        String name = getElementText(courseElement, "name");
        String description = getElementText(courseElement, "description");
        String length = getElementText(courseElement, "length");
        String level = getElementText(courseElement, "level");
        int instructorId = Integer.parseInt(getElementText(courseElement, "instructorId"));

        return new Course(id, name, description, length, level, instructorId);
    }

    private String getElementText(Element parentElement, String childName) {
        Element childElement = parentElement.getChild(childName);
        return (childElement != null) ? childElement.getText() : "";
    }
}
