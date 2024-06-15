package br.com.palutec.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.palutec.core.service.AbstractCrudController;
import br.com.palutec.core.service.PageDTO;
import br.com.palutec.dto.SystemUserDTO;
import br.com.palutec.model.SystemUser;
import br.com.palutec.service.SystemUserService;

@RestController
@RequestMapping(path = "/v1/user")
public class SystemUserController extends AbstractCrudController<SystemUserDTO, SystemUser, SystemUserService>{

	@PutMapping("toggle-status/{id}")
	public void toggleStatus(@PathVariable("id") String id) {
		getService().toggleStatus(id);
	}
	
	@GetMapping
	@Override
	public PageDTO<?> getByFilterPageable(SystemUserDTO filter, @SortDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
		return super.getByFilterPageable(filter, pageable);
	}
	
}
