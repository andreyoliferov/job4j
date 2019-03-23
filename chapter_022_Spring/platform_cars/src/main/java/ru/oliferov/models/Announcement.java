package ru.oliferov.models;

import ru.oliferov.models.User;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @autor aoliferov
 * @since 18.03.2019
 */
@Entity
@Table(name = "announcement")
public class Announcement {

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

}
