package ru.oliferov.models;


import com.google.common.base.Objects;

import javax.persistence.*;

/**
 * @autor aoliferov
 * @since 10.03.2019
 */
@Entity
@Table(name = "engine")
public class Engine {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "power")
    private int power;

    /**
     * Объем двигателя
     */
    @Column(name = "volume")
    private int volume;

    /**
     * Тип двигателя (бензин, гибрид, дизель, электро, газ)
     */
    @ManyToOne
    @JoinColumn(name = "type_engine")
    private TypeEngine type;

    public Engine() {
    }

    public Engine(int power) {
        this.power = power;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public TypeEngine getType() {
        return type;
    }

    public void setType(TypeEngine type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Engine engine = (Engine) o;
        return id == engine.id
                && power == engine.power
                && volume == engine.volume
                && Objects.equal(type, engine.type);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, power, volume, type);
    }
}
