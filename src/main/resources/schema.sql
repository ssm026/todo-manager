CREATE TABLE `task` (
  `task_id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  `name` VARCHAR(32) NOT NULL,
  `finish_flag` TINYINT(1) NOT NULL DEFAULT 0,
  `create_time` DATETIME NOT NULL DEFAULT NOW(),
  `update_time` DATETIME NULL
);

CREATE INDEX idx_finish_flag ON task(finish_flag);

CREATE TABLE `reference` (
  `reference_id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  `task_id` INT NOT NULL,
  `reference_task_id` INT NOT NULL,
  FOREIGN KEY (`task_id`) REFERENCES `task` (`task_id`)
);

CREATE INDEX idx_reference_task_id ON reference(reference_task_id);
