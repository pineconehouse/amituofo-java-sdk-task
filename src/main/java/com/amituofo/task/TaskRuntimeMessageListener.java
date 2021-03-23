package com.amituofo.task;

public interface TaskRuntimeMessageListener {
	public final static TaskRuntimeMessageListener NOOP = new TaskRuntimeMessageListener() {
		@Override
		public void updateRuntimeMessage(String msg) {
		}

		@Override
		public void updateAdditionMessage(String msg) {
		}
	};

	void updateRuntimeMessage(String msg);

	void updateAdditionMessage(String msg);
}
