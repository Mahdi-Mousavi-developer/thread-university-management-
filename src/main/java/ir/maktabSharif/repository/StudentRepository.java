package ir.maktabSharif.repository;

import ir.maktabSharif.model.Student;

public interface StudentRepository extends BaseRepository<Student>{
    void secUpdate(Student student);
}
