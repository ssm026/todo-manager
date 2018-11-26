package com.kakaopay.todomanager.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by jyp on 2018. 11. 24.
 * Email :ssm027@gmail.com
 */

@Data
@NoArgsConstructor
public class RegistTaskRequest {
    @NotEmpty
    @Size(max = 32)
    private String name;

    private List<Integer> referenceTaskIdList;

    public RegistTaskRequest(String name, List<Integer> referenceTaskIdList) {
        this.name = name;
        this.referenceTaskIdList = referenceTaskIdList;
    }
}
