package br.com.palutec.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.palutec.core.service.AbstractCrudController;
import br.com.palutec.dto.ProductDTO;
import br.com.palutec.model.Product;
import br.com.palutec.service.ProductService;

@RestController
@RequestMapping(path = "/v1/products")
public class ProductController extends AbstractCrudController<ProductDTO, Product, ProductService>{

	
}
