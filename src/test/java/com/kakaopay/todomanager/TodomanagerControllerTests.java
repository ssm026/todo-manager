package com.kakaopay.todomanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.todomanager.model.dto.RegistTaskRequest;
import com.kakaopay.todomanager.model.dto.UpdateTaskNameRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TodomanagerControllerTests extends CommonTest {
    @Autowired
    private WebApplicationContext context;

    MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void methodNotAllowedTest() throws Exception {
        mockMvc.perform(delete("/api/v1/task/1"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void unsupportedMediaTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        RegistTaskRequest request = new RegistTaskRequest();
        request.setName("집안일");

        mockMvc.perform(post("/api/v1/task")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isUnsupportedMediaType());
    }

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
