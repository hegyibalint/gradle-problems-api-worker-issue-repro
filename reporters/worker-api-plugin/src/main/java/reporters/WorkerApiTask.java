package reporters;

import org.gradle.api.DefaultTask;
import org.gradle.api.problems.Problems;
import org.gradle.api.problems.Severity;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;
import org.gradle.work.DisableCachingByDefault;
import org.gradle.workers.WorkQueue;
import org.gradle.workers.WorkerExecutor;

import javax.inject.Inject;

@DisableCachingByDefault
public abstract class WorkerApiTask extends DefaultTask {

    @Input
    public abstract Property<Boolean> getIsFork();

    @Inject
    public abstract WorkerExecutor getWorkerExecutor();

    @Inject
    public abstract Problems getProblems();

    @TaskAction
    public void execute() {
        getProblems().getReporter().reporting(problem -> problem
            .id("adhoc-worker-deprecation", "Plugin is deprecated")
            .contextualLabel("The 'worker-api-plugin' is deprecated")
            .documentedAt("https://github.com/gradle/gradle/README.md")
            .severity(Severity.WARNING)
            .solution("Please use a more recent plugin version")
        );

        WorkQueue workQueue = getIsFork().get()
            ? getWorkerExecutor().processIsolation(spec -> {})
            : getWorkerExecutor().classLoaderIsolation(spec -> {});

        workQueue.submit(WorkerApiAction.class, params -> {});
    }
}
