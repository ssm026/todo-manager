package com.kakaopay.todomanager;

import com.kakaopay.todomanager.controller.TodoManagerController;
import com.kakaopay.todomanager.model.domain.common.ResponseCode;
import com.kakaopay.todomanager.model.domain.common.TodoResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TodomanagerApplicationTests extends CommonTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private TodoManagerController todoManagerController;

    @Autowired
    MockMvc mockMvc;


    @Test
    public void testSuccessResult() {
        ResponseEntity<TodoResult> response = testRestTemplate.getForEntity("/api/v1/task", TodoResult.class);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getCode()).isEqualTo(ResponseCode.SUCCESS.getCode());
        assertThat(response.getBody().getMessage()).isEqualTo(ResponseCode.SUCCESS.getMessage());
    }

    @Test
    public void getListTest() throws Exception {
        mockMvc.perform(get("/api/v1/task"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testRegistTask() throws Exception {
        mockMvc.perform(post("/api.v1/task"))
                .andExpect(status().isOk());
    }
}
