### psql commands
```bash
# List databases
\l

# list schemas
\dn

# list relations, including tables and views
\d

# list tables of default `public` schem
\dt 

# add schema to search
SET search_path TO <schema nam>, public;
SHOW search_path; 


# switch display mode
\x [on | off | auto]
```


### sql queries
```sql
# check DB size
SELECT pg_size_pretty( pg_database_size('<db name>') );

```

### SQL Join Venn diagram
![Image of Yaktocat](https://i.stack.imgur.com/UI25E.jpg)

