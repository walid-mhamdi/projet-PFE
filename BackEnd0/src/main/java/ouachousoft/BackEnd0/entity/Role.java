package ouachousoft.BackEnd0.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ouachousoft.BackEnd0.TypeDeRole;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role {
    @Id
    //@Id
//@GeneratedValue(strategy = GenerationType.IDENTITY)
// private int id;
    @Enumerated(EnumType.STRING)
    @Column(name = "libelle")
    private TypeDeRole libelle;

    // Optional: To avoid recursion during JSON serialization
    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private List<Utilisateur> utilisateurs;
}

