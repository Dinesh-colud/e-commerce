package com.ecommerce.project.controller;

import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.service.AddressService;
import com.ecommerce.project.util.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private AddressService addressService;

    @Tag(name = "Address APIs", description = "APIs to managing the address")
    @Operation(summary = "Create address", description = "APIs to create address")
    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO){
        User user = authUtil.loggedInUser();
        AddressDTO savedAddressDTO = addressService.createAddress(addressDTO, user);

        return new ResponseEntity<>(savedAddressDTO, HttpStatus.CREATED);
    }

    @Tag(name = "Address APIs", description = "APIs to managing the address")
    @Operation(summary = "Get all the addresses", description = "APIs to get all the addresses")
    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getAddresses(){
        List<AddressDTO> addressDTOSList = addressService.getAddresses();
        return new ResponseEntity<>(addressDTOSList, HttpStatus.OK);
    }

    @Tag(name = "Address APIs", description = "APIs to managing the address")
    @Operation(summary = "Get address by ID", description = "APIs to get address by Id")
    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> getAddressesById(@PathVariable Long addressId){
        AddressDTO addressDTO = addressService.getAddressesWithId(addressId);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }

    @Tag(name = "Address APIs", description = "APIs to managing the address")
    @Operation(summary = "Get user address", description = "Get address by user")
    @GetMapping("/users/addresses")
    public ResponseEntity<List<AddressDTO>> getUserAddresses(){
        User user = authUtil.loggedInUser();
        List<AddressDTO> addressDTOSList = addressService.getUserAddresses(user);
        return new ResponseEntity<>(addressDTOSList, HttpStatus.OK);
    }

    @Tag(name = "Address APIs", description = "APIs to managing the address")
    @Operation(summary = "Update the address", description = "APIs to update the address")
    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddresses(@PathVariable Long addressId,
                                                          @RequestBody AddressDTO addressDTO){
        AddressDTO updatedAddress = addressService.updateAddress(addressId, addressDTO);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }

    @Tag(name = "Address APIs", description = "APIs to managing the address")
    @Operation(summary = "Delete address", description = "APIs to delete the address")
    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long addressId){
        java.lang.String status = addressService.deleteAddress(addressId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
