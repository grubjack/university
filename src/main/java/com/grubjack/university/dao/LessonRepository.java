package com.grubjack.university.dao;

import com.grubjack.university.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by grubjack on 30.11.2016.
 */
@Transactional(readOnly = true)
public interface LessonRepository extends JpaRepository<Lesson, Integer> {

    List<Lesson> findAllByOrderByDayOfWeekAscTimeOfDayAsc();

    List<Lesson> findByGroupId(int groupId);

    List<Lesson> findByTeacherId(int teacherId);

}