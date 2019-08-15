package exodia.service;

import exodia.domain.model.service.UserServiceModel;
import exodia.domain.entity.User;
import exodia.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper,
                           UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public boolean saveUser(UserServiceModel userServiceModel) {
        try {
            userServiceModel.setPassword(DigestUtils.sha256Hex(userServiceModel.getPassword()));
            userRepository.save(modelMapper.map(userServiceModel, User.class));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public UserServiceModel loginUser(UserServiceModel userServiceModel) {
        try {
            String password = DigestUtils.sha256Hex(userServiceModel.getPassword());
            User user = userRepository.findByUsernameAndPassword(userServiceModel.getUsername(), password);
            return modelMapper.map(user, UserServiceModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
