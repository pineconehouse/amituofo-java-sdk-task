package com.amituofo.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amituofo.task.impl.TaskRuntimeMessage;
import com.amituofo.task.impl.TaskRuntimePerformance;
import com.amituofo.task.impl.TaskRuntimeProgress;

//public class TaskExecuter implements Runnable {
public class TaskThread extends Thread {
	protected final static Logger logger = LogManager.getLogger(Task.class);

	private final TaskDetail taskDetail;
	private final TaskRuntimeStatus taskRuntimeStatus = new TaskRuntimeStatus();
	private final TaskRuntimeProgress taskRuntimeProgress = new TaskRuntimeProgress();
	private final TaskRuntimeMessage taskRuntimeMessage = new TaskRuntimeMessage();
	private final TaskRuntimePerformance taskRuntimePerformance = new TaskRuntimePerformance();

	private TaskRuntimeProgressListener progressListener;
	private TaskRuntimeMessageListener runtimeMessageListener;
	private TaskRuntimePerformanceListener performanceListener;

	private TaskBase taskInstance = null;

	private final String LOG_HEAD1;
	private final String LOG_HEAD2;

	public TaskThread(TaskDetail taskDetail) {
		this.taskDetail = taskDetail;

		LOG_HEAD1 = "Task-Name:[" + taskDetail.getName() + "] Catalog:[" + taskDetail.getCatalog() + "] ID:[" + taskDetail.getId() + "] ";
		LOG_HEAD2 = "Task-[" + taskDetail.getId() + "] ";
		
		this.setProgressListener(null);
		this.setRuntimeMessageListener(null);
		this.setPerformanceListener(null);
	}

	public String getTaskId() {
		return taskDetail.getId();
	}

	public TaskDetail getTaskDetails() {
		return taskDetail;
	}

	public TaskRuntimeStatus getRuntimeStatus() {
		return taskRuntimeStatus;
	}

	public TaskRuntimeProgress getRuntimeProgress() {
		return taskRuntimeProgress;
	}

	public TaskRuntimeMessage getRuntimeMessage() {
		return taskRuntimeMessage;
	}

	public TaskRuntimePerformance getRuntimePerformance() {
		return taskRuntimePerformance;
	}

	public void setEventListener(final TaskRuntimeStatusListener eventListener) {
		if (eventListener == null) {
			this.taskRuntimeStatus.setTaskRuntimeStatusListener(TaskRuntimeStatusListener.NOOP);
		} else {
			this.taskRuntimeStatus.setTaskRuntimeStatusListener(eventListener);
		}
	}

	public void setProgressListener(final TaskRuntimeProgressListener progressListener) {
		if (progressListener == null) {
			this.progressListener = new TaskRuntimeProgressListener() {

				@Override
				public void updateProgressTo100Percent() {
					taskRuntimeProgress.updateProgressTo100Percent();
				}

				@Override
				public void updateProgress(long progress) {
					taskRuntimeProgress.updateProgress(progress);
				}

				@Override
				public void updateProgressMode(ProgressMode progressMode) {
					taskRuntimeProgress.updateProgressMode(progressMode);
				}

				@Override
				public void setMaxProgress(long maxProgress) {
					taskRuntimeProgress.setMaxProgress(maxProgress);
				}
			};
		} else {
			this.progressListener = new TaskRuntimeProgressListener() {

				@Override
				public void updateProgressTo100Percent() {
					taskRuntimeProgress.updateProgressTo100Percent();
					progressListener.updateProgressTo100Percent();
				}

				@Override
				public void updateProgress(long progress) {
					taskRuntimeProgress.updateProgress(progress);
					progressListener.updateProgress(progress);
				}

				@Override
				public void updateProgressMode(ProgressMode progressMode) {
					taskRuntimeProgress.updateProgressMode(progressMode);
					progressListener.updateProgressMode(progressMode);
				}

				@Override
				public void setMaxProgress(long maxProgress) {
					taskRuntimeProgress.setMaxProgress(maxProgress);
					progressListener.setMaxProgress(maxProgress);
				}
			};
		}
	}

	public void setRuntimeMessageListener(final TaskRuntimeMessageListener runtimeMessageListener) {
		if (runtimeMessageListener == null) {
			this.runtimeMessageListener = new TaskRuntimeMessageListener() {

				@Override
				public void updateRuntimeMessage(String runtimeMessage) {
					taskRuntimeMessage.updateRuntimeMessage(runtimeMessage);
				}

				@Override
				public void updateAdditionMessage(String msg) {
					taskRuntimeMessage.updateAdditionMessage(msg);
				}
			};
		} else {
			this.runtimeMessageListener = new TaskRuntimeMessageListener() {

				@Override
				public void updateRuntimeMessage(String runtimeMessage) {
					taskRuntimeMessage.updateRuntimeMessage(runtimeMessage);
					runtimeMessageListener.updateRuntimeMessage(runtimeMessage);
				}

				@Override
				public void updateAdditionMessage(String msg) {
					taskRuntimeMessage.updateAdditionMessage(msg);
					runtimeMessageListener.updateAdditionMessage(msg);
				}
			};
		}
	}

	public void setPerformanceListener(final TaskRuntimePerformanceListener performanceListener) {
		if (performanceListener == null) {
			this.performanceListener = new TaskRuntimePerformanceListener() {

				@Override
				public void updateWorkerSize(int workerSize) {
					taskRuntimePerformance.updateWorkerSize(workerSize);
				}

				@Override
				public void updatePerformance(Object performance) {
					taskRuntimePerformance.updatePerformance(performance);
				}

				@Override
				public void updateRemainSecond(int remainSecond) {
					taskRuntimePerformance.updateRemainSecond(remainSecond);
				}
			};
		} else {
			this.performanceListener = new TaskRuntimePerformanceListener() {

				@Override
				public void updateWorkerSize(int workerSize) {
					taskRuntimePerformance.updateWorkerSize(workerSize);
					performanceListener.updateWorkerSize(workerSize);
				}

				@Override
				public void updatePerformance(Object performance) {
					taskRuntimePerformance.updatePerformance(performance);
					performanceListener.updatePerformance(performance);
				}
				
				@Override
				public void updateRemainSecond(int remainSecond) {
					taskRuntimePerformance.updateRemainSecond(remainSecond);
				}
			};
		}
	}

	@Override
	public void interrupt() {
		if (taskInstance != null) {
			TaskStatus status = taskRuntimeStatus.getStatus();
			if (status != TaskStatus.Canceled && status != TaskStatus.Canceling && status != TaskStatus.Finished && status != TaskStatus.Finishing) {
				logger.info(LOG_HEAD2 + "Catching interrupt event. ");

				taskRuntimeStatus.setStatus(TaskStatus.Canceling, taskInstance.getStatusDescription(TaskStatus.Canceling));
//				eventListener.statusChanged(TaskStatus.Canceling);

				try {
					boolean stoped = taskInstance.tryStop();

					if (stoped) {
						taskRuntimeStatus.setStatus(TaskStatus.Canceled, taskInstance.getStatusDescription(TaskStatus.Canceled));
//						eventListener.statusChanged(TaskStatus.Canceled);
						logger.info(LOG_HEAD2 + "Interrupted. ");
					} else {
						// ??????Bug ?????????????????????????????? 
						super.interrupt();
						logger.info(LOG_HEAD2 + "Interrupted. ");
					}

					taskRuntimeStatus.setExecResult(ExecResult.Interrupted);
					// ??????????????????
					taskRuntimeStatus.setEndTimeMillis(System.currentTimeMillis());
				} catch (Exception e) {
					// e.printStackTrace();
					logger.error(LOG_HEAD2 + "Interrupted with error. ", e);
				}

				try {
					taskInstance.release();
					logger.info(LOG_HEAD2 + "Resource released.");
				} catch (Exception e) {
					logger.error(LOG_HEAD2 + "Catch exception when releasing resource.", e);
				}
			}
		} else {
			taskRuntimeStatus.setStatus(TaskStatus.Canceled, taskInstance.getStatusDescription(TaskStatus.Canceled));
//			eventListener.statusChanged(TaskStatus.Canceled);
		}
	}

	@Override
	public void run() {
		if (taskRuntimeStatus.getStatus() == TaskStatus.Canceled) {
			logger.info("Task Canceled " + LOG_HEAD1);
			return;
		}

		ExecResult result = null;
		boolean initialized = false;
		try {
			// ??????????????????
			logger.info("Creating instance of " + LOG_HEAD1);
			taskInstance = taskDetail.getTaskClass().newInstance();
			taskInstance.setTaskDetail(taskDetail);
			// ????????????
			taskInstance.setRuntimeProgressListener(progressListener);
			taskInstance.setRuntimePerformanceListener(performanceListener);
			taskInstance.setRuntimeMessageListener(runtimeMessageListener);

			TaskParameter parameter = taskDetail.getParameter();

			// ????????????????????????
			taskRuntimeStatus.setStatus(TaskStatus.Initializing, taskInstance.getStatusDescription(TaskStatus.Initializing));
//			eventListener.statusChanged(TaskStatus.Initializing);

			// ???????????????
			logger.info(LOG_HEAD2 + "Initializing... ");

			// ??????????????????
			taskRuntimeStatus.setStartTimeMillis(System.currentTimeMillis());
			initialized = taskInstance.initialize(parameter);

			if (initialized) {
				// ????????????????????????
				taskRuntimeStatus.setStatus(TaskStatus.Preparing, taskInstance.getStatusDescription(TaskStatus.Preparing));
//				eventListener.statusChanged(TaskStatus.Preparing);

				// ????????????
				logger.info(LOG_HEAD2 + "Preparing to run.");
				boolean prepareOK = taskInstance.prepare();

				if (prepareOK) {
					// ????????????????????????
					taskRuntimeStatus.setStatus(TaskStatus.Running, taskInstance.getStatusDescription(TaskStatus.Running));
//					eventListener.statusChanged(TaskStatus.Running);

					result = null;

					// ????????????
					logger.info(LOG_HEAD2 + "Start running...");
					taskInstance.execute();
					logger.info(LOG_HEAD2 + "End running.");
				} else {
					// ???????????????
					logger.info(LOG_HEAD2 + "Prepare failed.");
					result = ExecResult.InitFailed;
				}
			} else {
				// ???????????????
				logger.info(LOG_HEAD2 + "Initialize failed.");
				result = ExecResult.InitFailed;
			}

		} catch (Throwable e) {
			// e.printStackTrace();
			result = ExecResult.Failed;
			taskRuntimeStatus.setCause(e);

			logger.error(LOG_HEAD2 + "Catch exception when processing task.", e);

//			if (taskInstance != null) {
//				logger.info(LOG_HEAD2 + "Interrupting task because of an error.");
//
//				TaskThread.this.interrupt();
//			}
		} finally {
			// ??????????????????
			taskRuntimeStatus.setEndTimeMillis(System.currentTimeMillis());

			ExecResult finalResult = null;
			// ?????????????????????????????????finish????????????
			if (taskInstance != null && initialized) {
				try {
					taskRuntimeStatus.setStatus(TaskStatus.Finishing, taskInstance.getStatusDescription(TaskStatus.Finishing));
//					eventListener.statusChanged(TaskStatus.Finishing);

					logger.info(LOG_HEAD2 + "Finising...");
					finalResult = taskInstance.finish();
				} catch (Exception e) {
					// e.printStackTrace();
					result = ExecResult.Failed;
					taskRuntimeStatus.setCause(e);
					logger.error(LOG_HEAD2 + "Catch exception when finishing task.", e);
				}
			}

			// ????????????????????????
			if (result == null) {
				result = (finalResult == null ? ExecResult.Succeed : finalResult);
			}

			logger.info(LOG_HEAD2 + "Finished with [" + result + "]");

			taskRuntimeStatus.setExecResult(result);

			switch (result) {
				case Interrupted:
					taskRuntimeStatus.setStatus(TaskStatus.Canceled, taskInstance.getStatusDescription(TaskStatus.Canceled));
//					eventListener.statusChanged(TaskStatus.Canceled);
					break;
				default:
					taskRuntimeStatus.setStatus(TaskStatus.Finished, taskInstance.getStatusDescription(TaskStatus.Finished));
//					eventListener.statusChanged(TaskStatus.Finished);
			}

			if (taskInstance != null) {
				try {
					taskInstance.release();
					logger.info(LOG_HEAD2 + "Resource released.");
				} catch (Exception e) {
					logger.error(LOG_HEAD2 + "Catch exception when releasing resource.", e);
				}
			}
		}
	}

}
