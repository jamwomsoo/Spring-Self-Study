package com.greglturnquist.hackingspringboot.reactive;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import reactor.core.publisher.Mono;

@Service
public class CartService {
	
	private final ItemRepository itemRepository;
	private final CartRepository cartRepository;
	
	CartService(ItemRepository itemRepository, CartRepository cartRepository){
		this.itemRepository = itemRepository;
		this.cartRepository = cartRepository;
	}
	
	
//	장바구니에 상품 추가

	Mono<Cart> addToCart(String cartId, String id){
		return this.cartRepository.findById(cartId)//
				   .log("founCart")
				   .defaultIfEmpty(new Cart(cartId))
				   .log("emptyCart")
				   .flatMap(cart -> cart.getCartItems().stream()
						   .filter(cartItem -> cartItem.getItem()//
								   .getId().equals(id))//
						   .findAny()//
						   .map(cartItem -> {
							   cartItem.increment();
							   return Mono.just(cart).log("nweCartItem");
						   }) //
						   .orElseGet(() -> { //
							   
							   return this.itemRepository.findById(id)//
							         .log("fetchedItem")
									 .map(CartItem::new)
									 .log("cartItem")
//									 .doOnNext(cartItem -> // 
//										   cart.getCartItems().add(cartItem))//
//									 .map(cartItem -> cart)))
									 .map(cartItem -> {
										 cart.getCartItems().add(cartItem);
										 return cart;
									 }).log("addedCartItem");
								}))
				   .log("cartwWithAnotherItem")
				   .flatMap(this.cartRepository::save)
				   .log("saveCart");
	}
}
