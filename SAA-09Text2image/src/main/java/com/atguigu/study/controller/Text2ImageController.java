package com.atguigu.study.controller;

import com.alibaba.cloud.ai.dashscope.audio.DashScopeSpeechSynthesisModel;
import com.alibaba.cloud.ai.dashscope.audio.DashScopeSpeechSynthesisOptions;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisModel;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisOptions;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisPrompt;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisResponse;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.UUID;


/**
 * @auther zzyybs@126.com
 * @create 2025-07-28 20:10
 * @Description 知识出处
 * https://help.aliyun.com/zh/model-studio/text-to-image?spm=a2c4g.11186623.help-menu-2400256.d_0_5_0.1a457d9dv6o7Kc&accounttraceid=6ec3bf09599f424a91a2a88b27b31570nrdd
 */
@RestController
public class Text2ImageController
{
    // img model
    public static final String IMAGE_MODEL = "wanx2.1-t2i-turbo";

    @Resource
    private ImageModel imageModel;


    /**
     * zzyybs@126.com
     * http://localhost:8009/t2i/image
     * @param prompt
     * @return
     */
    @GetMapping(value = "/t2i/image")
    public String image(@RequestParam(name = "prompt",defaultValue = "刺猬") String prompt)
    {
        return imageModel.call(
                    new ImagePrompt(prompt, DashScopeImageOptions.builder().withModel(IMAGE_MODEL).build())
                )
                .getResult()
                .getOutput()
                .getUrl();
    }
}
