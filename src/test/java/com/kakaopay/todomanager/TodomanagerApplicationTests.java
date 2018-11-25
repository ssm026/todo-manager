package com.kakaopay.todomanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.todomanager.controller.TodoManagerController;
import com.kakaopay.todomanager.model.domain.RegistTaskRequest;
import com.kakaopay.todomanager.model.domain.UpdateTaskNameRequest;
import com.kakaopay.todomanager.model.domain.common.ResponseCode;
import com.kakaopay.todomanager.model.domain.common.TodoResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TodomanagerApplicationTests extends CommonTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getTaskListTest() throws Exception {
        mockMvc.perform(get("/api/v1/task"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getNotFinishedIdListTest() throws Exception{
        mockMvc.perform(get("/api/v1/task/id/list")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void registTaskTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        RegistTaskRequest request = new RegistTaskRequest();
        request.setName("집안일");

        mockMvc.perform(post("/api/v1/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void registTaskFailTest_nameMaxLength() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        RegistTaskRequest request = new RegistTaskRequest();
        request.setName("1234567890123456789012345678901234");

        mockMvc.perform(post("/api/v1/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void modifyTaskTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UpdateTaskNameRequest request = new UpdateTaskNameRequest();
        request.setName("청소");

        mockMvc.perform(patch("/api/v1/task/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void modifyTaskFailTest_nameMaxLength() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UpdateTaskNameRequest request = new UpdateTaskNameRequest();
        request.setName("1234567890123456789012345678901234");

        mockMvc.perform(patch("/api/v1/task/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void finishTaskTest() throws Exception {
        mockMvc.perform(post("/api/v1/task/1/finish")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
