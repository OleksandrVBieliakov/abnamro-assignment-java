package nl.abnamro.assignment.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "ingredient")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "dish_id", foreignKey = @ForeignKey(name = "fk_ingredient_dish"))
    private Dish dish;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_ingredient_product"))
    private Product product;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "unit_id", foreignKey = @ForeignKey(name = "fk_ingredient_unit"))
    private Unit unit;

    @Column(nullable = false)
    private Double amount;
}
