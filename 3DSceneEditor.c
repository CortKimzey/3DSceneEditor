// C Program to illustrate the system function
#include <stdio.h>
#include <stdlib.h>

int main()
{
    //system("for /r src/com %f in (*.java) do javac -d bin \"%f\"");

    system("javac -d bin src/com/canvas/*.java src/com/frame/*.java src/*.java src/com/menu/*.java src/com/editor/*.java src/com/scene/*.java src/com/object/*.java src/com/use/*.java");
    system("echo Project Compiled");

    system("java -cp bin SceneEditor");
    return 0;
}