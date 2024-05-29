package ouachousoft.BackEnd0.dto;

import lombok.Getter;
import lombok.Setter;
import ouachousoft.BackEnd0.TypeDeRole;

@Setter
@Getter
public class UtilisateurDTO {


    private int id;
    private String nom;

    private String email;
    private TypeDeRole role;
    private boolean actif;


    // Constructeurs
    public UtilisateurDTO(int id, String nom, String email, TypeDeRole role, boolean actif) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.role = role;
        this.actif= actif;


    }


}
