//package mediaproject.its.domain.dto;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import mediaproject.its.domain.entity.PostContent;
//
//public class PostContentDto {
//
//    @Builder
//    @AllArgsConstructor
//    @Getter
//    public static class Request{
//        private int postId;
//        private String content;
//
//        public PostContent toEntity(){
//            return PostContent.builder()
//                    .id(postId)
//                    .content(content)
//                    .build();
//        }
//    }
//
//    @Builder
//    @AllArgsConstructor
//    @Getter
//    public static class Response{
//        private int postId;
//        private String content;
//
//        public Response(PostContent postContent){
//            this.postId = postContent.getId();
//            this.content = postContent.getContent();
//        }
//    }
//}
