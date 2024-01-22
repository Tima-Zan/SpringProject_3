package ru.zan.springcourse.SpringCourseProject_3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import ru.zan.springcourse.SpringCourseProject_3.model.Measurements;

import java.util.Optional;

@Repository
public interface MeasurementsRepositories extends JpaRepository<Measurements,Integer> {


}
