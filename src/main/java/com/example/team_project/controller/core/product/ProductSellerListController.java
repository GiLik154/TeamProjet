package com.example.team_project.controller.core.product;

import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product/seller/list")
public class ProductSellerListController {

    private final ProductRepository productRepository;

    @GetMapping("")
    public String list(@SessionAttribute("sellerId") Long sellerId, Pageable pageable, Model model) {

        int page = Math.max(pageable.getPageNumber(), 1);
        int limitPage = 8;

        Page<Product> list = productRepository.findBySellerId(sellerId, PageRequest.of(page - 1, limitPage, Sort.Direction.DESC, "id"));
        model.addAttribute("productList", list);

        int totalPage = list.getTotalPages();
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("page", page);

        return "thymeleaf/product/productSellerList";

    }

    @GetMapping("{categoryId}")
    public String categoryList(@SessionAttribute("sellerId") Long sellerId, @PathVariable("categoryId") Long categoryId, Pageable pageable, Model model) {

        int page = Math.max(pageable.getPageNumber(), 1);
        int limitPage = 8;

        Page<Product> list = productRepository.findBySellerIdAndCategoryId(sellerId, categoryId, PageRequest.of(page - 1, limitPage, Sort.Direction.DESC, "id"));
        model.addAttribute("productList", list);

        int totalPage = list.getTotalPages();
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("page", page);

        return "thymeleaf/product/productSellerCategoryList";
    }
}
