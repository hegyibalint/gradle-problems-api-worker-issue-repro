package reporters;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.problems.Problems;
import org.gradle.api.problems.Severity;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.internal.tasks.JvmConstants;
import org.gradle.language.base.plugins.LifecycleBasePlugin;

import javax.inject.Inject;

/**
 * This is a simple, standard Gradle plugin that is applied to a project.
 *
 * This plugin registers a task which reports problems via the Worker API.
 */
public class WorkerApiPlugin implements Plugin<Project> {

    public WorkerApiPlugin() {
    }

    @Override
    public void apply(Project target) {
        TaskProvider<WorkerApiTask> workerApiTask = target.getTasks()
            .register("createProblems", WorkerApiTask.class, task -> {
                task.getIsFork().convention(false);
            });

        target.getTasks().named(JvmConstants.CLASSES_TASK_NAME, task -> task.dependsOn(workerApiTask));
    }
}
