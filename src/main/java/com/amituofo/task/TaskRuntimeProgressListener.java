package com.amituofo.task;

import com.amituofo.task.impl.TaskRuntimeProgress;

public interface TaskRuntimeProgressListener {
	public final static TaskRuntimeProgressListener NOOP = new TaskRuntimeProgressListener() {
		@Override
		public void setMaxProgress(long progress) {
		}

		@Override
		public void updateProgress(long progress) {
		}

		@Override
		public void updateProgressTo100Percent() {
		}

		@Override
		public void updateProgressMode(ProgressMode progressMode) {
		}
	};

	void setMaxProgress(long maxProgress);

	void updateProgress(long progress);

	void updateProgressTo100Percent();

	void updateProgressMode(ProgressMode progressMode);
}
