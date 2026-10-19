package org.example.controller.ai;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.jsonwebtoken.io.IOException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.Result.Result;
import org.example.aitools.RagService;
import org.example.context.BaseContext;
import org.example.entity.Employee;
import org.example.mapper.AdminLoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@Tag(name = "管理员Rag知识库上传")
@RestController
@RequestMapping("/api/admin/knowledgeUpload")
public class AdminChatController {
    @Autowired
    private RagService ragService;
    @Autowired
    private AdminLoginMapper adminLoginMapper;

    @Operation(summary = "Rag知识库上传")
    @PostMapping("/upload")
    public Result<Integer> upload(@RequestParam MultipartFile file)
            throws IOException {
        Long adminId = BaseContext.getCurrentId();

       Employee i=adminLoginMapper.getById(adminId);
       if (i==null){
           throw new RuntimeException("无权上传文件");
       }

        return Result.success(ragService.ragSave(file));
    }
}
