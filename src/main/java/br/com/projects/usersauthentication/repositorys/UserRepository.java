package br.com.projects.usersauthentication.repositorys;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.projects.usersauthentication.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	@Query("{ 'login' : ?0 }")
	User findByLogin(String login);

}
