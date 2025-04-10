# Chat AI

Estamos desarrollando una App para chatear con una IA.

Para ello, tendremos una pantalla principal, donde podremos iniciar conversaciones con la IA, de tal forma que por cada mensaje que enviemos, la IA nos devuelva una respuesta.

Esta IA siempre debe recibir como contexto toda la conversación previa, para que le sea más sencillo generar una nueva respuesta coherente.

Además, también desarrollaremos la funcionalidad de historial, donde tendremos un menú lateral que podremos desplegar y seleccionar conversaciones previas. Este menú, tendrá un botón en la parte superior que nos permita iniciar una nueva conversación.

## Tecnologías

Estas son las tecnologías que vamos a utilizar para cada una de las funcionalidades

- Jetpack Compose como sistema de interfaces.
- Material 3 como sistema de diseño.
- Kotlin como lenguaje de programación.
- Los ViewModels de Architecture Components para la comunicación entre la UI y la capa de datos.
- Hilt como inyector de dependencias.
- Para persistencia de las conversaciones, utilizaremos Room. Recuerda usar KSP (la versión actual es 2.1.20-1.0.32) y no KAPT para las dependencias que generan código, como el compiler de Room.
- La API de OpenAI para la parte de inteligencia artificial.
- Para conectarnos con OpenAI, utilizaremos la librería de la comunidad open-ai kotlin https://github.com/Aallam/openai-kotlin

## Arquitectura
Vamos a usar una arquitectura sencilla, donde tendremos:
- La UI en Compose
- Comunicación con la capa de datos mediante MVVM
- La capa de datos estará formada por repositorios, que ocultarán qué librerías concretas se están utilizando.

## Reglas extra

- Siempre que termines de generar un código, compílalo para ver que no hay problemas. Para ello, utiliza compileDebugKotlin
- Aunque pienses que los build.gradle.kts están incorrectos, los que tienes ahora mismo en el contexto son válidos. Si tienes que modificar el libs.versions o los ficheros gradle, simplemente añade lo nuevo que necesites, y no modifiques lo que ya existe.
- No incluyas comentarios solo para explicar lo que ya hace el código. Solo en caso de que haya alguna parte que se quede así porque en el futuro haya que añadir nueva funcionalidad.
