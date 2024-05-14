package mediaproject.its.service;

import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.dto.PostDto;
import mediaproject.its.domain.dto.PostInterface;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.repository.SearchRepository;
import mediaproject.its.domain.repository.SearchRepositoryCustom;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepositoryCustom searchRepositoryCustom;

    @Transactional(readOnly = true)
    @Cacheable(value = "search_posts", key = "'search_' + #title")
    public List<PostDto.Response> searchPostsWithFiltering(String title, String hiringType, String positionType, String processType, String recruitingType, String techStackType){
        return searchRepositoryCustom.findByFiltering(title,hiringType,positionType,processType,recruitingType,techStackType);
    }
}

