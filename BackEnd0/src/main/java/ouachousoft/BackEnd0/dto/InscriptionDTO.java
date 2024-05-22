package ouachousoft.BackEnd0.dto;

import ouachousoft.BackEnd0.TypeDeRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InscriptionDTO {
    private String nom;
    private String email;
    private String mdp;
    private TypeDeRole role;
}
