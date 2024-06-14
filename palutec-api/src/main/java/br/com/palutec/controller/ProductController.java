package br.com.palutec.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.palutec.dto.ProductDTO;
import br.com.palutec.model.Product;
import br.com.palutec.service.ProductService;
import br.com.squada.core.service.AbstractCrudController;

@RestController
@RequestMapping(path = "/v1/product")
public class ProductController extends AbstractCrudController<ProductDTO, Product, ProductService>{

	
}
