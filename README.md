# Мой дом

Приложение для получения данных с удаленных веб-камер, демонстрирующее работу с современными технологиями. Также позволяет сохранять веб-камеры в локальную базу данных и редактировать их.

Этот проект разработан в демонстрационных целях и не предназначен для реального использования. Он служит примером моих текущих навыков и подходов к разработке Android-приложений.

## Особенности

- _REST API с Ktor_: Приложение взаимодействует с внешним сервером через RESTful API, реализованный с использованием библиотеки Ktor
- _Локальная база данных RealmDB_: Для удобства пользователей приложение сохраняет избранные камеры в локальной базе данных RealmDB
- _Интерфейс Jetpack Compose_: Проект использует современную библиотеку Jetpack Compose для построения динамичного и интуитивно понятного пользовательского интерфейса, обеспечивает высокую скорость работы приложения и отличный пользовательский опыт
- _Загрузка изображений с использованием Coil_: Для получения превью с веб-камер приложение использует библиотеку Coil, что обеспечивает быструю загрузку и кеширование изображений
- _Многопоточность и асинхронность_: Проект активно использует корутины для обеспечения отзывчивости интерфейса и эффективной работы с сетью
- _Архитектурные принципы_: При разработке приложения я придерживался принципов SOLID и Clean Architecture для обеспечения чистоты и структурированности кода

## Скриншоты

![](https://github.com/anikolsky/MyHomeApp/blob/main/Screenshot1.png)&nbsp;&nbsp;&nbsp;&nbsp;![](https://github.com/anikolsky/MyHomeApp/blob/main/Screenshot2.png)

---

# My home

A webcam monitoring application demonstrating how to work with the REST API using the Ktor client. Allows you to save favorite cameras to a local RealmDB database and edit them. The application is also written using Jetpack Compose and Coil to fetch webcam previews.
