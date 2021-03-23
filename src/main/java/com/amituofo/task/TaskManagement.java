package com.amituofo.task;

import java.util.List;

import com.amituofo.common.api.Callback;


public interface TaskManagement {

	void add(TaskDetail td);

	void add(TaskDetail td, TaskRuntimeStatusListener eventListener);

	void add(TaskDetail td,
			TaskRuntimeStatusListener eventListener,
			TaskRuntimeProgressListener progressListener,
			TaskRuntimePerformanceListener performanceListener,
			TaskRuntimeMessageListener runtimeMessageListener);

	TaskDetail get(String id);

//	void remove(String id);

	void stop(String id);

	void stopAll();

//	void removeAll();

	List<TaskThread> listActiveTasks();

	// List<TaskDetail> list(String groupId, String catalog);

//	void resume(String id);

	int getActiveCount();

	void setTaskPoolSize(int poolSize);

	int count();

	void shutdown();

	void enableAutoClean(int cyclePeriod, int deadAfterTimeMillis);

	void disableAutoClean();

	int clean(int deadAfterTimeMillis);

	boolean isAutoCleanEnabled();

	void removeAutoCleanCallbacks();

	void addAutoCleanCallback(Callback<Integer> callback);
	
	void addGlobalTaskEventListener(GlobalTaskEventListener listener);

	void removeGlobalTaskEventListener(GlobalTaskEventListener listener);

}
