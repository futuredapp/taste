import com.thefuntasty.taste.TasteTestingScenario;

/**
 * Created by eidamsvoboda on 09/02/2017.
 */

public class BaseScenario extends TasteTestingScenario {
	@Override protected String getWaitedForViewId() {
		return "username";
	}

	@Override protected String getPackageName() {
		return "com.amateri.app";
	}
}
