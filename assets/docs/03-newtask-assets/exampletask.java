/*********************************************************
Copyright or © or Copr. IETR/INSA: Maxime Pelcat, Jean-François Nezan,
Karol Desnos

[mpelcat,jnezan,kdesnos]@insa-rennes.fr

This software is a computer program whose purpose is to prototype
parallel applications.

This software is governed by the CeCILL-C license under French law and
abiding by the rules of distribution of free software.  You can  use, 
modify and/ or redistribute the software under the terms of the CeCILL-C
license as circulated by CEA, CNRS and INRIA at the following URL
"http://www.cecill.info". 

As a counterpart to the access to the source code and  rights to copy,
modify and redistribute granted by the license, users are provided only
with a limited warranty  and the software's author,  the holder of the
economic rights,  and the successive licensors  have only  limited
liability. 

In this respect, the user's attention is drawn to the risks associated
with loading,  using,  modifying and/or developing or reproducing the
software by the user in light of its specific status of free software,
that may mean  that it is complicated to manipulate,  and  that  also
therefore means  that it is reserved for developers  and  experienced
professionals having in-depth computer knowledge. Users are therefore
encouraged to load and test the software's suitability as regards their
requirements in conditions enabling the security of their systems and/or 
data to be ensured and,  more generally, to use and operate it in the 
same conditions as regards security. 

The fact that you are presently reading this means that you have had
knowledge of the CeCILL-C license and that you accept its terms.
 *********************************************************/

package org.ietr.preesm.tutorial.example;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IProgressMonitor;

import net.sf.dftools.algorithm.model.parameters.InvalidExpressionException;
import net.sf.dftools.algorithm.model.sdf.SDFEdge;
import net.sf.dftools.algorithm.model.sdf.SDFGraph;
import net.sf.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import net.sf.dftools.workflow.WorkflowException;
import net.sf.dftools.workflow.elements.Workflow;
import net.sf.dftools.workflow.implement.AbstractTaskImplementation;
import net.sf.dftools.workflow.tools.WorkflowLogger;

/**
 * {@link ExampleTask} is an dummy workflow task that multiply all the
 * production and consumption rates of a {@link SDFGraph} by a factor given as a
 * parameter to the task.
 * 
 * @author kdesnos
 * 
 */
public class ExampleTask extends AbstractTaskImplementation {

	@Override
	public Map<String, Object> execute(Map<String, Object> inputs,
			Map<String, String> parameters, IProgressMonitor monitor,
			String nodeName, Workflow workflow) throws WorkflowException {

		// The logger is used to display messages in the console
		// Message can have different colors depending of their severity.
		// The severity of a message is set when calling logger.log()
		Logger logger = WorkflowLogger.getLogger();

		// Retrieve the task parameter
		String paramString = parameters.get("factor");
		int factor;
		try {
			factor = (paramString != null) ? Integer.decode(paramString) : 1;
		} catch (NumberFormatException e) {
			String message = "Factor parameter of task " + nodeName
					+ " is not an integer.";
			logger.log(Level.SEVERE, message);
			throw new WorkflowException(message);
		}

		// Check if the factor is greater than 0
		if (factor <= 0) {
			logger.log(Level.WARNING,
					"Factor cannot be less than or equal to 0, default value is used instead.");
			factor = 1;
		}

		// Retrieve the task input
		SDFGraph algo = (SDFGraph) inputs.get("SDF");

		// Do the work: Multiply all production/consumption rates of the graph
		// with the factor.
		// NB. A proper implementation should use the Visitor design pattern
		for (SDFEdge edge : algo.edgeSet()) {

			// Retrieve the production/consumption rates
			int prod = 0, cons = 0;
			try {
				prod = edge.getProd().intValue();
				cons = edge.getCons().intValue();
			} catch (InvalidExpressionException e) {
				e.printStackTrace();
			}

			// Set with the new values
			edge.setProd(new SDFIntEdgePropertyType(prod * factor));
			edge.setCons(new SDFIntEdgePropertyType(cons * factor));
		}

		// Display a message in the console
		logger.log(Level.INFO, algo.edgeSet().size() + " edges were treated.");

		// Put the resulting graph in the output map
		Map<String, Object> outputs = new HashMap<String, Object>();
		outputs.put("SDF", algo);

		return outputs;
	}

	@Override
	public Map<String, String> getDefaultParameters() {
		Map<String, String> defaultParams = new HashMap<String, String>();
		defaultParams.put("factor", "1");
		return defaultParams;
	}

	@Override
	public String monitorMessage() {
		return "Starting Execution of Example Task ";
	}
}
