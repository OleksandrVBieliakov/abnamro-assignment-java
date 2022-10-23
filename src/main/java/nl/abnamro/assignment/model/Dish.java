package nl.abnamro.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "dish")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dish {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(name = "number_of_servings", nullable = false)
    private Integer numberOfServings;

    private String instruction;

    @OneToMany(mappedBy = "dish", orphanRemoval = true)
    private List<Ingredient> ingredients = new ArrayList<>();

}
