# MCItaliaRewards
_Dai dei premi ai giocatori del tuo server chiedendo in cambio solamente di sostenere il tuo server votandolo sulla lista di minecraft-italia!_
<p align="center">
        <a href="https://www.coralmc.it/" target="_blank"><img src="https://www.coralmc.it/img/dev.png" width="230px"></a>
</p>

## REQUISITI

- MongoDB
- Firewall che consente le connessioni verso minecraft-italia

## CONFIGURAZIONE SISTEMA DI VOTO

- Dovrai inserire il nome del tuo server che utilizza minecraft-italia, quello che trovi in questo link https://minecraft-italia.it/server/NOMESERVER
- Dovrai inserire l'API KEY del tuo server su minecraft-italia
>server-info:                                                                                                                                                                            
name: "writeServerNameHere"                                                                                                                                                         
key: "addServerKeyHere"                                                                                                                                                             
last-update: "01/01/1965" #don't touch this unless you don't known what are you doing

La sezione "last-update" non dovrà essere mai toccata manualmente in quanto viene aggiornata automaticamente ogni volta che viene fatto il check dei voti del giorno precedente, cancellando tale data o modificandola rischiate di dare più premi riscattabili ai vostri giocatori nonostante magari abbiano votato solo 1 volta.

- Dovrai inserire l'IP, la porta e il nome del database per MongoDB
>mongodb:                                                                                                                                                                               
>  authentication: false                                                                                                                                                                
>  address: "writeYourMongodbAddressHere"                                                                                                                                               
>  port: 27017                                                                                                                                                                          
>  database: "writeYourDatabaseNameHere"                                                                                                                                                
>  collection: "writeYourCollectionNameHere"                                                                                                                                            
>  username: "writeYourMongodbUsernameHereIfAuthenticationEnabled"                                                                                                                              
>  password: "writeYourPasswordHereIfAuthenticationEnabled"

Avrai la possibilità di scegliere il nome della collection del database, in questo modo potrai dividere i voti per modalità, quindi una collection per ogni server, oppure dare lo stesso nome della collection in tutte le modalità, in modo da avere un solo premio riscattabile in tutto il network.

Inoltre l'autenticazione di MongoDB è facoltativa, per attivarla o disattivarla cambia "authentication" con true o false. 

## CONFIGURAZIONE GUI
## _Sezione GUI_
- Dovrai specificare il numero di righe della GUI, massimo 6 (54 slot totali).
- Dovrai specificare il nome della GUI.
>gui:                                                                                                                                                                                   
>  name: "&cREWARD GUI"                                                                                                                                                                 
>  rows: 3 #max 6

## _Sezione premi_
- Dovrai inserire i dettagli dell'item che apparirà in GUI, quindi materiale, quantità, data, nome e descrizione.
- Dovrai inserire il comando da eseguire, è disponibile la variabile "%p" che verrà sostituita con il nome del giocatore che clicca.
- Dovrai specificare se il comando viene eseguito da console o dal giocatore stesso.

Ecco alcuni esempi:

>  rewards:                                                                                                                                                                             
>    diamond:                                                                                                                                                                           
>      material: DIAMOND                                                                                                                                                                        
>      data: 0                                                                                                                                                                                          
>      amount: 1                                                                                                                                                                                                                        
>      name: "§bDiamond"                                                                                                                                                                
>      lore:                                                                                                                                                                            
>        - "§7Click here if you want"                                                                                                                                                           
>        - "&7to get a &bdiamond &7as"                                                                                                                                                                                                          
>        - "&7a reward."                                                                                                                                                                
>      command: "give %p diamond 1"                                                                                                                                                                                     
>      run-as-console: true                                                                                                                                                             
>      slot: 12                                                                                                                                                                                 
>    msg:                                                                                                                                                                                                       
>      material: PAPER                                                                                                                                                                                          
>      data: 0                                                                                                                                                                                                                  
>      amount: 1                                                                                                                                                                        
>      name: "§fMsg"                                                                                                                                                                                                            
>      lore:                                                                                                                                                                            
>        - "§7Click here if you want"                                                                                                                                                                                   
>        - "&7to write a &fmessage &7as"                                                                                                                                                                        
>        - "&7a reward."                                                                                                                                                                                        
>      command: "tell @a Hello my friend!"                                                                                                                                                                              
>      run-as-console: false                                                                                                                                                                                    
>      slot: 14

## _Sezione estetica_
- Dovrai semplicemente inserire i dettagli dell'item come per i premi.

Per quanto riguarda la sezione estetica, gli slot di questi oggetti sono messi come una lista, in modo da poter essere inseriti in più slot senza la necessità di copiarli e incollarli decine di volte all'interno del config.

Ecco alcuni esempi:
> esthetics:                                                                                                                                                                                                                                            
>    glass:                                                                                                                                                                             
>      material: STAINED_GLASS_PANE                                                                                                                                                                                                     
>      data: 7                                                                                                                                                                                                                  
>      amount: 1                                                                                                                                                                                                
>      name: "&7Vote us on mc-italia!"                                                                                                                                                                                                                  
>      lore:                                                                                                                                                                                            
>        - "&fhttp://vote.yourserver.net"                                                                                                                                               
>      slots:                                                                                                                                                                           
>        - 0                                                                                                                                                                                    
>        - 1                                                                                                                                                                                  
>        - 2                                                                                                                                                                                  
>        - 3                                                                                                                                                                                  
>        - 4                                                                                                                                                                                  
>        - 5                                                                                                                                                                                  
>        - 6                                                                                                                                                                                  
>        - 7                                                                                                                                                                                  
>        - 8                                                                                                                                                                                  
>    vine:                                                                                                                                                                                  
>      material: VINE                                                                                                                                                                                  
>      data: 0                                                                                                                                                                                  
>      amount: 1                                                                                                                                                                                  
>      name: "&7Join our discord!"                                                                                                                                                                                  
>      lore:                                                                                                                                                                                  
>        - "&fhttp://discord.yourserver.net"                                                                                                                                                                                  
>      slots:                                                                                                                                                                                  
>        - 18                                                                                                                                                                                  
>        - 19                                                                                                                                                                                  
>        - 20                                                                                                                                                                                  
>        - 21                                                                                                                                                                                  
>        - 22                                                                                                                                                                                  
>        - 23                                                                                                                                                                                  
>        - 24                                                                                                                                                                                  
>        - 25                                                                                                                                                                                  
>        - 26
        
## CONCLUSIONE
Se trovate qualche bug sentitevi liberi di aprire un issue qui su GitHub.
Ogni messaggio è configurabile.
## TODO:
- comando per reloddare il plugin
- possibilità di modificare dal game la quantità dei voti riscattabili per singolo giocatore
