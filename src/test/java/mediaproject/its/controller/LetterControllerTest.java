package mediaproject.its.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mediaproject.its.auth.CustomUserDetails;
import mediaproject.its.domain.dto.LetterDto;
import mediaproject.its.domain.dto.ProfileDto;
import mediaproject.its.domain.entity.Letter;
import mediaproject.its.domain.entity.User;
import mediaproject.its.domain.repository.LetterRepository;
import mediaproject.its.domain.repository.UserRepository;
import mediaproject.its.response.dto.CommonResponseDto;
import mediaproject.its.service.LetterService;
import mediaproject.its.util.AuthenticationHelper;
import mediaproject.its.util.MockTester;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LetterControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Mock
    UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    AuthenticationHelper authenticationHelper;

    @Mock
    private LetterService letterService; // Mock 객체 주입

    @Test
    @DisplayName("쪽지 생성 테스트")
    @MockTester
    void postLetterTest() throws Exception {

        // given
        int recipientId = 123;
        User loginUser = authenticationHelper.getCurrentUser();
        LetterDto.Request letterRequestDto = LetterDto.Request.builder()
                .content("테스트 쪽지 내용")
                .build();

        // when
        when(letterService.postLetter(any(LetterDto.Request.class), eq(recipientId), eq(loginUser.getUsername())))
                .thenReturn(new Letter());

        // then
        mockMvc.perform(post("/its/api/letter/{recipientId}", recipientId)
                        .content(objectMapper.writeValueAsString(letterRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("쪽지 단건 조회 테스트")
    @MockTester
    void getLetterByIdTest() throws Exception {

        // given
        int letterId = 123;
        User loginUser = authenticationHelper.getCurrentUser();

        // when
        when(letterService.getLetterById(eq(letterId), eq(loginUser.getUsername())))
                .thenReturn(new Letter()); // Mock 객체의 반환값 설정

        // then
        mockMvc.perform(get("/its/api/letter/{letterId}", letterId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());


    }
    @Test
    @DisplayName("쪽지 리스트 조회 테스트")
    @MockTester
    void getLetterListTest() throws Exception {

        // given
        User loginUser = authenticationHelper.getCurrentUser();
        List<Letter> letterList = new ArrayList<>();

        // when
        when(letterService.getLetterList(loginUser.getUsername())).thenReturn(letterList);

        // then
        mockMvc.perform(get("/its/api/letter")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("쪽지 소프트삭제 테스트")
    @MockTester
    void softDeleteLetterTest() throws Exception {

        // given
        User loginUser = authenticationHelper.getCurrentUser();
        int letterId = 123;

        // when
        when(letterService.softDeleteLetter(eq(letterId), eq(loginUser.getUsername()))).thenReturn(new Letter());

        mockMvc.perform(put("/its/api/letter/{letterId}", letterId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("쪽지 휴지통 테스트")
    @MockTester
    void getLetterTrashcanTest() throws Exception {

        // given
        User loginUser = authenticationHelper.getCurrentUser();
        List<Letter> trashcanList = new ArrayList<>();

        when(letterService.getSoftDeletedLetterList(loginUser.getUsername())).thenReturn(trashcanList);

        // when
        mockMvc.perform(get("/its/api/letter/trashcan")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("쪽지 하드삭제 테스트")
    @MockTester
    void hardDeleteLetterTest() throws Exception {

        // given
        User loginUser = authenticationHelper.getCurrentUser();
        int letterId = 123;

        // when
        when(letterService.deleteLetter(eq(letterId),eq(loginUser.getUsername()))).thenReturn(new Letter());

        // then
        mockMvc.perform(delete("/its/api/letter/{letterId}", letterId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
