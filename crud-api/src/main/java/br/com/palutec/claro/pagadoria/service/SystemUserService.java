package br.com.palutec.claro.pagadoria.service;

import org.springframework.stereotype.Service;

import br.com.palutec.claro.pagadoria.dto.SystemUserDTO;
import br.com.palutec.claro.pagadoria.model.SystemUser;
import br.com.palutec.claro.pagadoria.model.SystemUser.UserStatus;
import br.com.palutec.claro.pagadoria.model.repository.SystemUserRepository;
import br.com.palutec.core.exception.BusinessException;
import br.com.palutec.core.service.AbstractCrudService;

@Service
public class SystemUserService extends AbstractCrudService<SystemUserDTO, SystemUser, SystemUserRepository> {

	
	public void toggleStatus(String id) {
		SystemUser user = findModelById(id);
		user.setStatus(user.getStatus() == UserStatus.BLOCKED ? UserStatus.ACTIVE : UserStatus.BLOCKED);
		getRepository().save(user);
	}
	
	public SystemUser getUserByAuthentication(String usernameOrMail, String password) {
		
		SystemUser user = getRepository().findByUsernameOrMail(usernameOrMail, usernameOrMail);
		
		if(user == null || !user.getPassword().equals(user.getPassword())) {
			throw new BusinessException("Usuário ou senha inválidos.");
		}

		
//		if(!passwordEncoder.matches(password, user.getPassword())) {
//			throw new BusinessException("Usuário ou senha inválidos.");
//		}
		
		return user;
	}
	
}
