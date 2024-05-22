package ouachousoft.BackEnd0.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ouachousoft.BackEnd0.entity.Role;
import ouachousoft.BackEnd0.TypeDeRole;

public interface RoleRepository extends JpaRepository<Role, TypeDeRole> {
}
