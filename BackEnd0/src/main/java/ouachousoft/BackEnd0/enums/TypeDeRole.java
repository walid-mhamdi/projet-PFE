package ouachousoft.BackEnd0.enums;

import lombok.Getter;

@Getter
public enum TypeDeRole {
    ADMIN(1),
    EMPLOYEE(2);

    private final int id;

    TypeDeRole(int id) {
        this.id = id;
    }

}
