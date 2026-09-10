package org.example.controller.worker;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.Result.Result;
import org.example.service.WorkerGrabService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name="工人端抢单业务")
@RestController
@RequestMapping("/api/worker")
public class WorkerGrabController {

    @Resource
    private WorkerGrabService workerGrabService;

    /**
     * 工人抢单
     * @param id
     * @return
     */
    @Operation(summary = "工人抢单")
    @PutMapping("/orders/{id}/grab")
    public Result grabOrder(@PathVariable Long id) {
      workerGrabService.grabOrder(id);
        return Result.success();
    }
}
