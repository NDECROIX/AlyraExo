# -*- coding: utf-8 -*-
#!/usr/bin/env python3 
import os

from random import randrange
 
nombrePropose = 0
 
nombreMystere = randrange(1, 100)

# print(nombreMystere)

print("------ JEU DU NOMBRE MYSTERE ------")

# Boucle du jeu  
while nombrePropose != nombreMystere:
     
    #on test le nombre saisi
    error = True
    while error:
        
        print("\nQuel est le nombre ?")
        
        nombrePropose = input()
        
        try: # On essaye de le convertir en entier
            nombrePropose = int(nombrePropose)
            if nombrePropose > 100 or nombrePropose < 1:
                    print("Vous devez saisir un nombre compris entre 1 et 100")
            else:
                error = False
        except ValueError:
            print("Vous n'avez pas saisi un nombre !")

    #condition pour un nombre saisi trop petit
    if nombrePropose < nombreMystere:
        print("C'est trop petit ! ")
        if nombrePropose >= (nombreMystere - 5):
            print("Mais vous êtes très proche ! \n")
        elif nombrePropose >= (nombreMystere - 10):
            print("Mais vous êtes proche ! \n")
	
	#condition pour un nombre saisi trop grand	
    elif nombrePropose > nombreMystere:
        print("C'est trop grand ! ")
        if nombrePropose <= (nombreMystere + 5):
            print("Mais vous êtes très proche ! \n")
        elif nombrePropose <= (nombreMystere + 10):
            print("Mais vous êtes proche ! \n")
    
    else:
        print("\n ######################################################### \n ### FELICITATION, vous avez trouvé le nombre mystere !!! \n #########################################################")

 

		
os.system("pause")
 