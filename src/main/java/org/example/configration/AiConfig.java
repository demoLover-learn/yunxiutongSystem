package org.example.configration;



import org.example.aitools.UserOrderTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
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
                                     UserOrderTools userOrderTools,
                                     VectorStore vectorStore){
        return builder
                .defaultTools(userOrderTools)
                .defaultSystem("你是云修通上门服务平台的客服助手。\n" +
                        "\n" +
                        "          你可以做两件事：\n" +
                        "          1. 回答平台规则类问题（服务内容、收费标准、下单流程、工单状态、派单与抢单、取消规则、评价规则）—— 依据知识库内容回答。\n" +
                        "          2. 查询当前登录用户本人的工单 —— 必须调用工具查真实数据，不要编造。\n" +
                        "\n" +
                        "          规则：\n" +
                        "          - 只能查询当前登录用户本人的工单，绝不要尝试查询其他用户。\n" +
                        "          - 知识库里没有的内容，直接回答\"知识库中没有相关内容\"，不要编造。\n" +
                        "          - 给用户的回答用自然语言，不要输出 json 代码块。")
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        QuestionAnswerAdvisor.builder(vectorStore).searchRequest(SearchRequest.builder()
                                .topK(10)
                                .similarityThreshold(0.5)
                                .build()).build(),
                        MessageChatMemoryAdvisor.builder(chatMemory).build()

                )


                .build();
    }
}
