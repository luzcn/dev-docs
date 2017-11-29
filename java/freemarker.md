### Use freemarker template 
We can use freemarker template to create some templates for configuration, emails and web pages.


### set up configuration
There's a point to be made of using 

setClassForTemplateLoading(this.getClass(), "/"); 

vs 

setClassLoaderForTemplateLoading(this.getClass().getClassLoader(), "");

Unlike here, I used the classLoader variant elsewhere in the code for loading resources, however, they are functionally equivalent
https://freemarker.apache.org/docs/api/freemarker/template/Configuration.html#setClassLoaderForTemplateLoading-java.lang.ClassLoader-java.lang.String-

https://stackoverflow.com/questions/6608795/what-is-the-difference-between-class-getresource-and-classloader-getresource

"In other words, they do the same thing if the "path" begins with a "/", but if not, then in the latter case, the path will be relative to the class's package, whereas the classloader one will be absolute.

In short, the first fetches path/to/my/properties and the second fetches package/of/myclass/path/to/my/properties."
