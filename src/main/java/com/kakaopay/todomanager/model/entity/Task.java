package com.kakaopay.todomanager.model.entity;

/**
 * Created by jyp on 2018. 11. 23.
 * Email :ssm027@gmail.com
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kakaopay.todomanager.model.entity.Reference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity(name = "task")
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Integer taskId;

    private String name;

    private Boolean finishFlag;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    List<Reference> referenceList;

    @JsonIgnore
    @ManyToOne(optional=false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="member_id")
    private Member member;

    @Builder
    public Task(String name, Boolean finishFlag, Date createTime, Date updateTime, Member member) {
        this.name = name;
        this.finishFlag = finishFlag;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.referenceList = new ArrayList<Reference>();
        this.member = member;
    }
}
