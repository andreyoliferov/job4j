package ru.oliferov.platform.advt.models;


import ru.oliferov.platform.car.models.Car;
import ru.oliferov.platform.user.models.User;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @autor aoliferov
 * @since 18.03.2019
 */
@Entity
@Table(name = "advt")
public class Advt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "theme")
    private String theme;

    @Column(name = "description")
    private String desc;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "car")
    private Car car;

    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "author")
    private User author;

    @ManyToOne
    @JoinColumn(name = "state")
    private State state;

}
