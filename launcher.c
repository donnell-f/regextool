#include <stdio.h>
#include <stdlib.h>
#include <unistd.h> 
#include <string.h>

//The Regextool launcher... this is the product of bash not working well with regex.

int main( int argc, char *argv[] )
{
    //Go to the directory with the actual .jar file.
    chdir("./build");
    


    //Find the size of the initial command and args.
    char init_command[] = "java -jar ./RegexTool.jar";
    unsigned int strsize = strlen(init_command)*4096;

    for(int i = 1; i < argc; i++)
    {
        //Multiply by 4096 to stop strsize from having an identity crisis (segmentation fault).
        strsize += strlen(argv[i])*4096;

        //This is to account for the unescaped slashes and quotes that I need to re-escape...
        for(int j = 0; j < strlen(argv[i]); j++)
        {
            if( (argv[i][j] == '\\') || (argv[i][j] == '\"') )
            {
                strsize += 4096;
            }
        }
    }

    //Allocate memory for adding back the quotes that bash so rudely takes out...
    strsize += argc * 8192;



    char cmd_final[strsize];

    strcat(cmd_final, init_command);

    //Compile the command and all args into the cmd_final string.
    for(int j = 1; j < argc; j++)
    {
        strcat(cmd_final, " ");

        if(argv[j][0] != '-')
        {
            strcat(cmd_final, "\"");

                //Go through all the characters in the args and sort out the stuff the slow way, becasue at this point speed is beneath me.
                for(int h = 0; h < strlen(argv[j]); h++)
                {
                    //If the current character must have been unescaped, then escape it.
                    if(argv[j][h] == '\"')
                    {
                        strcat(cmd_final, "\\");
                    }
                    if(argv[j][h] == '\\')
                    {
                        strcat(cmd_final, "\\");
                    }

                    //Continue to pipe the arg characters into the final command.
                    strncat(cmd_final, &argv[j][h], 1);
                }

            strcat(cmd_final, "\"");
        }
        else
        {
            strcat(cmd_final, argv[j]);
        }
        
    }

    //For reading the output stream from the command + args (cmd_final).
    FILE *fp;
    char store[1035];

    //Open the command for reading.
    fp = popen(cmd_final, "r");
    if (fp == NULL) {
        printf("Failed to run command.\n");
        exit(1);
    }

    while (fgets(store, sizeof(store), fp) != NULL) {
        printf("%s", store);
    }

    //Close command output reader.
    pclose(fp);
}