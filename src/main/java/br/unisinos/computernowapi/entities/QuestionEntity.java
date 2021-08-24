package br.unisinos.computernowapi.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @OneToMany
    @ToString.Exclude
    private List<AnswerEntity> answers = new ArrayList<>();

    public void addAnswer(AnswerEntity answerEntity) {
        Assert.notNull(answerEntity, "answer entity cannot be null");
        answers.add(answerEntity);
    }

    public void removeAnswer(AnswerEntity answerEntity) {
        Assert.notNull(answerEntity, "answer entity cannot be null");
        answers.remove(answerEntity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        QuestionEntity that = (QuestionEntity) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 104819025;
    }
}
