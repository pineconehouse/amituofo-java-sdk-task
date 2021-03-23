package com.amituofo.task;

import java.util.List;

public interface TaskArchive {

	void save(TaskDetail td);

	TaskDetail get(String id);

	void delete(String id);

	List<TaskDetail> list();

	List<TaskDetail> list(String groupId, String catalog);

	void deleteAll();

	int count();
}
