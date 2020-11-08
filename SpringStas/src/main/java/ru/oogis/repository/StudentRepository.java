package ru.oogis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.oogis.model.StudentEntity;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
}
