package com.springboot.securitytest.controllers;

import com.springboot.securitytest.dto.ProductDto;
import com.springboot.securitytest.dto.ProductResponseDto;
import com.springboot.securitytest.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/products")
public class ProductController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{number}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long number) {
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[getProduct] request Data :: productId : {}", number);
        ProductResponseDto productResponseDto = productService.getProduct(number);

        LOGGER.info(
                "[getProduct] response Data :: productId : {}, productName : {}, productPrice : {}, productStock : {}",
                productResponseDto.getNumber(), productResponseDto.getName(),
                productResponseDto.getPrice(), productResponseDto.getStock());
        LOGGER.info("[getProduct] Response Time : {}ms", System.currentTimeMillis() - currentTime);
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDto);
    }

//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
//    })
//    @PostMapping()
//    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductDto productDto) {
//        long currentTime = System.currentTimeMillis();
//        ProductResponseDto productResponseDto = productService.saveProduct(productDto);
//
//        LOGGER.info("[createProduct] Response Time : {}ms", System.currentTimeMillis() - currentTime);
//        return ResponseEntity.status(HttpStatus.OK).body(productResponseDto);
//    }
//
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
//    })
//    @PutMapping()
//    public ResponseEntity<ProductResponseDto> changeProductName(
//            @RequestBody ChangeProductNameDto changeProductNameDto) throws Exception {
//        long currentTime = System.currentTimeMillis();
//        LOGGER.info("[changeProductName] request Data :: productNumber : {}, productName : {}",
//                changeProductNameDto.getNumber(), changeProductNameDto.getName());
//
//        ProductResponseDto productResponseDto = productService.changeProductName(
//                changeProductNameDto.getNumber(),
//                changeProductNameDto.getName());
//
//        LOGGER.info(
//                "[changeProductName] response Data :: productNumber : {}, productName : {}, productPrice : {}, productStock : {}",
//                productResponseDto.getNumber(), productResponseDto.getName(),
//                productResponseDto.getPrice(), productResponseDto.getStock());
//        LOGGER.info("[changeProductName] response Time : {}ms",
//                System.currentTimeMillis() - currentTime);
//        return ResponseEntity.status(HttpStatus.OK).body(productResponseDto);
//    }
//
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token",
//                    required = true, dataType = "String", paramType = "header")
//    })
//    @DeleteMapping()
//    public ResponseEntity<String> deleteProduct(Long number) throws Exception {
//        long currentTime = System.currentTimeMillis();
//        LOGGER.info("[deleteProduct] request Data :: productNumber : {}", number);
//
//        productService.deleteProduct(number);
//
//        LOGGER.info("[deleteProduct] response Time : {}ms",
//                System.currentTimeMillis() - currentTime);
//        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
//    }

}