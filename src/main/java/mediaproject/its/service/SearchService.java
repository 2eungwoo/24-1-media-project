package mediaproject.its.service;

import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.dto.PostInterface;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.repository.SearchRepository;
import mediaproject.its.domain.repository.SearchRepositoryCustom;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

//    private final SearchRepository searchRepository;
    private final SearchRepositoryCustom searchRepositoryCustom;

//    @Transactional(readOnly = true)
//    public List<PostInterface> searchPostsWithTitle(String title){
//        return searchRepository.findAllPostBySearch(title);
//    }

    @Transactional(readOnly = true)
    public List<Post> searchPostsWithTitle(String title){
        return searchRepositoryCustom.findByTitle(title);
    }
}

