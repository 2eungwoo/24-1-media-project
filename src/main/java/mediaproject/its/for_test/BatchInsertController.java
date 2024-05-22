package mediaproject.its.for_test;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BatchInsertController {
    private final BatchInsertService batchInsertService;

    @PostMapping("/its/test/users")
    public String makeUsers(){
        batchInsertService.makeUsers();
        return "success";
    }

    @PostMapping("/its/test/posts")
    public String makePosts(){
        batchInsertService.makePosts();
        return "success";
    }

    @PostMapping("/its/test/post-contents")
    public String makePostContents(){
        batchInsertService.makePostContents();
        return "success";
    }

}
