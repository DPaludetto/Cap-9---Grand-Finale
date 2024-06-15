package br.com.palutec.model.repository;

import br.com.palutec.core.db.JpaSpecificationRepository;
import br.com.palutec.model.SystemUser;

public interface SystemUserRepository extends JpaSpecificationRepository<SystemUser, String>{

	SystemUser findByUsernameOrMail(String usernameOrMail, String usernameOrMail2);


	
}
