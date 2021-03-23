package com.amituofo.task.impl;

import com.amituofo.common.api.Callback;
import com.amituofo.task.TaskManagement;
import com.amituofo.task.TaskService;

public class MemoryTaskService implements TaskService {
	private final TaskManagement taskManagement;

	public MemoryTaskService(int concurrent_task_count) {
		taskManagement = new TaskManagementImpl(concurrent_task_count, new MemoryTaskArchiveImpl());
	}

	@Override
	public void start() {
	}

	@Override
	public void stop(Callback<Void> callback) {
		taskManagement.shutdown();
	}

	@Override
	public TaskManagement getTaskManagement() {
		return taskManagement;
	}

}
