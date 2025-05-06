package com.trading.app.demo.model;


import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wires")
public class Wire {

    //FIELDS
    @Id
    @SequenceGenerator(
            name = "wire_sequence_generator",
            sequenceName = "wire_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "wire_sequence_generator"
    )
    private long id;
    private int amount;

    // RELATIONSHIPS
    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

}
