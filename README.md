# Single Page Spring Boot
This is a simple application based on the Spring Boot framework to serve single page web applications such as web pages created with Angular. Highlighted features:
 * Take care of virtual routes all having to map to an `index.html` file. In other words if a path cannot be found in the  files of your application then the `index.html` is served.
 * Serve multiple localized versions of the same application. This is useful if you use the default localization mechanism of Angular where you compile once for each locale.
 * Serve the right locale of your application based on a cookie and the `Accept` header. The cookie, if present, takes precedence over the `Accept` header.
 * Provide sensible compression defaults for single page applications.
 * Serving multiple virtual hosts (disabled by default).
 
## Table of Contents
  * [Usage](#usage)
  * [Configuration](#configuration)
  * [Building](building)
  * [License](license)

# Usage
How to use singlepage-springboot 

## Layout of your application locales
Essentially you want a directory named like your locale for each of your locales. Each locale directory should then also contain an `index.html` at the very least. Example for locales `en-US` and `de-DE`:
```
dist/
|-- de-DE/
|   |-- index.html
|   |-- style.css
|-- en-US/
    |-- index.html
    |-- style.css
```

## Using with Docker
It's recommended to use singlepage-springboot with Docker. Create a Dockerfile similar to:

```Dockerfile
FROM singlepage-springboot
ENV SPRING_PROFILES_ACTIVE env                             # use the Spring profile "env"
COPY --chown=app:app application-env.properties $APP_HOME/ # copy application properties for Spring profile "env" 
COPY --chown=app:app dist $APP_HOME/static                 # copy application resources
```
* `env` denotes your profile.
* `application-env.properties` contains the Spring Boot properties for your project.
* `dist/en-US` is a directory containing your compiled single page application in the the locale `en-US`.

The Docker container is run by the user `app` with UID `8080` and GID `8080` by default. The application is served via an embedded Tomcat on port `8080`.

## Serving a single locale
Just put your application resources into `en-US` (`$APP_HOME/static/en-US` in the Docker). It doesn't actually matter if that's your actual locale - it will be served as the default locale if there is no match. You can configure a different default locale and then use a matching directory. See [Configuration](#configuration) for more details.

## Locale matching
Singlepage-springboot uses Javas locale [filtering](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Locale.html#filter(java.util.List,java.util.Collection,java.util.Locale.FilteringMode)) with the [extended filter](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Locale.FilteringMode.html#EXTENDED_FILTERING) to match locales. This means a locale like `en` will match both `en-US` and `en-GB`. If more than one locale is requested through an `Accept` header as priority list, then the best match based on those priorities is chosen.

# Configuration
In addition to [Spring Boot configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html) values singlepage-springboot offers some additional configuration properties.

## Configuring more locales
You can specify more locales by providing the other compiled versions of your single page application in directories that are named just like the locale of that particular version. The locales that your application has available should be configured via the `singlepage-springboot.locale.available` setting in your `application-env.properties` file.

## Configuring the default locale
The default locale is served whenever there is no match between what was requested by the browser/client and what is made available by the application. By default singlepage-springboot uses `en-US` as the default locale. This can be changed by configuring `singlepage-springboot.locale.default`. 

## Configuring the cookie
You can configure the name of the cookie that - if present - overrides the `Accept` header by configuring `singlepage-springboot.locale.cookie`. The default is `Localization`.

## Configuring the file root
The path where your directory structure with the locales of your application lie can also be configured via `singlepage-springboot.static-file-root`. The default is `/home/app/static`.

## Configuring the index file
If a path is not found in the application resources of a requested locale then the index file is returned. This is by default `index.html`, but can be overridden via `singlepage-springboot.index-file-name`. 

## Configuration of virtual hosts
The virtual host feature is disabled by default. The feature can be turned on by setting `singlepage-springboot.virtual-host.enabled=true`. This introduces level of virtual host directories between the file root and each localization:

```
dist/
|-- de-DE/
|   |-- index.html
|   |-- style.css
|-- en-US/
|   |-- index.html
|   |-- style.css
|-- virtual-host-1
    |-- de-DE/
    |   |-- index.html
    |   |-- style.css
    |-- en-US/
        |-- index.html
        |-- style.css
```

Note that if the `Host` header is not present in the request then the default behavior -- i.e. directory structure -- is used.

# Building
You can build with Docker or just with Maven.

## Build via Docker
Run `docker build -t singlepage-springboot .`. This will build via Maven inside of Docker and then copy the build result to the final image based on openjdk.

## Build via Maven
Run: `mvn clean package` That's it!

# License
Springboot-singlepage is licensed under the [MIT](https://opensource.org/licenses/MIT) license. See `LICENSE` file.
