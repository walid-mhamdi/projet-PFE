package ouachousoft.BackEnd0.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ouachousoft.BackEnd0.TypeDeRole;
import ouachousoft.BackEnd0.TypeModeDePaiement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="mode_de_paiement")
public class ModeDePaiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    private String code;

    @Enumerated(EnumType.STRING)
    private TypeModeDePaiement libelle;

}
