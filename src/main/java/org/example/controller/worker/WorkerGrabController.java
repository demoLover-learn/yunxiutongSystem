package org.example.controller.worker;


import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.Result.Result;
import org.example.service.WorkerGrabService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PutMapping("/orders/{id}/grab")
    public Result grabOrder(@PathVariable Long id) {
      workerGrabService.grabOrder(id);
        return Result.success();
    }
}
