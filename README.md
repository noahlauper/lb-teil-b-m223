# Abschlussprojekt Coworking Space

Coworking Space ist ein Coworking Mietsystem mit Spring Boot. Mit dieser Applikation können Coworking Spaces gemietet werden. 
Es gibt 3 verschiedene Rollen: Administrator, Mitglied und Besucher. Jede dieser Rollen haben bestimmte Rechte. Um Mitglied zu werden muss man sich einfach Registrieren.

## Erste Schritte

1. Erstelle eine Kopie (fork) von diesem Projekt.
1. Stelle sicher, dass Docker installiert ist und läuft.
1. Stelle sicher, dass Visual Studio Code und die Erweiterung Remote Container installiert ist.
1. Klone (clone) das Projekt lokal, um damit arbeiten zu können.
1. Öffne das Projekt mit Visual Studio Code.
1. Öffne das Projekt im Entwicklungscontainer.
1. Starte das Projekt mit dem Befehl `./mvnw spring-boot:run.`
1. Probiere die Client-Applikation unter http://localhost:8080 aus.
1. Schaue die API unter http://localhost:8080/swagger-ui/index.html an und gebe im Suchfeld : ` /v3/api-docs` ein.

## Datenbank

Die Daten werden in einer PostgreSQL-Datenbank gespeichert. In der Entwicklungsumgebung wird diese in der [docker-compose-yml](./.devcontainer/docker-compose.yml) konfiguriert.

### Datenbankadministration

Über http://localhost:5050 ist PgAdmin4 erreichbar. Damit lässt sich die Datenbank komfortabel verwalten. Der Benutzername lautet `zli@example.com` und das Passwort `zli*123`. Die Verbindung zur PostgreSQL-Datenbank muss zuerst mit folgenden Daten konfiguriert werden:
- Host name/address: `db`
- Port: `5432`
- Maintenance database: `postgres`
- Username: `postgres`
- Password: `postgres`

## Testdaten
Testdaten werden automatisch beim starten des Projektes erstellt.
