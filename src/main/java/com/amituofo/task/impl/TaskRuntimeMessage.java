package com.amituofo.task.impl;

import com.amituofo.task.TaskRuntimeMessageListener;

public class TaskRuntimeMessage implements TaskRuntimeMessageListener {
	private String runtimeMessage;
	private String additionMessage = null;

	@Override
	public void updateRuntimeMessage(String runtimeMessage) {
		this.runtimeMessage = runtimeMessage;
	}

	@Override
	public void updateAdditionMessage(String additionMessage) {
		this.additionMessage = additionMessage;
	}

	public String getRuntimeMessage() {
		return runtimeMessage;
	}

	public String getAdditionMessage() {
		return additionMessage;
	}

}
