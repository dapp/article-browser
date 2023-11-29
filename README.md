The Athletic Test App

This application implements The Athletic's coding challenge to create a simple article browser

It reads from RESTful HTTP APIs and displays the data

The application is constructed using a tried-and-true MVP architecture, utilizing presenters (instead
of the more common ViewModels) to express business logic. Data is technically bi-directional, as it
is with virtually all architectures, but all possible interactions are eagerly handled in the single
present() function of each screen, bringing the application *closer* to unidirectional data flow, which
in reality is, of course, a lie.

The application components themselves are simply two activities. Since the two views always occupy 
separate screens, there is no need for the more complex fragments.  Also, as of 2023, multiple 
activites can be displayed on a single screen if necessary.

The application code illustrates mostly basic language semantics, with some more advanced features 
such as higher order functions, and includes unit tests that show how to exercise them.  

The app uses Coroutines for asynchronous work management, Hilt for dependency injection, retrofit for
HTTP operations, Moshi for JSON parsing, and JUnit + Mockito for unit testing. 

The application was written entirely from scratch, relying on no existing application as a bootstrap.

Trade Offs:
To save time, I made several sacrifices.  The app's UI is simple, and somewhat inconsistent.  I didn't
unit test all the business logic, and instead simply wrote some tests to illustrate the art of unit
testing.  I used XML in lieu of Compose, since I'm more familiar with XML and can build XML layouts
faster and cleaner currently than I can in Compose.  I also used MVP, which most Android devs never
bother to even learn, even though the entire Android ecosystem is setup to support MVVM.  Again this
is bc MVP is currently fresher in my mind and was faster to implement.

