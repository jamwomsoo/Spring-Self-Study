package com.greglturnquist.hackingspringboot.reactive;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.mongodb.core.ReactiveFluentMongoOperations;


import static org.springframework.data.mongodb.core.query.Criteria.byExample;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class InventoryService {

	private final ItemRepository itemRepository;
//	private final ItemExampleRepository itemExampleRepository;
//	private final ReactiveFluentMongoOperations fluentOperations;

	private final CartRepository cartRepository;
	//private String description;
	InventoryService(ItemRepository itemRepository, 
	         CartRepository cartRepository ){
	this.itemRepository        = itemRepository;
//	this.itemExampleRepository = null;
//	this.fluentOperations = null;
	this.cartRepository        = cartRepository;	
	}
//	
//	InventoryService(ItemRepository itemRepository, 
//			         ItemExampleRepository itemExampleRepository, 
//			         ReactiveFluentMongoOperations fluentOperations){
//		this.itemRepository        = itemRepository;
//		this.itemExampleRepository = itemExampleRepository;
//		this.fluentOperations      = fluentOperations;
//		this.cartRepository = null;	
//	}
//
////	Example 쿼리
//	Flux<Item> searchByExample(String name, String description,  boolean useAnd){
//		Item item = new Item(name, description, 0.0);
//		
//		ExampleMatcher matcher = (useAnd 
//				? ExampleMatcher.matchingAll() //
//				: ExampleMatcher.matchingAny()) //
//					.withStringMatcher(StringMatcher.CONTAINING)
//					.withIgnoreCase()
//					.withIgnorePaths("price");
//		Example<Item> probe = Example.of(item, matcher);
//		return itemExampleRepository.findAll(probe);
//	}
//	
////	평문형연산
//	Flux<Item> searchByFluentExample(String name, String description){
//		return fluentOperations.query(Item.class)//
//				.matching(query(where("TV tray").is(name).and("Smurf").is(description)))
//				.all();
//	}
//
//
////	 평문형 API를 사용한 Example 쿼리 검색 구현 코드
//	
//	Flux<Item> searchByFluentExample(String name, String description, boolean useAnd){
//		Item item = new Item(name, description, 0.0);
//		
//		ExampleMatcher matcher = (useAnd//
//				? ExampleMatcher.matchingAll()  //
//				: ExampleMatcher.matchingAny()) //
//				.withStringMatcher(StringMatcher.CONTAINING)
//				.withIgnoreCase()//
//				.withIgnorePaths("price");
//		
//		return fluentOperations.query(Item.class)
//				.matching(query(byExample(Example.of(item, matcher))))//
//				.all();
//				
//	}
//	
	
	public Mono<Cart> getCart(String cartId){
		return this.cartRepository.findById(cartId);
	}
	
	public Flux<Item> getInventory(){
		return this.itemRepository.findAll();
	}
	Mono<Item> saveItem(Item newItem){
		return this.itemRepository.save(newItem);
	}
	Mono<Void> deleteItem(String id){
		return this.itemRepository.deleteById(id);
	}

	Mono<Cart> addItemToCart(String cartId, String itemId){
		return this.cartRepository.findById(cartId)
				.defaultIfEmpty(new Cart(cartId))
				.flatMap(cart -> cart.getCartItems().stream()
						.filter(cartItem -> cartItem.getItem().getId().equals(itemId))
						.findAny()
						.map(cartItem -> {
							cartItem.increment();
							return Mono.just(cart);
						})
						.orElseGet(() -> {
							return this.itemRepository.findById(itemId)
									.map(item -> new CartItem(item))
									.map(cartItem ->{
										cart.getCartItems().add(cartItem);
										return cart;
									});
						}))
				.flatMap(cart -> this.cartRepository.save(cart));
	}
	
	

}
