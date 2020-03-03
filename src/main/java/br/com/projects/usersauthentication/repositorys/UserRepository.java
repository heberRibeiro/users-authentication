package br.com.projects.usersauthentication.repositorys;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.projects.usersauthentication.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{

}
