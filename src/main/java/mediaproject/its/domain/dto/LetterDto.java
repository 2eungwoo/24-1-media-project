package mediaproject.its.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mediaproject.its.domain.entity.Letter;
import mediaproject.its.domain.entity.User;

import java.time.LocalDateTime;

public class LetterDto {

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class Request{
        private int letterId;
        private String content;
        private String sender;
        private String recipient;
        private Boolean readStatus;
        private Boolean activeStatus;
        private LocalDateTime createdAt;

        public Letter toEntity(){
            return Letter.builder()
                    .id(letterId)
                    .content(content)
                    .sender(sender)
                    .recipient(recipient)
                    .readStatus(false)
                    .activeStatus(true)
                    .createdAt(createdAt)
                    .build();
        }
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class Response{
        private int letterId;
        private String sender;
        private String recipient;
        private String content;
        private Boolean readStatus;
        private Boolean activeStatus;
        private LocalDateTime createdAt;

        public Response(Letter letter){
            this.letterId = letter.getId();
            this.sender = letter.getSender();
            this.recipient = letter.getRecipient();
            this.content = letter.getContent();
            this.readStatus = letter.getReadStatus();
            this.activeStatus = letter.getActiveStatus();
            this.createdAt = LocalDateTime.now();
        }
    }
}
