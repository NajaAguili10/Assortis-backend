package com.backend.assorttis.security.services;

import com.backend.assorttis.entities.User;
import com.backend.assorttis.repository.UserRepository;
import com.backend.assorttis.repository.UserRoleRepository;
import com.backend.assorttis.repository.RolePermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final com.backend.assorttis.repository.OrganizationUserRepository organizationUserRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findOne((root, query, cb) -> cb.equal(root.get("email"), email))
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

        Set<GrantedAuthority> authorities = new HashSet<>();

        // Add Roles as Authorities (prefix with ROLE_)
        userRoleRepository.findAll((root, query, cb) -> cb.equal(root.get("user").get("id"), user.getId()))
                .forEach(userRole -> {
                    String roleCode = userRole.getRole().getCode();
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + roleCode));

                    // Add individual Permissions from this Role
                    rolePermissionRepository.findAll(
                            (root, query, cb) -> cb.equal(root.get("role").get("id"), userRole.getRole().getId()))
                            .forEach(rolePermission -> {
                                authorities.add(new SimpleGrantedAuthority(rolePermission.getPermission().getCode()));
                            });
                });

        Long organizationId = organizationUserRepository.findMembershipsByUserId(user.getId()).stream()
                .map(ou -> ou.getId().getOrganizationId())
                .findFirst()
                .orElse(null);

        return UserDetailsImpl.build(user, organizationId, authorities);
    }
}
