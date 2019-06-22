# Optimize mysql

## TABLE DESIGN GENERALITIES:

- How Will your Table Primarily be Used?
	- If you will be updating certain pieces of data often, it is often best to have those in their own table.
- What Kind of Data Types are Required?
	- if there are a limited number of valid entries for a specific field that takes string values, you could use the "enum" type instead of "varchar".
- Which Columns Will You be Querying?
	- Indexing columns that you expect to use for searching helps immensely.
	- Use Explain to Find Points to Index in Queries

## OPTIMIZING QUERIES FOR SPEED:

- Certain operations are more computationally intensive than others. There are often multiple ways of getting the same result, some of which will avoid costly operations.
- If your queries use "or" comparisons, and the two components parts are testing different fields, your query can be longer than necessary.
