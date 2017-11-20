package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConditionChecker {

	private Map<String, Boolean> map;

	public ConditionChecker(String[] conditions) {
		this.map = new HashMap<>();

		for (int i = 0; i < conditions.length; i++)
			map.put(conditions[i], false);
	}

	public void check(String condition) {
		if (!map.containsKey(condition))
			throw new UnexpectedConditionException(condition);

		map.replace(condition, true);
	}

	public void uncheck(String condition) {
		if (!map.containsKey(condition))
			throw new UnexpectedConditionException(condition);

		map.replace(condition, false);
	}

	public boolean allChecked() {
		for (Map.Entry<String, Boolean> entry: this.map.entrySet()) {
			if (entry.getValue() == false)
				return false;
		}
		return true;
	}

	public ArrayList<String> getUnpassedList() {
		ArrayList<String> list = new ArrayList<>();

		for (Map.Entry<String, Boolean> entry: this.map.entrySet()) {
			if (entry.getValue() == false)
				list.add(entry.getKey());
		}

		return list;
	}

	public class UnexpectedConditionException extends RuntimeException {
		private String condition;

		public UnexpectedConditionException(String condition) {
			this.condition = condition;
		}

		public String toString() {
			return "UnexpectedConditionException: condition '"+condition+"' not exists in initial condition list";
		}
	}
}
