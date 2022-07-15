//package app.pool.project.controller;
//
//import app.pool.project.domain.Message;
//import app.pool.project.repository.MessageRepository;
//import app.pool.project.request.MessageCreate;
//import app.pool.project.request.MessageSearch;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import org.aspectj.lang.annotation.Before;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.restdocs.RestDocumentationExtension;
//import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
//import org.springframework.restdocs.payload.PayloadDocumentation;
//import org.springframework.restdocs.request.RequestDocumentation;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.snippet.Attributes.key;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc(addFilters = false)
//@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.pool.network", uriPort = 443)
//@ExtendWith(RestDocumentationExtension.class)
//public class MessageControllerDocTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private MessageRepository messageRepository;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    public void before() {
//        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//    }
//
//    @Test
//    @DisplayName("메시지 작성")
//    void test1() throws Exception {
//        // given
//        MessageCreate request = MessageCreate.builder()
//                .title("제목입니다.")
//                .body("본문입니다.")
//                .messageLink("https://github.com/Lee-DoHa")
//                .build();
//
//        String json = objectMapper.writeValueAsString(request);
//
//        // expected
//        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/messages")
//                    .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(json))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(status().isOk())
//                .andDo(document("message-create",
//                        PayloadDocumentation.requestFields(
//                                PayloadDocumentation.fieldWithPath("title").description("제목")
//                                        .attributes(key("constraint").value("메시지의 제목을 입력해주세요.")),
//                                PayloadDocumentation.fieldWithPath("body").description("본문")
//                                        .attributes(key("constraint").value("메시지의 본문을 입력해주세요.")),
//                                PayloadDocumentation.fieldWithPath("messageLink").description("링크")
//                                        .attributes(key("constraint").value("메시지의 링크를 첨부해주세요."))
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("메시지 단건 조회")
//    void test2() throws Exception {
//        // given
//        Message message = Message.builder()
//                .title("제목")
//                .body("본문")
//                .messageLink("링크")
//                .build();
//        messageRepository.save(message);
//
//        // expected
//        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/messages/{messageId}", 1L)
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(status().isOk())
//                .andDo(document("message-inquiry", RequestDocumentation.pathParameters(
//                        RequestDocumentation.parameterWithName("messageId").description("메시지 ID")
//                ),
//                        PayloadDocumentation.responseFields(
//                                PayloadDocumentation.fieldWithPath("id").description("메시지 ID"),
//                                PayloadDocumentation.fieldWithPath("title").description("메시지 제목"),
//                                PayloadDocumentation.fieldWithPath("body").description("메시지 본문"),
//                                PayloadDocumentation.fieldWithPath("messageLink").description("메시지 링크")
//                        )
//                ));
//    }
//
////    @Test
////    @DisplayName("메시지 여러개 조회")
////    void test3() throws Exception{
////        // given
////        List<Message> requestPosts = IntStream.range(1, 31)
////                .mapToObj(i -> Message.builder()
////                        .title("예시 제목 - " + i)
////                        .body("예시 본문 - " + i)
////                        .messageLink("예시 링크 - " + i)
////                        .build())
////                .collect(Collectors.toList());
////        messageRepository.saveAll(requestPosts);
////
////        MessageSearch messageSearch = MessageSearch.builder()
////                .page(1)
////                .build();
////
////        // expected
////        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/messages?page=1&size=10")
////                .accept(MediaType.APPLICATION_JSON))
////                .andDo(MockMvcResultHandlers.print())
////                .andExpect(status().isOk())
////                .andDo(document())
////
////    }
//
//}