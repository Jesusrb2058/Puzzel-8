# Puzzle 8 - Juego de Rompecabezas.

# Así debe verse el juego finalizado 

<img src="Puzzle_8/Img/Juego Terminado .jpeg" alt="Vista del juego" width="50">

# Descripción

Este proyecto implementa un juego de Puzzle 8 en Java utilizando la biblioteca Swing para la interfaz gráfica. El objetivo del juego es organizar las piezas numeradas del 1 al 8 en el orden correcto moviendo una a la vez, utilizando el espacio vacío como guía. El juego permite realizar movimientos con el mouse y cuenta con un contador de movimientos y un temporizador para medir el progreso.

# Características

- Interfaz Gráfica: Utiliza JFrame y JPanel para la estructura visual y JButton para cada una de las piezas del puzzle.
- Dificultad Configurable: El jugador puede seleccionar entre tres niveles de dificultad (Fácil, Medio y Difícil).
- Control de Movimientos: Cada movimiento realizado es contado y se muestra en un contador.
- Timer: Incluye un temporizador para medir el tiempo total transcurrido desde el inicio del juego.
- Funciones de Control:
Adelante y Atrás para navegar entre los movimientos realizados.
Resetear para reiniciar el juego con una nueva disposición aleatoria de las piezas.
- Validación de Solución: Al resolver el puzzle, se muestra un mensaje con el número de movimientos realizados y el tiempo total.

# Requisitos

- Java JDK 8 o superior.
- Entorno de desarrollo como Eclipse o IntelliJ IDEA.

# Explicación del Código
El archivo Puzzle8.java contiene la implementación principal del juego. A continuación se presenta una descripción de las principales secciones del código:

- Clase Principal (Puzzle8): Extiende JFrame e implementa ActionListener y MouseListener para manejar eventos de botones y movimientos del ratón.- 
- Componentes de la Interfaz:
- JPanel puzzlePanel: Panel principal que contiene los botones del puzzle.
- JPanel buttonPanel: Panel inferior con botones de control y etiquetas de información.
- JButton[] puzzleButtons: Arreglo que representa cada una de las piezas del puzzle.
- JLabel moveCounterLabel: Etiqueta que muestra el número de movimientos realizados.
- JLabel timerLabel: Etiqueta que muestra el tiempo transcurrido.

# Métodos Principales:
- Puzzle8(): Constructor principal que inicializa la interfaz gráfica y establece la disposición inicial del puzzle.
- setDifficulty(): Método que permite seleccionar la dificultad del juego y reorganiza las piezas aleatoriamente según el nivel.
- shufflePuzzle(int difficulty): Reorganiza las piezas del puzzle según la dificultad seleccionada.
- startTimer(): Inicia el temporizador para medir el tiempo transcurrido.
- updateMoveCounter(): Actualiza el contador de movimientos en la interfaz.
- avanzar(): Deshace el último movimiento realizado usando una pila (Stack).
- retroceder(): Rehace el movimiento previo usando una pila (Stack).
- resetPuzzle(): Reinicia el puzzle con una nueva disposición aleatoria.
- mouseClicked(MouseEvent e): Detecta un clic en las piezas y verifica si se pueden mover. Si se completa el puzzle, muestra un mensaje de éxito.
- isPuzzleSolved(): Verifica si las piezas del puzzle están en el orden correcto.

# Funciones de Interfaz
Botón Adelante: Deshace el último movimiento.
Botón Atrás: Rehace un movimiento anterior.
Botón Resetear: Reinicia el puzzle y restablece el temporizador.

# Ejemplo de Uso
Al iniciar el programa, el usuario puede seleccionar la dificultad del juego, tras lo cual las piezas del puzzle se desordenan. El jugador puede mover las piezas utilizando el ratón para hacer clic en las piezas adyacentes al espacio vacío. Al resolver el puzzle, se mostrará un mensaje con el tiempo total y los movimientos realizados.

# Compilar el Código en un .jar
Primero, debes compilar el archivo .java y empaquetarlo en un archivo .jar. Si ya tienes el código en un archivo .jar, puedes omitir esta parte y saltar a la sección de ejecución.

Compila tu código fuente: Asegúrate de que tienes el archivo Puzzle8.java en la carpeta actual y compílalo con el siguiente comando en la terminal:


javac Puzzle8.java
Esto generará un archivo .class para cada clase en tu proyecto (en este caso, Puzzle8.class).

Crear el archivo .jar: Empaqueta las clases compiladas en un archivo .jar. Si el nombre de tu clase principal es Puzzle8, asegúrate de crear un MANIFEST.MF con la siguiente estructura:

Crea un archivo de texto llamado MANIFEST.MF (asegúrate de que no haya líneas en blanco al final).

- 
Manifest-Version: 1.0
Main-Class: Puzzle8
Luego, usa el siguiente comando para empaquetar el .jar:

- 
jar cfm Puzzle8.jar MANIFEST.MF Puzzle8.class
Esto creará un archivo Puzzle8.jar en la misma carpeta.

2. Ejecutar el Archivo .jar
Una vez que tengas el archivo .jar, puedes ejecutarlo utilizando la siguiente instrucción:

En la terminal: Si estás en la misma carpeta donde está el archivo Puzzle8.jar, usa:

- 
java -jar Puzzle8.jar
Con doble clic (en Windows/Mac):

En Windows: Si tienes instalado Java correctamente, simplemente haz doble clic en el archivo Puzzle8.jar para ejecutarlo.
En Mac: Dependiendo de la configuración, es posible que tengas que dar permisos adicionales. Intenta hacer clic derecho y seleccionar "Abrir con" > "Java Runtime Environment".

3. Problemas Comunes al Ejecutar .jar
Verifica la instalación de Java: Asegúrate de tener instalada una versión compatible de Java (por ejemplo, JDK 8 o superior). Para verificar la versión, ejecuta:

- 
java -version
Configura las variables de entorno: Asegúrate de que java esté configurado en las variables de entorno PATH para que el comando java -jar funcione correctamente.

Errores de NoClassDefFoundError: Si recibes errores de clases no encontradas, asegúrate de que el archivo MANIFEST.MF esté correctamente configurado y de que las clases estén dentro del .jar.

4. Empaquetado de Proyectos Más Complejos
Si tu proyecto tiene múltiples archivos .java o dependencias externas, asegúrate de compilar todos los archivos a la vez y empaquetar el .jar correctamente incluyendo todas las clases:

- 
javac -d . *.java
jar cfm Puzzle8.jar MANIFEST.MF Puzzle_8/*.class
De esta manera, el .jar contendrá todas las clases necesarias para ejecutar el juego correctamente.

# Autor
Desarrolladores del proyecto. 

- Jesus Ruben Rodriguez Bernal 
- Felipe Ramirez Cortes
- Yonath Hortega Herrera 

