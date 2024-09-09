# Как использовать данное приложение

Для начала необходимо скачать код из репозитория на свой компьютер.

## Конфигурация проекта

1. Откройте файл `application.properties` и измените некоторые параметры:
    - `server.port=7777` — HTTP порт для запуска приложения.
    - `server.https.port=8443` — HTTPS порт без сертификата для запуска приложения.

2. В файле присутствуют закомментированные строки для работы с HTTPS соединением с сертификатом. Заполните их по необходимости.

3. Чтобы не пересобирать проект каждый раз, файл `application.properties` можно положить рядом с `.jar` файлом, и он будет иметь приоритет для Spring.

## Компиляция в IntelliJ IDEA

1. Скачайте проект из репозитория.

2. Установите все зависимости из файла `.pom`.

3. Выполните команду `mvn clean package` (она соберёт `.jar` файл с зависимостями).

## Запуск `.jar` на сервере

1. Для запуска на Linux в фоновом режиме есть два варианта:
    - Запустить через `nohup`:
      ```bash
      nohup java -jar rogii-0.0.1-SNAPSHOT.jar &
      ```
      Обратите внимание, что этот метод не обеспечит автоматический запуск при перезагрузке сервера.
    - Сделать файл `.sh` с командой запуска `.jar` и создать службу `systemd`:
        - Создайте файл скрипта (например, `/usr/local/bin/start-my-app.sh`):
          ```bash
          #!/bin/bash
          java -jar /path/to/your-app.jar
          ```
        - Убедитесь, что скрипт исполняемый:
          ```bash
          sudo chmod +x /usr/local/bin/start-my-app.sh
          ```
        - Создайте файл сервиса `systemd` (например, `/etc/systemd/system/my-app.service`):
          ```ini
          [Unit]
          Description=My Java Application
          After=network.target
   
          [Service]
          Type=simple
          User=your-username
          ExecStart=/usr/local/bin/start-my-app.sh
          Restart=on-failure
          RestartSec=10
   
          [Install]
          WantedBy=multi-user.target
          ```
        - Перезагрузите `systemd` и запустите службу:
          ```bash
          sudo systemctl daemon-reload
          sudo systemctl start my-app.service
          sudo systemctl enable my-app.service
          ```
        - Проверьте статус службы:
          ```bash
          sudo systemctl status my-app.service
          ```
