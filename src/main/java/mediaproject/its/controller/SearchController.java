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

        List<PostDto.Response> postsResponseDto = searchService.searchPostsWithTitle(title,hiringType,positionType,processType,recruitingType,techStackType);

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("검색 성공")
                .data(postsResponseDto)
                .build();
    }
}
