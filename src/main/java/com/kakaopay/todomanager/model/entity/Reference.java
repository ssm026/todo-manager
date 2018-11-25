package com.kakaopay.todomanager.model.entity;

/**
 * Created by jyp on 2018. 11. 23.
 * Email :ssm027@gmail.com
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "reference")
public class Reference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer referenceId;

    private Integer referenceTaskId;

    @JsonIgnore
    @ManyToOne(optional=false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="task_id")
    private Task task;

    public Reference() {

    }

    @Builder
    public Reference(Task task, Integer referenceTaskId) {
        this.task = task;
        this.referenceTaskId = referenceTaskId;
    }
}
