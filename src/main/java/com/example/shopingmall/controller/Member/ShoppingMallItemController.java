package com.example.shopingmall.controller.Member;

import com.example.shopingmall.domain.Member;
import com.example.shopingmall.domain.Item;
import com.example.shopingmall.repository.ItemRepository;
import com.example.shopingmall.repository.ItemRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/items")
@RequiredArgsConstructor
public class ShoppingMallItemController {
    ItemRepository itemRepository = new ItemRepositoryImpl();
    @GetMapping
    public String ItemList(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        log.info("items ={}",items);
        return "item/itemList";
    }

    @GetMapping("/add")
    public String AddPage(@ModelAttribute Item item, Model model) {
        model.addAttribute("items", item);
        return "item/addItemForm";
    }

    @PostMapping("/add")
    public String AddItem(@ModelAttribute Item item, BindingResult bindingResult, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {
        if (!StringUtils.hasText(item.getItemName())){
            bindingResult.addError(new FieldError("items","itemName", "상품 이름이 올바르지 않습니다."));
        }
        if (item.getPrice()<0){
            bindingResult.addError(new FieldError("items","price", "상품 가격이 올바르지 않습니다."));
        }
        if (!StringUtils.hasText(item.getItemName())){
            bindingResult.addError(new FieldError("items","itemDescription", "상품 설명이 올바르지 않습니다."));
        }
        if (bindingResult.hasErrors()) {
            log.info("상품 추가 오류 발생 bindingResult = {}",bindingResult);
            return "item/addItemForm";
        }
        item.setSellerId(loginMember.getLoginId());
        item.setSellerName(loginMember.getName());
        itemRepository.save(item);
        log.info("item add ={}",item);
        return "redirect:/items";
    }

    @GetMapping("/{itemId}")
    public String ItemInformation(@PathVariable(name = "itemId") Long findItemId, Model model) {
        Item item = itemRepository.findById(findItemId);
        log.info("findItem = {}",item);
        model.addAttribute("item", item);
        return "item/item";
    }

    @PostMapping("/{itemId}")
    public String ItemDelete(@PathVariable(name = "itemId") Long findItemId, Model model,
                             @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {
        Item item = itemRepository.findById(findItemId);
        if(loginMember.getLoginId().equals(item.getSellerId())&&loginMember.getName().equals(item.getSellerName())&&loginMember!=null&&item!=null) {
            log.info("deleteItem = {}", item);
            itemRepository.delete(item.getItemId());
            return "redirect:/items";
        }
        else{
            model.addAttribute("item", item);
            model.addAttribute("deleteError","본인의 제품이 아닐경우 삭제하실 수 없습니다.");
            return "item/item";
        }
    }

}