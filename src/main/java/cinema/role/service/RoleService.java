package cinema.role.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import cinema.role.repository.RoleRepository;
import cinema.role.roleEntity.RoleEntity;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
	private final RoleRepository roleRepo;

	public String saveRoleData(String rolename) {
		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setName("ROLE_" + rolename.toUpperCase());
		roleEntity.setCreatedAt(LocalDateTime.now());
		roleEntity.setCreatedBy(rolename.toUpperCase());
		roleEntity.setUpdatedAt(LocalDateTime.now());
		roleEntity.setUpdatedBy(rolename.toUpperCase());
		roleRepo.save(roleEntity);
		return rolename + " Role Added into Database";
	}

}
