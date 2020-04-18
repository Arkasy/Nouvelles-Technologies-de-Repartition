# Nouvelles-Technologies-de-Repartition
TP - Application bancaire mettant en œuvre l’architecture Wildfly et Tomcat avec Spring

## Application développé en Spring

### Interface web (Moteur de template : thymeleaf)

#### Inscription
Cette application permet à un utilisateur de se créer un compte bancaire à l'adresse suivante :
- http://localhost:8080/banque/register
Après son inscription, un identifiant unique est affiché. Il permettra de se connecter.

#### Connexion
Un utilisateur doit forcément se connecter pour accéder aux différentes fonctionnalités. La connexion s'effectue à partir de l'identifiant unique qui s'affiche après l'inscription de l'utilisateur, et son mot de passe.
- http://localhost:8080/banque/login

#### Utilisateur connecté
Lorsque l'utilisateur est connecté, il pourra voir le solde de ses comptes : 
- http://localhost:8080/banque/

#### Consulter ses opérations d'un compte
L'utilisateur peut consulter la liste des opérations qui ont eu lieu sur un de ses comptes. Il a juste à cliquer sur "voir opérations" sur la ligne du compte correspondant.


### API en JSON
Pour accéder à l'API en JSON, il n'y pas besoin d'identifiant ou de token (nous ne devions pas nous occuper de l'aspect sécurité). Celle ci doit nous permettre de récupérer différentes données qui ont été précisées dans le sujet. On doit donc utiliser une méthode GET

#### GET liste des utilisateurs
- http://localhost:8080/banque/api/customer

#### GET détails d'un utilisateur
- http://localhost:8080/banque/api/customer/{uniqueID}

#### GET listes des opérations d'un compte bancaire
- http://localhost:8080/banque/api/customer/{uniqueID}/account/{accountID}/operations

### SOAP 
Un web service a aussi été développé en SOAP. Il permet d'ajouter des transactions a un compte bancaire, que ce soit des débits, ou des crédits.

#### POST ajouter une opération à un compte bancaire 

le webservice est accessible à cet adresse : 
- http://localhost:8080/banque/ws 

Il faut préciser que le format envoyé (Content-Type) est du text/xml)
- http://localhost:8080/banque/ws

Exemple de format à envoyer : 
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
- **gs;name** est le nom de la transaction.
- **gs:idAccount** le compte bancaire à créditer ou débiter.
- **gs:amount** le montant de l'opération. Préciser le '-' si c'est un débit.

## Service de test
Un service de test a été développé dans le dossier service_test. Il contient 2 mains, qui permet d'essayer sur les 2 différentes API. 

/!\ les liens correspondant à l'accès au application est en écrit en dur dans le code, comme les identifiants de compte client et de compte bancaire. Il faut donc les modifier pour effectuer les tests.

Un main permet de tester les services JSON : main_JSON.

Résultat du test : 
![alt text](https://zupimages.net/up/20/16/pnja.jpg)

Pour tester le service SOAP : main_SOAP 
Résultat du test : 
![alt text](https://zupimages.net/up/20/16/wp49.png)

## Projet site commerce
Le site de commerce est un site de location où on peut s'inscrire avec son numéro de compte en banque, ajouter des biens à la location et louer des biens.

Ces différentes actions sont présentées dans la vidéo Youtube.

## Lien vidéo youtube
[Cliquez ici](https://www.youtube.com/watch?v=NiHrwfvTaCU)

