Adam Gleichsner (amg188)
eecs293
PA 10

**To run**
You can call Chemistry from the command line by giving it string arguments.

**Architecture**
Correctness is favored over robustness since primary function dictates the necessity of correct input.

Error-Handling architecture
-All variables are initialized to default valid values

-All regex patterns are declared private and final

-Throwing exceptions is favored over correcting or ignoring bad input

-However, all exceptions are caught for security reasons and the system is exited with code

-All input is checked for validity (i.e. is it null, empty, or contains whitespace")
In the case of incorrect input, we throw an IllegalArgumentException

-All input errors are stopped by helper methods before syntax checking begins (i.e. our barricade is before all data checking)
	-Barricade consists of all "throwIf" functions and, for main, the empty argument check

All in all, regex makes for some pretty secure code.
