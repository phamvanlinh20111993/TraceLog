package tracelog.phamlinh.example.main;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tracelog.phamlinh.example.console.ConsoleLogImpl;
import tracelog.phamlinh.example.object.KeyPairValue;
import tracelog.phamlinh.example.object.RegexCondition;

public class Main {

	public static void main(String[] args) {
		System.out.println();

		ConsoleLogImpl console = new ConsoleLogImpl();

		console.logError("Can not find value for arguments: %%ag. and %%ag and %%ag --- and %%ag",
				new String[] { "name"}, 
				new String[] { "qt3" },
				new String[] { "n23r23ame"},
				new String[] { "nr23r23Wame"});

		console.logInfo("Value not match for this argument: %%ag <> %%va", 
				new String[] { "name" },
				new String[] { "Pham Van Linh" });

		console.logInfo("Value not match for list argument: %%{ag} <> %%{va}", 
				new String[] { "name", "gender", "age" },
				new String[] { "Pham Van Linh", "180", "312313123" }); 
		
		console.logInfo("Value not match for list argument: %%{s} <> %%{n}", 
				new String[] { "age", "num"},
				new Integer[] { 312321312, 13213123});
	
		console.logWarning("Show list object: %%{ob} = %%{n}", new KeyPairValue[] {
				new KeyPairValue("karteId", "customerName"), new KeyPairValue("karteId1", "customerName1"), null,
				new KeyPairValue("karteId2", "customerName2", null, new Integer[] { 1, 2, 3, 4 }),
				new KeyPairValue("karteId3", "customerName3"),
				new KeyPairValue("karteId4", "customerName4", new RegexCondition("aaa", true, "aaa", "aaa", "aaa")),
				new KeyPairValue("karteId5", "customerName5",
						new RegexCondition[] { new RegexCondition("bb", true, "bbb", "bbb", "bbb"),
								new RegexCondition("ccc", true, "cccc", "ccc", "ccc")}) },

				new Integer[] { 10, 100 });
		
		console.logError("Show list object number: %%{n}.", 
				new Float[] { -400.9999f, 121200.9999f, 
						100.9132999f, -3121100.9999f	
		});
		
		console.logInfo("%%n <> %%n", 0423423.5f, 4654645);
		
		console.logInfo("%%ob <> %%ob",  new KeyPairValue("karteId", "customerName"), 
				new KeyPairValue("karteId1", "customerName1"));
		
				
		// https://www.codingunit.com/printf-format-specifiers-format-conversions-and-formatted-output
	//	console.logError("value = %%n", new Float[] {42423.32576567567563f});
	}
}
