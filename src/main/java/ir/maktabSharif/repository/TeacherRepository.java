package ir.maktabSharif.repository;

import ir.maktabSharif.model.Teacher;
import ir.maktabSharif.model.User;

import java.util.List;

public interface TeacherRepository extends BaseRepository<Teacher>{
    List<Teacher> FindTeachersByFirstName (String name);
}
