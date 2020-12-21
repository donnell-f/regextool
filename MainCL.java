import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.cli.*;

class MainCL
{
	public static void main(String args[])
	{
		Options opt = new Options();
		opt.addOption("f", "file", true, "File based input.");
		opt.addOption("s", "single", true, "Single-line formatted string input.");
		opt.addOption("r", "regex", true, "Regex pattern to use.");
		opt.addOption("g", "group", true, "Capture group to print. The whole match will be printed if you leave this blank or put some nonsense in it.");

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd;

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
		
		//==Lint==//

		/*
		if(cmd.hasOption("f") && cmd.hasOption("s"))
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
		*/

//trycatch here


		//final Pattern thepat = Pattern.compile("");
		

		System.out.println( String.valueOf( cmd.getOptionValue("g") ) );
	}

}