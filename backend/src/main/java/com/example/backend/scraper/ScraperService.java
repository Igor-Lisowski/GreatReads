package com.example.backend.scraper;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ScraperService {
    public void createScrappingJob() {
//        Thread thread = new Thread(this::scrape);
//        thread.start();
        this.scrape();
    }

    private void scrape() {
        try {
            // fetching the target website
            var doc = Jsoup.connect("https://www.goodreads.com").get();
            doc.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
