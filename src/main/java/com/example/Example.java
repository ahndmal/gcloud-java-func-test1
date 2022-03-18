package com.example;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import java.io.BufferedWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.slf4j.LoggerFactory;

public class Example implements HttpFunction {
  @Override
  public void service(HttpRequest request, HttpResponse response) throws Exception {
    var LOG = LoggerFactory.getLogger(Example.class);
    var gson = new GsonBuilder().create();

//    ScheduledExecutorService service = Executors.newScheduledThreadPool(3);
//    service.scheduleAtFixedRate(() -> {
//      LOG.info("Test logging");
//    }, 0L, 10L, TimeUnit.SECONDS);

    var doc = Jsoup.connect("https://example.com/").get();
    Elements paragraphs = doc.select("p");
    var firstPara = paragraphs.first();
    var html = firstPara.html();
    BufferedWriter writer = response.getWriter();
    writer.write(html);
    writer.write(gson.toJson(new Cat("Murz", 7).toString()));
  }
}
