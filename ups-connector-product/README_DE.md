# UPS-Modul

Der Axon Ivy [UPS-Anschluss](https://developer.ups.com/catalog) ermöglicht es
Benutzern, USV-Dienste nahtlos in jeden Geschäftsprozess zu integrieren.

Dieser Konnektor:

- Ergänzt Sie mit vollem Zugriff auf den OpenAPI UPS API-Katalog.
- Bietet Funktionen für den täglichen Gebrauch, wie z. B. Sendungsverfolgung,
  Ermittlung von Versandkosten und Adressvalidierung.

Sehen Sie sich den [API-Katalog](https://developer.ups.com/catalog) an, um
herauszufinden, welche APIs am besten zu Ihren geschäftlichen Anforderungen
passen.

## Demo
### Tracking-Informationen abrufen
Dieser Dienst wird zum Abrufen von Paketinformationen verwendet.
1. Melden Sie sich beim Axon Ivy Portal an.
2. Klicken Sie auf der Seite „Prozessliste“ auf „ **“ und dann auf „Tracking
   abrufen“.**

![Alt-Text](images/image-1.png)

3. Geben Sie Ihre Sendungsverfolgungsnummer ein.

![Alt-Text](images/image-2.png)

4. Klicken Sie auf „ **“ Klicken Sie auf die Schaltfläche „** “, um alle
   Informationen zum Paket anzuzeigen.

![Alt-Text](images/image-3.png)

### Erstellung der Abholung
Dieser Service wird zur Planung von Abholungen genutzt.
1. Melden Sie sich beim Axon Ivy Portal an.
2. Klicken Sie auf der Seite „Prozessliste“ auf „ **-Erstellung abholen“.**

![Alt-Text](images/image-5.png)

3. Füllen Sie die erforderlichen Felder aus.

![Alt-Text](images/image-4.png)

4. Klicken Sie auf „ **“ Klicken Sie auf die Schaltfläche „** “, um Abholungen
   zu planen

![Alt-Text](images/image-12.png)

### Abholung stornieren
Dieser Service wird verwendet, um zuvor geplante Abholungen zu stornieren.
1. Melden Sie sich beim Axon Ivy Portal an.
2. Klicken Sie auf der Seite „Prozessliste“ auf „ **-Erstellung abholen“.**

![Alt-Text](images/image-8.png)

3. Füllen Sie die erforderlichen Felder aus.

![Alt-Text](images/image-7.png)

4. Klicken Sie auf „ **“ (Abbrechen) und dann auf „** “ (Abbrechen), um den
   Vorgang abzuschließen.

### Adressvalidierung
Dieser Dienst wird verwendet, um Adressen anhand der Datenbank des United States
Postal Service mit gültigen Adressen in den USA zu überprüfen.
1. Melden Sie sich beim Axon Ivy Portal an.
2. Klicken Sie auf der Seite „Prozessliste“ auf „ **-Adresse validieren“.**

![Alt-Text](images/image-9.png)

3. Geben Sie die Adressdaten ein.

![Alt-Text](images/image-10.png)

4. Klicken Sie auf „ **“ (Validieren) und dann auf die Schaltfläche „** “
   (Validieren).

## Einrichtung
1. Gehen Sie zu https://developer.ups.com, melden Sie sich mit Ihrem
   Benutzerkonto an oder erstellen Sie ein neues UPS-Konto.
2. Erstellen Sie eine Anwendung auf UPS.
3. **Sobald Ihre Anwendung erstellt ist, werden die Client-ID** und der
   Client-Secret** von **generiert und können verwendet werden, um einen
   Zugriffstoken für die Autorisierung Ihrer API-Anfragen zu erhalten.
4. Konfigurieren Sie die folgenden Variablen in Ihrem Projekt:
```
@variables.yaml@
```
> [!HINWEIS] Der variable Pfad `ups-connector` wird ab Version 13.1 in
> `upsConnector` umbenannt.
