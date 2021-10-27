# springboot-vault-database-secret-integration
springboot custom datasource setup using Harshicorp vault database secret.

Database: MySql
Vault database secret is used with rotation policy.


SETUP DYNAMIC DATABASE SECRET
####################################

NOTE: Create 2 user in which 1 is for root and another for application. Root user will use by vault to rotate application user password.

1. Create sql rotation file with name "rotation.sql".
    
    ALTER USER "{{name}}" WITH PASSWORD '{{password}}';
   
2. Create custom password policy file with name "custom-password.hcl".
    
    length = 20

    rule "charset" {
      charset = "abcdefghijklmnopqrstuvwxyz"
      min-chars = 1
    }

    rule "charset" {
      charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
      min-chars = 1
    }

    rule "charset" {
      charset = "0123456789"
      min-chars = 1
    }

    rule "charset" {
      charset = "!@#$%^&*"
      min-chars = 1
    }
    
3. Enable database

    vault secrets enable database
    
4. Custom Password Policy

   vault write sys/policies/password/custom-password policy=@custom-password.hcl

5. Create mysql database config

    vault write database/config/mysql-vault \
    plugin_name=mysql-database-plugin \
    connection_url="{{username}}:{{password}}@tcp(mysql:3306)/" \
    allowed_roles="mysql-vault-role" \
    username="vaultuser" \
    password="vaultpass" \
    password_policy="custom-password"

6. Create role

    vault write database/static-roles/mysql-vault-role \
    db_name=mysql-vault \
    rotation_statements=@rotation.sql \
    username="appuser" \
    rotation_period=300
    
7. Get mysql password

    vault read database/static-creds/mysql-vault-role

