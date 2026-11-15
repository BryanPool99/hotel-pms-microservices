package com.bryandev.identity_service.infrastructure.output.persistence.mapper;

import com.bryandev.identity_service.domain.model.User;
import com.bryandev.identity_service.infrastructure.output.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceMapper {
    public UserEntity toEntity(User domain) {
        if (domain==null) return null;
        return UserEntity.builder()
                .id(domain.getId())
                .username(domain.getUsername())
                .password(domain.getPassword())
                .email(domain.getEmail())
                .role(domain.getRole())
                .build();
    }

    public User toDomain(UserEntity entity) {
        if (entity==null) return null;
        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getEmail(),
                entity.getRole()
        );
    }
}
