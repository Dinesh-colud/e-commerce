package com.ecommerce.project.controller;

import com.ecommerce.project.model.Cart;
import com.ecommerce.project.payload.CartDTO;
import com.ecommerce.project.repositories.CartItemRepository;
import com.ecommerce.project.repositories.CartRepository;
import com.ecommerce.project.service.CartService;
import com.ecommerce.project.util.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    AuthUtil authUtill;

    @Tag(name = "Cart APIs", description = "APIs to managing the cart")
    @Operation(summary = "Create cart", description = "APIs to create the cart")
    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId,
                                                    @PathVariable Integer quantity){
        CartDTO cartDTO = cartService.addProductToCart(productId, quantity);
        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.CREATED);
    }

    @Tag(name = "Cart APIs", description = "APIs to managing the cart")
    @Operation(summary = "Get all cart", description = "APIs to get all the cart")
    @GetMapping("/carts")
    public ResponseEntity<List<CartDTO>> getCarts(){

        List<CartDTO> cartDTOS = cartService.getAllCarts();

        return new ResponseEntity<>(cartDTOS, HttpStatus.OK);
    }

    @Tag(name = "Cart APIs", description = "APIs to managing the cart")
    @Operation(summary = "Get cart by email", description = "APIs to get cart by Email Id")
    @GetMapping("/carts/users/cart")
    public ResponseEntity<CartDTO> getCartById(){
        String emailId = authUtill.loggedInEmail();
        Cart cart = cartRepository.findCartByEmail(emailId);
        Long cartId = cart.getCartId();
       CartDTO cartDTO = cartService.getCart(emailId, cartId);
       return new ResponseEntity<CartDTO>(cartDTO,HttpStatus.OK);
    }

    @Tag(name = "Cart APIs", description = "APIs to managing the cart")
    @Operation(summary = "Update cart", description = "APIs to update the cart")
    @PutMapping("/cart/products/{productId}/quantity/{operation}")
    public ResponseEntity<CartDTO> updateCartProduct(@PathVariable Long productId,
                                                     @PathVariable String operation){
        CartDTO cartDTO = cartService.updateProductQuantityInCart(productId,
                operation.equalsIgnoreCase("delete") ? -1 : 1);

        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.OK);
    }

    @Tag(name = "Cart APIs", description = "APIs to managing the cart")
    @Operation(summary = "Delete cart", description = "APIs to delete the cart")
    @DeleteMapping("/carts/{cartId}/product/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId,
                                                        @PathVariable Long productId){
      String status = cartService.deleteProductFromCart(cartId, productId);

      return new ResponseEntity<String>(status, HttpStatus.OK);
    }

}
