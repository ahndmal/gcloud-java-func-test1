package com.example;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import java.io.BufferedWriter;

import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

public class Example implements HttpFunction {
  @Override
  public void service(HttpRequest request, HttpResponse response) throws Exception {
    var gson = new GsonBuilder().create();

    var doc = Jsoup.connect("https://example.com/").get();
    Elements paragraphs = doc.select("p");
    var firstPara = paragraphs.first();
    var html = firstPara.html();
    BufferedWriter writer = response.getWriter();
    writer.write(html);
    writer.write(gson.toJson(new Cat("", 7).toString()));
  }
}
