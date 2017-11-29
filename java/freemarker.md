### Use freemarker template 
We can use freemarker template to create some templates for configuration, emails and web pages.


### Create a configuration instance
```java
// Create your Configuration instance, and specify if up to what FreeMarker
// version (here 2.3.27) do you want to apply the fixes that are not 100%
// backward-compatible. See the Configuration JavaDoc for details.
Configuration cfg = new Configuration(Configuration.getVersion());

// Specify the source where the template files come from.
// Here, it tells the load path is src/main/resources
// alternatively, we can use cfg.setClassLoaderForTemplateLoading(this.getClass().getClassLoader(), "")
cfg.setClassForTemplateLoading(this.getClass(), "/");

// Set the preferred charset template files are stored in.
cfg.setDefaultEncoding("UTF-8");

// Sets how errors will appear.
// During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

// Don't log exceptions inside FreeMarker that it will thrown at you anyway:
cfg.setLogTemplateExceptions(false);

// Wrap unchecked exceptions thrown during template processing into TemplateException-s.
cfg.setWrapUncheckedExceptions(true);

```

### Create the Data Model
```
@Value
public class Product {
     String url;
     String name;
     
     public Product(String url, String name){
        
     }
     
}

```

### some additional notes
There's a point to be made of using 

setClassForTemplateLoading(this.getClass(), "/"); 

vs 

setClassLoaderForTemplateLoading(this.getClass().getClassLoader(), "");

Unlike here, I used the classLoader variant elsewhere in the code for loading resources, however, they are functionally equivalent
https://freemarker.apache.org/docs/api/freemarker/template/Configuration.html#setClassLoaderForTemplateLoading-java.lang.ClassLoader-java.lang.String-

https://stackoverflow.com/questions/6608795/what-is-the-difference-between-class-getresource-and-classloader-getresource

"In other words, they do the same thing if the "path" begins with a "/", but if not, then in the latter case, the path will be relative to the class's package, whereas the classloader one will be absolute.

In short, the first fetches path/to/my/properties and the second fetches package/of/myclass/path/to/my/properties."
