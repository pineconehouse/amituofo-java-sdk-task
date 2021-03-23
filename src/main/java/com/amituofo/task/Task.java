package com.amituofo.task;

import com.amituofo.task.ex.TaskException;

public interface Task {// extends Runnable {
	void setRuntimeProgressListener(TaskRuntimeProgressListener progress);

	/**
	 * 1.任务初始化
	 * @param parameter
	 * @return
	 * @throws TaskException
	 */
	boolean initialize(TaskParameter parameter) throws TaskException;

	/**
	 * 2.任务之前前准备
	 * @return
	 * @throws TaskException
	 */
	boolean prepare() throws TaskException;

	/**
	 * 3.执行任务处理
	 * @throws TaskException
	 */
	void execute() throws TaskException;

	/**
	 * 4.结束
	 * @return
	 * @throws TaskException
	 */
	ExecResult finish() throws TaskException;
	
	/**
	 * 清理所用资源
	 * @throws TaskException
	 */
	void release() throws TaskException;;

	/**
	 * 任务终止
	 * @return
	 * @throws TaskException
	 */
	boolean tryStop() throws TaskException;
}
