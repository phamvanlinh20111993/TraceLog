package tracelog.phamlinh.example.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Dictionary;

import tracelog.phamlinh.example.console.ConsoleLogImpl;
import tracelog.phamlinh.example.logfile.LoggerFileImpl;
import tracelog.phamlinh.example.object.KeyPairValue;
import tracelog.phamlinh.example.object.RegexCondition;

public class Main {

	public static void main(String[] args) {
		System.out.println();

		ConsoleLogImpl console = new ConsoleLogImpl("My project prepare to interview");

		console.logError("Can not find value for arguments: %%va. and %%ag and %%ag --- and %%ag",
				new String("name"), 
				new String("qt3"),
				new String("n23r23ame"),
				new String("nr23r23Wame"));

		console.logInfo("Value not match for this argument1: %%{ag} <> %%{va}", 
				new String[] { "name" },
				new String[] { "Pham Van Linh" });

		console.logInfo("Value not match for list argument2: %%{ag} <> %%{va}", 
				new String[] { "name", "gender", "age" },
				new String[] { "Pham Van Linh", "180", "312313123" }); 
		
		console.logInfo("Value not match for list argument3: %%{s} <> %%{n}", 
				new String[] { "age", "num"},
				new float[] { 312321312.232f, 324.32f, 4324.324f, 232323.23423f, 13213123.9f}); 
	
		List<RegexCondition> test = new ArrayList<>();
		test.add(new RegexCondition("aaa", true, "type", "signalPrefix", "format"));
		HashMap<String, RegexCondition> valueTestMap = new HashMap<>();
		valueTestMap.put("test for key", new RegexCondition("dddđ", true, "we", "ddrưerewdđ", "drweddđ"));
		console.logWarning("Show list object: %%{ob} = %%{n}", new KeyPairValue[] {
				new KeyPairValue("karteId", "customerName"), new KeyPairValue("karteId1", "customerName1"), null,
				new KeyPairValue("karteId2", "customerName2", null, new Integer[] { -1,312321, 1, 2, 3, 4 }),
				new KeyPairValue("karteId3", "customerName3"),
				new KeyPairValue("karteId4", "customerName4", new RegexCondition("aaa", true, "aaa", "aaa", "aaa")),
				new KeyPairValue(test),
				new KeyPairValue("karteId5", "customerName5",
						new RegexCondition[] { new RegexCondition("bb", true, "bbb", "bbb", "bbb"),
								new RegexCondition("ccc", true, "cccc", "ccc", "ccc")})
				, new KeyPairValue("test map ok bavy", valueTestMap)},

				new Integer[] { 10, 100 });
		
		console.logInfo("Show list object number: %%{n} = %%n ", 
				new Float[] { -400.9999f, 121200.9999f, 
						100.9132999f, -3121100.9999f	
		}, new Integer(30));  
		
		
		console.logError("%%n7.2f <> %%n + %%n and %%{n} = %%{n}", 0423423.554353454535345f, new Integer(32), 3243, 
				new short[] {1, 1, 23}, new short[] {1, 1, 23});
		
		console.logInfo("%%ob <> %%ob and %%bl",  new KeyPairValue("karteId", "customerName"), 
				new KeyPairValue("karteId1", "customerName1"), true); 
		
				
		// https://www.codingunit.com/printf-format-specifiers-format-conversions-and-formatted-output
		console.logInfo("value = %%n", new Float[] {42423.32576567567563f});
		
		console.logInfo("value test array double: %%{n14.6f}", new double[] {42423.32576567567563d, 234234.423423d});
		
		List<Long> testA = new ArrayList<>();
		testA.add(1L);
		testA.add(2L);
		testA.add(3L);
		
		Map<String, Integer> testB = new LinkedHashMap<String, Integer>();
		testB.put("222", 32);
		testB.put("444", 132);
		testB.put("233", 2);
		
		console.logWarning("Show list object number collection: %%{n} = %%n and  %%{ag}", 
				testA, new Integer(30), testB);  
		
		byte tmp = 122;
		console.logWarning("Check type boolean and byte: %%bl = %%by", 
				new Boolean(true), tmp );  
		
		console.logInfo("Test value for value = %%ag", new Date());
		
//		Map<String, String> abc = new HashMap<>();
//		abc.put("fas", "gdfdgdf");
	//	System.out.println("check " + TraceLogUtils.isJavaUtilObject(test));
		
//		Double numb = 1222222222222222222222222222223333333333331.433333333333333d;
//		System.out.println("number is " + numb);
//		String format = String.format("%34.4444442f", numb);
//		System.out.println("After format " + format);
		
		
	/*	Double numb = 122222.43333344444444444444433333d;
		System.out.println("number is " + numb);
		DecimalFormat df = new DecimalFormat("###################################.###############################################");
		String format = df.format(numb);
		System.out.println("After format " + format);
		
		Number s = new Float(300.23423);
		System.out.println(s.toString()); */
	
		
		//System.out.println(TraceLogUtils.repeatStart("hhe", ".", 3000));
		
		LoggerFileImpl loggerFile = new LoggerFileImpl();
		
		loggerFile.logFileError("Show list object: %%{ob} = %%{n}", new KeyPairValue[] {
				new KeyPairValue("karteId", "customerName"), new KeyPairValue("karteId1", "customerName1"), null,
				new KeyPairValue("karteId2", "customerName2", null, new Integer[] { -1,312321, 1, 2, 3, 4 }),
				new KeyPairValue("karteId3", "customerName3"),
				new KeyPairValue("karteId4", "customerName4", new RegexCondition("aaa", true, "aaa", "aaa", "aaa")),
				new KeyPairValue(test),
				new KeyPairValue("karteId5", "customerName5",
						new RegexCondition[] { new RegexCondition("bb", true, "bbb", "bbb", "bbb"),
								new RegexCondition("ccc", true, "cccc", "ccc", "ccc")})
				, new KeyPairValue("test map ok bavy", valueTestMap)},

				new Integer[] { 10, 100 });
		
	}
}
