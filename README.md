# Task Manager

Простое Android-приложение для управления задачами.  
Позволяет создавать, редактировать, удалять задачи, отмечать их выполненными и просматривать статистику.

## 📱 Основные возможности
- Добавление новых задач с категорией и датой
- Фильтрация задач по категориям (Все, Личное, Работа, Учёба)
- Отметка задач как выполненных
- Просмотр статистики завершённых и незавершённых задач
- Автоматическое обновление списка благодаря `LiveData`
- Хранение данных в базе Room (SQLite)
- Удобный интерфейс на основе RecyclerView и ViewBinding

## 🛠 Технологии
- Java
- Android SDK (minSdk 24, targetSdk 35)
- Room Database
- ViewModel + LiveData
- RecyclerView
- Navigation Component
- Material Design

---

## Скриншоты

### Главный экран
![Главный экран](app/screenshots/Screenshot_task.jpg)

### Фильтрация
![Фильтрация по категории "Личное"](app/screenshots/Screenshot_filter.jpg)

### Календарь
![Календарь](app/screenshots/Screenshot_calender.jpg)

### Статистика
![Статистика](app/screenshots/Screenshot_stat.jpg)

### Завершенные задачи
![Статистика по завершенным задачам](app/screenshots/Screenshot_complete.jpg)

---

## 📂 Структура проекта
```bash
app/
├── java/com/example/taskmanager/
│   ├── App.java                  # Application класс, инициализация Room
│   ├── data/                     # Room (AppDatabase, TaskDao)
│   ├── model/Task.java           # Модель задачи (Entity)
│   ├── screens/
│   │   ├── main/                 # MainActivity, ViewModel, Adapter
│   │   └── details/              # TaskDetailsActivity (создание/редактирование задачи)
│   └── ui/                       # UI-компоненты (фильтры, календарь, статистика)
├── res/layout/                   # XML-макеты экранов
├── res/navigation/               # Граф навигации
├── res/menu/                     # Меню нижней навигации
└── build.gradle                  # Конфигурация проекта
