package com.fastrender.SiteMapExtractor.Controllers;

import com.fastrender.SiteMapExtractor.Dto.WebsiteData;
import com.fastrender.SiteMapExtractor.Services.MessageBrokerService;
import com.fastrender.SiteMapExtractor.Services.SiteMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/sitemap")
public class SiteMapExtractorController {

    @Autowired
    private MessageBrokerService messageBrokerService;

    @Autowired
    private SiteMapService siteMapService;

    @PostMapping("/crawl")
    public String extractAndPublishToMessageBroker(@RequestParam String queueName,
                                                   @RequestParam String sitemapUrl,
                                                   @RequestParam String websiteDomain,
                                                   @RequestParam String expirationTime)
    {
        try {
            messageBrokerService.createQueue(queueName);
            siteMapService.getUrlsFromSitemap(sitemapUrl).forEach(url -> {
                WebsiteData websiteData = new WebsiteData(url, websiteDomain, expirationTime);
                messageBrokerService.sendMessage(queueName, websiteData);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "Extracted and published";
    }
}