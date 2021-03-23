package com.amituofo.task.impl;

import java.util.ArrayList;
import java.util.List;

import com.amituofo.common.util.StringUtils;
import com.amituofo.task.TaskArchive;
import com.amituofo.task.TaskDetail;

public class MemoryTaskArchiveImpl implements TaskArchive {
	private final List<TaskDetail> tasks = new ArrayList<TaskDetail>();

	public MemoryTaskArchiveImpl() {
	}

	@Override
	public void save(TaskDetail td) {
		if (get(td.getId()) == null) {
			tasks.add(0, td);
		}
	}

	@Override
	public TaskDetail get(String id) {
		List<TaskDetail> tasks = list();

		for (TaskDetail taskDetail : tasks) {
			if (taskDetail.getId().equals(id)) {
				return taskDetail;
			}
		}

		return null;
	}

	@Override
	public void delete(String id) {
		TaskDetail task = get(id);
		tasks.remove(task);
	}

	@Override
	public List<TaskDetail> list() {
		return tasks;
	}
	
	@Override
	public int count() {
		return tasks.size();
	}

	@Override
	public List<TaskDetail> list(String groupId, String catalog) {
		List<TaskDetail> gettasks = new ArrayList<TaskDetail>();
		List<TaskDetail> tasks = list();

		for (TaskDetail taskDetail : tasks) {
			if (StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(taskDetail.getGroupId())) {
				if (!taskDetail.getGroupId().equalsIgnoreCase(groupId)) {
					continue;
				}
			}
			if (StringUtils.isEmpty(catalog)) {
				gettasks.add(taskDetail);
			} else if (StringUtils.isNotEmpty(taskDetail.getCatalog()) && taskDetail.getCatalog().toLowerCase().contains(catalog.toLowerCase())) {
				gettasks.add(taskDetail);
			}
		}

		return gettasks;
	}

	@Override
	public void deleteAll() {
		tasks.clear();
	}

}
