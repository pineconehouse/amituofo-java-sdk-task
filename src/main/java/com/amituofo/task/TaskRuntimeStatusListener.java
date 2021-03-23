package com.amituofo.task;

public interface TaskRuntimeStatusListener {
	TaskRuntimeStatusListener NOOP = new TaskRuntimeStatusListener() {
		@Override
		public void statusChanged(TaskStatus status, Object meta) {
		}
	};

	public void statusChanged(TaskStatus status, Object statusDescription);
}
