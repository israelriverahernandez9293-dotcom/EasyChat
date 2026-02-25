# EasyChat Android

Aplicación Android base para EasyChat con autenticación por correo usando Firebase Authentication y guardado de usuarios en Cloud Firestore.

## Funcionalidades incluidas

- Registro con:
  - Correo electrónico
  - Teléfono
  - Contraseña + confirmación
- Envío de correo de verificación al crear la cuenta.
- Inicio de sesión solo permitido para correos verificados.
- Persistencia del perfil del usuario en `users/{uid}` en Firestore.
- Vista de inicio con diseño renovado y menú flotante doble:
  - `Iniciar chat`
  - `Videollamada`
- Persistencia local (SharedPreferences) para mantener interacciones de chat/videollamada, incluso al cerrar y abrir la app.

## Requisitos

1. Android Studio (Koala o superior recomendado).
2. Proyecto Firebase configurado.
3. Agregar `google-services.json` dentro de `app/`.
4. Habilitar en Firebase:
   - Authentication > Sign-in method > Email/Password.
   - Cloud Firestore.

## Estructura

- `AuthRepository`: integra Firebase Auth + Firestore.
- `AuthViewModel`: lógica de autenticación y estado de sesión.
- `HomeViewModel`: lógica de acciones del menú flotante y persistencia local.
- `LocalInteractionStore`: almacenamiento local de contador de acciones.
- `AuthScreen`: pantalla Compose de login/registro.
- `HomeScreen`: pantalla Compose de inicio con acciones rápidas.

## Próximos pasos sugeridos

- Navegación con `Navigation Compose` para flujo multi-pantalla.
- Conectar las acciones de chat/videollamada con servicios reales (WebRTC o proveedor backend).
- Añadir tests unitarios de `AuthViewModel` y `HomeViewModel`.
