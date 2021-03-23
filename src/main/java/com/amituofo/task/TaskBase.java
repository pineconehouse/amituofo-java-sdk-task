package com.amituofo.task;

public abstract class TaskBase implements Task {
	protected TaskRuntimeProgressListener progressListener;
	protected TaskRuntimeMessageListener runtimeMessageListener;
	protected TaskRuntimePerformanceListener performanceListener;

	protected TaskDetail taskDetail;
	// private TaskThread taskThread;

	public void setRuntimeProgressListener(TaskRuntimeProgressListener progress) {
		this.progressListener = progress;
	}

	public void setRuntimeMessageListener(TaskRuntimeMessageListener runtimeMessageListener) {
		this.runtimeMessageListener = runtimeMessageListener;
	}

	public void setRuntimePerformanceListener(TaskRuntimePerformanceListener performanceListener) {
		this.performanceListener = performanceListener;
	}

	public TaskDetail getTaskDetail() {
		return taskDetail;
	}

	public void setTaskDetail(TaskDetail taskDetail) {
		this.taskDetail = taskDetail;
	}

	public String getTaskId() {
		return taskDetail.getId();
	}

	public abstract Object getStatusDescription(TaskStatus status);

	// public static String getCatelog() {
	// return "";
	// }

	// public boolean isInterrupted() {
	// return taskThread.isInterrupted();
	// }

	// public void setTaskThread(TaskThread taskThread) {
	// this.taskThread = taskThread;
	// }

}
