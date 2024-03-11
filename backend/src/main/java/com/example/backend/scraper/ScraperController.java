package com.example.backend.scraper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scraper")
public class ScraperController {
    @Autowired ScraperService scraperService;
    @PostMapping()
    public void createScrappingJob() {
        scraperService.createScrappingJob();
    }
}
