import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class MainCL
{
	public static void main(String args[])
	{
		//Variables for the terminal options.
		String thestuff = "";
		String thepattern = "";
		int capgroup = 0;

		//Define the terminal options that should be recognised and what they do.
		Options opt = new Options();
		opt.addOption("f", "file", true, "Path to a file with the input.");
		opt.addOption("s", "single", true, "Single-line formatted string input.");
		opt.addOption("r", "regex", true, "Regex pattern to use.");
		opt.addOption("g", "group", true, "Capture group to print. The whole match will be printed if you leave this blank or put some nonsense in it.");

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
		
		//==Arg Linting==//
		
		/*if(cmd.hasOption("f") && cmd.hasOption("s"))
		{
			System.out.println("Error: Can't use both file and input");
			return;
		}

		if( !(cmd.hasOption("s") || cmd.hasOption("f")) )
		{
			System.out.println("Error: Must have input.");
			return;
		}

		if( !(cmd.hasOption("r")) )
		{
			System.out.println("Error: Must have a regex pattern.");
			return;
		}

		try
		{

		}
		catch(Exception e)
		{
			System.out.println("Error: Capture group must be a number.");
			return;
		}*/

		//==Variable Filling==//
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

				System.out.println(thestuff);
			}
			catch (Exception e) { System.out.println("Error parsing file."); }
		}
		



		//final Pattern thepat = Pattern.compile("");
	}

}