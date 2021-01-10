### RegexTool - Easy command line regex for Linux.
RegexTool is a simple app I created to make regex replacement and selection easy and efficient.


### Features:
- Replace regex matches by group.
- Select regex matches by group.
- Replace all regex matches in a file.
- Select all regex matches in a file.
- etc.


### Usage:
| Short  | Long  | Description  |
| ------------ | ------------ | ------------ |
| N/A  | --help  | Show the help message.  |
| f  | --file  |  Use the path to a (f)ile as the input. |
| s  |  --single | Use a (s)ingle-line string as the input. Note: escape sequences apply.  |
| r  | --regex  |  The (r)egex pattern to use. |
|  g | --group  | The capture (g)roup to print or replace. The whole match will be selected if you leave this blank.  |
| p  | --replace   | The re(p)lacement text. Replaces the selected text instead of printing it. |


### Examples:
```bash
./regextool -s "this \"is\"  an example" -r "(\"([^\"]+)\")" -g 2
./regextool -s "lorem ipsum dolor..." -r "ipsum" -p "lorem"
./regextool -f "/path/to/file" -r "ipsum" -p "lorem"
./regextool -s "lorem lorem ipsum ipsum dolor lorem..." -r "(lor)(em)" -g 2 -p "emit"
./regextool -s "lorax lorrey lord lorem ipsum legit lorem..." -r "(lor)(\S+)" -g 2
```
Note: examples are intended to run from the project directory.


### Warnings:
- Because this is a CLI app, you may have to double escape your regex. For instance the \w token (any non-whitespace) should be typed as \\w.
- This is Java regex so it may work slightly differently than the PCRE regex we all know and love.
