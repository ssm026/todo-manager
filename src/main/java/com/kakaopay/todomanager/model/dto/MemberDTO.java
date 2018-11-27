package com.kakaopay.todomanager.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by jyp on 2018. 11. 27.
 * Email :ssm027@gmail.com
 */

@Data
@NoArgsConstructor
public class MemberDTO {
    @JsonIgnore
    private Integer memberId;

    @NotEmpty
    @Size(max = 64)
    private String name;

    @NotEmpty
    private String password;

    @Builder
    public MemberDTO(Integer memberId, String name, String password) {
        this.memberId = memberId;
        this.name = name;
        this.password = password;
    }
}
