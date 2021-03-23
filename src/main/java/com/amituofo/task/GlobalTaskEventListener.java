package com.amituofo.task;

public interface GlobalTaskEventListener {
	public void statusChanged(String id, TaskStatus status);
}
