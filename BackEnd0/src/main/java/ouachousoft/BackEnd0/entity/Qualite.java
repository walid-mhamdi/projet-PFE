package ouachousoft.BackEnd0.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "qualite")
public class Qualite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    private String code;

    private String libelle;
}
