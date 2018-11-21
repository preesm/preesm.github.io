package org.ietr.preesm.tutorial.example;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.preesm.algorithm.model.parameters.InvalidExpressionException;
import org.preesm.algorithm.model.sdf.SDFEdge;
import org.preesm.algorithm.model.sdf.SDFGraph;
import org.preesm.algorithm.model.types.LongEdgePropertyType;
import org.preesm.commons.logger.PreesmLogger;
import org.preesm.workflow.WorkflowException;
import org.preesm.workflow.elements.Workflow;
import org.preesm.workflow.implement.AbstractTaskImplementation;

/**
 * {@link ExampleTask} is an dummy workflow task that multiply all the production and consumption rates of a
 * {@link SDFGraph} by a factor given as a parameter to the task.
 *
 * @author kdesnos
 *
 */
public class ExampleTask extends AbstractTaskImplementation {

  @Override
  public Map<String, Object> execute(final Map<String, Object> inputs, final Map<String, String> parameters,
      final IProgressMonitor monitor, final String nodeName, final Workflow workflow) throws WorkflowException {

    // The logger is used to display messages in the console
    // Message can have different colors depending of their severity.
    // The severity of a message is set when calling logger.log()
    final Logger logger = PreesmLogger.getLogger();

    // Retrieve the task parameter
    final String paramString = parameters.get("factor");
    int factor;
    try {
      factor = (paramString != null) ? Integer.decode(paramString) : 1;
    } catch (final NumberFormatException e) {
      final String message = "Factor parameter of task " + nodeName + " is not an integer.";
      logger.log(Level.SEVERE, message);
      throw new WorkflowException(message);
    }

    // Check if the factor is greater than 0
    if (factor <= 0) {
      logger.log(Level.WARNING, "Factor cannot be less than or equal to 0, default value is used instead.");
      factor = 1;
    }

    // Retrieve the task input
    final SDFGraph algo = (SDFGraph) inputs.get("SDF");

    // Do the work: Multiply all production/consumption rates of the graph
    // with the factor.
    // NB. A proper implementation should use the Visitor design pattern
    for (final SDFEdge edge : algo.edgeSet()) {

      // Retrieve the production/consumption rates
      long prod = 0;
      long cons = 0;
      try {
        prod = edge.getProd().longValue();
        cons = edge.getCons().longValue();
      } catch (final InvalidExpressionException e) {
        e.printStackTrace();
      }

      // Set with the new values
      edge.setProd(new LongEdgePropertyType(prod * factor));
      edge.setCons(new LongEdgePropertyType(cons * factor));
    }

    // Display a message in the console
    logger.log(Level.INFO, algo.edgeSet().size() + " edges were treated.");

    // Put the resulting graph in the output map
    final Map<String, Object> outputs = new LinkedHashMap<>();
    outputs.put("SDF", algo);

    return outputs;
  }

  @Override
  public Map<String, String> getDefaultParameters() {
    final Map<String, String> defaultParams = new LinkedHashMap<>();
    defaultParams.put("factor", "1");
    return defaultParams;
  }

  @Override
  public String monitorMessage() {
    return "Starting Execution of Example Task ";
  }
}
