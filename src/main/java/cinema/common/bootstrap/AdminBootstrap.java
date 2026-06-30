package cinema.common.bootstrap;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import cinema.role.repository.RoleRepository;
import cinema.role.roleEntity.RoleEntity;
import cinema.user.repository.UserRepository;
import cinema.user.userEntity.UserEntity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminBootstrap {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {

        if(userRepo.findByEmail("admin@movieverse.com").isEmpty()) {

            UserEntity admin = new UserEntity();

            admin.setName("superadmin");
            admin.setEmail("admin@movieverse.com");
            admin.setPassword(
                passwordEncoder.encode("Admin@123")
            );

            RoleEntity role = new RoleEntity();
            role.setCreatedAt(LocalDateTime.now());
            role.setCreatedBy("SUPER_ADMIN");
            role.setName("ROLE_SUPER_ADMIN");
            role.setUpdatedAt(LocalDateTime.now());
            role.setUpdatedBy("SUPER_ADMIN");
            
            roleRepo.save(role);
            
            admin.setCreatedAt(LocalDateTime.now());
            admin.setCreatedBy("SUPER_ADMIN");
            admin.setRole(role);
            admin.setUpdatedAt(LocalDateTime.now());
            admin.setUpdatedBy("SUPER_ADMIN");

            userRepo.save(admin);
        }
    }
}