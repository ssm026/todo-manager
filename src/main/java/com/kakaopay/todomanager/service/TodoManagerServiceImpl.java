package com.kakaopay.todomanager.service;

import com.kakaopay.todomanager.entity.Reference;
import com.kakaopay.todomanager.entity.Task;
import com.kakaopay.todomanager.repository.ReferenceRepository;
import com.kakaopay.todomanager.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.util.List;

/**
 * Created by jyp on 2018. 11. 23.
 * Email :ssm027@gmail.com
 */

@Slf4j
@Service
public class TodoManagerServiceImpl implements TodoManagerService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<Task> findAllTask() {
        return taskRepository.findAll();
    }
}
