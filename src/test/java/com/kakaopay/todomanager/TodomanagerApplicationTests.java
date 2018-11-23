package com.kakaopay.todomanager;

import com.kakaopay.todomanager.controller.TodoManagerController;
import com.kakaopay.todomanager.model.ResponseCodeEnum;
import com.kakaopay.todomanager.model.TodoResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodomanagerApplicationTests {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private TodoManagerController todoManagerController;



    @Test
    public void testSuccessResult() {
        ResponseEntity<TodoResult> response = testRestTemplate.getForEntity("/api/v1/task", TodoResult.class);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getCode()).isEqualTo(ResponseCodeEnum.SUCCESS.getCode());
        assertThat(response.getBody().getMessage()).isEqualTo(ResponseCodeEnum.SUCCESS.getMessage());
    }

}
