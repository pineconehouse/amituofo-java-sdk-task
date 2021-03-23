package com.amituofo.task.impl;

import java.util.concurrent.atomic.AtomicLong;

import com.amituofo.common.util.RandomUtils;
import com.amituofo.task.ProgressMode;
import com.amituofo.task.TaskRuntimeProgressListener;

public class TaskRuntimeProgress implements TaskRuntimeProgressListener {
	private long maxProgress = 100;
	private AtomicLong progress = new AtomicLong(0);
	private ProgressMode progressMode = ProgressMode.Precent;

	public TaskRuntimeProgress() {
		super();
	}

	@Override
	public void setMaxProgress(long maxProgress) {
		if (maxProgress <= 0) {
			this.maxProgress = 1;
		} else {
			this.maxProgress = maxProgress;
		}
		this.progress.set(0);
	}

	@Override
	public void updateProgress(long progress) {
		// if (this.progress.longValue() < this.maxProgress) {
		this.progress.getAndAdd(progress);
		// } else {
		// this.progress.set(this.maxProgress);
		// }
	}

	@Override
	public void updateProgressTo100Percent() {
		this.progress.set(maxProgress);
	}

	@Override
	public void updateProgressMode(ProgressMode progressMode) {
		this.progressMode = progressMode;
	}

	public long getMaxProgress() {
		return maxProgress;
	}

	public long getProgress() {
		return progress.longValue();
	}

	public double getProgressInPercent() {
		double percent = (int) (((double) progress.doubleValue() / (double) maxProgress) * 100);
		return percent > 100 ? 100 : percent;
	}

	public ProgressMode getProgressMode() {
		return progressMode;
	}

}
