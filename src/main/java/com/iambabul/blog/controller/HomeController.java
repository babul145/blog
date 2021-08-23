package com.iambabul.blog.controller;

import com.iambabul.blog.entity.TitleSubtitle;
import com.iambabul.blog.service.TitleSubtitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    private final TitleSubtitleService titleSubtitleService;

    @Autowired
    public HomeController(TitleSubtitleService titleSubtitleService) {
        this.titleSubtitleService = titleSubtitleService;
    }

    @GetMapping("title-subtitle")
    public Map<String, String> getTitleSubtitle() {
        Map<String, String> titleSubtitleMap = new HashMap<>();
        TitleSubtitle titleSubtitle = titleSubtitleService.getTitleSubtitle();
        titleSubtitleMap.put("title", titleSubtitle.getTitle());
        titleSubtitleMap.put("subtitle", titleSubtitle.getSubtitle());
        return titleSubtitleMap;
    }
}
