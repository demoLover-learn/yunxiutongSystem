package org.example.configration;


import org.example.aitools.UserOrderTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {


    //回忆，工具调用，文本块切割，向量数据库vectorStore,

    /**
     * 会话记忆上下文设置
     * @param chatMemoryRepository
     * @return
     */
    @Bean
    public ChatMemory chatMemory(ChatMemoryRepository chatMemoryRepository){
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(20)
                .build();
    }


    /**
     * 文本块切割
     * @return
     */
    @Bean
    public TokenTextSplitter tokenTextSplitter(){
        return TokenTextSplitter.builder()
                .withChunkSize(200)
                .withMinChunkSizeChars(100)
                .build();
    }

    /**
     * ChatClient构建
     * @param builder
     * @return
     */
    @Bean
    public ChatClient userChatClient(ChatClient.Builder builder,
                                     ChatMemory chatMemory,
                                     UserOrderTools userOrderTools){
        return builder
                .defaultTools(userOrderTools)
                .defaultSystem("你是云修通的用户客服助手。\n" +
                        "                    你只能查询当前登录用户本人的订单，不要尝试查询其他用户。\n" +
                        "                    用户问订单状态时，调用工具查真实数据，不要编造。结果以json的格式返回")
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
                .build();
    }
}
