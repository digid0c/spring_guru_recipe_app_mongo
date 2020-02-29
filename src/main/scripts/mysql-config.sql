# connect to MySQL and run as root user
## create databases
CREATE DATABASE recipe_dev;
CREATE DATABASE recipe_prod;

## create database service users
CREATE USER 'recipe_dev_user'@'%' IDENTIFIED BY 'dev';
CREATE USER 'recipe_prod_user'@'%' IDENTIFIED BY 'prod';

## users grants
GRANT SELECT ON recipe_dev.* to 'recipe_dev_user'@'%';
GRANT INSERT ON recipe_dev.* to 'recipe_dev_user'@'%';
GRANT DELETE ON recipe_dev.* to 'recipe_dev_user'@'%';
GRANT UPDATE ON recipe_dev.* to 'recipe_dev_user'@'%';

GRANT SELECT ON recipe_prod.* to 'recipe_prod_user'@'%';
GRANT INSERT ON recipe_prod.* to 'recipe_prod_user'@'%';
GRANT DELETE ON recipe_prod.* to 'recipe_prod_user'@'%';
GRANT UPDATE ON recipe_prod.* to 'recipe_prod_user'@'%';