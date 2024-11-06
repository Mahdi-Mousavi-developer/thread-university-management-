package ir.maktabSharif.thread;
import ir.maktabSharif.model.Student;
import ir.maktabSharif.repository.StudentRepository;

import java.util.List;

public class CountOfAllStudent implements Runnable {


    StudentRepository studentRepository;

    public CountOfAllStudent(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void run() {
        List<Student> students = null;

        while (true) {
            try {
                students = studentRepository.getAll();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            int number = students.size();
            System.out.println(number);
            try {
                Thread.sleep(2000, 1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}