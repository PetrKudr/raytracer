Ray tracer
=========

Ray tracer for the University

How to build:

1. Install Maven (skip if it is already installed on you machine):
  - Download Maven 3.0.x from http://maven.apache.org/download.cgi
  - Add environment variable M2_HOME pointing to the Maven folder
  - Add %M2_HOME%/bin to the Path variable
2. Change directory to ".../RayTracer" (where the POM file is located)
3. Type 'mvn clean', then 'mvn compile' and then 'mvn install'
4. RayTracer-\<version\>-jar-with-dependencies.jar under the target directory is an executable ray tracer


To get help execute it without any params:

java -jar RayTracer-1.1-jar-with-dependencies.jar

To render built-in scene 1:

java -jar RayTracer-1.1-jar-with-dependencies.jar -scene default_1 -output image_1 -resolution_x 512 -resolution_y 512

To render built-in scene 2 (cow model):

java -jar RayTracer-1.1-jar-with-dependencies.jar -scene default_2 -output image_2 -resolution_x 256 -resolution_y 256

To render built-in scene 3 (CSG object):

java -jar RayTracer-1.1-jar-with-dependencies.jar -scene default_3 -output image_3 -resolution_x 512 -resolution_y 512

To render own scene:

java -jar RayTracer-1.1-jar-with-dependencies.jar -scene \<path/to/scene.xml\> -output \<image_name\> -resolution_x \<resX\> -resolution_y \<resY\>
