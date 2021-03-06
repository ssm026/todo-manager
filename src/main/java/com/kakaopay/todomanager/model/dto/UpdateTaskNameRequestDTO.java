package com.kakaopay.todomanager.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by jyp on 2018. 11. 24.
 * Email :ssm027@gmail.com
 */

@Data
public class UpdateTaskNameRequestDTO {
    @NotEmpty
    @Size(max = 32)
    private String name;
}
