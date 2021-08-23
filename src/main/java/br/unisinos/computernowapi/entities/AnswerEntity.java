package br.unisinos.computernowapi.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class AnswerEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String title;
    @OneToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<ComputerEntity> computers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AnswerEntity that = (AnswerEntity) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 748908716;
    }
}
