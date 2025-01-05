package com.fastrender.SiteMapExtractor.Services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SiteMapService {
    public List<String> getUrlsFromSitemap(String sitemapUrl) throws IOException {
        List<String> urls = new ArrayList<>();
        Document doc = Jsoup.connect(sitemapUrl).get();
        for (Element urlElement : doc.select("url > loc")) {
            String url = urlElement.text();
            if (url.trim().endsWith(".xml")) {
                urls.addAll(getUrlsFromSitemap(url));
            } else {
                urls.add(url);
            }
        }
        return urls;
    }
}