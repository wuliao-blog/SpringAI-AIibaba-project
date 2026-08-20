package com.atguigu.study.controller;

import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingOptions;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @auther zzyybs@126.com
 * @create 2025-07-29 19:54
 * @Description TODO
 */
@RestController
@Slf4j
public class Embed2VectorController
{
    @Resource
    private EmbeddingModel embeddingModel;

    @Resource
    private VectorStore vectorStore;

    /**
     * 文本向量化
     * http://localhost:8011/text2embed?msg=射雕英雄传
     *
     * @param msg
     * @return
     */
    @GetMapping("/text2embed")
    public EmbeddingResponse text2Embed(String msg)
    {
        //EmbeddingResponse embeddingResponse = embeddingModel.call(new EmbeddingRequest(List.of(msg), null));

        EmbeddingResponse embeddingResponse = embeddingModel.call(new EmbeddingRequest(List.of(msg),
                DashScopeEmbeddingOptions.builder().withModel("text-embedding-v3").build()));

        System.out.println(Arrays.toString(embeddingResponse.getResult().getOutput()));

        return embeddingResponse;
    }


    /**
     * 文本向量化 后存入向量数据库RedisStack
     */
    @GetMapping("/embed2vector/add")
    public void add()
    {
        List<Document> documents = List.of(
                new Document("i study LLM"),
                new Document("i love java")
        );

        vectorStore.add(documents);
    }


    /**
     * 从向量数据库RedisStack查找，进行相似度查找
     * http://localhost:8011/embed2vector/get?msg=LLM
     * @param msg
     * @return
     */
    @GetMapping("/embed2vector/get")
    public List getAll(@RequestParam(name = "msg") String msg)
    {
        SearchRequest searchRequest = SearchRequest.builder()
                .query(msg)
                .topK(2)
                .build();

        List<Document> list = vectorStore.similaritySearch(searchRequest);

        System.out.println(list);

        return list;
    }
}
