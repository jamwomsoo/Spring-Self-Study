package com.greglturnquist.hackingspringboot.reactive;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;

import reactor.core.publisher.Mono;

@Controller
//템플릿 뷰를 제공하는 스프링 웹 컨트롤러임
public class HomeController {
	
	private ItemRepository   itemRepository;
	private CartRepository   cartRepository;
	
	private CartService      cartService;
	
	private InventoryService inventoryService;
	
	public HomeController(ItemRepository itemRepository, CartRepository cartRepository, CartService cartService) {
		this.itemRepository = itemRepository;
		this.cartRepository = cartRepository;
		this.cartService    = cartService;
	}
	
	@GetMapping
	Mono<Rendering> home(){
		return Mono.just(Rendering.view("home.html")
				.modelAttribute("items", //
						this.itemRepository.findAll().doOnNext(System.out::println))
				.modelAttribute("cart",//
						this.cartRepository.findById("My Cart")
						    .defaultIfEmpty(new Cart("My Cart")))
				.build());
	}
	
	@PostMapping("/add/{id}")
	Mono<String> addToCart(@PathVariable String id){
		return this.cartService.addToCart("My Cart", id)//
				   .thenReturn("redirect:/");
	}
	
//	
//	@GetMapping("/search")
//	Mono<Rendering> search(
//			@RequestParam(required = false) String name,
//			@RequestParam(required = false) String description,
//			@RequestParam boolean useAnd){
//		return Mono.just(Rendering.view("home.html")
//				.modelAttribute("items",
//						inventoryService.searchByExample(name, description, useAnd))
//				.modelAttribute("cart",
//						this.cartRepository.findById("My Cart")
//						.defaultIfEmpty(new Cart("My Cart")))
//				 .build());
//	}
}
