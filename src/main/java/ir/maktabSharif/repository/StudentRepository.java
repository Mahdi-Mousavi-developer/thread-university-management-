package ir.maktabSharif.repository;

import ir.maktabSharif.model.Student;

import java.util.List;

public interface StudentRepository extends BaseRepository<Student>{
    List<Student> FindStudentsByFirstName (String name);
}
