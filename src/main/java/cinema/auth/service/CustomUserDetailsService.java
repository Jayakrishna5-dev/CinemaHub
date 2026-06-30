package cinema.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import cinema.exception.customException.ResourceNotFoundException;
import cinema.role.roleEntity.RoleEntity;
import cinema.user.repository.UserRepository;
import cinema.user.userEntity.UserEntity;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) {

        UserEntity user = repo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email : "+email));
        
        RoleEntity roleEnt = user.getRole();

        return new org.springframework.security.core.userdetails.User(
        		user.getEmail(),
        		user.getPassword(),
        		List.of(new SimpleGrantedAuthority(roleEnt.getName()))
        );
    }
}
