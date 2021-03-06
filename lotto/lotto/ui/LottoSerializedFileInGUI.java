/*
 * Created on Oct 20, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package lotto.ui;


import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JTextArea;


import lotto.db.*;
import lotto.model.*;
import utilities.ConsoleGUI;

/**
 * @author bempn
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LottoSerializedFileInGUI {
	public static void showInput(LottoSerializedFileIn in)
		throws IOException, FileNotFoundException, ClassNotFoundException {
		String inputStream = ConsoleGUI.readString("\nInput File: ");
		ArrayList <LottoCombination>serializedList = in.getSerializedList(inputStream);
		ConsoleGUI.write(
			"\n"
				+ serializedList.size()
				+ " lottocombinations have been read from disk:\n");
		in.fillUp(serializedList);
		JTextArea output;
		if (!in.getUniek().isEmpty()) {
			output = new JTextArea("Combinations found more than ones:\n");
			output.append("Combinations\t\t#\n");
			LottoCombination actueelElement;
			for (Iterator <LottoCombination>iteration = in.getUniek().iterator();
				iteration.hasNext();
				) {
				actueelElement = (LottoCombination) iteration.next();
				output.append(
					actueelElement.toString()
						+ "\t"
						+ ((Integer) in
							.getCount()
							.get(in.getUniek().indexOf(actueelElement)))
							.intValue()
						+ "\n");
			}
			ConsoleGUI.write(output, "Unieke combinaties");
		} else
			ConsoleGUI.write("None of the combinations exists more than ones");
	}

	public void run() {
		LottoSerializedFileIn in = new LottoSerializedFileIn();
		try {
			showInput(in);
		} catch (FileNotFoundException e) {
			ConsoleGUI.write("File not found");
		} catch (IOException e) {
			ConsoleGUI.write("IO ERROR!");
		} catch (Exception e) {
			ConsoleGUI.write("no elements found");
		}
	}

}
