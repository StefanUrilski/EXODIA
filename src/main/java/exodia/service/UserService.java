package exodia.service;

import exodia.domain.model.service.UserServiceModel;

public interface UserService {

    boolean saveUser(UserServiceModel userServiceModel);

    UserServiceModel loginUser(UserServiceModel userServiceModel);
}
