package mediaproject.its.controller;

import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.dto.CommentDto;
import mediaproject.its.domain.dto.PostDto;
import mediaproject.its.domain.dto.PostInterface;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.response.dto.CommonResponseDto;
import mediaproject.its.service.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/its/search")
    public CommonResponseDto<?> searcPostshWithTitle(@RequestParam(required = false) String title,
                                                     @RequestParam(required = false) String hiringType,
                                                     @RequestParam(required = false) String positionType,
                                                     @RequestParam(required = false) String processType,
                                                     @RequestParam(required = false) String recruitingType,
                                                     @RequestParam(required = false) String techStackType){

        List<Post> posts = searchService.searchPostsWithTitle(title,hiringType,positionType,processType,recruitingType,techStackType);
        List<PostDto.Response> postsResponseDto = new ArrayList<>();

        for(Post p : posts){
            PostDto.Response postsDto = PostDto.Response.builder()
                    .postId(p.getId())
                    .title(p.getTitle())
                    .content(p.getContent())
                    .username(p.getUser().getUsername())
                    .viewCount(p.getViewCount())
                    .likesCount(p.getLikesCount())
                    .comments(p.getComments().stream().map(CommentDto.Response::new).collect(Collectors.toList()))
                    .hiringType(p.getHiringType())
                    .positionType(p.getPositionType())
                    .processType(p.getProcessType())
                    .recruitingType(p.getRecruitingType())
                    .techStackType(p.getTechStackType())
                    .build();
            postsResponseDto.add(postsDto);
        }
        return CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("검색 성공")
                .data(postsResponseDto)
                .build();
    }

    @GetMapping("/its/search-v2")
    public CommonResponseDto<?> searcPostshWithTitle2(@RequestParam String title) {

        List<PostInterface> posts = searchService.searchPostsWithTitleV2(title);
        List<PostDto.InterfaceResponse> postsInterfaceResponseDto = PostController.extractPostInterfaceList(posts);

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("v2 검색 성공")
                .data(postsInterfaceResponseDto)
                .build();
    }

}
