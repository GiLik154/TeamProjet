package com.example.team_project.controller.core.product;

import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
import com.example.team_project.domain.domain.product.countduplication.domain.LikeCountCheck;
import com.example.team_project.domain.domain.product.countduplication.domain.LikeCountRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.ProductCategoryStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product/user/list")
public class ProductUserListController {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @GetMapping("")
    public String list(Pageable pageable, Model model) {

        int page = Math.max(pageable.getPageNumber(), 1);
        int limitPage = 8;
        Page<Product> list = productRepository.findAll(PageRequest.of(page - 1, limitPage, Sort.Direction.DESC, "id"));
        int totalPage = list.getTotalPages();

        model.addAttribute("productList", list);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("page", page);

        return "thymeleaf/product/productUserList";
    }

    @GetMapping("{categoryId}")
    public String categoryList(Pageable pageable, Model model, @PathVariable("categoryId") String categoryId) {

        int page = Math.max(pageable.getPageNumber(), 1);
        int limitPage = 8;

        productCategoryRepository.findByStatus(ProductCategoryStatus.valueOf(categoryId)).ifPresent(productCategory -> {

        Page<Product> list = productRepository.findByCategoryId(productCategory.getId(), PageRequest.of(page - 1, limitPage, Sort.Direction.DESC, "id"));
        int totalPage = list.getTotalPages();

        model.addAttribute("productList", list);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("page", page);
        });


        return "thymeleaf/product/categoryList";
    }
}
