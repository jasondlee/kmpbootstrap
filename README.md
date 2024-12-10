KMP Bootstrap is project that aims to provide a Kotlin Multiplatform example that runs out of the box.
This demo targets (currently?) Android and iOS. It offers support for :

* Kotlin Multiplatform
* Decompose
* Room database access
* DataStore preferences
* Ktor HTTP Client

Currently, the application is extremely basic, offering 3 components:

* Root - this is the entry point of the application, and visually wraps the current component
* ClickMe - this a Decompose-ified version of the KMP Wizard demo. It offers two buttons, "Click me!"
and "Show users".
** The "Click me!" button initiates an animation, showing the KMP logo and a greeting specific to
the running platform.
** The "Show users" buttons navigates to the `UserListComponent`
* UserList - this is a simple component that demonstrates a way to load data from a database using
the Room library and displaying the results on the page/screen.

The application is set up to provide all facilities across both supported targets, giving examples
of the `actual`/`expect` mechanism for providing platform-specific resources. Facilities provided are

* `AppContext` for capturing platform-specific context for the application
* `AppLogger` for cross-platform logging
* `HttpClientFactory` for creating a platform-specific factory for Ktor's `HttpClient`
* `getDatabaseBuilder` for acquiring platform-specific `RoomDatabase.Builder`.
* `createPlatformDataStore` for platform-specific `DataStore` instances.

TODO:

* Koin
* Testing

*NOTE*: This application template is a work-in-progress. My experience in writing Android application
dates back to the days of Java and XML views. As I update to the current state of the art and move
toward a cross-platform solution, I'm having to relearn (and unlearn!) a lot of things. This application
is a product of that effort. As it evolves, it will represent both my understanding of how to build
modern mobile applications, as well as how I am actually doing so. It is not meant to be a collection
of best practices or even a suggestion that this is the best way to build an application.

As I learn, this will change, so hold what you learn here lightly. If you find it helpful, then please
use it in good health, as they say. If there's something you think is wrong or you'd like to see
added/changed, etc., please feel free to file issues or pull requests. Regardless, I hope you find this
helpful in some way.

jason
