# Feeling App

## Como rodar o projeto

1. Crie um projeto no Firebase (https://console.firebase.google.com/).
2. Adicione o serviço de autenticação por email e senha no projeto Firebase.
3. Adicione o serviço de banco de dados Realtime Database no projeto Firebase e configure as regras de acesso.
4. Adicione o serviço de Storage no projeto Firebase.
5. Clone o projeto e abra no Android Studio.
6. Configure o Java 8 (coretto-1.8) no Gradle JDK e sincronize o projeto clonado.
7. Renomeie o nome do pacote do projeto clonado para o nome do pacote do projeto criado no Firebase.
8. Conecte o projeto clonado ao Firebase **automaticamente** pelo Android Studio **OU** manualmente adicionando o arquivo **google-services.json** na pasta **app**.