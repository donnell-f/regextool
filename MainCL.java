import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import org.apache.commons.text.StringEscapeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

class MainCL
{
	public static void main(String args[])
	{
		//Variables for the terminal options.
		String thestuff = "";
		String thepattern = "";
		int capgroup = 0;
		Boolean replace = false;
		String reptext = "";

		//Define the terminal options that should be recognised and what they do.
		Options opt = new Options();
		
		opt.addOption("", "help", false, "Show the help message.");
		opt.addOption("f", "file", true, "Use the path to a (f)ile as the input.");
		opt.addOption("s", "single", true, "Use a (s)ingle-line string as the input. Note: escape sequences apply.");
		opt.addOption("r", "regex", true, "The (r)egex pattern to use.");
		opt.addOption("g", "group", true, "The capture (g)roup to print or replace. The whole match will be selected if you leave this blank.");
		opt.addOption("p", "replace", true, "The re(p)lacement text. Replaces the selected text instead of printing it.");

		//Make a parser to get the arg values.
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd;

		//Hopefully use our parser from before to parse the args into Apache standard options.
		try
		{
			cmd = parser.parse(opt, args);
		}
		catch (ParseException e)
        { 
            System.out.println(e.getMessage());
            System.out.println("Error: Can't parse arguments for some reason.");
            return;
		}
	




		//==== Arg Linting ====//
		
		if( args.length == 0 || cmd.hasOption("help"))
		{
			System.out.println("Regextool - easy regex on-demand.");
			System.out.println("");
			System.out.println("Usage: regextool {-s | -f} -r [-p] [-g]");
			System.out.println("--help | Show the help message.");
			System.out.println("-f, --file | Use the path to a (f)ile as the input.");
			System.out.println("-s, --single | Use a (s)ingle-line string as the input. Note: escape sequences apply.");
			System.out.println("-r, --regex | The (r)egex pattern to use.");
			System.out.println("-g, --group | The capture (g)roup to print or replace. The whole match will be selected if you leave this blank.");
			System.out.println("-p, --replace | The re(p)lacement text. Replaces the selected text instead of printing it.");
			System.out.println("");
			System.out.println("Example:");
			System.out.println("./regextool -s \"i \\\"hate\\\" this\" -r \"(\\\"([^\\\"]+)\\\")\" -g 2");
			return;
		}
		
		//Make sure the user is only using one input type.
		if(cmd.hasOption("f") && cmd.hasOption("s"))
		{
			System.out.println("Error: Can't use both file and input");
			return;
		}

		//Make sure the user actually has an input.
		if( !(cmd.hasOption("s") || cmd.hasOption("f")) )
		{
			System.out.println("Error: Must have input.");
			return;
		}

		//Make sure the user has regex.
		if( !(cmd.hasOption("r")) )
		{
			System.out.println("Error: Must have a regex pattern.");
			return;
		}





		//========= Variable Filling ==========//

		//Set the replace values if specified.
		if(cmd.hasOption("p"))
		{
			replace = true;
			reptext = cmd.getOptionValue("p");
		}

		//Read the group.
		if(cmd.hasOption("g"))
		{
			try
			{
				capgroup = (int)( Integer.parseInt( cmd.getOptionValue("g") ) );
			}
			catch(Exception e)
			{
				System.out.println("Error: Capture group must be a number.");
				return;
			}	
		}
		else
		{
			capgroup = 0;
		}
		
		//Read the file that the user gives.
		if(cmd.hasOption("f"))
		{
			try
			{
				File file = new File( cmd.getOptionValue("f") );
				Scanner sc = new Scanner(file);

				while(sc.hasNextLine())
				{
					thestuff += sc.nextLine() + "\n";
				}
				
				sc.close();
			}
			catch (Exception e) { System.out.println("Error parsing file."); return; }

		}

		//Read the string input that the user gives.
		if(cmd.hasOption("s"))
		{
			thestuff = StringEscapeUtils.unescapeJava(cmd.getOptionValue("s"));
		}

		//Read the regular expression.
		if(cmd.hasOption("r"))
		{
			thepattern = cmd.getOptionValue("r");
		}





		//==== Regex ====//

		if(replace == true)
		{
			System.out.println( replaceAllGroup(thestuff, thepattern, reptext, capgroup) );
		}
		else
		{
			//Matcher matching = Pattern.compile(thepattern).matcher(thestuff);
			Matcher matching = Pattern.compile(thepattern).matcher(thestuff);

			while(matching.find())
			{
				try
				{
					System.out.println(matching.group(capgroup));
				}
				catch (Exception e)
				{
					System.out.println("Error: capture group out of range.");
					return;
				}
			}
		}

	}
	
	public static String replaceAllGroup(String input, String pat, String repl, int group)
	{
		Matcher m = Pattern.compile(pat).matcher(input);

		ArrayList<Integer> starts = new ArrayList<Integer>();
		ArrayList<Integer> ends = new ArrayList<Integer>();

		//While you can find the next group...
		while(m.find())
		{
			//add the start and end coords of each group match to the ArrayLists.
			starts.add( (Integer)(m.start(group)) );
			ends.add( (Integer)(m.end(group)) );
		}

		int lenIndices = starts.size();
		int strEnd = input.length();
		String newStr = "";

		if(starts.size() > 0)
		{
			newStr += input.substring(0, starts.get(0));

			for(int i = 0; i < lenIndices; i++)
			{
				//Add I-th match to the output string.
				newStr += repl;
	
				//Add the bit after that and inbetween the (I+1)-th match to the output string.
				if( (i+1) < lenIndices )
				{
					newStr += input.substring(ends.get(i), starts.get(i+1));
				}
			}
	
			newStr += input.substring(ends.get(lenIndices-1), strEnd);

			return newStr;
		}
		else
		{
			return input;
		}
	}
}