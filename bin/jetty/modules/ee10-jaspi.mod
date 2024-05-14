# DO NOT EDIT THIS FILE - See: https://eclipse.dev/jetty/documentation/

[description]
Enables JASPI authentication for deployed web applications.

[environment]
ee10

[tags]
security

[depend]
ee10-security
auth-config-factory

[ini]
ee10.jakarta.authentication.api.version?=3.0.0

[lib]
lib/jetty-ee10-jaspi-${jetty.version}.jar
lib/ee10-jaspi/jakarta.authentication-api-${ee10.jakarta.authentication.api.version}.jar

[xml]
etc/jaspi/jetty-ee10-jaspi-authmoduleconfig.xml

[files]
basehome:etc/jaspi/jetty-ee10-jaspi-authmoduleconfig.xml|etc/jaspi/jetty-ee10-jaspi-authmoduleconfig.xml

