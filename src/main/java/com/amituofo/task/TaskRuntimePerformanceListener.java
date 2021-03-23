package com.amituofo.task;

import com.amituofo.task.impl.TaskRuntimePerformance;

public interface TaskRuntimePerformanceListener {
	public final static TaskRuntimePerformanceListener NOOP = new TaskRuntimePerformanceListener() {
		@Override
		public void updatePerformance(Object performance) {
		}

		@Override
		public void updateWorkerSize(int workerSize) {
		}

		@Override
		public void updateRemainSecond(int remainSecond) {
		}
	};

	void updatePerformance(Object performance);

	void updateWorkerSize(int workerSize);

	void updateRemainSecond(int remainSecond);
}
