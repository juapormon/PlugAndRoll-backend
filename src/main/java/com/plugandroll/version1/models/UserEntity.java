package com.plugandroll.version1.models;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Document(collection="UserEntity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity extends BaseEntity implements UserDetails {

    private static final long serialVersionUID = 88749523013034397L;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @Email
    @NotBlank
    private String email;

    @NotNull
    private Boolean isPremium;

    @NotEmpty
    private Set<TypeRol> roles;

    @Range(min = 0, max = 5)
    private Double rating;

    private Integer CoachedGames;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return roles.stream().map(ur -> new SimpleGrantedAuthority("ROLE_"+ur.name())).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }
    public Boolean hasAuthority(String aut) {
        return this.getAuthorities().stream().anyMatch(a -> a.equals(new SimpleGrantedAuthority(aut)));
    }
}
