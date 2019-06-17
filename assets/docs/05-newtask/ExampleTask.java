package org.preesm.example;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IProgressMonitor;
import org.preesm.commons.doc.annotations.Port;
import org.preesm.commons.doc.annotations.PreesmTask;
import org.preesm.commons.exceptions.PreesmException;
import org.preesm.commons.exceptions.PreesmRuntimeException;
import org.preesm.commons.logger.PreesmLogger;
import org.preesm.model.pisdf.DataInputPort;
import org.preesm.model.pisdf.DataOutputPort;
import org.preesm.model.pisdf.Expression;
import org.preesm.model.pisdf.Fifo;
import org.preesm.model.pisdf.PiGraph;
import org.preesm.workflow.elements.Workflow;
import org.preesm.workflow.implement.AbstractTaskImplementation;

/**
 * Example task. Multiplies the rates of all ports by a factor given as task parameter. Use a default factor of 1.
 */
@PreesmTask(
	id = "my.unique.workflow.task.identifier",
	name = "Example Task",
	inputs = {@Port(name = "PiMM", type = PiGraph.class)},
	outputs = {@Port(name = "PiMM", type = PiGraph.class)}
)
public class ExampleTask extends AbstractTaskImplementation {

  @Override
  public Map<String, Object> execute(final Map<String, Object> inputs, final Map<String, String> parameters,
      final IProgressMonitor monitor, final String nodeName, final Workflow workflow) {

    // The logger is used to display messages in the console.
    // Messages can have different colors depending of their severity.
    // The severity of a message is set when calling logger.log(Level, String)
    final Logger logger = PreesmLogger.getLogger();

    // Retrieve the task parameter
    final String paramString = parameters.get("factor");
    long factor;
    try {
      factor = (paramString != null) ? Long.decode(paramString) : 1;
    } catch (final NumberFormatException e) {
      final String message = "Factor parameter '" + paramString + "' of task '" + nodeName + "' is not an integer.";
      throw new PreesmRuntimeException(message);
    }

    // Check if the factor is greater than 0
    if (factor <= 0) {
      logger.log(Level.WARNING, "Factor cannot be less than or equal to 0, default value is used instead.");
      factor = 1;
    }

    // Retrieve the task input
    final PiGraph algo = (PiGraph) inputs.get("PiMM");

    // Do the work: Multiply all production/consumption rates of the graph with the factor.
    // NB. A proper implementation should use the Visitor design pattern
    for (final Fifo fifo : algo.getFifos()) {

      final DataInputPort targetPort = fifo.getTargetPort();
      final DataOutputPort sourcePort = fifo.getSourcePort();

      final Expression targetRateExpr = targetPort.getPortRateExpression();
      final Expression sourceRateExpr = sourcePort.getPortRateExpression();

      // Retrieve the production/consumption rates
      long prod = 0;
      long cons = 0;
      try {
        prod = sourceRateExpr.evaluate();
        cons = targetRateExpr.evaluate();
      } catch (final PreesmException e) {
        throw new PreesmRuntimeException("Could not evaluate rates. Make sure your input algorithm is static.");
      }

      targetPort.setExpression(prod * factor);
      sourcePort.setExpression(cons * factor);
      // Set with the new values
    }

    // Display a message in the console
    final String message = algo.getFifos().size() + " Fifos were treated.";
    logger.log(Level.INFO, message);

    // Put the resulting graph in the output map
    final Map<String, Object> outputs = new LinkedHashMap<>();
    outputs.put("PiMM", algo);

    return outputs;
  }

  @Override
  public Map<String, String> getDefaultParameters() {
    final Map<String, String> defaultParams = new HashMap<>();
    defaultParams.put("factor", "1");
    return defaultParams;
  }

  @Override
  public String monitorMessage() {
    return "Starting Execution of Example Task ";
  }

}
