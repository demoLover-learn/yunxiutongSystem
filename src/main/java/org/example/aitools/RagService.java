package org.example.aitools;


import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Slf4j
@Service
public class RagService {
    @Autowired
    private TokenTextSplitter textSplitter;
    @Autowired
    private VectorStore vectorStore;



    public int ragSave(MultipartFile file) {
        Resource resource = file.getResource();
        List<Document> docs = new TikaDocumentReader(resource).read();

        String source = file.getOriginalFilename();
        docs.forEach(d->d.getMetadata().put("source", source));

        List<Document> chunks = textSplitter.apply(docs);
        //同一份文档先删后插
        vectorStore.delete("source == '" + source + "'");
        //单批上传不超过25
        int batchSize = 25;
        for (int i = 0; i < chunks.size(); i += batchSize) {
            vectorStore.add(chunks.subList(i, Math.min(i + batchSize,
                    chunks.size())));
        }
        log.info("文档 {} 入库完成，共 {} 块", source,
                chunks.size());
        return chunks.size();}

    }
