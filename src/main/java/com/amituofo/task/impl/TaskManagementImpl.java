package com.amituofo.task.impl;

import java.util.List;

import com.amituofo.common.api.Callback;
import com.amituofo.task.GlobalTaskEventListener;
import com.amituofo.task.TaskArchive;
import com.amituofo.task.TaskDetail;
import com.amituofo.task.TaskManagement;
import com.amituofo.task.TaskRuntimeMessageListener;
import com.amituofo.task.TaskRuntimePerformanceListener;
import com.amituofo.task.TaskRuntimeProgressListener;
import com.amituofo.task.TaskRuntimeStatusListener;
import com.amituofo.task.TaskThread;
import com.amituofo.task.TaskThreadManagement;

public class TaskManagementImpl implements TaskManagement {
	private final TaskArchive archive;
	// private final List<TaskDetails> tasks = new ArrayList<TaskDetails>();
	// private final Map<String, TaskExecuter> tasks = new HashMap<String, TaskExecuter>();
	private final TaskThreadManagement tasksMgr;

	public TaskManagementImpl(int taskPoolSize, TaskArchive archive) {
		this.tasksMgr = new TaskThreadManagementImpl(taskPoolSize);
		this.archive = archive;
	}

	@Override
	public void add(TaskDetail td) {
		add(td, null);
	}

	@Override
	public void add(TaskDetail taskDetail, TaskRuntimeStatusListener eventListener) {
		if (get(taskDetail.getId()) == null) {
			archive.save(taskDetail);
			tasksMgr.execute(taskDetail, eventListener, new TaskRuntimeProgress(), new TaskRuntimePerformance(), new TaskRuntimeMessage());
		}
	}

	@Override
	public void add(TaskDetail taskDetail,
			TaskRuntimeStatusListener eventListener,
			TaskRuntimeProgressListener progressListener,
			TaskRuntimePerformanceListener performanceListener,
			TaskRuntimeMessageListener runtimeMessageListener) {
		
		if (get(taskDetail.getId()) == null) {
			archive.save(taskDetail);
			tasksMgr.execute(taskDetail, eventListener, progressListener, performanceListener, runtimeMessageListener);
		}
	}

	@Override
	public void disableAutoClean() {
		tasksMgr.disableAutoClean();
	}

	@Override
	public void enableAutoClean(int cyclePeriod, final int deadAfterTimeMillis) {
		tasksMgr.enableAutoClean(cyclePeriod, deadAfterTimeMillis);
	}
	

	@Override
	public void addAutoCleanCallback(final Callback<Integer> callback) {
		tasksMgr.addAutoCleanCallback(callback);
	}
	
	@Override
	public void removeAutoCleanCallbacks() {
		tasksMgr.removeAutoCleanCallbacks();
	}
	
	@Override
	public boolean isAutoCleanEnabled() {
		return tasksMgr.isAutoCleanEnabled();
	}

	@Override
	public synchronized int clean(int deadAfterTimeMillis) {
		return tasksMgr.clean(deadAfterTimeMillis);
	}

	@Override
	public void setTaskPoolSize(int poolSize) {
		tasksMgr.setTaskPoolSize(poolSize);
	}

//	@Override
//	public void resume(String id) {
//		tasksMgr.resume(id);
//	}

	@Override
	public TaskDetail get(String id) {
		return archive.get(id);
	}

//	@Override
//	public void remove(String id) {
//		archive.delete(id);
//	}

	@Override
	public void stop(String id) {
		tasksMgr.interrupt(id);
	}

	@Override
	public synchronized void stopAll() {
		tasksMgr.interruptAll();

		// while(tasksMgr.getActiveCount()>0) {
		// try {
		// Thread.sleep(50);
		// } catch (InterruptedException e) {
		// }
		// }
	}

//	@Override
//	public synchronized void removeAll() {
//		stopAll();
//
//		archive.deleteAll();
//	}

	// @Override
	// public List<TaskDetail> list() {
	// return archive.list();
	// }

	@Override
	public int count() {
		return archive.count();
	}

	// @Override
	// public List<TaskDetail> list(String groupId, String catalog) {
	// return archive.list(groupId, catalog);
	// }

	@Override
	public List<TaskThread> listActiveTasks() {
		return tasksMgr.list();
	}

	@Override
	public int getActiveCount() {
		return tasksMgr.getActiveCount();
	}

	@Override
	public void shutdown() {
		tasksMgr.shutdown();
	}

	@Override
	public void addGlobalTaskEventListener(GlobalTaskEventListener listener) {
		tasksMgr.addGlobalTaskEventListener(listener);
	}

	@Override
	public void removeGlobalTaskEventListener(GlobalTaskEventListener listener) {
		tasksMgr.addGlobalTaskEventListener(listener);
	}

}
