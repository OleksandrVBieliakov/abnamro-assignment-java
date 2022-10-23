package nl.abnamro.assignment.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "unit",
    indexes = {
        @Index(name = "uk_unit_name", columnList = "name", unique = true),
    })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Unit {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String name;
}
