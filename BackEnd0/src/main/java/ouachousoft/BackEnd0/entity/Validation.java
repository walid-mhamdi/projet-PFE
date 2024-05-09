package ouachousoft.BackEnd0.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "validation")
public class Validation {

    @Id
    @GeneratedValue()
    private int id;


    private Instant creation;
    private Instant expiration;
    private Instant activation;
    private  String code;
    private String email; // Nouveau champ pour l'email


    @OneToOne(cascade = CascadeType.ALL)
    private Utilisateur utilisateur;
}
