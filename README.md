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

## Requisitos

1. Android Studio (Koala o superior recomendado).
2. Proyecto Firebase configurado.
3. Agregar `google-services.json` dentro de `app/`.
4. Habilitar en Firebase:
   - Authentication > Sign-in method > Email/Password.
   - Cloud Firestore.

## Estructura

- `AuthRepository`: integra Firebase Auth + Firestore.
- `AuthViewModel`: lógica de UI y validaciones.
- `AuthScreen`: pantalla Compose de login/registro.

## Próximos pasos sugeridos

- Navegación a pantalla principal después de login exitoso.
- Validaciones más estrictas para correo/teléfono/fortaleza de contraseña.
- Gestión de sesión persistente.
- Tests unitarios del `AuthViewModel`.
