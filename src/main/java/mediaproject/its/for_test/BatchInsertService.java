package mediaproject.its.for_test;

import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.entity.Letter;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.entity.PostContent;
import mediaproject.its.domain.entity.User;
import mediaproject.its.domain.repository.PostRepository;
import mediaproject.its.domain.repository.UserRepository;
import mediaproject.its.for_test.util.MakeRandomValues;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BatchInsertService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final BatchInsertRepository batchInsertRepository;
    private final static int USERS_COUNT = 100000;
    private final static int POSTS_COUNT = 20;
    private final static int LETTERS_COUNT = 100000;

    @Transactional
    public void makeUsers(){
        List<User> users = new ArrayList<>();
        for (int i = 0; i < USERS_COUNT; i++) {
            User user = User.builder()
                    .username("user"+i)
                    .password("1234")
                    .role("ROLE_USER")
                    .description("description")
                    .email("user@email.com")
                    .followingsCount(0)
                    .followersCount(0)
                    .activeStatus(Math.random() < 0.8)
                    .build();
            users.add(user);
        }
        batchInsertRepository.saveAllUsers(users);
    }

    @Transactional
    public void makePosts(){
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < POSTS_COUNT; i++) {
            int randomUserId = MakeRandomValues.getRandomNumber(1, 30000); // 1부터 사용자 총 수까지의 랜덤한 ID 선택
            User fkUser = userRepository.findById(randomUserId).orElseThrow(() -> new RuntimeException("User not found"));

            Post post = Post.builder()
                    .title(MakeRandomValues.getRandomString())
                    .user(fkUser)
                    .viewCount(MakeRandomValues.getRandomNumber(0,30000))
                    .likesCount(MakeRandomValues.getRandomNumber(0,30000))
                    .commentCount(MakeRandomValues.getRandomNumber(0,30))
                    .hiringType(MakeRandomValues.getRandomHiringType())
                    .positionType(MakeRandomValues.getRandomPositionType())
                    .processType(MakeRandomValues.getRandomProcessType())
                    .recruitingType(MakeRandomValues.getRandomRecruitingType())
                    .techStackType(MakeRandomValues.getRandomTechStackType())
                    .comments(new ArrayList<>())
                    .createdAt(LocalDateTime.now().plusNanos(i))
                    .build();
            posts.add(post);
        }
        batchInsertRepository.saveAllPosts(posts);
    }

    @Transactional
    public void makePostContents(){
        List<PostContent> postContents = new ArrayList<>();

        int randomPostId = MakeRandomValues.getRandomNumber(1, 500000);
        Post fkPost = postRepository.findById(randomPostId).orElseThrow(() -> new RuntimeException("Post not found"));

        for (int i = 0; i < POSTS_COUNT; i++) {
            PostContent postContent = PostContent.builder()
                    .postId(fkPost.getId())
                    .content(MakeRandomValues.getRandomString()+MakeRandomValues.getRandomString()+MakeRandomValues.getRandomString())
                    .build();
            postContents.add(postContent);
        }
        batchInsertRepository.saveAllPostContents(postContents);
    }

    @Transactional
    public void makeLetter(){
        List<Letter> letters = new ArrayList<>();

        for (int i = 0; i < LETTERS_COUNT; i++) {
            Letter letter = Letter.builder()
                    .content(MakeRandomValues.getRandomString())
                    .sender("user"+MakeRandomValues.getRandomNumber(1,3000))
                    .recipient("user"+MakeRandomValues.getRandomNumber(1,3000))
                    .readStatus(Math.random() < 0.4)
                    .activeStatus(Math.random() < 0.8)
                    .createdAt(LocalDateTime.now().plusNanos(i))
                    .build();
            letters.add(letter);
        }
        batchInsertRepository.saveAllLetters(letters);
    }


}
