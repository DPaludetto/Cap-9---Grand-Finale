package br.com.palutec.model.repository;

import br.com.palutec.model.SystemUser;
import br.com.squada.core.db.JpaSpecificationRepository;

public interface SystemUserRepository extends JpaSpecificationRepository<SystemUser, String>{

	SystemUser findByUsernameOrMail(String usernameOrMail, String usernameOrMail2);


	
}
