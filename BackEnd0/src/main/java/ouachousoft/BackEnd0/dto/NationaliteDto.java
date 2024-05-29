package ouachousoft.BackEnd0.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ouachousoft.BackEnd0.entity.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NationaliteDto {

    private String code;
    private String libelle;
    private Role role;



}
