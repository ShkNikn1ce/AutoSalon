# 🚗 Система управления автосалоном

REST API для управления автосалоном, разработанный на **Java + Spring Boot**.

Проект реализует полный цикл покупки автомобиля:
- управление пользователями;
- управление автомобилями;
- управление брендами;
- оформление заявок на покупку;
- назначение менеджеров;
- одобрение/отклонение заявок;
- оформление продажи автомобиля.

---

# 📌 Возможности

## Пользователи

- регистрация пользователя;
- обновление данных;
- удаление пользователя;
- поиск по email;
- получение списка пользователей.

### Роли

- CUSTOMER
- MANAGER

---

## Бренды

- создание бренда;
- изменение названия;
- удаление;
- поиск по названию;
- получение списка брендов.

---

## Автомобили

- добавление автомобиля;
- редактирование информации;
- удаление автомобиля;
- поиск по VIN;
- поиск по ID;
- получение списка автомобилей.

### Статусы автомобиля

- AVAILABLE
- RESERVED
- SOLD

---

## Заявки на покупку

Клиент может оставить заявку на покупку автомобиля.

Менеджер:

- назначается на заявку;
- рассматривает заявку;
- одобряет;
- отклоняет.

### Статусы заявки

- NEW
- IN_PROGRESS
- APPROVED
- REJECTED
- COMPLETED

---

## Продажи

После одобрения заявки создается продажа.

Во время оформления продажи автоматически:

- автомобиль получает статус SOLD;
- заявка получает статус COMPLETED.

Можно получить:

- список продаж клиента;
- список продаж менеджера.

---

# 🛠 Используемые технологии

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Lombok
- PostgerSQL
- Maven

---

# 📂 Структура проекта

```
src
│
├── controller
│
├── service
│
├── repository
│
├── entity
│
├── dto
│   ├── request
│   └── response
│
├── mapper
│
├── enums
│
└── config
```

---

# 🏗 Архитектура

Проект построен по классической многослойной архитектуре.

```
Controller
      │
      ▼
Service
      │
      ▼
Repository
      │
      ▼
Database
```

Используются DTO для передачи данных между клиентом и сервером.

```
HTTP Request
      │
      ▼
Request DTO
      │
      ▼
Service
      │
      ▼
Entity
      │
      ▼
Database
      │
      ▼
Entity
      │
      ▼
Mapper
      │
      ▼
Response DTO
      │
      ▼
HTTP Response
```

---

# 📦 Основные сущности

## User

- id
- firstName
- lastName
- email
- phone
- password
- role

---

## Brand

- id
- name

---

## Car

- id
- brand
- model
- vin
- year
- mileage
- price
- status

---

## PurchaseRequest

- customer
- manager
- car
- comment
- rejectReason
- createdAt
- status

---

## Sale

- customer
- manager
- car
- finalPrice
- saleDate

---

# 📌 Бизнес-логика

### Создание заявки

Перед созданием выполняются проверки:

- пользователь существует;
- пользователь является CUSTOMER;
- автомобиль существует;
- автомобиль AVAILABLE;
- отсутствуют активные заявки;
- комментарий заполнен.

---

### Назначение менеджера

Возможные условия:

- заявка должна быть NEW;
- пользователь должен иметь роль MANAGER.

После назначения:

```
   NEW
    ↓
IN_PROGRESS
```

---

### Одобрение заявки

Возможные условия:

- статус IN_PROGRESS;
- менеджер назначен.

После одобрения:

```
   IN_PROGRESS
        ↓
   APPROVED
```

---

### Отклонение заявки

После отклонения:

```
   IN_PROGRESS
        ↓
   REJECTED
```

Сохраняется причина отказа.

---

### Продажа автомобиля

Перед оформлением продажи проверяется:

- заявка APPROVED;
- автомобиль AVAILABLE;
- менеджер существует;
- клиент существует.

После оформления:

```
Автомобиль

   AVAILABLE
      ↓
   SOLD
```

```
Заявка

   APPROVED
      ↓
   COMPLETED
```

---

# ⚙ Используемые паттерны

- DTO
- Mapper
- Repository
- Service Layer
- Dependency Injection
- Transaction (@Transactional)

---

# 📚 REST API

## Users

```
POST    /users
GET     /users
GET     /users/{id}
PUT     /users/{id}
DELETE  /users/{id}
```

---

## Brands

```
POST    /brands
GET     /brands
PUT     /brands/{id}
DELETE  /brands/{id}
```

---

## Cars

```
POST    /cars
GET     /cars
GET     /cars/{id}
PUT     /cars/{id}
DELETE  /cars/{vin}
```

---

## Purchase Requests

```
POST    /purchaseRequests

GET     /purchaseRequests?status=NEW

GET     /purchaseRequests/customer/{customerId}

GET     /purchaseRequests/manager/{managerId}

PUT     /purchaseRequests/{requestId}/assign/{managerId}

PUT     /purchaseRequests/{requestId}/approve

PUT     /purchaseRequests/{requestId}/reject
```

---

## Sales

```
POST    /sales

GET     /sales/customer/{customerId}

GET     /sales/manager/{managerId}
```

---

# 🚀 Возможности для развития

- Spring Security + JWT
- Авторизация по ролям
- Swagger / OpenAPI
- Docker
- Unit-тесты (JUnit + Mockito)
- Интеграционные тесты
- Global Exception Handler
- Pagination
- Логирование (SLF4J)
- MapStruct вместо ручных Mapper
- CI/CD (GitHub Actions)

---

# 👨‍💻 Автор

**Никита Шкодин**

Pet-проект, разработанный для изучения Spring Boot, REST API и многослойной архитектуры.
