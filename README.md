# Nouvelles-Technologies-de-Repartition
TP - Application bancaire mettant en œuvre l’architecture Wildfly et Tomcat avec Spring

## Application développé en Spring

### Interface web (Moteur de template : thymeleaf)

#### Inscription
Cette application permet à un utilisateur de se créer un compte bancaire à l'adresse suivante :
- http://localhost:8080/register
Après son inscription, un identifiant unique est affiché. Il permettra de se connecter.

#### Connexion
Un utilisateur doit forcément se connecter pour accéder aux différentes fonctionnalités. La connexion s'effectue à partir de l'identifiant unique qui s'affiche après l'inscription de l'utilisateur, et son mot de passe.
- http://localhost:8080/login

#### Utilisateur connecté
Lorsque l'utilisateur est connecté, il pourra voir le solde de ses comptes : 
- http://localhost:8080/

#### Consulter ses opérations d'un compte
L'utilisateur peut consulter la liste des opérations qui ont eu lieu sur un de ses comptes. Il a juste à cliquer sur "voir opérations" sur la ligne du compte correspondant.


### API en JSON
Pour accéder à l'API en JSON, il n'y pas besoin d'identifiant ou de token (nous ne devions pas nous occuper de l'aspect sécurité). Celle ci doit nous permettre de récupérer différentes données qui ont été précisées dans le sujet. On doit donc utiliser une méthode GET

#### GET liste des utilisateurs
- http://localhost:8080/api/customer

#### GET détails d'un utilisateur
- http://localhost:8080/api/customer/{uniqueID}

#### GET listes des opérations d'un compte bancaire
- http://localhost:8080/api/customer/{uniqueID}/account/{accountID}/operations

### SOAP 
Un web service a aussi été développé en SOAP. Il permet d'ajouter des transactions a un compte bancaire, que ce soit des débits, ou des crédits.

#### POST ajouter une opération à un compte bancaire 

- Code à envoyer en XML
```
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:gs="http://istv.com/banque/operations_webservice">
    <soapenv:Header/>
    <soapenv:Body>
        <gs:AddOperationRequest>
            <gs:name>test api</gs:name>
            <gs:idAccount>1</gs:idAccount>
            <gs:amount>-150.00</gs:amount>
        </gs:AddOperationRequest>
    </soapenv:Body>
</soapenv:Envelope>
```


