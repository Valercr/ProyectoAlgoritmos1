package ucr.proyecto1.domain.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class reportGenerator {

    public void generateReportCourse(String dest){
        Document document = new Document();
        try{
            PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();

            //Encabezado
            document.add(new Paragraph("Registration Report by Course"));
            document.add(new Paragraph("Sistem name"));
            document.add(new Paragraph("Date: " + java.time.LocalDate.now().toString()));
            document.add(new Paragraph("\n"));

            //Crear la tabla
            PdfPTable table = new PdfPTable(2);
            table.addCell("Course");
            table.addCell("Amount of Students");

            //Datos simulados
            Map<String, Integer> courseEnrollments = new HashMap<>();
            courseEnrollments.put("Matemáticas", 30);

            //Agregar los datos a la tabla
            for (Map.Entry<String, Integer> entry : courseEnrollments.entrySet()) {
                table.addCell(entry.getKey());
                table.addCell(String.valueOf(entry.getValue()));
            }

            //Agregar la tabla al documento
            document.add(table);

        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            document.close();
        }

        System.out.println("Generated report: " + dest);
    }

    private void generateReportStudent(String dest, int studentID){
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();

            document.add(new Paragraph("Student Progress Report"));
            document.add(new Paragraph("System name"));
            document.add(new Paragraph("Date: " + java.time.LocalDate.now().toString()));
            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(4);
            table.addCell("Course");
            table.addCell("Complete lessions");
            table.addCell("Grade");
            table.addCell("Dedicated time");

            List<StudentProgress> progressList = new ArrayList<>();
            progressList.add(new StudentProgress("Matemáticas", 10, 90.5f, 20));

            for (StudentProgress progress : progressList) {
                table.addCell(progress.courseName);
                table.addCell(String.valueOf(progress.lessonsCompleted));
                table.addCell(String.valueOf(progress.grade));
                table.addCell(progress.timeSpent + " hours");
            }

            document.add(table);

        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        System.out.println("Generated report: " + dest);
    }

    static class StudentProgress {
        String courseName;
        int lessonsCompleted;
        float grade;
        int timeSpent;

        StudentProgress(String courseName, int lessonsCompleted, float grade, int timeSpent) {
            this.courseName = courseName;
            this.lessonsCompleted = lessonsCompleted;
            this.grade = grade;
            this.timeSpent = timeSpent;
        }
    }

    public void generateEvaluationReport(String dest, int courseId) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();

            document.add(new Paragraph("Evaluations and Grades Report"));
            document.add(new Paragraph("System name"));
            document.add(new Paragraph("Date: " + java.time.LocalDate.now().toString()));
            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(3);
            table.addCell("Student");
            table.addCell("Evaluation");
            table.addCell("Grade");

            List<Evaluation> evaluations = new ArrayList<>();
            evaluations.add(new Evaluation("Juan Pérez", "Examen 1", 85.5f));

            for (Evaluation eval : evaluations) {
                table.addCell(eval.studentName);
                table.addCell(eval.evaluationName);
                table.addCell(String.valueOf(eval.grade));
            }

            document.add(table);

        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        System.out.println("Generated Report: " + dest);
    }

    static class Evaluation {
        String studentName;
        String evaluationName;
        float grade;

        Evaluation(String studentName, String evaluationName, float grade) {
            this.studentName = studentName;
            this.evaluationName = evaluationName;
            this.grade = grade;
        }
    }

}
