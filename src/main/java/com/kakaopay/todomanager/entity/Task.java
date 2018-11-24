package com.kakaopay.todomanager.entity;

/**
 * Created by jyp on 2018. 11. 23.
 * Email :ssm027@gmail.com
 */

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity(name = "task")
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
    @OneToMany(mappedBy = "task", cascade = CascadeType.PERSIST)
    List<Reference> referenceList;

    public Task() {

    }

    @Builder
    public Task(String name, Boolean finishFlag, Date createTime, Date updateTime) {
        this.name = name;
        this.finishFlag = finishFlag;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.referenceList = new ArrayList<Reference>();
    }
}
