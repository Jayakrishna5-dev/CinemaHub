package cinema.role.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cinema.role.service.RoleService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/role")
@RequiredArgsConstructor
public class RoleController {
	private final RoleService roleService;
	
	@GetMapping("/{rolename}")
	private String addRoleData(@PathVariable String rolename) {
		return roleService.saveRoleData(rolename);
	}
}
