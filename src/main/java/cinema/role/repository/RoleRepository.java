package cinema.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cinema.role.roleEntity.RoleEntity;


@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long>{

}
