package br.com.palutec.service;

import org.springframework.stereotype.Service;

import br.com.palutec.dto.SystemUserDTO;
import br.com.palutec.model.SystemUser;
import br.com.palutec.model.SystemUser.UserStatus;
import br.com.palutec.model.repository.SystemUserRepository;
import br.com.squada.core.exception.BusinessException;
import br.com.squada.core.service.AbstractCrudService;
import br.com.squada.core.util.UtilString;

@Service
public class SystemUserService extends AbstractCrudService<SystemUserDTO, SystemUser, SystemUserRepository> {

	
	public void toggleStatus(String id) {
		SystemUser user = findModelById(id);
		user.setStatus(user.getStatus() == UserStatus.BLOCKED ? UserStatus.ACTIVE : UserStatus.BLOCKED);
		getRepository().save(user);
	}
	
	public SystemUser getUserByAuthentication(String usernameOrMail, String password) {
		
		SystemUser user = getRepository().findByUsernameOrMail(usernameOrMail, usernameOrMail);
		
		if(user == null || !UtilString.isEquals(password, user.getPassword())) {
			throw new BusinessException("Usuário ou senha inválidos.");
		}

		
//		if(!passwordEncoder.matches(password, user.getPassword())) {
//			throw new BusinessException("Usuário ou senha inválidos.");
//		}
		
		return user;
	}
	
}
