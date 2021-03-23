package com.amituofo.task;

import java.util.List;

import com.amituofo.common.api.Callback;

public interface TaskThreadManagement {

	void execute(TaskDetail td, TaskRuntimeStatusListener eventListener,
			TaskRuntimeProgressListener progressListener,
			TaskRuntimePerformanceListener performanceListener,
			TaskRuntimeMessageListener runtimeMessageListener);

	TaskThread get(String id);

	void interrupt(String id);

	void interruptAll();

	List<TaskThread> list();

	int getActiveCount();

	void resume(String id);
	
	void setTaskPoolSize(int poolSize);

	void shutdown();

	int clean(int deadAfterTimeMillis);

	void enableAutoClean(int cyclePeriod, int deadAfterTimeMillis);

	void disableAutoClean();

	boolean isAutoCleanEnabled();

	void removeAutoCleanCallbacks();

	void addAutoCleanCallback(Callback<Integer> callback);

	void addGlobalTaskEventListener(GlobalTaskEventListener listener);

	void removeGlobalTaskEventListener(GlobalTaskEventListener listener);

}
