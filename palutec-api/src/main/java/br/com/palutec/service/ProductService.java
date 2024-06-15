package br.com.palutec.service;

import org.springframework.stereotype.Service;

import br.com.palutec.core.service.AbstractCrudService;
import br.com.palutec.dto.ProductDTO;
import br.com.palutec.model.Product;
import br.com.palutec.model.repository.ProductRepository;

@Service
public class ProductService extends AbstractCrudService<ProductDTO, Product, ProductRepository> {
	
}
