package br.unisinos.computernowapi.entities;

import br.unisinos.computernowapi.enums.SOEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class ComputerEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    @Enumerated(EnumType.STRING)
    private SOEnum so;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private Double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ComputerEntity that = (ComputerEntity) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 88965591;
    }
}
