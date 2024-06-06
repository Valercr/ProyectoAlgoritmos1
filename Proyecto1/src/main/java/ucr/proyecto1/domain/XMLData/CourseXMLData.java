package ucr.proyecto1.domain.XMLData;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import ucr.proyecto1.domain.data.Course;

public class CourseXMLData {
    private String filePath;
    private Element root;
    private Document document;

    public CourseXMLData(String filePath) throws IOException, JDOMException {
        this.filePath = filePath;
        File file = new File(filePath);
        if (!file.exists()) {
            this.root = new Element("courses");
            this.document = new Document(root);
            save();
        } else {
            SAXBuilder saxBuilder = new SAXBuilder();
            this.document = saxBuilder.build(new File(filePath));
            this.root = document.getRootElement();
        }
    }

    private void save() throws IOException {
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(document, new PrintWriter(filePath));
    }

    public void addCourse(Course course) throws IOException {
        if (course == null) {
            throw new IllegalArgumentException("Course object cannot be null.");
        }

        // Validar campos obligatorios
        if (course.getName() == null || course.getName().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be null or empty.");
        }

        Element courseElement = new Element("course");
        courseElement.setAttribute("id", String.valueOf(course.getId()));

        Element nameElement = new Element("name");
        nameElement.addContent(course.getName());

        Element descriptionElement = new Element("description");
        descriptionElement.addContent(course.getDescription());

        Element lengthElement = new Element("length");
        lengthElement.addContent(course.getCourseLength());

        Element levelElement = new Element("level");
        levelElement.addContent(course.getLevel());

        Element instructorIdElement = new Element("instructorId");
        instructorIdElement.addContent(String.valueOf(course.getInstructorId()));

        courseElement.addContent(nameElement);
        courseElement.addContent(descriptionElement);
        courseElement.addContent(lengthElement);
        courseElement.addContent(levelElement);
        courseElement.addContent(instructorIdElement);

        root.addContent(courseElement);
        save();
    }


    public void updateCourse(Course updatedCourse) throws IOException {
        List<Element> courseElements = root.getChildren("course");
        boolean courseFound = false;
        for (Element courseElement : courseElements) {
            if (courseElement.getAttributeValue("id").equals(String.valueOf(updatedCourse.getId()))) {
                courseElement.getChild("name").setText(updatedCourse.getName());
                courseElement.getChild("description").setText(updatedCourse.getDescription());
                courseElement.getChild("length").setText(updatedCourse.getCourseLength());
                courseElement.getChild("level").setText(updatedCourse.getLevel());
                courseElement.getChild("instructorId").setText(String.valueOf(updatedCourse.getInstructorId()));
                save();
                courseFound = true;
                break; // No es necesario seguir buscando
            }
        }
        if (!courseFound) {
            throw new IllegalArgumentException("The course to update does not exist in the XML.");
        }
    }



    public void removeCourse(int courseId) throws IOException {
        List<Element> courseElements = root.getChildren("course");
        for (Element courseElement : courseElements) {
            if (courseElement.getAttributeValue("id").equals(String.valueOf(courseId))) {
                root.removeContent(courseElement);
                save();
                return;
            }
        }
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


    private Course buildCourseFromElement(Element courseElement) throws IllegalArgumentException {
        int id = Integer.parseInt(courseElement.getAttributeValue("id"));

        String name = getElementText(courseElement, "name");
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Course name is missing in XML.");
        }

        String description = getElementText(courseElement, "description");
        if (description.isEmpty()) {
            throw new IllegalArgumentException("Course description is missing in XML.");
        }

        String length = getElementText(courseElement, "length");

        String level = getElementText(courseElement, "level");
        if (level.isEmpty()) {
            throw new IllegalArgumentException("Course level is missing in XML.");
        }

        int instructorId = Integer.parseInt(getElementText(courseElement, "instructorId"));

        return new Course(id, name, description, length, level, instructorId);
    }

    private String getElementText(Element parentElement, String childName) {
        Element childElement = parentElement.getChild(childName);
        return (childElement != null) ? childElement.getText() : "";
    }


}

