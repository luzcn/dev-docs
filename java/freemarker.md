### Use freemarker template 
We can use freemarker template to create some templates for configuration, emails and web pages.
```java
public class test {
    public String getProductDetail() {
        // Create your Freemarker Configuration instance, and specify version
        Configuration cfg = new Configuration(Configuration.getVersion());

        // Specify the source where the template files come from.
        // Here, it tells the load path is src/main/resources
        // alternatively, we can use cfg.setClassLoaderForTemplateLoading(this.getClass().getClassLoader(), "")
        cfg.setClassForTemplateLoading(this.getClass(), "/");

        // Set the preferred charset template files are stored in.
        cfg.setDefaultEncoding("UTF-8");


        // get template
        Template template = cfg.getTemplate("product.ftl");
        Product productDataModel = new Product("url", "name");

        Writer out = new StringWriter();

        // bind the data motel and templet
        template.process(productDataModel, out);

        return out.toString();
    }

    @Value
    public static class Product {
        String url;
        String name;

        public Product(String url, String name) {
            this.url = url;
            this.name = name;
        }
    }
}
```
And the template should be:

```html
<html>
<head>
  <title>Welcome!</title>
</head>
<body>
  <p>Our latest product:
  <a href="${url}">${name}</a>!
</body>
</html>
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
