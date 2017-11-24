package mymusictray.activity.list;

import mymusictray.activity.Activity;
import mymusictray.activity.MenuActivity;
import mymusictray.model.ListableModel;

import java.util.List;

public abstract class ListSelectingActivity<T extends ListableModel> extends MenuActivity {
	private List<T> models;

	public ListSelectingActivity(Activity previousActivity, List<T> models) {
		super(previousActivity);
		this.models = models;
	}

	@Override
	public String getFirstMenuTitle() { return "Close List"; }

	@Override
	public String[] getMenu() {
		String[] ret = new String[models.size()];
		for (int i = 0; i < models.size(); i++)
			ret[i] = "Manage '" + (models.get(i).getName()) + "'";
		return ret;
	}

	@Override
	public void operate(int choice) {
		operate(this.models.get(choice-1));
	}

	abstract public void operate(T model);
}