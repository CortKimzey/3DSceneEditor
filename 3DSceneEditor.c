// C Program to illustrate the system function
#include <stdio.h>
#include <stdlib.h>

int main()
{
    system("javac -d bin src/com/canvas/*.java src/com/frame/*.java src/*.java src/com/menu/*.java src/com/editor/*.java src/com/scene/*.java src/com/use/*.java src/com/room/*.java src/com/file/*.java src/com/object/*.java src/com/point/*.java");
    system("echo Project Compiled");

    system("java -cp bin SceneEditor");
    return 0;
}
