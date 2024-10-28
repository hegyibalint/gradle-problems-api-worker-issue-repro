package reporters;

import org.gradle.api.problems.Problems;
import org.gradle.api.problems.Severity;
import org.gradle.workers.WorkAction;

import javax.inject.Inject;

public abstract class WorkerApiAction implements WorkAction<WorkerApiTaskParameters> {
    private Problems problems;

    @Inject
    public WorkerApiAction(Problems problems) {
        this.problems = problems;
    }

    @Override
    public void execute() {
        problems.getReporter().reporting(problem -> problem
            .id("adhoc-worker-deprecation", "Plugin is deprecated")
            .contextualLabel("The 'worker-api-plugin' is deprecated")
            .documentedAt("https://github.com/gradle/gradle/README.md")
            .severity(Severity.WARNING)
            .solution("Please use a more recent plugin version")
        );
    }
}
