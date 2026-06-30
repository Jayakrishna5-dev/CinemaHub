package cinema.empInvitation.service;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cinema.empInvitation.repository.EmpInvitationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CleanupService {
	private final EmpInvitationRepository empInvRepo;
	
	@Transactional
	@Scheduled(fixedRate = 60000)
    public void removeExpiredSecrets() {
		empInvRepo.deleteByExpiresAtBefore(LocalDateTime.now());
    }
}