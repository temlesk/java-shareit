package ru.practicum.shareit.user;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto create(UserDto userDto) {
        if (userDto.getEmail() == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        User user = UserMapper.toUser(userDto);
        user.setId(null);
        User saved = userRepository.save(user);
        return UserMapper.toUserDto(saved);
    }

    @Override
    public UserDto update(Long userId, UserDto userDto) {
        User existing = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (userDto.getName() != null) {
            existing.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            if (!existing.getEmail().equalsIgnoreCase(userDto.getEmail())) {
                if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
                    throw new IllegalArgumentException("Email already in use");
                }
                existing.setEmail(userDto.getEmail());
            }
        }
        User updated = userRepository.update(existing);
        return UserMapper.toUserDto(updated);
    }

    @Override
    public UserDto getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return UserMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}