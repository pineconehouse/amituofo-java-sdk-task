package com.amituofo.task.impl;

import com.amituofo.task.TaskRuntimePerformanceListener;

public class TaskRuntimePerformance implements TaskRuntimePerformanceListener {
	private int workerSize = 1;
	private Object performance = "-";
	private int remainSecond;

	@Override
	public void updatePerformance(Object performance) {
		this.performance = performance;
	}

	@Override
	public void updateWorkerSize(int workerSize) {
		this.workerSize = workerSize;
	}

	public int getWorkerSize() {
		return workerSize;
	}

	public Object getPerformance() {
		return performance;
	}

	public int getRemainSecond() {
		return remainSecond;
	}

	@Override
	public void updateRemainSecond(int remainSecond) {
		this.remainSecond = remainSecond;
	}

}
