/**
*    Copyright (C)  DataMelt project. The jHPLot package by S.Chekanov and Work.ORG
*    All rights reserved.
*
*    This program is free software; you can redistribute it and/or modify it under the terms
*    of the GNU General Public License as published by the Free Software Foundation; either
*    version 3 of the License, or any later version.
*
*    This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
*    without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
*    See the GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License along with this program;
*    if not, see <http://www.gnu.org/licenses>.
*
*    Additional permission under GNU GPL version 3 section 7:
*    If you have received this program as a library with written permission from the DataMelt team,
*    you can link or combine this library with your non-GPL project to convey the resulting work.
*    In this case, this library should be considered as released under the terms of
*    GNU Lesser public license (see <https://www.gnu.org/licenses/lgpl.html>),
*    provided you include this license notice and a URL through which recipients can access the
*    Corresponding Source.
**/
package jhplot;

import jplot.LinePars;
import hep.aida.*;
import jscl.math.Expression;
import jscl.text.ParseException;
import java.io.Serializable;

import jhplot.gui.HelpBrowser;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;

/**
 * Create a function in one dimension using "x" as a variable. The function name
 * could have parameters named in unique way as P0, P1, P2 ... They have to be
 * replaced with values using setPar() method for evaluation.
 * 
 * The function may have one independent variable: x
 * <p>
 * <b>Operators and functions</b><br/>
 * <br/>
 * the following operators are supported:
 * <ul>
 * <li>Addition: '2 + 2'</li>
 * <li>Subtraction: '2 - 2'</li>
 * <li>Multiplication: '2 * 2'</li>
 * <li>Division: '2 / 2'</li>
 * <li>Exponential: '2 ^ 2' or ** (raise to a power)</li>
 * <li>Unary Minus,Plus (Sign Operators): '+2 - (-2)'</li>
 * <li>Modulo: '2 % 2'</li>
 * </ul>
 * the following functions are supported:
 * <ul>
 * <li>abs: absolute value</li>
 * <li>acos: arc cosine</li>
 * <li>asin: arc sine</li>
 * <li>atan: arc tangent</li>
 * <li>cbrt: cubic root</li>
 * <li>ceil: nearest upper integer</li>
 * <li>cos: cosine</li>
 * <li>cosh: hyperbolic cosine</li>
 * <li>exp: euler's number raised to the power (e^x)</li>
 * <li>floor: nearest lower integer</li>
 * <li>log: logarithmus naturalis (base e)</li>
 * <li>sin: sine</li>
 * <li>sinh: hyperbolic sine</li>
 * <li>sqrt: square root</li>
 * <li>tan: tangent</li>
 * <li>tanh: hyperbolic tangent</li>
 * </ul>
 * <br/>
 * It also recognizes the pi (or Pi) values; <br/>
 * 
 * 
 * @author S.Chekanov
 * 
 */

public class F1D extends DrawOptions implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double min;

	private double max;

	private int points;

	private String name;

	private double[] x = null;

	private double[] y = null;

	private Calculable calc = null;

	private ExpressionBuilder function = null;

	private IFunction iname = null;

	private boolean isParsed = false;

	private String lastException = "";

	/**
	 * Create a function in 1D. 500 points are used between Min and Max for
	 * evaluation. The title is set to the function's definition
	 * 
	 * The function may have one independent variable: x. Example: x*x
	 * 
	 * <b>Operators and functions</b><br/>
	 * <br/>
	 * the following operators are supported:
	 * <ul>
	 * <li>Addition: '2 + 2'</li>
	 * <li>Subtraction: '2 - 2'</li>
	 * <li>Multiplication: '2 * 2'</li>
	 * <li>Division: '2 / 2'</li>
	 * <li>Exponential: '2 ^ 2' or ** (raise to a power)</li>
	 * <li>Unary Minus,Plus (Sign Operators): '+2 - (-2)'</li>
	 * <li>Modulo: '2 % 2'</li>
	 * </ul>
	 * the following functions are supported:
	 * <ul>
	 * <li>abs: absolute value</li>
	 * <li>acos: arc cosine</li>
	 * <li>asin: arc sine</li>
	 * <li>atan: arc tangent</li>
	 * <li>cbrt: cubic root</li>
	 * <li>ceil: nearest upper integer</li>
	 * <li>cos: cosine</li>
	 * <li>cosh: hyperbolic cosine</li>
	 * <li>exp: euler's number raised to the power (e^x)</li>
	 * <li>floor: nearest lower integer</li>
	 * <li>log: logarithmus naturalis (base e)</li>
	 * <li>sin: sine</li>
	 * <li>sinh: hyperbolic sine</li>
	 * <li>sqrt: square root</li>
	 * <li>tan: tangent</li>
	 * <li>tanh: hyperbolic tangent</li>
	 * </ul>
	 * <br/>
	 * It also recognizes the pi (or Pi) values;
	 * 
	 * <br/>
	 * 
	 * 
	 * @param name
	 *            String representing the function's definition
	 * @param min
	 *            Min value
	 * @param max
	 *            Max value
	 */
	public F1D(String name, double min, double max) {
		this(name, name, min, max, true);

	}

	/**
	 * Same function, but one can specify is it parsed or not.
	 * 
	 * @param name
	 *            Name
	 * @param min
	 *            Min value
	 * @param max
	 *            Max value
	 * @param parsed
	 *            parsed or not.
	 */
	public F1D(String name, double min, double max, boolean parsed) {
		this(name, name, min, max, parsed);

	}

	/**
	 * Create a function in 1D for evaluation.
	 * 
	 * The function may have one independent variable: x
	 * <p>
	 * <b>Operators and functions</b><br/>
	 * <br/>
	 * the following operators are supported:
	 * <ul>
	 * <li>Addition: '2 + 2'</li>
	 * <li>Subtraction: '2 - 2'</li>
	 * <li>Multiplication: '2 * 2'</li>
	 * <li>Division: '2 / 2'</li>
	 * <li>Exponential: '2 ^ 2' or ** (raise to a power)</li>
	 * <li>Unary Minus,Plus (Sign Operators): '+2 - (-2)'</li>
	 * <li>Modulo: '2 % 2'</li>
	 * </ul>
	 * the following functions are supported:
	 * <ul>
	 * <li>abs: absolute value</li>
	 * <li>acos: arc cosine</li>
	 * <li>asin: arc sine</li>
	 * <li>atan: arc tangent</li>
	 * <li>cbrt: cubic root</li>
	 * <li>ceil: nearest upper integer</li>
	 * <li>cos: cosine</li>
	 * <li>cosh: hyperbolic cosine</li>
	 * <li>exp: euler's number raised to the power (e^x)</li>
	 * <li>floor: nearest lower integer</li>
	 * <li>log: logarithmus naturalis (base e)</li>
	 * <li>sin: sine</li>
	 * <li>sinh: hyperbolic sine</li>
	 * <li>sqrt: square root</li>
	 * <li>tan: tangent</li>
	 * <li>tanh: hyperbolic tangent</li>
	 * </ul>
	 * <br/>
	 * It also recognizes the pi (or Pi) values; <br/>
	 * 
	 * @param name
	 *            String representing the function
	 */
	public F1D(String name) {
		this(name, name, 0.0, 1.0, true);
	}

	/**
	 * Create new function.
	 * 
	 * @param title
	 *            title
	 * @param name
	 *            definition
	 */

	public F1D(String title, String name) {
		this(title, name, 0.0, 1.0, true);
	}

	/**
	 * Create a new function.
	 * 
	 * The function may have one independent variable: x
	 * <p>
	 * <b>Operators and functions</b><br/>
	 * <br/>
	 * the following operators are supported:
	 * <ul>
	 * <li>Addition: '2 + 2'</li>
	 * <li>Subtraction: '2 - 2'</li>
	 * <li>Multiplication: '2 * 2'</li>
	 * <li>Division: '2 / 2'</li>
	 * <li>Exponential: '2 ^ 2' or ** (raise to a power)</li>
	 * <li>Unary Minus,Plus (Sign Operators): '+2 - (-2)'</li>
	 * <li>Modulo: '2 % 2'</li>
	 * </ul>
	 * the following functions are supported:
	 * <ul>
	 * <li>abs: absolute value</li>
	 * <li>acos: arc cosine</li>
	 * <li>asin: arc sine</li>
	 * <li>atan: arc tangent</li>
	 * <li>cbrt: cubic root</li>
	 * <li>ceil: nearest upper integer</li>
	 * <li>cos: cosine</li>
	 * <li>cosh: hyperbolic cosine</li>
	 * <li>exp: euler's number raised to the power (e^x)</li>
	 * <li>floor: nearest lower integer</li>
	 * <li>log: logarithmus naturalis (base e)</li>
	 * <li>sin: sine</li>
	 * <li>sinh: hyperbolic sine</li>
	 * <li>sqrt: square root</li>
	 * <li>tan: tangent</li>
	 * <li>tanh: hyperbolic tangent</li>
	 * </ul>
	 * <br/>
	 * It also recognizes the pi (or Pi) values; <br/>
	 * 
	 * 
	 * @param title
	 *            title
	 * @param name
	 *            definition
	 * @param min
	 *            min value
	 * @param max
	 *            max value
	 * @param parse
	 *            parset or not
	 */
	public F1D(String title, String name, double min, double max, boolean parse) {
		this.iname = null;
		this.title = title;
		this.name = name;
		this.name = this.name.replace("**", "^"); // preprocess power
		this.name = this.name.replace("pi", "3.14159265");
		this.name = this.name.replace("Pi", "3.14159265");

		this.points = 500;
		this.min = min;
		this.max = max;
		setTitle(title);
		lpp.setType(LinePars.F1D);
		function = new ExpressionBuilder(this.name);
                isParsed = false;
		if (parse == true) {
			try {
				calc = function.withVariableNames("x").build();
				isParsed = true;
			} catch (UnknownFunctionException e) {
				isParsed = false;
                                jhplot.utils.Util.ErrorMessage("Failed to parse function " + this.name+" Error:"+e.toString());
				// System.err.println("Failed to parse function " + this.name+" Error:"+e1.toString()); 
                       } catch (UnparsableExpressionException e) {
                                isParsed = false;
                                jhplot.utils.Util.ErrorMessage("Failed to parse function " + this.name+" Error:"+e.toString());
                                // System.err.println("Failed to parse function " + this.name+" Error:"+e2.toString());
                        }

		}

	}

	/**
	 * Create a polynomial analytical function using a list of values. Example:
	 * pars[0]+pars[1]*x+pars[2]*x*x +pars[3]*x*x*x
	 * 
	 * @param title
	 *            Title of this function
	 * @param pars
	 *            array of coefficients for polynomial function
	 * @param min
	 *            Min value for evaluation
	 * @param max
	 *            Max value for evaluation
	 * @param parse
	 *            set true if it should be parsed
	 */

	public F1D(String title, double[] pars, double min, double max,
			boolean parse) {
		this.iname = null;
		this.title = title;
		if (pars == null || pars.length < 1)
			System.err.println("Failed to evaluate this polynomial");

		this.name = Double.toString(pars[0]);
		for (int i = 1; i < pars.length; i++) {
			String sig = "+";
			if (pars[i] < 0)
				sig = "-";
			double val = Math.abs(pars[i]);
			String X = "*x";
			for (int j = 1; j < i; j++)
				X = X + "*x";
			this.name = this.name + sig + Double.toString(val) + X;

		}

		this.points = 500;
		this.min = min;
		this.max = max;
		setTitle(title);
		lpp.setType(LinePars.F1D);
		if (parse == true) {
			function = new ExpressionBuilder(this.name);
			try {
				calc = function.withVariableNames("x").build();
				isParsed = true;
			} catch (UnknownFunctionException  e) {
				isParsed = false;
                                // System.err.println("Failed to parse function " + this.name+" Error:"+e1.toString());
                                 jhplot.utils.Util.ErrorMessage("Failed to parse function " + this.name+" Error:"+e.toString());
                        } catch (UnparsableExpressionException e) {
                                isParsed = false;
                                // System.err.println("Failed to parse function " + this.name+" Error:"+e2.toString());
                                 jhplot.utils.Util.ErrorMessage("Failed to parse function " + this.name+" Error:"+e.toString());
                        }


		}

	}

	/**
	 * Build a function. The function may have one independent variable: x
	 * <p>
	 * <b>Operators and functions</b><br/>
	 * <br/>
	 * the following operators are supported:
	 * <ul>
	 * <li>Addition: '2 + 2'</li>
	 * <li>Subtraction: '2 - 2'</li>
	 * <li>Multiplication: '2 * 2'</li>
	 * <li>Division: '2 / 2'</li>
	 * <li>Exponential: '2 ^ 2' or ** (raise to a power)</li>
	 * <li>Unary Minus,Plus (Sign Operators): '+2 - (-2)'</li>
	 * <li>Modulo: '2 % 2'</li>
	 * </ul>
	 * the following functions are supported:
	 * <ul>
	 * <li>abs: absolute value</li>
	 * <li>acos: arc cosine</li>
	 * <li>asin: arc sine</li>
	 * <li>atan: arc tangent</li>
	 * <li>cbrt: cubic root</li>
	 * <li>ceil: nearest upper integer</li>
	 * <li>cos: cosine</li>
	 * <li>cosh: hyperbolic cosine</li>
	 * <li>exp: euler's number raised to the power (e^x)</li>
	 * <li>floor: nearest lower integer</li>
	 * <li>log: logarithmus naturalis (base e)</li>
	 * <li>sin: sine</li>
	 * <li>sinh: hyperbolic sine</li>
	 * <li>sqrt: square root</li>
	 * <li>tan: tangent</li>
	 * <li>tanh: hyperbolic tangent</li>
	 * </ul>
	 * <br/>
	 * <br/>
	 * 
	 * @param name
	 *            name
	 * @param parse
	 *            is parsed or not?
	 */
	public F1D(String name, boolean parse) {
		this(name, name, 0., 1., parse);
	}

	/**
	 * Parse the function.
	 * @return true if parsed without problems. 
	 **/
	public boolean parse() {
		try {
			calc = function.withVariableNames("x").build();
			isParsed = true;
		} catch (UnknownFunctionException e) {
			isParsed = false;
		        //System.err.println("Failed to parse function " + this.name+" Error:"+e.toString());
                        jhplot.utils.Util.ErrorMessage("Failed to parse function " + this.name+" Error:"+e.toString());

		}
                catch (UnparsableExpressionException e) {
                        isParsed = false;
                        // System.err.println("Failed to parse function " + this.name+" Error:"+e.toString());
                        jhplot.utils.Util.ErrorMessage("Failed to parse function " + this.name+" Error:"+e.toString());

                }

		return isParsed;

	}

	/**
	 * Create a function in 1D. 500 points are used between Min and Max for
	 * evaluation.
	 * 
	 * The function may have x as independent variable.
	 * 
	 * 
	 * @param title
	 *            Title
	 * @param function
	 *            ExpressionBuilder
	 * @param min
	 *            Min value
	 * @param max
	 *            Max value
	 */
	public F1D(String title, ExpressionBuilder function, double min, double max) {
		this.iname = null;
		this.title = title;
		this.function = function;
		this.points = 500;
		this.min = min;
		this.max = max;
		setTitle(name);
		lpp.setType(LinePars.F1D);

		isParsed = parse();
		if (isParsed == false)
			jhplot.utils.Util.ErrorMessage("Failed to parse function " + this.name);

	}

	/**
	 * Build a function.
	 * 
	 * @param function
	 *            input
	 * @param min
	 *            Min value
	 * @param max
	 *            Max value
	 */
	public F1D(ExpressionBuilder function, double min, double max) {
		this("F1D", function, min, max);
	}

	/**
	 * Create a function in 1D. 500 points are used between Min and Max for
	 * evaluation. Function will be parsed.
	 * 
	 * @param title
	 *            String representing the title
	 * @param name
	 *            String representing the function's definition
	 * @param min
	 *            Min value
	 * @param max
	 *            Max value
	 */
	public F1D(String title, String name, double min, double max) {

		this(title, name, min, max, true);

	}

	/**
	 * Evaluate a function at a specific point in x
	 * 
	 * @param x
	 *            value in x for evaluation
	 * @return function value at x
	 */
	public double eval(double x) {

		double y = 0;

		// jPlot function first
		if (iname == null && (function == null || isParsed == false)) {
			jhplot.utils.Util
					.ErrorMessage("eval(): Function was not parsed correctly!");
			return y;
		}

		// evaluate function
		if (iname == null && function != null && isParsed == true) {
			try {
				calc.setVariable("x", x);
				y = calc.calculate();
			} catch (Exception e) {
				lastException = e.getMessage().toString();
                                String ss1 = Double.toString(x);
                                System.err.println("Failed to evaluate function:" + name+" at x="+ss1+"\n"+e.toString());

			}

			return y;
		} // end of the standard jPlot function

		// start AIDA function
		if (iname != null && iname.dimension() == 2) {
			try {
				double[] xx = new double[iname.dimension()];
				xx[0] = x;
				y = iname.value(xx);
			} catch (Exception e) {
				// System.out.println("Failed to evaluate function!");
				lastException = e.getMessage().toString();
                                String ss1 = Double.toString(x);
				System.err.println("Failed to evaluate function:" + name+" at x="+ss1+"\n"+e.toString());
			}

			return y;

		} // end IFunction

		return y;
	}

	/**
	 * Evaluate a function for an array of x-values
	 * 
	 * @param x
	 *            array of values in x for evaluation
	 * @return array of function values
	 */
	public double[] eval(double[] x) {

		double[] y = new double[x.length];

		// jPlot function first
		if (iname == null && (function == null || isParsed == false)) {
			jhplot.utils.Util
					.ErrorMessage("eval(): Function was not parsed correctly!");
			return y;
		}

		// evaluate function
		if (iname == null && function != null && isParsed == true) {

			for (int i = 0; i < x.length; i++) {

				try {

					calc.setVariable("x", x[i]);
					y[i] = calc.calculate();

				} catch (Exception e) {

					String ss = Integer.toString(i);
					lastException = e.getMessage().toString() + " at position="
							+ ss;
					jhplot.utils.Util.ErrorMessage("eval(): Failed to evaluate:"
							+ name + " at position=" + ss);
					return null;

				}
			}

			return y;
		} // end of the standard jPlot function

		// start AIDA function
		if (iname != null) {

			for (int i = 0; i < x.length; i++) {
				try {
					double[] xx = new double[iname.dimension()];
					xx[0] = x[i];
					y[i] = iname.value(xx);
				} catch (Exception e) {
					String ss = Integer.toString(i);
					lastException = e.getMessage().toString() + " at position="
							+ ss;
					jhplot.utils.Util.ErrorMessage("Failed to evaluate:" + name
							+ " at position=" + ss);
				}
			}

			return y;

		} // end IFunction

		return y;
	}

	/**
	 * Evaluate a function for graphic representation. Number of points for
	 * evaluations is 500.
	 * 
	 * @param Min
	 *            value in x
	 * @param Max
	 *            value in x
	 */
	public void eval(double min, double max) {

		this.min = min;
		this.max = max;
		this.points = 500;
		eval();

	}

	/**
	 * Evaluate a function for graphic representation. Number of points for
	 * evaluations is 500.
	 * 
	 * @param Min
	 *            value in x
	 * @param Max
	 *            value in x
	 * @param Number
	 *            of evaluation points
	 */
	public void eval(double min, double max, int Npoints) {

		this.min = min;
		this.max = max;
		this.points = Npoints;
		eval();

	}

	/**
	 * Evaluate a function for graphic representation. Min and Max should be
	 * given in constructor.
	 */
	public void eval() {

		if (iname == null && (function == null || isParsed == false)) {
			jhplot.utils.Util
					.ErrorMessage("eval(): Function was not parsed correctly!");
			return;
		}

		if (iname == null && isParsed == true) {
			x = new double[points];
			y = new double[points];
			double d = (max - min) / (points - 1);
			for (int i = 0; i < points; i++) {
				x[i] = min + i * d;
				try {

					calc.setVariable("x", x[i]);
					y[i] = calc.calculate();

				} catch (Exception e) {
					String ss = Double.toString(x[i]);
					System.err.println("Failed to evaluate:" + name
							+ " at position=" + ss);
					return;
				}

			}
		} // end of the standard jPlot function

		// start AIDA function
		if (iname != null) {

			x = new double[points];
			y = new double[points];

			double d = (max - min) / (points - 1);
			for (int i = 0; i < points; i++) {
				x[i] = min + i * d;
				double[] xx = new double[iname.dimension()];
				try {
					xx[0] = x[i];
					y[i] = iname.value(xx);
				} catch (Exception e) {
					String ss = Double.toString(x[i]);
					System.err.println("Failed to evaluate at x="
							+ ss);
					return;
				}

			}

		} // end IFunction

	}

	/**
	 * Show online documentation.
	 */
	public void doc() {

		String a = this.getClass().getName();
		a = a.replace(".", "/") + ".html";
		new HelpBrowser(HelpBrowser.JHPLOT_HTTP + a);

	}

	/**
	 * Create a F1D function from JAIDA IFunction. By default, 500 points for
	 * evaluation are used
	 * 
	 * @param iname
	 *            input IFunction
	 * @param min
	 *            Min value
	 * @param max
	 *            Max value
	 */
	public F1D(IFunction iname, double min, double max) {

		this.iname = iname;
		this.name = iname.title();
		this.points = 500;
		this.min = min;
		this.max = max;
		setTitle(this.name);
		lpp.setType(LinePars.F1D);

	}

	/**
	 * Create a F1D function from JAIDA IFunction. By default, 500 points for
	 * evaluation are used
	 * 
	 * @param iname
	 *            input IFunction
	 */
	public F1D(IFunction iname) {

		this.iname = iname;
		this.name = iname.title();
		this.points = 500;
		this.min = 0;
		this.max = 1;
		setTitle(this.name);
		lpp.setType(LinePars.F1D);

	}

	/**
	 * Create F1D function from JAIDA IFunction. By default 500 points are used
	 * 
	 * @param title
	 *            Title
	 * @param iname
	 *            input IFunction
	 * @param min
	 *            Min X values
	 * @param max
	 *            Max X values
	 */

	public F1D(String title, IFunction iname, double min, double max) {

		this.iname = iname;
		this.name = iname.title();
		this.points = 500;
		this.min = min;
		this.max = max;
		setTitle(title);
		lpp.setType(LinePars.F1D);

	}

	/**
	 * Print the F1D function to a Table in a separate Frame. The numbers are
	 * formatted to scientific format. One can sort and search the data in this
	 * table (data cannot be modified)
	 */

	public void toTable() {

		new HTable(this);

	}

	
	/**
	 * Replace abstract parameter with the value (double). Case sensitive!
	 * 
	 * @param parameter
	 *            parameter name
	 * @param value
	 *            value to be inserted
	 */

	public void setPar(String parameter, double value) {
		String s1 = Double.toString(value);
		this.name = name.replaceAll(parameter, s1);
                function.withExpression(this.name);
	}

	/**
	 * Return H1D histogram from F1D function. The number of points are given by
	 * setPoints() method, but the default 500 is used if not given. Min and Max
	 * values are given by the values used to parse the function.
	 * 
	 * @return
	 */

	public H1D getH1D() {

		int bins = getPoints();
		H1D h = new H1D(getTitle(), bins, min, max);
		int ibins = bins + 2;
		double[] newHeights = new double[ibins];
		double[] newErrors = new double[ibins];
		double[] newMeans = new double[ibins];
		double[] newRmss = new double[ibins];
		int[] newEntries = new int[ibins];
		newHeights[0] = 0;
		newHeights[ibins - 1] = 0;

		for (int i = 0; i < ibins - 2; i++) {
			newHeights[i + 1] = y[i];
			newErrors[i + 1] = 0;
			newEntries[i + 1] = (int) y[i];
			newMeans[i + 1] = y[i];
			newRmss[i + 1] = 0;
		}

		h.setContents(newHeights, newErrors, newEntries, newMeans, newRmss);
		h.setMeanAndRms(0, 0);

		/*
		 * for (int i = 0; i < bins; i++) { h.fill(x[i]+0.5*d, y[i] ); }
		 */

		return h;
	}

	/**
	 * Return a Histogram given by the F1D function. All statistical
	 * characteristics of such histogram are meaningless. Bins and Min and Max
	 * values are user defined. The function is evaluated at the bin center
	 * which is important for small number of bins.
	 * 
	 * @param hname
	 *            Name of the histogram
	 * @param bins
	 *            number of bins for histogram
	 * @param hmin
	 *            min value of histogram
	 * @param hmax
	 *            max value of histogram
	 * @return H1D histogram
	 */
	public H1D getH1D(String hname, int bins, double hmin, double hmax) {

		double d = (hmax - hmin) / (double) bins;
		double[] xx = new double[bins];

		for (int i = 0; i < bins; i++) {
			xx[i] = hmin + i * d + 0.5 * d;
		}
		double[] yy = eval(xx);

		H1D h = new H1D(hname, bins, hmin, hmax);
		int ibins = bins + 2;
		double[] newHeights = new double[ibins];
		double[] newErrors = new double[ibins];
		double[] newMeans = new double[ibins];
		double[] newRmss = new double[ibins];
		int[] newEntries = new int[ibins];
		newHeights[0] = 0;
		newHeights[ibins - 1] = 0;

		for (int i = 0; i < ibins - 2; i++) {
			newHeights[i + 1] = yy[i];
			newErrors[i + 1] = 0;
			newEntries[i + 1] = (int) yy[i];
			newMeans[i + 1] = yy[i];
			newRmss[i + 1] = 0;
		}

		h.setContents(newHeights, newErrors, newEntries, newMeans, newRmss);
		h.setMeanAndRms(0, 0);

		return h;

	}

	/**
	 * Replace abstract parameter with the value (integer). Case sensitive.
	 * 
	 * @param parameter
	 *            parameter name
	 * @param value
	 *            integer value to be inserted.
	 */

	public void setPar(String parameter, int value) {

		String s1 = Integer.toString(value);
		name = name.replaceAll(parameter, s1);
	}

	/**
	 * Get value in X-axis
	 * 
	 * @param i
	 *            index
	 * 
	 * @return value in X
	 */

	public double getX(int i) {
		return this.x[i];
	}

	/**
	 * Get value in Y-axis
	 * 
	 * @param i
	 *            index
	 * 
	 * @return value in Y
	 */

	public double getY(int i) {
		return this.y[i];
	}

	

	/**
	 * Sets a name of the function, i.e. what will be used for evaluation
	 * 
	 * @param name
	 *            Name
	 */

	public void setName(String name) {
		this.name = name;

	}

	/**
	 * Get the name of the function used for evaluation
	 * 
	 * @return Name
	 */
	public String getName() {
		return this.name;

	}

	/**
	 * Return parsed function. One can evaluate Y as: y =function.getResult(x),
	 * where function is what returned by this method.
	 * 
	 * @return function
	 **/
	public Calculable getParse() {
		return calc;
	}

	/**
	 * Set Min value in X
	 * 
	 * @param min
	 *            Minimum value
	 */

	public void setMin(double min) {
		this.min = min;

	}

	/**
	 * Get the minimum value in X
	 * 
	 * @return min Minimum value
	 */
	public double getMin() {
		return this.min;
	}

	/**
	 * Set the maximum value in X
	 * 
	 * @param max
	 *            Maximal value
	 */
	public void setMax(double max) {
		this.max = max;

	}

	/**
	 * Get the maximum value in X
	 * 
	 * @return Maximal value
	 */
	public double getMax() {
		return this.max;

	}

	/**
	 * Sets the number points between Min and Max for evaluation
	 * 
	 * @param bins
	 *            Number of points
	 */
	public void setPoints(int bins) {
		this.points = bins;

	}

	/**
	 * Numerical integration. Define types as:<br>
	 * type="gauss4" - Gaussian integration formula (4 points)<br>
	 * type="gauss8" - Gaussian integration formula (8 points)<br>
	 * type="richardson" - Richardson extrapolation <br>
	 * type="simpson" - using Simpson's rule. <br>
	 * type="trapezium" - trapezium rule. <br>
	 * 
	 * @param type
	 *            type of algorithm. Can be:
	 *            "gauss4","gauss8","richardson","simpson","trapezium".
	 * @param N
	 *            the number of strips to use for integration
	 * @param min
	 *            the first ordinate.
	 * @param max
	 *            the last ordinate.
	 * @return integral
	 */
	public double integral(String type, final int N, double min,
			final double max) {

		if (type == "gauss4") {
			return jhplot.math.Numeric.gaussian4(N, this, min, max);
		} else if (type == "gauss8") {
			return jhplot.math.Numeric.gaussian8(N, this, min, max);
		} else if (type == "richardson") {
			return jhplot.math.Numeric.richardson(N, this, min, max);
		} else if (type == "simpson") {
			return jhplot.math.Numeric.simpson(N, this, min, max);
		} else if (type == "trapezium") {
			return jhplot.math.Numeric.trapezium(N, this, min, max);

		} else {
			return jhplot.math.Numeric.gaussian4(N, this, min, max);

		}

	}

	/**
	 * Numerical integration using trapezium rule.
	 * 
	 * @param N
	 *            the number of strips to use for integration
	 * @param min
	 *            the first ordinate.
	 * @param max
	 *            the last ordinate.
	 * @return integral
	 */
	public double integral(final int N, double min, final double max) {

		return jhplot.math.Numeric.trapezium(N, this, min, max);

	}

	/**
	 * Get Jaida function
	 * 
	 * @return
	 */
	public IFunction getIFunction() {

		if (iname != null)
			return iname;

		/**
		 * if (function != null && iname==null) {
		 * 
		 * IAnalysisFactory af = IAnalysisFactory.create(); ITree m_ITree =
		 * af.createTreeFactory().create();
		 * 
		 * IFunctionFactory functionfact = af.createFunctionFactory(tree)
		 * 
		 * String ss=getName().replace("x", "x[0]");
		 * 
		 * IFunction inum=functionfact.createFunctionFromScript(getName(), arg1,
		 * arg2, arg3, arg4); return inum;
		 * 
		 * }
		 **/
		return null;

	}

	/**
	 * Get array of X-values after function after evaluation using the default
	 * number of points
	 * 
	 * @return X-values
	 */

	public double[] getArrayX() {
		return x;
	}

	/**
	 * Get array of Y-values after function after evaluation using the default
	 * number of points
	 * 
	 * @return Y-values
	 */

	public double[] getArrayY() {
		return y;
	}

	/**
	 * If the function is parsed correctly, return true. Use this check before
	 * drawing it.
	 * 
	 * @return true if parsed.
	 */
	public boolean isParsed() {

		return isParsed;
	}

	/**
	 * Convert the function into MathML form.
	 * 
	 * @return String representing this function in MathML.
	 */

	public String toMathML() {

		try {
			return Expression.valueOf(name).toMathML();
		} catch (ParseException e) {
			lastException = e.getMessage().toString();
			return "";
		}

	}

	/**
	 * Convert the function into Java code.
	 * 
	 * @return String representing this function in Java.
	 */

	public String toJava() {

		try {
			return Expression.valueOf(name).toJava();
		} catch (ParseException e) {
			lastException = e.getMessage().toString();
			return "";
		}

	}

	/**
	 * Try to simplify this function. It is often useful to rewrite an
	 * expression in term of elementary functions (log, exp, frac, sqrt,
	 * implicit roots), using the "elementary()" before simplifying it. Retrieve
	 * the simplified name as a string using getName() method.
	 * 
	 * @return false if error occurs. Retrieve this error as a string using
	 *         getException().
	 */

	public boolean simplify() {

		try {
			name = Expression.valueOf(name).simplify().toString();
		} catch (ParseException e) {
			lastException = e.getMessage().toString();
			return false;
		}
		return true;

	}

	/**
	 * Convert this function rewrite in term of elementary functions (log, exp,
	 * frac, sqrt, implicit roots) This is useful before simplifying function.
	 * Retrieve the simplified name as a string using getName() method.
	 * 
	 * @return false if error occurs. Retrieve this error as a string using
	 *         getException().
	 */

	public boolean elementary() {

		try {
			name = Expression.valueOf(name).elementary().toString();
		} catch (ParseException e) {
			lastException = e.getMessage().toString();
			return false;
		}
		return true;

	}

	/**
	 * Convert this function rewrite in expanded form. Retrieve the expanded
	 * name as a string using getName() method.
	 * 
	 * @return false if error occurs. Retrieve this error as a string using
	 *         getException().
	 */

	public boolean expand() {

		try {
			name = Expression.valueOf(name).expand().toString();
		} catch (ParseException e) {
			lastException = e.getMessage().toString();
			return false;
		}
		return true;

	}

	/**
	 * Convert this function rewrite in factorized form (if can). Retrieve the
	 * expanded name as a string using getName() method.
	 * 
	 * @return false if error occurs. Retrieve this error as a string using
	 *         getException().
	 */

	public boolean factorize() {

		try {
			name = Expression.valueOf(name).factorize().toString();
		} catch (ParseException e) {
			lastException = e.getMessage().toString();
			return false;
		}
		return true;

	}

	/**
	 * Perform some numeric substitutions. Examples: exp(1) should be
	 * 2.71828182, "pi" should be 3.14159 etc. Retrieve the expanded name as a
	 * string using getName() method.
	 * 
	 * @return false if error occurs. Retrieve this error as a string using
	 *         getException().
	 */

	public boolean numeric() {

		try {
			name = Expression.valueOf(name).numeric().toString();
		} catch (ParseException e) {
			lastException = e.getMessage().toString();
			return false;
		}
		return true;

	}

	/**
	 * Numerical differentiation.
	 * 
	 * @param N
	 *            the number of points to use.
	 * @param min
	 *            the first ordinate.
	 * @param max
	 *            the last ordinate.
	 * @return array with differentials
	 */
	public double[] differentiate(final int N, final double min,
			final double max) {
		return jhplot.math.Numeric.differentiate(N, this, min, max);

	}

	/**
	 * Get the number of points
	 * 
	 * @return Number of points
	 */

	public int getPoints() {
		return this.points;

	}

	/**
	 * If error occurs at some step, this is the way to retrieve it.
	 * 
	 * @return last exception happened in any method of this class.
	 */
	public String getException() {
		return lastException;
	}

}