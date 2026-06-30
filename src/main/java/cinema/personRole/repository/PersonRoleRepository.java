package cinema.personRole.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cinema.personRole.entity.PersonRoleEntity;

@Repository
public interface PersonRoleRepository extends JpaRepository<PersonRoleEntity, Long> {

	boolean existsByPersonRoleName(String personRoleName);

}
