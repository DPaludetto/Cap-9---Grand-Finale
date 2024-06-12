package br.com.palutec.service;

import org.springframework.stereotype.Service;

import br.com.palutec.dto.ProductDTO;
import br.com.palutec.model.Product;
import br.com.palutec.model.repository.ProductRepository;
import br.com.squada.core.service.AbstractCrudService;

@Service
public class ProductService extends AbstractCrudService<ProductDTO, Product, ProductRepository> {
	
}
