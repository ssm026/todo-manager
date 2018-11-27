package com.kakaopay.todomanager.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jyp on 2018. 11. 27.
 * Email :ssm027@gmail.com
 */

@Getter
@Setter
@Entity(name = "member")
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberId;

    private String name;

    private String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    List<Task> taskList;

    @Builder
    public Member(String name, String password, List<Task> taskList, Integer memberId) {
        this.memberId = memberId;
        this.name = name;
        this.password = password;
        this.taskList = new ArrayList<>();
    }
}
