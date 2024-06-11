package br.com.palutec.claro.pagadoria.model.repository;

import br.com.palutec.claro.pagadoria.model.SystemUser;
import br.com.palutec.core.db.JpaSpecificationRepository;

public interface SystemUserRepository extends JpaSpecificationRepository<SystemUser, String>{

	SystemUser findByUsernameOrMail(String usernameOrMail, String usernameOrMail2);


	
}
