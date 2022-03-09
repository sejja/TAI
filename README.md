Descripción:
El repositorio contine un sitio web que permite realizar test TAI, tanto crearlos como contestarlos, algunas funciones estan protegiddas por usuario y contraseña como crear TAI u obtener los resultadas de un TAI.

Estado:
El estado del desarrollo software proyecto está completado pero en fase de pruebas.

Requisitos del entorno:
Node.js v16.14.0
Java 11
MySQL 8.0.26
Docker version 20.10.10

Guía para la instalación:
Se recomienda usar Docker para el despliege, mediande el comando $docker-compose up en el directorio raiz del proyecto.
Tambien es posible su ejecución entrando en los directorios apiAuth, apiTAI, y myClient e iniciarlos uno por uno.

Para instalar en windows 10 sin docker se requiere windows x64 y la creadion de una conaxion mySQL de administración con nombre de usuario "root" y contraseña "root". Esto puede supener un problema de seguridad en fase de produción que habría que solventar.

La ejecución se realizará mediante en archivo run.bat, en caso de aparecer ventanas de administración para la conficuración de la red, es imprescindible permitir todo para que los usuarios de la red puedan acceder al programa y el firewall no bloque las conexiones de los demas usuarios

Funcionamiento:
Inicialmente la aplicación no continen ningún test, para crear uno se debera inicar sesión como administrador en el sitio web. Las sesiones de administración caducan a la hora de haberse logeado. Al arrancar la aplicación por primera vez se creará un usuario "root@root.com" con contraseña "root", este es el usuario por defecto y se recomienda borrarlo o edirar su contraseña en cuanto sea posible.

Los usuario administrador pueden borrar o edirar a otros usuarios administrador por lo tanto los permisos que tendra cualquire usuario registrado son los mismos. La aplicación no permite borrar todos los usuarios administrador siempre tendrá que haber al menos uno. En el caso de olvido de usuario y contraseña. Se tendrá que reiniciar el microservicio apiAuth sin manipilar ningún microservicio más.

Los administradores serán los que puedan crear Tais desde la pantalla de administración seleccionando nuevo Tai.
Un TAI bien formado debe de tener almenos 6 imagenes de cada CATEGORÍA y 6 palabras de cada CUALIDAD.

Tambien podrán eliminarlos, visualizar los resutados de las respuestas realizardas y descargar los datos en formato .csv.

Las imagenes que son utilizadas en un TAI se almacenan en la carpeta "uploads" que se crea en el directorio apiTAI o en caso de despliege mediante docker en el directorio raiz del contenedor del microservicio de apiTAI. Por lo tanto si durante el despliege se borrar este contenedor las imagenes de todos los Tais exitentes Tambien se borraran. Se recomienda realir cópias de seguridad de esta carpeta cada vez que se modifique la lista de Tais exitentes ya sea creando uno nuevo o borrando uno que ya exixtía. En caso de perdida de información retaurar esta carpeta con las imagenes.

Los usuarios que realizen el test no tienen porque estar registrados, desde la pantalla de inicio, seleccionando Empezar, se visualizará una lista de Tais que pueden ser realizados. Su realizacion consiste en tareas de clasificacno de imágens y palabras, se permite un máximmo de un 40% de respuestas invalidas por fase por Tai, en el caso de que se superen la respuesta se da como invalidas y no se calculan los resultados. si durante alguna de las fases de entrenamineto el usuario tienen un 40% o más de respuestas invalidas se repite la fase de entrenamineto hasta que la complete correctamente.

Una respuestas es valida si no es erronea y el tipo de respuestas está entre 300ms y 10000ms.

El test consta de 7 bloques. 1, 2 y 5 no se tienen en cuanta para la obtencion del resultado sino que son tareas de entrenamiento para que el usuario se acostrumbre al test. Los bloque 3, 4, 6 y 7 son con los que se calculan los resulados. en los bloques 3 y 4 se asocian la PRIMERA CATEGORÍA con la PRIMERA CUALIDAD y SEGUNDA CATEGORÍA con SEGUNDA CUALIDAD. En los bloques 6 y 7 se asocian la SEGUNDA CATEGORÍA con la PRIMERA CUALIDAD y PRIMERA CATEGORÍA con SEGUNDA CUALIDAD. En el test apareceren de foma aleatoria antes los bloques 3 y 4 o 6 y 7 pero a la hora me mostar los resultados siempre apareceren en el orden 3, 4, 6, 7.

Finalmente el cálculo de resultados consiste en la obtención de la métrica "d-score". Para calcularse la realizacion debe de ser válida, dede tener menor de un 40% de respuestas invalidas. Si es válido el tiempo de respuestas de cada invalida se sustituye por la media de las validas más 600ms. Despues se calculan el tiempo de respuestas medio de los bloques 3, 4, 6 y 7, con nombre mb3, mb4, mb6 y mb7 respectivamete, Tambien se calculan las desviaciones estandar de cada bloque, std3, std4, std6 y std7 respectivamete. Seguidamente se calculan la desviación estandar de los bloques 3 y 6 en conjunto y 4 y 7, std36, std47 respectivamete. Para terminar se calculan al diferencia de mb6 - mb3 = diff63 y mb7 - mb4 = diff74. Por último se obtienen dos dscores la dscore36 y dscore47 aunque es considera dscore47 el resultado valido. dscore47 = diff74 / std47.

Bugs:
No se han detentado Bugs por el momento.
