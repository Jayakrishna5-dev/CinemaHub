package cinema.empInvitation.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cinema.empInvitation.entity.EmpInvitationEntity;

@Repository
public interface EmpInvitationRepository extends JpaRepository<EmpInvitationEntity, Long> {
	Optional<EmpInvitationEntity> findByEmail(String email);
	
	Optional<EmpInvitationEntity> findByInvitationCode(String code);

	void deleteByExpiresAtBefore(LocalDateTime now);
}
