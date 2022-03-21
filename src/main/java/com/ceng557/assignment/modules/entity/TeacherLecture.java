package com.ceng557.assignment.modules.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "teacher_lecture", schema = "assignment")
public class TeacherLecture {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacher_lecture_seq")
    @SequenceGenerator(sequenceName = "teacher_lecture_seq", allocationSize = 1, name = "teacher_lecture_seq")
    private Long id;

    //    @Column(name = "teacher_id")
//    private Long teacherId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

//    @Column(name = "lecture_id")
//    private Long lectureId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherLecture that = (TeacherLecture) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TeacherLecture{" +
                "id=" + id +
                ", teacher=" + teacher +
                ", lecture=" + lecture +
                '}';
    }
}
