package ir.maktabSharif.repository;

import ir.maktabSharif.Exception.GenerallyNotFoundException;
import ir.maktabSharif.model.User;

public interface UserRepository extends BaseRepository<User> {
    User findUserByUsernameAndPassword(String username, String password);


}
