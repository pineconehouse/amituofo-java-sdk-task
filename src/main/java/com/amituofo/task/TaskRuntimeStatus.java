package com.amituofo.task;

public class TaskRuntimeStatus {
	private TaskStatus status = null;
	private long startTimeMillis = System.currentTimeMillis();
	private long endTimeMillis = 0L;
	private ExecResult execResult = null;
	private Throwable cause;

	private TaskRuntimeStatusListener runtimeStatusListener = null;

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status, Object meta) {
		if (this.status != status) {
			this.status = status;

			if (runtimeStatusListener != null) {
				runtimeStatusListener.statusChanged(status, meta);
			}
		}
	}

	public long getStartTimeMillis() {
		return startTimeMillis;
	}

	public void setStartTimeMillis(long startTimeMillis) {
		this.startTimeMillis = startTimeMillis;
	}

	public long getEndTimeMillis() {
		return endTimeMillis;
	}

	public void setEndTimeMillis(long endTimeMillis) {
		this.endTimeMillis = endTimeMillis;
	}

	public ExecResult getExecResult() {
		return execResult;
	}

	public void setExecResult(ExecResult execResult) {
		this.execResult = execResult;
	}

	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}

	public TaskRuntimeStatusListener getTaskRuntimeStatusListener() {
		return runtimeStatusListener;
	}

	public void setTaskRuntimeStatusListener(TaskRuntimeStatusListener eventListener) {
		this.runtimeStatusListener = eventListener;
	}

}
