#!/usr/bin/env python3
# -*- coding: utf-8 -*-


def fact(n):
    """
		Fonction qui calcule la factorielle de n
	"""
    x=1 # si n = 0 || 1 retourne 1
	
    for i in range( 2, n+1):
	
        x *= i
		
    return x
    
def saisiUtulisateur():
    """
		Fonction qui teste la saisi de l'utulisateur
	"""
	
    error = True
	
    while error:
			
            print("\n Saisissez un nombre (N) à factoriser ")
			
            nombre = input()
			
            try: # On essaye de le convertir en entier
			
                nombre = int(nombre)
				
                if nombre < 0:
                    print("Vous devez saisir un nombre égale ou supérieur à zéro...")
						
                else:
                    error = False
                    return nombre
					
            except ValueError:
                print("Vous n'avez pas saisi un nombre !")


nombre = saisiUtulisateur()

print("La factorielle du nombre saisi est : ", fact(nombre))

if nombre == 0: nombre = 1 #Possibilité de mettre un compteur dans la boucle for
print("Le calcul a demandé", nombre-1, "operations." )