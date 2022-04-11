package com.company.stockchecker.service;

import com.company.stockchecker.entity.User;
import com.company.stockchecker.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements CrudService<User>{

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getByChatId(Long chatId){
        return userRepository.findByChatId(chatId)
                .orElseThrow(() -> new EntityNotFoundException("User with chatId " + chatId + "not found !"));
    }

    @Override
    public Long create(User user) {

        user = Optional.ofNullable(userRepository.save(user))
                .orElseThrow(() -> new RuntimeException("Was not possible to save entity"));

        return user.getId();
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        return Optional.ofNullable(userRepository.getById(id))
                .orElseThrow(() -> new EntityNotFoundException("User with id "+ id +" not found !!!"));
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id))
            throw new EntityNotFoundException("User not found with id = " + id);

        userRepository.deleteById(id);
    }
}
