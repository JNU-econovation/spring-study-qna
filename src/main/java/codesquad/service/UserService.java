package codesquad.service;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.exception.UserFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserById(Long id) {
        Optional<User> maybeUser = userRepository.findById(id);
        if (!maybeUser.isPresent())
            throw new UserFailureException();
        return maybeUser.get();
    }

    public void create(User user) {
        userRepository.save(user);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public void update(Long id, User sessionUser, User updateUser) {
        User user = findUserById(id);
        user.update(updateUser, sessionUser);
        userRepository.save(user);
    }

    public User login(String userId, String password) {
        Optional<User> maybeUser = userRepository.findByUserIdAndPassword(userId, password);
        if (!maybeUser.isPresent())
            throw new UserFailureException();
        return maybeUser.get();
    }
}
