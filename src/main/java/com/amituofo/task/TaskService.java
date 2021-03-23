package com.amituofo.task;

import com.amituofo.common.api.Callback;

public interface TaskService {
	void start();

	void stop(Callback<Void> callback);
	
	TaskManagement getTaskManagement();
}
