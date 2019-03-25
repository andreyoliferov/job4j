package ru.oliferov.platform.car.models;

import com.google.common.base.Objects;

import javax.persistence.*;

/**
 * @autor aoliferov
 * @since 18.03.2019
 */
@Entity
@Table(name = "models")
public class Model {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Имя */
    @Column(name = "name")
    private String name;

    /** Марка */
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "mark")
    private Mark mark;

    public Model() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Model model = (Model) o;
        return id == model.id
                && Objects.equal(name, model.name)
                && Objects.equal(mark, model.mark);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, mark);
    }
}
