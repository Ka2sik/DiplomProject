## Дипломный проект.

В рамках дипломного проекта необходимо протестировать приложение.

### Описание тестируемого приложения.

Имеется веб-сервис, который предлагает купить тур по определённой цене двумя способами:

- оплата по дебетовой карте.
- выдача кредита по данным банковской карты.

Приложение пересылает данные по картам их банковским сервисам:

- сервису платежей Payment Gate.
- кредитному сервису Credit Gate.

Приложение в собственной СУБД сохраняет информацию о способе платежа и его статусе. 
Данные карт при этом сохранять не допускается.

### Подготовительный этап.

1. Установить и запустить IntelliJ IDEA.
2. Установать и запустить Docker Desktop.
3. Скопировать [репозиторий](https://github.com/Ka2sik/DiplomProject).
4. Открыть репозиторий в IntelliJ IDEA.

### Запуск тестов.

1. Запуск контейнеров NodeJS, MySQL, PostgreSQL в терминале командой: *docker-compose up*
2. Запуск тестируемого приложения в новой вкладке терминала:

Для базы MySQL: 

*java -jar artifacts/aqa-shop.jar*

Для базы PostgreSQL: 

в файле **application.properties** поставить решетку в начале 4 строки 
и снять решетку в начале 5 строки:

Ввести команду: *java -jar artifacts/aqa-shop.jar*

3. Проверить работоспособность приложения по адресу: http://localhost:8080/
4. Запуск тестов: 

Для базы MySQL: 

*gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app*

Для базы PostgreSQL:

*gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/app*

### Перезапуск тестов и приложения

Для остановки приложения в окне терминала нужно ввести команду **Ctrl+С**
и повторить запуск приложения и тестов.

### Формирование отчёта о тестировании

Подключены отчеты Allure. Для получения отчета в новой вкладке терминала ввести команду: *gradlew allureServe*

