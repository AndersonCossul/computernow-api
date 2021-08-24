package br.unisinos.computernowapi.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class AnswerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @OneToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<ComputerEntity> computers = new ArrayList<>();

    public void addComputer(ComputerEntity computerEntity) {
        Assert.notNull(computerEntity, "computer entity cannot be null");
        computers.add(computerEntity);
    }

    public void removeComputer(ComputerEntity computerEntity) {
        Assert.notNull(computerEntity, "computer entity cannot be null");
        computers.remove(computerEntity);
    }

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
