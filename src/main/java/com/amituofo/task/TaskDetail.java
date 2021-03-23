package com.amituofo.task;

import java.util.UUID;

import com.amituofo.common.util.RandomUtils;

public class TaskDetail {
	private final String id;
	private String name;
	private String catalog;
	private String groupId = "";
	private String description;
	private String scheduleExpression;
	private TaskParameter parameter;
	private Class<? extends TaskBase> taskClass;

//	private final TaskRuntimeStatus runtimeStatus = null;

	public TaskDetail(String name, Class<? extends TaskBase> taskClass) {
		// String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
		String uuid = RandomUtils.randomInt(100, 999) + "" + System.currentTimeMillis();
		this.id = uuid.substring(uuid.length() - 5);
		this.name = name;
		this.taskClass = taskClass;
		this.parameter = new TaskParameter();
	}

	public TaskDetail(Class<? extends TaskBase> taskClass) {
		this(null, taskClass);
	}

	public boolean equals(TaskDetail obj) {
		return id.equals(obj.getId());
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getScheduleExpression() {
		return scheduleExpression;
	}

	public void setScheduleExpression(String scheduleExpression) {
		this.scheduleExpression = scheduleExpression;
	}

	public TaskParameter getParameter() {
		return parameter;
	}

	public void setParameter(TaskParameter parameter) {
		this.parameter = parameter;
	}

	public Class<? extends TaskBase> getTaskClass() {
		return taskClass;
	}

	public void setTaskClass(Class<? extends TaskBase> taskClass) {
		this.taskClass = taskClass;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

//	public TaskRuntimeStatus getRuntimeStatus() {
//		return runtimeStatus;
//	}

}
