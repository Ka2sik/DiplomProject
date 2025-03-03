# План автоматизации тестирования

## 1. Перечень автоматизируемых сценариев

### **Позитивные сценарии**

### Корректные данные для тестовых сценариев

#### 1. Поле "Номер карты"

16 цифр, карта из базы данных.

#### 2. Поле "Месяц"

Цифры от 01-12.

#### 3. Поле "Год"

Две цифры текущий год и старше.

#### 4. Поле "Владелец"

Имя и фамилия латинскими буквами.

#### 5. Поле "CVC/CVV"

Три цифры.

#### Тест №1

1. Провести успешную покупку с карты 4444 4444 4444 4441 в статусе Approved

#### Тест №2

1. Провести успешную покупку в кредит с карты 4444 4444 4444 4441 в статусе Approved

#### Тест №3

1. Провести покупку с карты 4444 4444 4444 4442 в статусе Declined,
   убедиться в появлении ошибки, т.к. карта заблокирована

#### Тест №4

1. Провести покупку в кредит с карты 4444 4444 4444 4442 в статусе Declined,
   убедиться в появлении ошибки, т.к. карта заблокирована

### **Негативные тестовые сценарии**

Заполнить все поля корректными данными, кроме одного поля, в котором будут
некорректные данные. Таким образом проверить все поля формы для покупки с карты и в кредит.

### Некорректные данные для тестовых сценариев

#### 1. Поле "Номер карты"

Карта не из базы данных.

Пустое поле.

Заполнение пробелами.

15 цифр.

17 цифр.

Буквы киррилицы и латиницы, знаки.

#### 2. Поле "Месяц"

Пустое поле.

Заполнение пробелами.

1 цифра.

Число 13.

Буквы киррилицы и латиницы, знаки.

#### 3. Поле "Год"

Пустое поле.

Заполнение пробелами.

1 цифра.

Предыдущий год.

Буквы киррилицы и латиницы, знаки.

#### 4. Поле "Владелец"

Пустое поле.

Заполнение пробелами.

Цифры.

Только имя без фамилии.

Буквы киррилицы, цифры, знаки.

#### 5. Поле "CVC/CVV"

Пустое поле.

Заполнение пробелами.

2 цифры.

4 цифры.

Буквы киррилицы и латиницы, знаки.

## 2. Перечень используемых инструментов с обоснованием выбора

#### 1. IntelliJ IDEA

Интегрированная среда разработки (IDE) от компании JetBrains, предназначенная для разработки
на Java - одном из самых распространненых языков программирования, а также поддерживает множество других
языков программирования, расширений и плагинов.

#### 2. Git

Бесплатная система контроля версий, которая помогает отслеживать историю изменений в файлах с
возможностью загрузки проекта на удаленный репозиторий на GitHub.

#### 3. Gradle

Одна из самых популярных систем для автоматизации сборки приложений и сбора статистики
об использовании программных библиотек.

#### 4. Docker

Самая популярная программа, в основе которой лежит технология контейнеризации,
которая помогает запускать приложения изолированно от операционной системы.

#### 5. JUnit5

Последняя версия фреймворка для автоматического юнит-тестирования Java-приложений.
Он содержит специальные функции и правила, которые позволяют легко писать и запускать тесты,
то есть проверять, что каждый блок кода, или модуль, ответственный за определённую функцию программы,
работает как надо.

#### 6. Selenide

Фреймворк для написания удобных для чтения и обслуживания автоматизированных тестов на Java.

#### 7. Lombok

Библиотека для сокращения кода в классах и расширения функциональности языка Java.

#### 8. JavaFaker

Библиотека для генерации поддельных данных для тестов на Java.
Она предоставляет широкий спектр типов данных, включая имена, адреса, номера телефонов и другие.

#### 9. Allure Report

Инструмент для создания отчётов о тестировании с открытым исходным кодом.
Он помогает визуализировать результаты тестов в удобном и наглядном формате.

## 3. Описание возможных рисков при автоматизации

1. Возникновение сложностей с настройкой и запуском SUT.
1. Возможные сложности при выборе css-селекторов из-за отсутствия тестовых меток.
1. Падение автотестов в случае изменения структуры сайта.

## 4. Интервальная оценка с учётом рисков в часах

1. Планирование автоматизации тестирования - 5 часов
1. Написание тестов - 40 часов
1. Подготовка отчётных документов по итогам автоматизированного тестирования - 14-18 часов
1. Подготовка отчётных документов по итогам автоматизации - 10-12 часов

## 5. План сдачи дипломной работы

1. Написанные тесты - в течении 5 рабочих дней после согласования плана;
1. Отчеты по результатам тестирования - в течение 3 рабочих дней после тестирования;
1. Отчеты по итогам автоматизации — в течение 2 рабочих дней после предыдущего этапа.