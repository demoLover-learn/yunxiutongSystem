package org.example.controller.ai;


import org.example.Result.Result;
import org.example.context.BaseContext;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/ai")
public class UserChatController {


    @Autowired
    @Qualifier("userChatClient")
    private ChatClient chatClient;

    @GetMapping("/chat")
    public Result<String> chat(@RequestParam String message){
        Long userId = BaseContext.getCurrentId();
        String content = chatClient.prompt()
                .user(message)
                .advisors(a->a.param(ChatMemory.CONVERSATION_ID,"user:"+userId))
                .call()
                .content();
        return Result.success(content);
    }



}
