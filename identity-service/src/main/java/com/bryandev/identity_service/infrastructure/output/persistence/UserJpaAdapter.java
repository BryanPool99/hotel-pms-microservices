package com.bryandev.identity_service.infrastructure.output.persistence;

import com.bryandev.identity_service.application.output.UserRepositoryPort;
import com.bryandev.identity_service.domain.model.User;
import com.bryandev.identity_service.infrastructure.output.persistence.mapper.UserPersistenceMapper;
import com.bryandev.identity_service.infrastructure.output.persistence.repository.SpringDataUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserJpaAdapter implements UserRepositoryPort {

    private final SpringDataUserRepository springDataUserRepository;
    private final UserPersistenceMapper userPersistenceMapper;

    @Override
    public User save(User user) {
        log.info("Inicio de método save en UserJpaAdapter");
        var entity = userPersistenceMapper.toEntity(user);
        var savedEntity = springDataUserRepository.save(entity);
        log.info("Usuario guardado exitosamente en la base de datos");
        return userPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        log.info("Inicio de método findByUserame con username: {}", username);
        return springDataUserRepository.findByUsername(username)
                .map(userPersistenceMapper::toDomain);
    }

    @Override
    public boolean existsByUsername(String username) {
        log.info("Inicio de método existsByUsername con username :{}", username);
        return springDataUserRepository.existsByUsername(username);
    }
}
