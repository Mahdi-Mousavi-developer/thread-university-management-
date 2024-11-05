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
        List<Student> students = studentRepository.getAll();
        int number = students.size();
        while (true) {
            System.out.println(number);
            try {
                Thread.sleep(3422, 1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}