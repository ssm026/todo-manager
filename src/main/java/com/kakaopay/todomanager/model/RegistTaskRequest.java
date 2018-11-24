package com.kakaopay.todomanager.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Created by jyp on 2018. 11. 24.
 * Email :ssm027@gmail.com
 */

@Data
public class RegistTaskRequest {
    @NotEmpty
    private String name;

    private List<Integer> referenceTaskIdList;

    public RegistTaskRequest() {

    }

    public RegistTaskRequest(String name, List<Integer> referenceTaskIdList) {
        this.name = name;
        this.referenceTaskIdList = referenceTaskIdList;
    }
}
