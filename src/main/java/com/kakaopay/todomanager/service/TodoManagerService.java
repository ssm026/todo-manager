package com.kakaopay.todomanager.service;

import com.kakaopay.todomanager.entity.Reference;
import com.kakaopay.todomanager.entity.Task;

import java.util.List;

/**
 * Created by jyp on 2018. 11. 23.
 * Email :ssm027@gmail.com
 */
public interface TodoManagerService {
    List<Task> findAllTask();
}
